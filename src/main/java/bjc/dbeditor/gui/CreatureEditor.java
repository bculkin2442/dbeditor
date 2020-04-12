package bjc.dbeditor.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import bjc.dbeditor.data.creatures.Creature;
import bjc.dbeditor.data.creatures.CreatureAbility;
import bjc.dbeditor.data.creatures.CreatureAbilityScores;
import bjc.dbeditor.data.creatures.CreatureAttack;
import bjc.dbeditor.data.creatures.CreatureHitdieRecord;
import bjc.dbeditor.data.creatures.CreatureOffenses;
import bjc.dbeditor.data.creatures.CreatureSaves;
import bjc.dbeditor.data.creatures.CreatureSize;
import bjc.dbeditor.data.creatures.CreatureSkill;
import bjc.dbeditor.data.creatures.CreatureSpeed;
import bjc.dbeditor.data.creatures.CreatureType;
import bjc.dbeditor.data.creatures.builders.CreatureBuilder;
import bjc.dbeditor.data.creatures.builders.CreatureDefensesBuilder;
import bjc.dbeditor.data.creatures.builders.CreatureFlavorBuilder;
import bjc.dbeditor.data.creatures.builders.CreatureMiscBuilder;
import bjc.dbeditor.data.creatures.builders.CreatureOffensesBuilder;
import bjc.dbeditor.db.CreatureDB;
import bjc.dbeditor.db.FeatDB;
import bjc.dbeditor.gui.components.AbilityEditor;
import bjc.dbeditor.gui.components.HitdieEditor;
import bjc.funcdata.FunctionalList;
import bjc.funcdata.IList;
import bjc.utils.gui.SimpleInternalDialogs;
import bjc.utils.gui.SimpleInternalFrame;
import bjc.utils.gui.SimpleTitledBorder;
import bjc.utils.gui.layout.AutosizeLayout;
import bjc.utils.gui.layout.HLayout;
import bjc.utils.gui.layout.VLayout;
import bjc.utils.gui.panels.DropdownListPanel;
import bjc.utils.gui.panels.SimpleInputPanel;
import bjc.utils.gui.panels.SimpleListPanel;
import bjc.utils.gui.panels.SimpleSpinnerPanel;

public class CreatureEditor extends SimpleInternalFrame {
	private static final long serialVersionUID = -889902229115548570L;

	private Creature baseCreature;

	SimpleInternalFrame refFrame;

	JTextField nameField;

	private JComboBox<CreatureSize>		sizePicker;
	private JComboBox<CreatureType>		typePicker;
	private DefaultListModel<String>	subtypeModel;

	private DefaultListModel<CreatureHitdieRecord> hitdiceModel;

	private JSpinner	natArmorSpinner;
	private JSpinner	spellResSpinner;

	private JSpinner	fortSaveSpinner;
	private JSpinner	reflexSaveSpinner;
	private JSpinner	willSaveSpinner;

	private JSpinner baseAttackField;

	private JSpinner grappleField;

	private JTextArea normalAttack;

	private JTextArea fullAttack;

	private DefaultListModel<CreatureAbility> specialAttacks;

	private DefaultListModel<CreatureAbility> specialQualities;

	private DefaultListModel<String> featModel;

	private JTextArea advancementPanel;

	private JSpinner cr;

	private JTextField la;

	private JTextArea treasure;

	private JTextArea enviroment;

	private JTextArea organization;

	private JTextArea description;

	private JTextArea notes;

	private JTextArea source;

	DefaultListModel<CreatureSpeed> speeds;

	private DefaultListModel<CreatureSkill> skillModel;

	private JTextField alignment;

	private JSpinner	strength;
	private JSpinner	dexterity;
	private JSpinner	constitution;
	private JSpinner	intelligence;
	private JSpinner	charisma;
	private JSpinner	wisdom;

	public CreatureEditor() {
		this(null);
	}

	public CreatureEditor(Creature base) {
		super("Creature Editor");

		setupFrame();

		baseCreature = base;

		refFrame = this;

		if (baseCreature != null) {
			setTitle("Creature Editor - " + baseCreature.getName());
		}

		setupGUI();
	}

