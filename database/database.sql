CREATE DATABASE cardgame;

CREATE TABLE cards (
	id SERIAL,
	name VARCHAR(90),
	typeCard VARCHAR(30),
	description VARCHAR(120)
);

CREATE TABLE decks (
	id SERIAL,
	name VARCHAR(90),
	cards JSON,
	description VARCHAR(120)
);