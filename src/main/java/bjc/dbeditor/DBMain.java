package bjc.dbeditor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bjc.dbeditor.db.CreatureJDBCDB;
import bjc.dbeditor.db.FeatDB;
import bjc.dbeditor.db.FeatTagDB;
import bjc.dbeditor.gui.DBEditorGUI;

/**
 * Main application class
 * 
 * @author ben
 *
 */
public class DBMain {
	/*
	 * The single DB connection
	 */
	private static Connection conn;

	/*
	 * Make sure we close our connections when the main window gets closed
	 */
	private static final class ResourceDisposalListener extends WindowAdapter {
		public ResourceDisposalListener() {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("Disposing connection");

			try {
				// Dispose the connections in reverse order
				CreatureJDBCDB.disposeConnection();
				FeatTagDB.disposeConnection();
				FeatDB.disposeConnection();

				conn.close();
			} catch (SQLException sqlex) {
				System.out.println("Error: Could not close connection");
				sqlex.printStackTrace();
			}
		}
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *                Unused CLI args.
	 */
	public static void main(String[] args) {
		// Load driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfex) {
			System.out.println("Error: Could not load DB driver: " + cnfex);
			System.exit(1);
		}

		/* Get DB username */
		String username = JOptionPane.showInputDialog(null, "Enter the password for the database",
				"Enter password", JOptionPane.QUESTION_MESSAGE);

		/* Get DB pass */
		String pass = JOptionPane.showInputDialog(null, "Enter the password for the database", "Enter password",
				JOptionPane.QUESTION_MESSAGE);

		if (pass == null || pass == "") {
			System.out.println("Error: Password must be provided");
			System.exit(1);
		}

		try {
			// Establish general DB connections
			conn = DriverManager.getConnection("jdbc:postgresql:rpgdb", username, pass);
			conn.setAutoCommit(false);

			// Establish individual DB connections
			FeatDB.initConnection(conn);
			FeatTagDB.initConnection(conn);
			CreatureJDBCDB.initConnection(conn);

			System.out.println("Created connection");
		} catch (SQLException sqlex) {
			// Handle DB failure
			IllegalStateException isex = new IllegalStateException("ERROR: Could not initialize DB");

			isex.initCause(sqlex);

			throw isex;
		}

		// Create the GUI
		JFrame gui = DBEditorGUI.createGUI();

		// Configure the GUI
		gui.setSize(640, 480);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.addWindowListener(new ResourceDisposalListener());

		gui.setVisible(true);
	}
}
