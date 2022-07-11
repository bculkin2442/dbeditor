package bjc.dbeditor.gui.feat;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import bjc.dbeditor.data.feat.Feat;
import bjc.dbeditor.db.FeatDB;
import bjc.funcdata.FunctionalMap;
import bjc.funcdata.ListEx;
import bjc.utils.gui.SimpleInternalDialogs;
import bjc.utils.gui.SimpleInternalFrame;
import bjc.utils.gui.SimpleTitledBorder;
import bjc.utils.gui.layout.AutosizeLayout;

public class FeatBrowser extends SimpleInternalFrame {
	private static final long serialVersionUID = -2529817913539767911L;

	private ListEx<String> baseFeatList;

	public FeatBrowser(ListEx<String> featList) {
		super("Feat Browser");

		setupFrame();

		baseFeatList = featList;

		setupGUI();
	}

	public FeatBrowser() {
		this(null);
	}

	private void setupGUI() {
		SimpleInternalFrame refFrame = this;

		setLayout(new AutosizeLayout());

		DefaultListModel<String> featListModel = new DefaultListModel<>();

		ListEx<String> featSourceList = baseFeatList;

		if (featSourceList == null) {
			try {
				featSourceList = FeatDB.listFeatNames();
			} catch (SQLException sqlex) {
				System.out.println("Error: Could not load feats");
				sqlex.printStackTrace();
			}
		}

		if (featSourceList != null) {
			featSourceList.forEach(featListModel::addElement);
		}

		JList<String> featList = new JList<>(featListModel);
		featList.setBorder(new SimpleTitledBorder("Feat List"));
		featList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		featList.setLayoutOrientation(JList.VERTICAL);

		JPanel featDisplayPanel = new JPanel();
		featDisplayPanel.setBorder(new SimpleTitledBorder("Feat Details"));
		featDisplayPanel.setLayout(new AutosizeLayout());

		JTextArea featDisplayArea = new JTextArea();
		featDisplayArea.setEditable(false);
		featDisplayArea.setLineWrap(true);
		featDisplayArea.setWrapStyleWord(true);

		JScrollPane featScroller = new JScrollPane(featDisplayArea);
		featDisplayPanel.add(featScroller);

		FunctionalMap<String, Feat> featCache = new FunctionalMap<>();

		featList.addListSelectionListener((ev) -> {
			if (ev.getValueIsAdjusting() == false) {
				int selectionIndex = featList.getSelectedIndex();

				if (selectionIndex < 0) {
					return;
				}

				String displayedFeatName = featListModel.get(selectionIndex);

				try {
					Feat displayedFeat;

					if (featCache.containsKey(displayedFeatName)) {
						displayedFeat = featCache.get(displayedFeatName).get();
					} else {
						displayedFeat = FeatDB.lookupFeat(displayedFeatName);

						featCache.put(displayedFeatName, displayedFeat);
					}

					featDisplayArea.setText(displayedFeat.toFullString());

					refFrame.setTitle("Feat Browser - " + displayedFeat.getName());
				} catch (SQLException sqlex) {
					SimpleInternalDialogs.showError(refFrame, "Error",
							"Could not display feat " + displayedFeatName);
					sqlex.printStackTrace();
				}
			}
		});

		JButton refreshButton = new JButton("Refresh Feat Details");
		refreshButton.addActionListener((ev) -> {
			featCache.clear();

			featList.clearSelection();
			featDisplayArea.setText("");
		});

		JScrollPane listScroller = new JScrollPane(featList);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		listPanel.add(listScroller, BorderLayout.CENTER);
		listPanel.add(refreshButton, BorderLayout.PAGE_END);

		JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel,
				featDisplayPanel);

		add(mainPane);
	}
}
