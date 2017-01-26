create table FeatTags (
	name varchar(255),
	description text not null,
	primary key(name)
);

create index FeatTags_name on FeatTags(name);

create table Feats (
	id serial,
	name varchar(255),
	flavor text,
	description text not null,
	source text,
	nonfeat_prereqs varchar(255) array,
	primary key(id)
);

create index Feats_name on Feats(name);

create table Feat_tags (
	featid int,
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

create type abilitytype as enum (
	'extraordinary', 'inherent', 'psilike',
	'spelllike', 'supernatural'
);

create type racetype as enum (
	'aberration', 'animal', 'construct', 'deathless',
	'dragon','elemental', 'fey', 'giant',
	'humanoid', 'magicalbeast', 'monstroushumanoid', 'ooze',
	'outsider', 'plant', 'undead', 'vermin'
);

create type speed as (
	method varchar(255),
	rate int
);

create type skill as (
	name varchar(255),
	bonus int
);

create type objectsize as enum (
	'fine', 'diminutive', 'tiny', 'small', 'medium',
	'large','huge', 'gargantuan', 'colossal'
);

create table Abilities (
	id serial,
	name varchar(255),
	class abilitytype,
	description text,
	primary key(id)
);

create index Abilities_name on Abilities(name);
create index Abilities_typedname on Abilities(name, class);

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
	monsterid varchar(255),
	source varchar(255),
	count int,
	amount int,
	primary key(monsterid),
	foreign key(monsterid) references Monsters(id)
);

create view Monsters_totalhd as
	select m.id, m.name, sum(hd.count) as totalhd 
	from Monsters as m join Monster_hitdice as hd on m.id = hd.monsterid
	group by m.id, hd.monsterid;
