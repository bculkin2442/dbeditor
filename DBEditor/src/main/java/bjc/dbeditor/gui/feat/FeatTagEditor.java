package bjc.dbeditor.gui.feat;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bjc.utils.gui.SimpleInternalDialogs;
import bjc.utils.gui.SimpleInternalFrame;
import bjc.utils.gui.layout.HLayout;

import bjc.dbeditor.data.feat.FeatTag;
import bjc.dbeditor.db.FeatTagDB;

public class FeatTagEditor extends SimpleInternalFrame {
	private static final long	serialVersionUID	= -1072275991676239181L;

	private FeatTag				baseTag;

	public FeatTagEditor(FeatTag base) {
		super("Feat Tag Editor");

		setupFrame();

		if (base != null) {
			setTitle("Feat Tag Editor - " + base.getName());
		}

		baseTag = base;

		setupGUI();
	}

	public FeatTagEditor() {
		this(null);
	}

	private void setupGUI() {
		SimpleInternalFrame refFrame = this;

		setLayout(new BorderLayout());

		JPanel tagNamePanel = new JPanel();
		tagNamePanel.setLayout(new HLayout(2));

		JLabel tagNameLabel = new JLabel("Tag Name: ");
		JTextField tagNameField = new JTextField(255);

		if (baseTag != null) {
			tagNameField.setText(baseTag.getName());
		}
		tagNameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String tagName = tagNameField.getText();

				if (tagName == null || tagName.equals("")) {
					refFrame.setTitle("Feat Tag Editor");
				} else {
					refFrame.setTitle("Feat Tag Editor - " + tagName);
				}
			}
		});

		tagNamePanel.add(tagNameLabel);
		tagNamePanel.add(tagNameField);

		JTextArea tagDescriptionArea = new JTextArea();
		tagDescriptionArea.setLineWrap(true);
		tagDescriptionArea.setWrapStyleWord(true);

		JScrollPane descriptionScroller = new JScrollPane(
				tagDescriptionArea);

		if (baseTag != null) {
			tagDescriptionArea.setText(baseTag.getDescription());
		}

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new HLayout(3));

		JButton addTagButton = new JButton("Add Feat Tag");
		JButton resetTagButton = new JButton("Reset Feat Tag");
		JButton cancelTagButton = new JButton("Cancel Feat Tag");

		addTagButton.addActionListener((ev) -> {
			try {
				FeatTagDB.addFeatTag(new FeatTag(tagNameField.getText(),
						tagDescriptionArea.getText()));

				SimpleInternalDialogs.showMessage(refFrame, "Info",
						"Succesfully added feat tag");
			} catch (SQLException sqlex) {
				SimpleInternalDialogs.showError(refFrame, "Error",
						"Could not add feat tag");
				sqlex.printStackTrace();
			}
		});

		resetTagButton.addActionListener((ev) -> {
			tagNameField.setText("");
			tagDescriptionArea.setText("");

			refFrame.setTitle("Feat Tag Editor");
		});

		cancelTagButton.addActionListener((ev) -> {
			refFrame.dispose();
		});
		buttonPanel.add(addTagButton);
		buttonPanel.add(resetTagButton);
		buttonPanel.add(cancelTagButton);

		add(tagNamePanel, BorderLayout.PAGE_START);
		add(descriptionScroller, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
	}
}
