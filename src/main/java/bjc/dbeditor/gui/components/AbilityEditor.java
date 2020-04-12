package bjc.dbeditor.gui.components;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import bjc.dbeditor.data.creatures.CreatureAbility;
import bjc.dbeditor.data.creatures.CreatureAbilityType;
import bjc.funcdata.IList;
import bjc.utils.gui.layout.AutosizeLayout;
import bjc.utils.gui.layout.HLayout;
import bjc.utils.gui.panels.SimpleInputPanel;

/**
 * Editor for abilities.
 * 
 * @author bjculkin
 *
 */
public class AbilityEditor extends JPanel {
	private static final long serialVersionUID = 2155181373077752917L;

	/**
	 * Model of abilities to edit.
	 */
	public final DefaultListModel<CreatureAbility> abilityModel;

	/**
	 * Create a new ability editor.
	 * 
	 * @param baseAbilities
	 *                The list of abilities to edit.
	 */
	public AbilityEditor(IList<CreatureAbility> baseAbilities) {
		setLayout(new AutosizeLayout());

		JPanel abilityCreator = new JPanel();
		abilityCreator.setLayout(new BorderLayout());

		JPanel abilityBasics = new JPanel();
		abilityBasics.setLayout(new HLayout(2));

		SimpleInputPanel abilityName = new SimpleInputPanel("Ability Name: ", 255);

		JPanel abilityTypePanel = new JPanel();
		abilityTypePanel.setLayout(new BorderLayout());

		JLabel abilityTypeLabel = new JLabel("Ability Type: ");
		JComboBox<CreatureAbilityType> abilityType = new JComboBox<>(CreatureAbilityType.values());

		abilityTypePanel.add(abilityTypeLabel, BorderLayout.LINE_START);
		abilityTypePanel.add(abilityType, BorderLayout.CENTER);

		abilityBasics.add(abilityName);
		abilityBasics.add(abilityTypePanel);

		JTextArea abilityDescription = new JTextArea();
		abilityDescription.setLineWrap(true);
		abilityDescription.setWrapStyleWord(true);

		JScrollPane descriptionScroller = new JScrollPane(abilityDescription);

		JButton addAbility = new JButton("Add Ability");

		abilityCreator.add(abilityBasics, BorderLayout.PAGE_START);
		abilityCreator.add(descriptionScroller, BorderLayout.CENTER);
		abilityCreator.add(addAbility, BorderLayout.PAGE_END);

		JPanel abilityLister = new JPanel();
		abilityLister.setLayout(new AutosizeLayout());

		abilityModel = new DefaultListModel<>();

		if (baseAbilities != null) {
			baseAbilities.forEach(abilityModel::addElement);
		}

		addAbility.addActionListener((ev) -> {
			CreatureAbility ability = new CreatureAbility(abilityName.inputValue.getText(),
					abilityDescription.getText(),
					(CreatureAbilityType) abilityType.getSelectedItem());

			abilityModel.addElement(ability);
		});

		JList<CreatureAbility> abilityList = new JList<>(abilityModel);

		JTextArea abilityBody = new JTextArea();
		abilityBody.setLineWrap(true);
		abilityBody.setWrapStyleWord(true);

		abilityList.addListSelectionListener((ev) -> {
			if (ev.getValueIsAdjusting() == true) {
				return;
			}

			int selectedIndex = abilityList.getSelectedIndex();

			if (selectedIndex < 0) {
				abilityBody.setText("");
			} else {
				abilityBody.setText(abilityModel.get(selectedIndex).toFullString());
			}
		});

		JScrollPane listScroller = new JScrollPane(abilityList);
		JScrollPane bodyScroller = new JScrollPane(abilityBody);

		JSplitPane displaySplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroller, bodyScroller);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, abilityCreator, displaySplit);
		splitPane.setResizeWeight(0.5);

		add(splitPane);
	}

	/**
	 * Create a new ability editor.
	 */
	public AbilityEditor() {
		this(null);
	}
}