	private void addCreature() {
		CreatureBuilder builder = new CreatureBuilder();

		builder.setName(nameField.getText());

		builder.setSize((CreatureSize) sizePicker.getSelectedItem());

		builder.setType((CreatureType) typePicker.getSelectedItem());

		for (Object subtype : subtypeModel.toArray()) {
			builder.addSubtype((String) subtype);
		}

		for (Object hitdie : hitdiceModel.toArray()) {
			builder.addHitdie((CreatureHitdieRecord) hitdie);
		}

		for (Object speed : speeds.toArray()) {
			builder.addSpeed((CreatureSpeed) speed);
		}

		CreatureDefensesBuilder defenseBuilder = new CreatureDefensesBuilder();

		defenseBuilder.setNaturalArmor((Integer) natArmorSpinner.getValue());

		defenseBuilder.setSpellResistance((Integer) spellResSpinner.getValue());

		CreatureSaves creatureSaves = new CreatureSaves((Integer) fortSaveSpinner.getValue(),
				(Integer) reflexSaveSpinner.getValue(), (Integer) willSaveSpinner.getValue());

		defenseBuilder.setSaves(creatureSaves);

		builder.setDefenses(defenseBuilder.buildDefenses());

		CreatureOffensesBuilder offenseBuilder = new CreatureOffensesBuilder();

		CreatureAttack creatureAttack = new CreatureAttack((Integer) baseAttackField.getValue(),
				(Integer) grappleField.getValue());

		offenseBuilder.setAttackStats(creatureAttack);

		offenseBuilder.setAttack(normalAttack.getText());
		offenseBuilder.setFullAttack(fullAttack.getText());

		for (Object specialAttack : specialAttacks.toArray()) {
			offenseBuilder.addSpecialAttack((CreatureAbility) specialAttack);
		}

		for (Object specialQuality : specialQualities.toArray()) {
			offenseBuilder.addSpecialQuality((CreatureAbility) specialQuality);
		}

		builder.setOffenses(offenseBuilder.buildOffenses());

		CreatureAbilityScores scores = new CreatureAbilityScores((Integer) strength.getValue(),
				(Integer) dexterity.getValue(), (Integer) constitution.getValue(),
				(Integer) intelligence.getValue(), (Integer) wisdom.getValue(),
				(Integer) charisma.getValue());

		builder.setAbilityScores(scores);

		CreatureMiscBuilder miscBuilder = new CreatureMiscBuilder();
		CreatureFlavorBuilder flavorBuilder = new CreatureFlavorBuilder();

		for (Object skill : skillModel.toArray()) {
			miscBuilder.addSkill((CreatureSkill) skill);
		}

		flavorBuilder.setOrganization(organization.getText());
		flavorBuilder.setEnviroment(enviroment.getText());

		builder.setChallengeRating((BigDecimal) cr.getValue());
		builder.setLevelAdjustment(la.getText());

		miscBuilder.setTreasure(treasure.getText());
		miscBuilder.setAlignment(alignment.getText());

		builder.setAdvancement(advancementPanel.getText());

		flavorBuilder.setDescription(description.getText());
		flavorBuilder.setNotes(notes.getText());

		miscBuilder.setSource(source.getText());

		builder.setMiscData(miscBuilder.buildMisc());
		builder.setFlavor(flavorBuilder.buildFlavor());

		try {
			CreatureDB.addCreature(builder.buildCreature());

			SimpleInternalDialogs.showMessage(refFrame, "Info", "Succesfully added creature");
		} catch (SQLException sqlex) {
			SimpleInternalDialogs.showError(refFrame, "Error", "Could not save creature to database.");

			sqlex.printStackTrace();
		}
	}

