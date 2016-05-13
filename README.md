# Hair Salon
#### Add a stylist or client

#### by Cory Franks 5/7/2016
## Description
A web page made in java that allows you to add a stylist and then add client/s to only that stylist,  using an SQL database

## Setup/Installation Requirements

* Clone repository
* Install Postgres and start it in terminal/command line
* In a new terminal tab type psql
* CREATE DATABASE hair_salon;
* CREATE TABLE stylist (id serial PRIMARY KEY, name varchar, specialty varchar, gender varchar);
* CREATE TABLE client (id serial PRIMARY KEY, name varchar, stylist_id int);
* CREATE DATABASE  hair_salon_test WITH TEMPLATE hair_salon
* enter "gradle run" in the repositories directory
* in your browser go to "localhost:4567"

## Known Bugs
None so far

## Technologies Used
* Velocity Template Engine
* Spark
* Java
* Gradle
* Postgres
* SQL

### License

MIT License

Copyright (c) 2016 Cory Franks.
