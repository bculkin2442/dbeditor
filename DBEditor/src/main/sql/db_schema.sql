-- ====
-- Types
-- ====
create type abilitytype as enum (
	'extraordinary', 'inherent', 'psilike',
	'spelllike', 'supernatural'
);

create type racetype as enum (
	'aberration', 'animal',       'construct',         'deathless',
	'dragon',     'elemental',    'fey',               'giant',
	'humanoid',   'magicalbeast', 'monstroushumanoid', 'ooze',
	'outsider',   'plant',        'undead',            'vermin'
);

create type objectsize as enum (
	'fine',  'diminutive', 'tiny',
	'small', 'medium',     'large',
	'huge',  'gargantuan', 'colossal'
);

create type bloodlinestrength as enum (
	'minor', 'intermediate', 'major'
);

create type speed as (
	method varchar(255),
	rate int
);

create type skill as (
	name varchar(255),
	bonus int
);
-- ====
-- Feat Tags
-- ====
create table FeatTags (
	name        varchar(255),
	description text          not null,

	primary key(name)
);

create index FeatTags_name on FeatTags(name);

-- ====
-- Feats
-- ====
create table Feats (
	id              serial,
	name            varchar(255),
	flavor          text,
	description     text                not null,
	source          text,
	nonfeat_prereqs varchar(255) array,

	primary key(id)
);

create index Feats_name on Feats(name);

create table Feat_tags (
	featid  int,
	tagname varchar(255),

	primary key(featid, tagname),

	foreign key(featid) references Feats(id),
	foreign key(tagname) references FeatTags(name)
);

create table Feat_prereqs (
	featid int,
	prereqid int,
	primary key(featid, prereqid),
	foreign key(featid) references Feats(id),
	foreign key(prereqid) references Feats(id)
);

-- ====
-- Abilities
-- ====
create table Abilities (
	id serial,
	name varchar(255),
	class abilitytype,
	description text,
	primary key(id)
);

create index Abilities_name on Abilities(name);
create index Abilities_typedname on Abilities(name, class);

-- ====
-- Monsters
-- ====
create table Monsters (
	id serial,
	name varchar(255),
	sze objectsize,
	class racetype,
	subtypes varchar(255) array,
	natarmor int,
	sr int,
	attack text,
	fullattack text,
	enviroment text,
	organization varchar(255),
	cr decimal,
	la int,
	treasure text,
	alignment varchar(255),
	advancement text,
	description text,
	notes text,
	source text,
	saves int array,
	attackstat int array,
	abilityscores int array,
	skills skill array,
	speeds speed array,
	primary key(id)
);

create index Monsters_name on Monsters(name);
create index Monsters_cr on Monsters(cr);
create index Monsters_la on Monsters(la);

create table Monster_abilities (
	monsterid int,
	abilityid int,
	isquality boolean,
	primary key(monsterid, abilityid),
	foreign key(monsterid) references Monsters(id),
	foreign key(abilityid) references Abilities(id)
);

create table Monster_feats (
	monsterid int,
	featid int,
	primary key(monsterid, featid),
	foreign key(monsterid) references Monsters(id),
	foreign key(featid) references Feats(id)
);

create table Monster_hitdice (
	monsterid int,
	source varchar(255),
	count int,
	amount int,
	primary key(monsterid),
	foreign key(monsterid) references Monsters(id)
);

create view Monsters_saves as 
	select m.id, m.name, 
	m.saves[1] as fortsave, m.saves[2] as refsave, m.saves[3] as willsave
	from Monsters as m;

create view Monsters_abilities as
	select m.id, m.name,
	m.abilityscores[1] as strength, m.abilityscores[2] as dexterity, m.abilityscores[3] as constitution,
	m.abilityscores[4] as intelligence, m.abilityscores[5] as wisdom, m.abilityscores[6] as charisma
	from Monsters as m;

create view Monsters_totalhd as
	select m.id, m.name, sum(hd.count) as totalhd 
	from Monsters as m join Monster_hitdice as hd on m.id = hd.monsterid
	group by m.id, hd.monsterid;