	private JPanel setupAbilityScorePanel() {
		JPanel abilityScorePanel = new JPanel();
		abilityScorePanel.setLayout(new GridLayout(2, 3));

		SimpleSpinnerPanel strengthPanel = new SimpleSpinnerPanel("Strength: ",
				new SpinnerNumberModel(0, -1, Integer.MAX_VALUE, 1));
		strength = strengthPanel.inputValue;

		SimpleSpinnerPanel dexterityPanel = new SimpleSpinnerPanel("Dexterity: ",
				new SpinnerNumberModel(0, -1, Integer.MAX_VALUE, 1));
		dexterity = dexterityPanel.inputValue;

		SimpleSpinnerPanel constitutionPanel = new SimpleSpinnerPanel("Constitution: ",
				new SpinnerNumberModel(0, -1, Integer.MAX_VALUE, 1));
		constitution = constitutionPanel.inputValue;

		SimpleSpinnerPanel intelligencePanel = new SimpleSpinnerPanel("Intelligence: ",
				new SpinnerNumberModel(0, -1, Integer.MAX_VALUE, 1));
		intelligence = intelligencePanel.inputValue;

		SimpleSpinnerPanel wisdomPanel = new SimpleSpinnerPanel("Wisdom: ",
				new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		wisdom = wisdomPanel.inputValue;

		SimpleSpinnerPanel charismaPanel = new SimpleSpinnerPanel("Charisma: ",
				new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		charisma = charismaPanel.inputValue;

		abilityScorePanel.add(strengthPanel);
		abilityScorePanel.add(dexterityPanel);
		abilityScorePanel.add(constitutionPanel);
		abilityScorePanel.add(intelligencePanel);
		abilityScorePanel.add(wisdomPanel);
		abilityScorePanel.add(charismaPanel);

		return abilityScorePanel;
	}

	private JPanel setupCRLAAdvancementPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel laCRPanel = new JPanel();
		laCRPanel.setLayout(new VLayout(2));

		SimpleSpinnerPanel crPanel = new SimpleSpinnerPanel("Challenge Rating: ", new SpinnerNumberModel(
				BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.valueOf(1000), BigDecimal.valueOf(.5)));
		cr = crPanel.inputValue;

		SimpleInputPanel laPanel = new SimpleInputPanel("Level Adjustment: ", 255);

		la = laPanel.inputValue;

		laCRPanel.add(crPanel);
		laCRPanel.add(laPanel);

		advancementPanel = new JTextArea();
		advancementPanel.setLineWrap(true);
		advancementPanel.setWrapStyleWord(true);
		advancementPanel.setBorder(new SimpleTitledBorder("Advancement"));

		panel.add(laCRPanel, BorderLayout.PAGE_START);
		panel.add(advancementPanel, BorderLayout.CENTER);

		return panel;
	}

	private JPanel setupDefensePanel() {
		JPanel defensePanel = new JPanel();
		defensePanel.setLayout(new VLayout(3));

		SimpleSpinnerPanel natArmor = new SimpleSpinnerPanel("Natural Armor: ",
				new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		natArmorSpinner = natArmor.inputValue;

		if (baseCreature != null) {
			natArmorSpinner.setValue(baseCreature.getDefenses().getNaturalArmor());
		}

		SimpleSpinnerPanel spellRes = new SimpleSpinnerPanel("Spell Resistance: ",
				new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

		spellResSpinner = spellRes.inputValue;

		if (baseCreature != null) {
			spellResSpinner.setValue(baseCreature.getDefenses().getSpellResistance());
		}

		JPanel savesPanel = new JPanel();
		savesPanel.setLayout(new HLayout(3));

		SimpleSpinnerPanel fortSave = new SimpleSpinnerPanel("Fortitude Save: ",
				new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		fortSaveSpinner = fortSave.inputValue;

		SimpleSpinnerPanel reflexSave = new SimpleSpinnerPanel("Reflex Save: ",
				new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		reflexSaveSpinner = reflexSave.inputValue;

		SimpleSpinnerPanel willSave = new SimpleSpinnerPanel("Will Save: ",
				new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		willSaveSpinner = willSave.inputValue;

		savesPanel.add(fortSave);
		savesPanel.add(reflexSave);
		savesPanel.add(willSave);

		if (baseCreature != null) {
			CreatureSaves saves = baseCreature.getDefenses().getSaves();

			fortSaveSpinner.setValue(saves.getFortitude());
			reflexSaveSpinner.setValue(saves.getReflex());
			willSaveSpinner.setValue(saves.getWill());
		}

		defensePanel.add(natArmor);
		defensePanel.add(spellRes);
		defensePanel.add(savesPanel);

		return defensePanel;
	}

	private JPanel setupDetailPanel() {
		JPanel detailsPanel = new JPanel();
		detailsPanel.setLayout(new AutosizeLayout());

		DefaultListModel<String> detailModel = new DefaultListModel<>();

		JPanel detailEditorPanel = new JPanel();

		CardLayout detailLayout = new CardLayout();

		detailEditorPanel.setLayout(detailLayout);

		JPanel blankPanel = new JPanel();

		detailEditorPanel.add(blankPanel, "Blank");

		detailEditorPanel.add(setupSizeTypePanel(), "Size & Type/Subtypes");
		detailModel.addElement("Size & Type/Subtypes");

		detailEditorPanel.add(setupHitdiePanel(), "Hitdice");
		detailModel.addElement("Hitdice");

		detailEditorPanel.add(setupSpeedPanel(), "Speeds");
		detailModel.addElement("Speeds");

		detailEditorPanel.add(setupDefensePanel(), "Defenses");
		detailModel.addElement("Defenses");

		detailEditorPanel.add(setupAbilityScorePanel(), "Ability Scores");
		detailModel.addElement("Ability Scores");

		detailEditorPanel.add(setupOffensePanel(), "Offenses");
		detailModel.addElement("Offenses");

		detailEditorPanel.add(setupFeatPanel(), "Feats");
		detailModel.addElement("Feats");

		detailEditorPanel.add(setupSkillPanel(), "Skills");
		detailModel.addElement("Skills");

		detailEditorPanel.add(setupCRLAAdvancementPanel(), "CR, LA & Advancement");
		detailModel.addElement("CR, LA & Advancement");

		detailEditorPanel.add(setupFlavorPanel(), "Flavor");
		detailModel.addElement("Flavor");

		JList<String> detailList = new JList<>(detailModel);

		detailList.addListSelectionListener((ev) -> {
			if (ev.getValueIsAdjusting() == true) {
				return;
			}

			String selectedValue = detailList.getSelectedValue();

			if (selectedValue == null) {
				detailLayout.show(detailEditorPanel, "Blank");
			}

			detailLayout.show(detailEditorPanel, selectedValue);
		});

		JSplitPane detailSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailList, detailEditorPanel);

		detailsPanel.add(detailSplitter);
		return detailsPanel;
	}

	private JPanel setupFeatPanel() {
		IList<String> featNames = new FunctionalList<>();

		featModel = new DefaultListModel<>();

		try {
			featNames = FeatDB.listFeatNames();
		} catch (SQLException sqlex) {
			System.out.println("Error: Could not load feats");
			sqlex.printStackTrace();
		}

		if (baseCreature != null) {
			baseCreature.getFeats().forEach(featModel::addElement);
		}

		DropdownListPanel featPanel = new DropdownListPanel("Feat", featModel, featNames);

		return featPanel;
	}

	private JPanel setupFlavorPanel() {
		JPanel flavorPanel = new JPanel();
		flavorPanel.setLayout(new BorderLayout());

		JTabbedPane flavorChoice = new JTabbedPane();

		treasure = new JTextArea();
		treasure.setLineWrap(true);
		treasure.setWrapStyleWord(true);

		flavorChoice.addTab("Treasure", treasure);

		enviroment = new JTextArea();
		enviroment.setLineWrap(true);
		enviroment.setWrapStyleWord(true);

		flavorChoice.addTab("Enviroment", enviroment);

		organization = new JTextArea();
		organization.setLineWrap(true);
		organization.setWrapStyleWord(true);

		flavorChoice.addTab("Organization", organization);

		description = new JTextArea();
		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		flavorChoice.addTab("Description", description);

		notes = new JTextArea();
		notes.setLineWrap(true);
		notes.setWrapStyleWord(true);

		flavorChoice.addTab("Notes", notes);

		source = new JTextArea();
		source.setLineWrap(true);
		source.setWrapStyleWord(true);

		flavorChoice.addTab("Source", source);

		SimpleInputPanel alignmentPanel = new SimpleInputPanel("Alignment: ", 255);
		alignment = alignmentPanel.inputValue;

		flavorPanel.add(alignmentPanel, BorderLayout.PAGE_START);
		flavorPanel.add(flavorChoice, BorderLayout.CENTER);

		return flavorPanel;
	}

	private void setupGUI() {
		setLayout(new BorderLayout());

		JPanel namePanel = setupNamePanel();

		JPanel detailsPanel = setupDetailPanel();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new HLayout(3));

		JButton addCreature = new JButton("Add Creature");
		addCreature.addActionListener((ev) -> {
			addCreature();
		});

		JButton resetCreature = new JButton("Reset Creature");

		JButton cancelCreature = new JButton("Cancel Creature");
		cancelCreature.addActionListener((ev) -> {
			refFrame.dispose();
		});

		buttonPanel.add(addCreature);
		buttonPanel.add(resetCreature);
		buttonPanel.add(cancelCreature);

		add(namePanel, BorderLayout.PAGE_START);
		add(detailsPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
	}

	private JPanel setupHitdiePanel() {
		HitdieEditor hitdiePanel;

		if (baseCreature != null) {
			hitdiePanel = new HitdieEditor(baseCreature.getHitdice());
		} else {
			hitdiePanel = new HitdieEditor();
		}

		hitdiceModel = hitdiePanel.hitdiceModel;

		return hitdiePanel;
	}

	private JPanel setupNamePanel() {
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());

		JLabel nameLabel = new JLabel("Creature Name: ");
		nameField = new JTextField(255);
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (nameField.getText() == null || nameField.getText().equals("")) {
					refFrame.setTitle("Creature Editor");
				} else {
					refFrame.setTitle("Creature Editor - " + nameField.getText());
				}
			}
		});

		if (baseCreature != null) {
			nameField.setText(baseCreature.getName());
		}

		namePanel.add(nameLabel, BorderLayout.LINE_START);
		namePanel.add(nameField, BorderLayout.CENTER);

		return namePanel;
	}

	private JPanel setupOffensePanel() {
		JPanel offensePanel = new JPanel();
		offensePanel.setLayout(new BorderLayout());

		JPanel attackStatPanel = new JPanel();
		attackStatPanel.setLayout(new HLayout(2));

		SimpleSpinnerPanel baseAttack = new SimpleSpinnerPanel("Base Attack Bonus: ",
				new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		baseAttackField = baseAttack.inputValue;

		SimpleSpinnerPanel grapple = new SimpleSpinnerPanel("Grapple Modifier: ",
				new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));

		grappleField = grapple.inputValue;

		attackStatPanel.add(baseAttack);
		attackStatPanel.add(grapple);

		JTabbedPane offenseDetails = new JTabbedPane();

		normalAttack = new JTextArea();
		normalAttack.setLineWrap(true);
		normalAttack.setWrapStyleWord(true);

		offenseDetails.add("Normal Attack", normalAttack);

		fullAttack = new JTextArea();
		fullAttack.setLineWrap(true);
		fullAttack.setWrapStyleWord(true);

		offenseDetails.addTab("Full Attack", fullAttack);

		AbilityEditor specialAttackEditor = new AbilityEditor();
		specialAttacks = specialAttackEditor.abilityModel;

		offenseDetails.addTab("Special Attacks", specialAttackEditor);

		AbilityEditor specialQualityEditor = new AbilityEditor();
		specialQualities = specialQualityEditor.abilityModel;

		offenseDetails.addTab("Special Qualities", specialQualityEditor);

		if (baseCreature != null) {
			CreatureOffenses offenses = baseCreature.getOffenses();

			baseAttackField.setValue(offenses.getAttackStats().getBaseAttackBonus());
			grappleField.setValue(offenses.getAttackStats().getGrappleMod());

			normalAttack.setText(offenses.getAttack());
			fullAttack.setText(offenses.getFullAttack());

			offenses.getSpecialAttacks().forEach(specialAttacks::addElement);
			offenses.getSpecialQualities().forEach(specialQualities::addElement);
		}

		offensePanel.add(attackStatPanel, BorderLayout.PAGE_START);
		offensePanel.add(offenseDetails, BorderLayout.CENTER);

		return offensePanel;
	}

	private JPanel setupSizeTypePanel() {
		JPanel sizeTypePanel = new JPanel();
		sizeTypePanel.setLayout(new BoxLayout(sizeTypePanel, BoxLayout.PAGE_AXIS));

		JPanel sizePanel = new JPanel();
		sizePanel.setLayout(new HLayout(2));

		JLabel sizeLabel = new JLabel("Size: ");
		sizePicker = new JComboBox<>(CreatureSize.values());

		if (baseCreature != null) {
			sizePicker.setSelectedItem(baseCreature.getSize());
		}

		sizePanel.add(sizeLabel);
		sizePanel.add(sizePicker);

		JPanel typePanel = new JPanel();
		typePanel.setLayout(new HLayout(2));

		JLabel typeLabel = new JLabel("Type: ");
		typePicker = new JComboBox<>(CreatureType.values());

		if (baseCreature != null) {
			typePicker.setSelectedItem(baseCreature.getType());
		}

		typePanel.add(typeLabel);
		typePanel.add(typePicker);

		subtypeModel = new DefaultListModel<>();

		if (baseCreature != null) {
			baseCreature.getSubtypes().forEach(subtypeModel::addElement);
		}

		SimpleListPanel subtypePanel = new SimpleListPanel("Subtype", subtypeModel,
				(string) -> !string.equals(""), (subtype) -> {
					SimpleInternalDialogs.showError(refFrame, "Error",
							"Error: Invalid subtype " + subtype);
				});
		subtypePanel.setBorder(new SimpleTitledBorder("Subtypes"));

		sizeTypePanel.add(sizePanel);
		sizeTypePanel.add(typePanel);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		panel.add(sizeTypePanel, BorderLayout.PAGE_START);
		panel.add(subtypePanel, BorderLayout.CENTER);

		return panel;
	}

	private JPanel setupSkillPanel() {
		JPanel skillPanel = new JPanel();
		skillPanel.setLayout(new BorderLayout());

		JPanel skillCreator = new JPanel();
		skillCreator.setLayout(new BorderLayout());

		JPanel skillInput = new JPanel();
		skillInput.setLayout(new HLayout(2));

		SimpleInputPanel skillName = new SimpleInputPanel("Skill Name: ", 255);

		SimpleSpinnerPanel skillBonus = new SimpleSpinnerPanel("Skill Bonus: ",
				new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));

		skillInput.add(skillName);
		skillInput.add(skillBonus);

		JButton addSkillButton = new JButton("Add Skill");

		skillCreator.add(skillInput, BorderLayout.CENTER);
		skillCreator.add(addSkillButton, BorderLayout.PAGE_END);

		skillModel = new DefaultListModel<>();

		if (baseCreature != null) {
			baseCreature.getMiscData().getSkills().forEach(skillModel::addElement);
		}

		addSkillButton.addActionListener((ev) -> {
			String skillNam = skillName.inputValue.getText();
			int skillBonu = ((SpinnerNumberModel) skillBonus.inputValue.getModel()).getNumber().intValue();

			skillModel.addElement(new CreatureSkill(skillNam, skillBonu));
		});

		JList<CreatureSkill> skillList = new JList<>(skillModel);

		JButton removeSkill = new JButton("Remove Skill");
		removeSkill.addActionListener((ev) -> {
			int selectedIndex = skillList.getSelectedIndex();

			if (selectedIndex >= 0) {
				skillModel.remove(selectedIndex);
			}
		});

		JScrollPane skillScroller = new JScrollPane(skillList);

		skillPanel.add(skillCreator, BorderLayout.PAGE_START);
		skillPanel.add(skillScroller, BorderLayout.CENTER);
		skillPanel.add(removeSkill, BorderLayout.PAGE_END);

		return skillPanel;
	}

