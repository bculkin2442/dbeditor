package bjc.dbeditor.gui.feat;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bjc.dbeditor.data.feat.Feat;
import bjc.dbeditor.data.feat.FeatBuilder;
import bjc.dbeditor.db.FeatDB;
import bjc.dbeditor.db.FeatTagDB;
import bjc.funcdata.FunctionalList;
import bjc.funcdata.ListEx;
import bjc.utils.gui.SimpleInternalDialogs;
import bjc.utils.gui.SimpleInternalFrame;
import bjc.utils.gui.layout.AutosizeLayout;
import bjc.utils.gui.layout.HLayout;
import bjc.utils.gui.panels.DropdownListPanel;
import bjc.utils.gui.panels.SimpleListPanel;

public class FeatEditor extends SimpleInternalFrame {
	private static final long serialVersionUID = -4016698246942706462L;

	private JTextField nameInputField;

	private JTextArea descriptionInputArea;

	private DefaultListModel<String> tagListModel = new DefaultListModel<>();
	private DefaultListModel<String> featPrereqListModel = new DefaultListModel<>();
	private DefaultListModel<String> nonFeatPrereqListModel = new DefaultListModel<>();

	private JInternalFrame parentFrame;

	private JTextArea flavorInputArea;

	private JTextArea sourceInputArea;

	private Feat baseFeat;

	public FeatEditor(Feat base) {
		super("Feat Editor");

		setupFrame();

		parentFrame = this;

		baseFeat = base;

		if (baseFeat != null) {
			setTitle("Feat Editor - " + baseFeat.getName());
		}

		setupGUI();
	}

	public FeatEditor() {
		this(null);
	}

	private void setupGUI() {
		setLayout(new BorderLayout());

		JPanel namePanel = createNamePanel();

		JTabbedPane detailsPane = new JTabbedPane();

		ListEx<String> featTags = new FunctionalList<>();

		try {
			featTags = FeatTagDB.listTagNames();
		} catch (SQLException sqlex) {
			System.out.println("Error: Could not load tags");
			sqlex.printStackTrace();
		}

		if (baseFeat != null) {
			baseFeat.getTags().forEach(tagListModel::addElement);
		}

		DropdownListPanel tagPanel = new DropdownListPanel("Tag", tagListModel, featTags);

		ListEx<String> featNames = new FunctionalList<>();

		try {
			featNames = FeatDB.listFeatNames();
		} catch (SQLException sqlex) {
			System.out.println("Error: Could not load feats");
			sqlex.printStackTrace();
		}

		if (baseFeat != null) {
			baseFeat.getFeatPrereqs().forEach(featPrereqListModel::addElement);
		}

		DropdownListPanel prereqFeatPanel = new DropdownListPanel("Prerequisite Feat",
				featPrereqListModel, featNames);

		if (baseFeat != null) {
			baseFeat.getNonFeatPrereqs().forEach(nonFeatPrereqListModel::addElement);
		}

		SimpleListPanel nonFeatPrereqPanel = new SimpleListPanel("Nonfeat Prerequisite",
				nonFeatPrereqListModel, (tag) -> {
					return !nonFeatPrereqListModel.contains(tag);
				}, (tag) -> {
					JOptionPane.showInternalMessageDialog(parentFrame,
							"A given non-feat prerequisite can only be applied to a feat once",
							"Error", JOptionPane.ERROR_MESSAGE);
				});

		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new AutosizeLayout());

		descriptionInputArea = new JTextArea();
		descriptionInputArea.setLineWrap(true);
		descriptionInputArea.setWrapStyleWord(true);

		if (baseFeat != null) {
			descriptionInputArea.setText(baseFeat.getDescription());
		}

		JScrollPane descriptionScroller = new JScrollPane(descriptionInputArea);

		descriptionPanel.add(descriptionScroller);

		JPanel flavorPanel = new JPanel();
		flavorPanel.setLayout(new AutosizeLayout());

		flavorInputArea = new JTextArea();
		flavorInputArea.setLineWrap(true);
		flavorInputArea.setWrapStyleWord(true);

		if (baseFeat != null) {
			flavorInputArea.setText(baseFeat.getFlavor());
		}

		JScrollPane flavorScroller = new JScrollPane(flavorInputArea);

		flavorPanel.add(flavorScroller);

		JPanel sourcePanel = new JPanel();
		sourcePanel.setLayout(new AutosizeLayout());

		sourceInputArea = new JTextArea();
		sourceInputArea.setLineWrap(true);
		sourceInputArea.setWrapStyleWord(true);