create view Monsters_playerdetails as
	select m.id, m.name, m.la, m.class, hd.totalhd, (m.la + hd.totalhd) as effectivela
	from Monsters as m join Monsters_totalhd as hd on m.id = hd.id;

-- ====
-- Templates
-- ====
create table Templates (
	id serial,
	name varchar(255),
	origin racetype array,
	destinationtype racetype,
	destinationsubtypes varchar(255) array,
	hitdice varchar(255),
	speed varchar(255),
	saves int array,
	abilityscores int array,
	skilladj text,
	featadj text,
	climate text,
	organization text,
	cradj text,
	la int,
	lanotes text,
	cohortonly boolean,
	treasure text,
	alignment text,
	advancement text,
	flavor text,
	notes text,
	source text,
	primary key(id)
);

create index Templates_name on Templates(name);
create index Templates_origin on Templates(origin);
create index Templates_destination on Templates(destinationtype);
create index Templates_origindest on Templates(origin, destinationtype);
create index Templates_la on Templates(la);

create table Template_abilities (
	templateid int,
	abilityid int,
	isquality boolean,
	primary key(templateid, abilityid),
	foreign key(templateid) references Templates(id),
	foreign key(abilityid) references Abilities(id)
);

create view Templates_abilityadj as
	select t.id, t.name,
	t.abilityscores[1] as strength, t.abilityscores[2] as dexterity, t.abilityscores[3] as constitution,
	t.abilityscores[4] as intelligence, t.abilityscores[5] as wisdom, t.abilityscores[6] as charisma
	from Templates as t;

create view Templates_saveadj as 
	select t.id, t.name, 
	t.saves[1] as fortsave, t.saves[2] as refsave, t.saves[3] as willsave
	from Templates as t;

/* query to find templates with a certain destination type
 * select t.id, t.name, t.la from Templates as t where ? = t.destinationtype order by t.la
 * 
 * query to find templates with a certain origin type
 * select t.id, t.name, t.la from Templates as t where ? = any (t.origin) order by t.la
 * 
 * query to find templates from a origin to a destination
 * select t.id, t.name, t.la from Templates as t where ? = any (t.origin) and ? = t.destinationtype order by la
 */
 
-- ====
-- Races
-- ====
create table Races (
	id serial,
	name varchar(255),
	sze objectsize,
	class racetype,
	subtypes varchar(255) array,
	speeds speed,
	autolanguages varchar(255) array,
	bonuslanguages varchar(255) array,
	abilityscores int array,
	la int,
	primary key(id)
);

create index Races_name on Races(name);
create index Races_class on Races(class);
create index Races_la on Races(la);

create table Race_abilities (
	raceid int,
	abilityid int,
	primary key(raceid, abilityid),
	foreign key(raceid) references Races(id),
	foreign key(abilityid) references Abilities(id)
);

create table Race_hitdice (
	raceid int,
	source varchar(255),
	count int,
	amount int,
	primary key(raceid),
	foreign key(raceid) references Races(id)
);

create view Races_abilityadj as
	select r.id, r.name,
	r.abilityscores[1] as strength, r.abilityscores[2] as dexterity, r.abilityscores[3] as constitution,
	r.abilityscores[4] as intelligence, r.abilityscores[5] as wisdom, r.abilityscores[6] as charisma
	from Races as r;

create view Races_totalhd as
	select r.id, r.name, sum(hd.count) as totalhd 
	from Races as r join Race_hitdice as hd on r.id = hd.raceid
	group by r.id, hd.raceid;

create view Races_playerdetails as
	select r.id, r.name, r.la, r.class, hd.totalhd, (r.la + hd.totalhd) as effectivela
	from Races as r join Races_totalhd as hd on r.id = hd.id;
	group by m.id, hd.monsterid
);

-- ====
-- Bloodlines
-- ====
create table Bloodlines (
	id serial,
	name varchar(255) NOT NULL,
	description text,
	strength bloodlinestrength NOT NULL,

	primary key(bloodlineid)
);

create table Bloodline_Abilities (
	bloodlineid int,
	level int,
	name varchar(255),
	description varchar(255),

	primary key(bloodlineid, level)
)
