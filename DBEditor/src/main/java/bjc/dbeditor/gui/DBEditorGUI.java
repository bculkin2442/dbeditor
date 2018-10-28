package bjc.dbeditor.gui;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import bjc.utils.gui.layout.AutosizeLayout;

import bjc.dbeditor.gui.feat.FeatBrowser;
import bjc.dbeditor.gui.feat.FeatEditor;
import bjc.dbeditor.gui.feat.FeatTagBrowser;
import bjc.dbeditor.gui.feat.FeatTagEditor;

/**
 * Main GUI class
 */
public class DBEditorGUI {
	// Create the main GUI
	public static JFrame createGUI() {
		// Create/Configure initial frame
		JFrame mainGUIFrame = new JFrame("D&D DB Utility");
		mainGUIFrame.setLayout(new AutosizeLayout());

		// Create/Add the desktop
		JDesktopPane mainPane = new JDesktopPane();
		mainGUIFrame.add(mainPane);

		// Create main menu bar
		JMenuBar menuBar = new JMenuBar();

		// Create/configure creation menus
		JMenu createMenu = new JMenu("Create...");
		createMenu.setMnemonic('C');

		JMenu createFeatMenu = new JMenu("Feat...");

		JMenuItem createFeatTagItem = new JMenuItem("Feat Tag...");
		createFeatTagItem.setMnemonic('M');
		createFeatTagItem.addActionListener((ev) -> {
			FeatTagEditor editor = new FeatTagEditor();

			mainPane.add(editor);
			editor.setVisible(true);
		});
		createFeatMenu.add(createFeatTagItem);

		JMenuItem createFeatItem = new JMenuItem("Feat...");
		createFeatItem.setMnemonic('F');
		createFeatItem.addActionListener((ev) -> {
			FeatEditor editor = new FeatEditor();

			mainPane.add(editor);
			editor.setVisible(true);
		});
		createFeatMenu.add(createFeatItem);

		/*
		 * JMenuItem createCreatureItem = new JMenuItem("Creature...");
		 * createCreatureItem.setMnemonic('C');
		 * createCreatureItem.addActionListener((ev) -> { CreatureEditor
		 * editor = new CreatureEditor(); mainPane.add(editor);
		 * editor.setVisible(true); });
		 * 
		 * createMenu.add(createCreatureItem);
		 */

		createMenu.add(createFeatMenu);

		// Create/configure listing menus
		JMenu listMenu = new JMenu("List...");
		listMenu.setMnemonic('L');

		JMenu listFeatMenu = new JMenu("Feats...");

		JMenuItem listFeatItem = new JMenuItem("Feats...");
		listFeatItem.setMnemonic('F');
		listFeatItem.addActionListener((ev) -> {
			FeatBrowser browser = new FeatBrowser();

			mainPane.add(browser);
			browser.setVisible(true);
		});

		JMenuItem listFeatTagsItem = new JMenuItem("Feat Tags...");
		listFeatTagsItem.setMnemonic('T');
		listFeatTagsItem.addActionListener((ev) -> {
			FeatTagBrowser browser = new FeatTagBrowser();

			mainPane.add(browser);
			browser.setVisible(true);
		});

		listFeatMenu.add(listFeatItem);
		listFeatMenu.add(listFeatTagsItem);

		listMenu.add(listFeatMenu);

		menuBar.add(createMenu);
		menuBar.add(listMenu);

		mainGUIFrame.setJMenuBar(menuBar);

		return mainGUIFrame;
	}
}
