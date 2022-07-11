package bjc.dbeditor.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;

import bjc.dbeditor.data.creatures.CreatureHitdieRecord;
import bjc.funcdata.*;
import bjc.utils.gui.layout.HLayout;
import bjc.utils.gui.layout.VLayout;
import bjc.utils.gui.panels.SimpleSpinnerPanel;

/**
 * An GUI component for editing hitdie records
 */
public class HitdieEditor extends JPanel {
	// List renderer for hitdie
	private final class HitdieRenderer extends JLabel
			implements ListCellRenderer<CreatureHitdieRecord> {
		private static final long serialVersionUID = -6874538320973247325L;

		public HitdieRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(
				JList<? extends CreatureHitdieRecord> list, CreatureHitdieRecord value,
				int index, boolean isSelected, boolean cellHasFocus) {
			// Call the right toString method
			setText(value.toFullString());

			// Properly format background
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			return this;
		}
	}

	private static final long serialVersionUID = -8817598625503039790L;
	// The model for holding hitdie
	public final DefaultListModel<CreatureHitdieRecord> hitdiceModel;

	public HitdieEditor(ListEx<CreatureHitdieRecord> baseHitdie) {
		setLayout(new BorderLayout());

		JPanel hitdieEntryPanel = new JPanel();
		hitdieEntryPanel.setLayout(new VLayout(3));

		JPanel hitdieBasicEntry = new JPanel();
		hitdieBasicEntry.setLayout(new HLayout(2));

		SimpleSpinnerPanel hitdieCount = new SimpleSpinnerPanel("Number of Hitdice: ",
				new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

		SimpleSpinnerPanel hitdieSize = new SimpleSpinnerPanel("Size of Hitdice: ",
				new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

		hitdieBasicEntry.add(hitdieCount);

		hitdieBasicEntry.add(hitdieSize);

		JPanel hitdieClassEntry = new JPanel();
		hitdieClassEntry.setLayout(new BorderLayout());

		JLabel hitdieClassLabel = new JLabel("Hitdice Class (Optional): ");
		JTextField hitdieClassField = new JTextField(255);

		hitdieClassEntry.add(hitdieClassLabel, BorderLayout.LINE_START);
		hitdieClassEntry.add(hitdieClassField, BorderLayout.CENTER);

		JButton addHitdieButton = new JButton("Add Hitdice");

		hitdieEntryPanel.add(hitdieBasicEntry);
		hitdieEntryPanel.add(hitdieClassEntry);
		hitdieEntryPanel.add(addHitdieButton);

		hitdiceModel = new DefaultListModel<>();

		if (baseHitdie != null) {
			baseHitdie.forEach(hitdiceModel::addElement);
		}

		addHitdieButton.addActionListener((ev) -> {
			addHitdie(hitdieCount.inputValue, hitdieSize.inputValue, hitdieClassField);
		});

		hitdieClassField.addActionListener((ev) -> {
			addHitdie(hitdieCount.inputValue, hitdieSize.inputValue, hitdieClassField);

			hitdieCount.inputValue.requestFocusInWindow();
		});

		JList<CreatureHitdieRecord> hitdieList = new JList<>(hitdiceModel);
		hitdieList.setCellRenderer(new HitdieRenderer());

		JButton removeHitdieButton = new JButton("Remove Hitdie");
		removeHitdieButton.addActionListener((ev) -> {
			int selectedIndex = hitdieList.getSelectedIndex();

			if (selectedIndex >= 0) {
				hitdiceModel.remove(selectedIndex);
			}
		});

		add(hitdieEntryPanel, BorderLayout.PAGE_START);
		add(hitdieList, BorderLayout.CENTER);
		add(removeHitdieButton, BorderLayout.PAGE_END);
	}

	public HitdieEditor() {
		this(null);
	}

	private void addHitdie(JSpinner hitdieCountSpinner, JSpinner hitdieSizeSpinner,
			JTextField hitdieClassField) {
		int dieCount = ((SpinnerNumberModel) hitdieCountSpinner.getModel()).getNumber()
				.intValue();
		int dieSize = ((SpinnerNumberModel) hitdieSizeSpinner.getModel()).getNumber()
				.intValue();

		CreatureHitdieRecord record
				= new CreatureHitdieRecord(dieCount, dieSize, hitdieClassField.getText());

		hitdiceModel.addElement(record);

		hitdieCountSpinner.setValue(1);
		hitdieSizeSpinner.setValue(1);
		hitdieClassField.setText("");

	}
}
