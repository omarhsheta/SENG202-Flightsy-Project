-- The database has had a lot of changes since this initial starting query.
-- Do not run to re-create the database.

create table airline (
id_airline integer primary key,
name varchar(25),
alias varchar(3),
iata varchar(2) unique,
icao varchar(3) unique,
callsign varchar(24),
country varchar(40),
active char
);

create table airport (
  id_aiport integer primary key,
  name varchar(25),
  city varchar(25),
  country varchar(40),
  iata varchar(3) unique,
  icao varchar(4) unique,
  latitude float(6, 6),
  longitude float(6, 6),
  altitude integer,
  timezone integer,
  dst char
);

create table route (
  airline varchar(3),
  id_airline int,
  source_airport varchar(4),
  source_airport_id int,
  destination_airport varchar(4),
  destination_airport_id int,
  codeshare char,
  stops int,
  equipment varchar(3),
  primary key (id_airline, source_airport_id, destination_airport_id),
  foreign key (airline) references airline(icao),
  foreign key (id_airline) references airline(id_airline),
  foreign key (source_airport) references airport(icao),
  foreign key (source_airport_id) references airport(id_airport),
  foreign key (destination_airport) references airport(icao),
  foreign key (destination_airport_id) references airport(id_airport)
);

create table plane (
  name varchar(3),
  iata varchar(3) unique,
  icao varchar(4) unique
);