		if (baseFeat != null) {
			sourceInputArea.setText(baseFeat.getSource());
		}

		JScrollPane sourceScroller = new JScrollPane(sourceInputArea);

		sourcePanel.add(sourceScroller);

		detailsPane.addTab("Tags", tagPanel);

		detailsPane.addTab("Flavor", flavorPanel);

		detailsPane.addTab("Feat Prerequisites", prereqFeatPanel);
		detailsPane.addTab("Nonfeat Prerequisites", nonFeatPrereqPanel);

		detailsPane.addTab("Description/Benefit", descriptionPanel);

		detailsPane.addTab("Source", sourcePanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new HLayout(3));

		JButton addFeat = new JButton("Add Feat");
		JButton resetFeat = new JButton("Reset Feat");
		JButton cancelFeat = new JButton("Cancel");

		addFeat.addActionListener((ev) -> {
			buildFeat();
		});

		resetFeat.addActionListener((ev) -> {
			nameInputField.setText("");

			tagListModel.clear();
			featPrereqListModel.clear();
			nonFeatPrereqListModel.clear();

			descriptionInputArea.setText("");
		});

		cancelFeat.addActionListener((ev) -> {
			dispose();
		});

		buttonPanel.add(addFeat);
		buttonPanel.add(resetFeat);
		buttonPanel.add(cancelFeat);

		add(namePanel, BorderLayout.PAGE_START);
		add(detailsPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
	}

	private void buildFeat() {
		FeatBuilder currentFeat = new FeatBuilder();

		if (nameInputField.getText() == null || nameInputField.getText().equals("")) {
			SimpleInternalDialogs.showError(parentFrame, "Error",
					"The feat must have a name");
			return;
		}

		currentFeat.setName(nameInputField.getText());

		if (tagListModel.size() > 0) {
			for (Object tag : tagListModel.toArray()) {
				currentFeat.addTag((String) tag);
			}
		}

		if (featPrereqListModel.size() > 0) {
			for (Object featPrereq : featPrereqListModel.toArray()) {
				currentFeat.addFeatPrereq((String) featPrereq);
			}
		}

		if (nonFeatPrereqListModel.size() > 0) {
			for (Object nonFeatPrereq : nonFeatPrereqListModel.toArray()) {
				currentFeat.addNonFeatPrereq((String) nonFeatPrereq);
			}
		}

		if (descriptionInputArea.getText() == null
				|| descriptionInputArea.getText().equals("")) {
			SimpleInternalDialogs.showError(parentFrame, "Error",
					"The feat must have a description");
			return;
		}

		currentFeat.setDescription(descriptionInputArea.getText());

		currentFeat.setFlavor(flavorInputArea.getText());

		if (sourceInputArea.getText() == null || sourceInputArea.getText().equals("")) {
			SimpleInternalDialogs.showError(parentFrame, "Error",
					"The feat must have a source");
			return;
		}

		currentFeat.setSource(sourceInputArea.getText());

		Feat featToAdd = currentFeat.buildFeat();

		if (baseFeat != null && featToAdd.getName().equals(baseFeat.getName())) {
			boolean updateFeat = SimpleInternalDialogs.getYesNo(parentFrame,
					"Update Feat?", "The feat " + featToAdd.getName()
							+ " already exists. Would you like to update it?");

			if (updateFeat) {
				SimpleInternalDialogs.showMessage(parentFrame, "Info",
						"Updating feats isn't supported yet");
				// TODO implement updating feats
				return;
			}

			return;
		}

		try {
			FeatDB.addFeat(featToAdd);

			SimpleInternalDialogs.showMessage(parentFrame, "Info",
					"Succesfully added feat");
		} catch (SQLException sqlex) {
			SimpleInternalDialogs.showError(parentFrame, "Error",
					"Could not save feat into database");

			sqlex.printStackTrace();
		}
	}

	private JPanel createNamePanel() {
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new HLayout(2));

		JLabel nameInputLabel = new JLabel("Feat Name: ");
		nameInputField = new JTextField(255);

		if (baseFeat != null) {
			nameInputField.setText(baseFeat.getName());
		}

		nameInputField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (nameInputField.getText() == null
						|| nameInputField.getText().equals("")) {
					parentFrame.setTitle("Feat Editor");
				} else {
					parentFrame.setTitle("Feat Editor - " + nameInputField.getText());
				}
			}
		});

		namePanel.add(nameInputLabel);
		namePanel.add(nameInputField);
		return namePanel;
	}
}
