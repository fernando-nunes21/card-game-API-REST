CREATE DATABASE cardgame;

CREATE TABLE cards (
	id SERIAL,
	name VARCHAR(90),
	type_card VARCHAR(30),
	description VARCHAR(120)
);

CREATE TABLE decks (
	id SERIAL,
	name VARCHAR(90),
	id_cards INTEGER[],
	description VARCHAR(120)
);