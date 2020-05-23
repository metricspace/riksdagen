drop database if exists riksdagen;
create database riksdagen charset utf8 collate utf8_swedish_ci;
create user if not exists 'someuser'@'localhost' identified by 'somepassword';
grant all on riksdagen.* to 'someuser'@'localhost';
use riksdagen;

create table betankande (
  id VARCHAR(16) NOT NULL PRIMARY KEY,
  beteckning VARCHAR(8) NOT NULL,
  datum date NOT NULL,
  dokument_typ VARCHAR(8) NOT NULL,
  dokument_url_html VARCHAR(64) NOT NULL,
  riks_mote VARCHAR(8) NOT NULL,
  titel VARCHAR(250) NOT NULL
);


create table dir (
  id VARCHAR(16) NOT NULL PRIMARY KEY,
  beteckning int NOT NULL,
  datum date NOT NULL,
  dokument_typ VARCHAR(8) NOT NULL,
  dokument_url_html VARCHAR(64) NOT NULL,
  riks_mote VARCHAR(8) NOT NULL,
  titel VARCHAR(250) NOT NULL
);

create table fraga (
  id VARCHAR(16) NOT NULL PRIMARY KEY,
  beteckning int NOT NULL,
  datum date NOT NULL,
  dokument_typ VARCHAR(8) NOT NULL,
  dokument_url_html VARCHAR(64) NOT NULL,
  riks_mote VARCHAR(8) NOT NULL,
  titel VARCHAR(250) NOT NULL
);

create table interpellation (
  id VARCHAR(16) NOT NULL PRIMARY KEY,
  beteckning int NOT NULL,
  datum date NOT NULL,
  dokument_typ VARCHAR(8) NOT NULL,
  dokument_url_html VARCHAR(64) NOT NULL,
  riks_mote VARCHAR(8) NOT NULL,
  titel VARCHAR(250) NOT NULL
);

create table motion (
  id VARCHAR(16) NOT NULL PRIMARY KEY,
  beteckning int NOT NULL,
  datum date NOT NULL,
  dokument_typ VARCHAR(8) NOT NULL,
  dokument_url_html VARCHAR(64) NOT NULL,
  riks_mote VARCHAR(8) NOT NULL,
  titel VARCHAR(250) NOT NULL
);

create table proposition (
  id VARCHAR(16) NOT NULL PRIMARY KEY,
  beteckning int NOT NULL,
  datum date NOT NULL,
  dokument_typ VARCHAR(8) NOT NULL,
  dokument_url_html VARCHAR(64) NOT NULL,
  riks_mote VARCHAR(8) NOT NULL,
  titel VARCHAR(250) NOT NULL
);

create table rir (
  id VARCHAR(16) NOT NULL PRIMARY KEY,
  beteckning int NOT NULL,
  datum date NOT NULL,
  dokument_typ VARCHAR(8) NOT NULL,
  dokument_url_html VARCHAR(64) NOT NULL,
  riks_mote VARCHAR(8) NOT NULL,
  titel VARCHAR(128) NOT NULL
);

create table sou (
  id VARCHAR(16) NOT NULL PRIMARY KEY,
  beteckning int NOT NULL,
  datum date NOT NULL,
  dokument_typ VARCHAR(8) NOT NULL,
  dokument_url_html VARCHAR(64) NOT NULL,
  riks_mote VARCHAR(8) NOT NULL,
  titel VARCHAR(250) NOT NULL
);
