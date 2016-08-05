package bjc.dbeditor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bjc.dbeditor.db.CreatureDB;
import bjc.dbeditor.db.FeatDB;
import bjc.dbeditor.db.FeatTagDB;
import bjc.dbeditor.gui.DBEditorGUI;

public class DBMain {
	private static Connection conn;

	private static final class ResourceDisposalListener
			extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("Disposing connection");

			try {
				CreatureDB.disposeConnection();
				FeatTagDB.disposeConnection();
				FeatDB.disposeConnection();

				conn.close();
			} catch (SQLException sqlex) {
				System.out.println("Error: Could not close connection");
				sqlex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// Load driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfex) {
			System.out
					.println("Error: Could not load DB driver: " + cnfex);
			System.exit(1);
		}

		String pass = JOptionPane.showInputDialog(null,
				"Enter the password for the database", "Enter password",
				JOptionPane.QUESTION_MESSAGE);

		if (pass == null || pass == "") {
			System.out.println("Error: Password must be provided");
			System.exit(1);
		}

		try {
			conn = DriverManager.getConnection(
					"jdbc:postgresql:dand_records", "dand_info", pass);
			conn.setAutoCommit(false);

			FeatDB.initConnection(conn);
			FeatTagDB.initConnection(conn);
			CreatureDB.initConnection(conn);

			System.out.println("Created connection");
		} catch (SQLException sqlex) {
			IllegalStateException isex = new IllegalStateException(
					"ERROR: Could not initialize DB");

			isex.initCause(sqlex);

			throw isex;
		}

		JFrame gui = DBEditorGUI.createGUI();

		gui.setSize(640, 480);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gui.addWindowListener(new ResourceDisposalListener());
		gui.setVisible(true);
	}
}
