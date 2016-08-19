package bjc.dbeditor.gui;

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

import bjc.utils.funcdata.FunctionalMap;
import bjc.utils.funcdata.IList;
import bjc.utils.gui.SimpleInternalDialogs;
import bjc.utils.gui.SimpleInternalFrame;
import bjc.utils.gui.SimpleTitledBorder;
import bjc.utils.gui.layout.AutosizeLayout;

import bjc.dbeditor.data.FeatTag;
import bjc.dbeditor.db.FeatTagDB;

public class FeatTagBrowser extends SimpleInternalFrame {
	private static final long	serialVersionUID	= -2529817913539767911L;

	private IList<String>		baseTagList;

	public FeatTagBrowser(IList<String> tagList) {
		super("Feat Tag Browser");

		setupFrame();

		baseTagList = tagList;

		setupGUI();
	}

	public FeatTagBrowser() {
		this(null);
	}

	private void setupGUI() {
		SimpleInternalFrame refFrame = this;

		setLayout(new AutosizeLayout());

		DefaultListModel<String> featTagListModel = new DefaultListModel<>();

		IList<String> featTagSourceList = baseTagList;

		if (baseTagList == null) {
			try {
				featTagSourceList = FeatTagDB.listTagNames();
			} catch (SQLException sqlex) {
				System.out.println("Error: Could not load feats");
				sqlex.printStackTrace();
			}
		}

		if (featTagSourceList != null) {
			featTagSourceList.forEach(featTagListModel::addElement);
		}

		JList<String> featTagList = new JList<>(featTagListModel);
		featTagList.setBorder(new SimpleTitledBorder("Feat Tag List"));
		featTagList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		featTagList.setLayoutOrientation(JList.VERTICAL);

		JPanel featTagDisplayPanel = new JPanel();
		featTagDisplayPanel
				.setBorder(new SimpleTitledBorder("Feat Tag Details"));
		featTagDisplayPanel.setLayout(new AutosizeLayout());

		JTextArea featTagDisplayArea = new JTextArea();
		featTagDisplayArea.setEditable(false);
		featTagDisplayArea.setLineWrap(true);
		featTagDisplayArea.setWrapStyleWord(true);

		JScrollPane featTagDisplayScroller = new JScrollPane(
				featTagDisplayArea);
		featTagDisplayPanel.add(featTagDisplayScroller);

		FunctionalMap<String, FeatTag> featTagCache = new FunctionalMap<>();

		featTagList.addListSelectionListener((ev) -> {
			if (ev.getValueIsAdjusting() == false) {
				int selectedIndex = featTagList.getSelectedIndex();

				if (selectedIndex < 0) {
					return;
				}

				String displayedFeatTagName = featTagListModel
						.get(selectedIndex);

				FeatTag displayedFeatTag;

				if (featTagCache.containsKey(displayedFeatTagName)) {
					displayedFeatTag = featTagCache
							.get(displayedFeatTagName);
				} else {
					try {
						displayedFeatTag = FeatTagDB
								.lookupTag(displayedFeatTagName);

						featTagCache.put(displayedFeatTagName,
								displayedFeatTag);
					} catch (SQLException sqlex) {
						SimpleInternalDialogs.showError(refFrame, "Error",
								"Could not load feat tag "
										+ displayedFeatTagName);
						sqlex.printStackTrace();
						return;
					}
				}

				featTagDisplayArea
						.setText(displayedFeatTag.toFullString());

				refFrame.setTitle("Feat Tag Browser - "
						+ displayedFeatTag.getName());
			}
		});

		JButton refreshButton = new JButton("Refresh Feat Tag Details");
		refreshButton.addActionListener((ev) -> {
			featTagCache.clear();

			featTagList.clearSelection();
			featTagDisplayArea.setText("");
		});

		JScrollPane listScroller = new JScrollPane(featTagList);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());

		listPanel.add(listScroller, BorderLayout.CENTER);
		listPanel.add(refreshButton, BorderLayout.PAGE_END);

		JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				listPanel, featTagDisplayPanel);

		add(mainPane);
	}
}
