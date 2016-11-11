package bjc.dbeditor.data.creatures.builders;

import java.math.BigDecimal;

import bjc.utils.funcdata.FunctionalList;

import bjc.dbeditor.data.creatures.Creature;
import bjc.dbeditor.data.creatures.CreatureAbilityScores;
import bjc.dbeditor.data.creatures.CreatureDefenses;
import bjc.dbeditor.data.creatures.CreatureFlavor;
import bjc.dbeditor.data.creatures.CreatureHitdieRecord;
import bjc.dbeditor.data.creatures.CreatureMisc;
import bjc.dbeditor.data.creatures.CreatureOffenses;
import bjc.dbeditor.data.creatures.CreatureSize;
import bjc.dbeditor.data.creatures.CreatureSpeed;
import bjc.dbeditor.data.creatures.CreatureType;

/** 
 * Class for building instances of creatures
 */
public class CreatureBuilder {
		// Fields for storing builder data
		private String									name;

		private CreatureSize							size;

		private CreatureType							type;
		private FunctionalList<String>					subtypes;

		private FunctionalList<CreatureHitdieRecord>	hitdice;

		private FunctionalList<CreatureSpeed>			speeds;

		private CreatureDefenses						defenses;

		private CreatureOffenses						offenses;

		private CreatureAbilityScores					abilityScores;

		private FunctionalList<String>					feats;

		private BigDecimal								challengeRating;
		private String									levelAdjustment;

		private String									advancement;

		private CreatureMisc							miscData;

		private CreatureFlavor							flavor;

		/**
		 * Create a new blank builder
		 */
		public CreatureBuilder() {
				// Initialize the lists as blank
				subtypes = new FunctionalList<>();
				hitdice = new FunctionalList<>();
				speeds = new FunctionalList<>();
				feats = new FunctionalList<>();
		}

		/*
		 * A bunch of self-explanatory builder functions 
		 */

		public void setName(String name) {
				this.name = name;
		}

		public void setSize(CreatureSize size) {
				this.size = size;
		}

		public void setType(CreatureType type) {
				this.type = type;
		}

		public void setChallengeRating(BigDecimal challengeRating) {
				this.challengeRating = challengeRating;
		}

		public void setLevelAdjustment(String levelAdjustment) {
				this.levelAdjustment = levelAdjustment;
		}

		public void setAdvancement(String advancement) {
				this.advancement = advancement;
		}

		public void setDefenses(CreatureDefenses defenses) {
				this.defenses = defenses;
		}

		public void setOffenses(CreatureOffenses offenses) {
				this.offenses = offenses;
		}

		public void setAbilityScores(CreatureAbilityScores scores) {
				abilityScores = scores;
		}

		public void setMiscData(CreatureMisc miscData) {
				this.miscData = miscData;
		}

		public void setFlavor(CreatureFlavor flavor) {
				this.flavor = flavor;
		}

		public void addSubtype(String subtype) {
				subtypes.add(subtype);
		}

		public void addHitdie(int count, int siz, String typ) {
				hitdice.add(new CreatureHitdieRecord(count, siz, typ));
		}

		public void addHitdie(CreatureHitdieRecord hitdie) {
				hitdice.add(hitdie);
		}

		public void addSpeed(String typ, int rate) {
				speeds.add(new CreatureSpeed(typ, rate));
		}

		public void addSpeed(CreatureSpeed speed) {
				speeds.add(speed);
		}

		public void addFeat(String feat) {
				feats.add(feat);
		}

		/**
		 * Convert this builder into a creature
		 */
		public Creature buildCreature() {
				return new Creature(name, size, type, subtypes, hitdice, speeds,
								defenses, offenses, abilityScores, challengeRating,
								levelAdjustment, advancement, miscData, flavor);
		}
}
