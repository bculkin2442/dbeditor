package bjc.dbeditor.gui;

import javax.swing.JInternalFrame;

public class DBInternalFrame extends JInternalFrame {
	private static final long serialVersionUID = -2966801321260716617L;

	public DBInternalFrame() {
		super();
	}

	public DBInternalFrame(String title) {
		super(title);
	}

	protected void setupFrame() {
		setSize(320, 240);

		setResizable(true);

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
	}

}