	private JPanel setupSpeedPanel() {
		JPanel speedPanel = new JPanel();
		speedPanel.setLayout(new BorderLayout());

		JPanel speedCreator = new JPanel();
		speedCreator.setLayout(new BorderLayout());

		JPanel speedDetails = new JPanel();
		speedDetails.setLayout(new HLayout(2));

		SimpleInputPanel speedType = new SimpleInputPanel("Speed Type: ", 255);

		SimpleSpinnerPanel speedRate = new SimpleSpinnerPanel("Speed Rate",
				new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 5));

		speedDetails.add(speedType);
		speedDetails.add(speedRate);

		JButton addSpeed = new JButton("Add Speed");

		speedCreator.add(speedDetails, BorderLayout.CENTER);
		speedCreator.add(addSpeed, BorderLayout.PAGE_END);

		speeds = new DefaultListModel<>();

		if (baseCreature != null) {
			baseCreature.getSpeeds().forEach(speeds::addElement);
		}

		addSpeed.addActionListener((ev) -> {
			String type = speedType.inputValue.getText();
			int rate = (Integer) speedRate.inputValue.getValue();

			speeds.addElement(new CreatureSpeed(type, rate));
		});

		speedRate.inputValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String type = speedType.inputValue.getText();
					int rate = (Integer) speedRate.inputValue.getValue();

					speeds.addElement(new CreatureSpeed(type, rate));

					speedType.inputValue.requestFocusInWindow();
				}
			}
		});
		JList<CreatureSpeed> speedList = new JList<>(speeds);

		JScrollPane listScroller = new JScrollPane(speedList);

		JButton removeSpeed = new JButton("Remove Speed");
		removeSpeed.addActionListener((ev) -> {
			int selectedIndex = speedList.getSelectedIndex();

			if (selectedIndex >= 0) {
				speeds.remove(selectedIndex);
			}
		});

		speedPanel.add(speedCreator, BorderLayout.PAGE_START);
		speedPanel.add(listScroller, BorderLayout.CENTER);
		speedPanel.add(removeSpeed, BorderLayout.PAGE_END);

		return speedPanel;
	}
}
