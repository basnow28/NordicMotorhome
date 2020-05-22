# Nordic Motorhome
1st Year Spring Boot Web Application Final Project - KEA

In order to run the project on your computer, make sure you have maven installed. 

Run 
```bash
$mvn -version 
```
to check if you have it on your machine.

If it is not installed, follow instructions from https://www.baeldung.com/install-maven-on-windows-linux-mac .

Once you made sure maven is installed, pull the project and inside the main project folder run: 
```bash
$mvn install
```

This command will build maven project for you. Once that is done, you can run the application and see it on localhost:1212. 


# Database

In order to run the project on your machine, make sure you have MySQL and MySQL Workbench installed on your computer (or any other administration tool for local servers). See https://overiq.com/installing-mysql-windows-linux-and-mac/ for further instructions.

Create a nordic_database schema:
```bash
CREATE SCHEMA `nordic_database` ;
```
Create a new user in the database: 
```bash
CREATE USER 'nordic'@'localhost' IDENTIFIED BY 'Nordic@Password1!';
```
Grant all the priviliges in the nordic_database
```bash
GRANT ALL ON nordic_database.* TO 'nordic'@'localhost';
FLUSH PRIVILEGES;
```

Once that is done, run below queries to create tables and insert basic data:

# Tables
by David Hards @dshards <https://github.com/dshards>

```bash
CREATE TABLE card_information (
card_id			INT		NOT NULL	AUTO_INCREMENT,
card_number		VARCHAR(20)	NOT NULL,
card_expiry_date	VARCHAR(45)	NOT NULL,
card_cvv		INT		NOT NULL,
PRIMARY KEY(card_id)
);
```

```bash
CREATE TABLE seasons (
season_id		INT		NOT NULL	AUTO_INCREMENT,
season_name		VARCHAR(45)	NOT NULL,
season_start		VARCHAR(45)	NOT NULL,
season_end		VARCHAR(45)	NOT NULL,
season_rate		DOUBLE		NOT NULL,
PRIMARY KEY(season_id)
);
```


```bash
CREATE TABLE employees (
employee_id		INT		NOT NULL	AUTO_INCREMENT,
employee_first_name	VARCHAR(45)	NOT NULL,
employee_last_name	VARCHAR(45)	NOT NULL,
employee_type		VARCHAR(45)	NOT NULL,
employee_login		VARCHAR(45)	NOT NULL,
employee_password	VARCHAR(45)	NOT NULL,
PRIMARY KEY(employee_id)
);

```


```bash
CREATE TABLE addresses (
address_id		INT		NOT NULL	AUTO_INCREMENT,
street_name		VARCHAR(45)	NOT NULL,
house_number		VARCHAR(45)	NOT NULL,
postcode		INT		NOT NULL,
city			VARCHAR(45)	NOT NULL,
country			VARCHAR(45)	NOT NULL,
PRIMARY KEY(address_id)
);
```


```bash
CREATE TABLE cancellations (
cancellation_id		INT		NOT NULL	AUTO_INCREMENT,
days_out		INT	        NOT NULL,
minimum_fee		INT,
cancellation_rate	DOUBLE		NOT NULL,
PRIMARY KEY(cancellation_id)
);
```


```bash
CREATE TABLE vehicle_types (
vehicle_type_id		INT		NOT NULL	AUTO_INCREMENT,
vehicle_type_name	VARCHAR(45)	NOT NULL,
cost_per_day		INT		NOT NULL,
vehicle_capacity	INT		NOT NULL,
fuel_type		VARCHAR(45)	NOT NULL,
storage_size		INT		NOT NULL,
kitchen			TINYINT,
shower			TINYINT,
wifi			TINYINT,
tv			TINYINT,
PRIMARY KEY(vehicle_type_id)
);

```


```bash
CREATE TABLE customers (
customer_id		INT		NOT NULL	AUTO_INCREMENT,
first_name		VARCHAR(45)	NOT NULL,
last_name		VARCHAR(45)	NOT NULL,
date_of_birth		DATE		NOT NULL,
phone_number		VARCHAR(45)	NOT NULL,
email			VARCHAR(45)	NOT NULL,
driver_licence_number	VARCHAR(45)	NOT NULL,
address_id		INT		NOT NULL,
PRIMARY KEY(customer_id),
FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);
```

```bash
CREATE TABLE vehicles (
vehicle_id		INT		NOT NULL	AUTO_INCREMENT,
vehicle_brand		VARCHAR(45)	NOT NULL,
vehicle_model		VARCHAR(45)	NOT NULL,
licence_plate		VARCHAR(45)	NOT NULL,
vehicle_odometer	INT		NOT NULL,
vehicle_status		VARCHAR(45)	NOT NULL,
additional_notes	VARCHAR(500)	NOT NULL,
mechanic_status		VARCHAR(45)	NOT NULL,
cleaning_status		VARCHAR(45)	NOT NULL,
vehicle_type_id		INT		NOT NULL,
PRIMARY KEY(vehicle_id),
FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(vehicle_type_id)
);
```

```bash
CREATE TABLE bookings (
booking_id		INT		NOT NULL	AUTO_INCREMENT,
start_date		DATE		NOT NULL,
end_date		DATE		NOT NULL,
distance_driven		INT,
booking_status		VARCHAR(20)	NOT NULL,
payment_amount		INT,
fuel_check		TINYINT,
booking_notes		VARCHAR(500),
has_picnic		TINYINT,
has_bikerack		TINYINT,
has_dvd_player		TINYINT,
has_tent		TINYINT,
has_linen		TINYINT,
vehicle_id		INT		NOT NULL,
employee_id		INT		NOT NULL,
season_id		INT		NOT NULL,
cancellation_id		INT		NOT NULL,
customer_id		INT		NOT NULL,
card_id			INT		NOT NULL,
FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),		
FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
FOREIGN KEY (season_id) REFERENCES seasons(season_id),
FOREIGN KEY (cancellation_id) REFERENCES cancellations(cancellation_id),
FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
FOREIGN KEY (card_id) REFERENCES card_information(card_id),
PRIMARY KEY(booking_id)
);
```

```bash
CREATE TABLE payments (
payment_id		INT		NOT NULL	AUTO_INCREMENT,
payment_amount		INT		NOT NULL,
card_id			INT		NOT NULL,
booking_id		INT		NOT NULL,
FOREIGN KEY (card_id) REFERENCES card_information(card_id),
FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
PRIMARY KEY(payment_id)
);
```

# Data

by Dagmara Przygocka @dprzygocka <https://github.com/dprzygocka>
```bash
INSERT INTO vehicle_types (vehicle_type_id,vehicle_type_name, cost_per_day, vehicle_capacity, fuel_type, storage_size, kitchen, shower, wifi, tv) VALUES
(1,"Class A", 599, 6, "Diesel", 20, true,true,true,true),
(2,"Class B", 449, 3, "Gasoline", 15,true, true,true, true ),
(3,"Class C", 349, 4, "Diesel", 10, false,true,false, true ),
(4,"Travel Trailer", 149, 4, "Diesel", 5,false, true, false,false),
(5,"5 Wheel trailer", 399, 6, "Gasoline", 15,true, true,true, true),
(6,"Folding tent trailer", 99, 2, "Natural Gas", 2,false,false,false,false),
(7,"Luton", 399, 4, "Diesel/Gas", 10,true, true,false, true ),
(8,"Micro/Campervan", 99, 2, "Natural Gas", 2,false,false,false,false);
```

```bash
INSERT INTO employees (employee_first_name, employee_last_name, employee_type, employee_login, employee_password) VALUES("David", "Hards", "Sale assistant", "dhards", "qoehg2741"),("Barbara", "Rekowska", "Sale assistant", "brekowska", "cksle3391"),("Maja", "Miskeri", "Sale assistant", "mmiskeri", "lspwh0021"),("Dagmara", "Przygocka", "Sale assistant", "dprzygocka", "dkagk3885"),("Thomas", "Jensen", "Cleaner", "tjensen", "gggls2017"),("Alex", "Pedersen", "Cleaner", "apedersen", "melsh8826"),("Maria", "Hansen", "Mechanic", "mhansen", "llsik3078"),("Arturo", "Jensen", "Bookkeeper", "ajensen", "dksla2534");
```

by Barbara Rekowska @basnow28 <https://github.com/basnow28>

```bash
INSERT INTO vehicles 
(vehicle_id, vehicle_brand, vehicle_model, licence_plate, vehicle_odometer, vehicle_status, additional_notes, mechanic_status, cleaning_status, vehicle_type_id)
VALUES 
(1, "American Dream", "42Q", "L-955q", 99560, "READY", "", "READY", "READY", 1),
(2,"Coachmen Sportscoach", "SRS 339DS", "I-627K", 86425, "READY", "", "READY", "READY", 1),
(3,"Entegra Cornerstone", "45Y", "I-2192", 72584, "READY", "", "READY", "READY", 2),
(4,"Entegra Cornerstone", "45Y", "M-134h", 125425, "READY", "", "READY", "READY", 2),
(5,"Entegra Emblem", "36H", "M-213D", 89545, "READY", "", "READY", "READY", 2),
(6,"Entegra Emblem", "36H", "J-448n", 50585, "READY", "", "READY", "READY", 2),
(7,"Fleetwood Discovery", "38W", "J-261n", 60985, "READY", "", "READY", "READY", 3),
(8,"Fleetwood Discovery", "38W", "J-828f", 100585, "READY", "", "READY", "READY", 3),
(9,"Fleetwood Pace Arrow", "35 QS", "J-344C", 50585, "READY", "", "READY", "READY", 3),
(10,"Fleetwood Pace Arrow", "35 QS", "H-301J", 150585, "READY", "", "READY", "READY", 3),
(11,"Forest River Berkshire", "XLT 45B", "H-350E", 50585, "READY", "", "READY", "READY", 3),
(12,"Forest River Berkshire", "XLT 45B", "H-482x", 50585, "READY", "", "READY", "READY", 3),
(13,"Forest River Georgetown", "FR3 33DS", "H-765G", 140590, "READY", "", "READY", "READY", 4),
(14,"Forest River Georgetown", "FR3 33DS", "H-331S", 145875, "READY", "", "READY", "READY", 4),
(15,"Forest River Georgetown", "GT3 33B3", "P-771o", 30905, "READY", "", "READY", "READY", 4),
(16,"Forest River Georgetown", "GT3 33B3", "P-511D", 230525, "READY", "", "READY", "READY", 4),
(17,"Foretravel", "ih-45", "P-113g", 150555, "READY", "", "READY", "READY", 5),
(18,"Foretravel", "ih-45", "P-113g", 350665, "READY", "", "READY", "READY", 5),
(19,"Newell Coach", "#1659", "P-793N", 45000, "READY", "", "READY", "READY", 5),
(20,"Newell Coach", "#1659", "P-2110", 98745, "READY", "", "READY", "READY", 5),
(21,"Newmar", "Dutch Star 4328", "P-351v", 80055, "READY", "", "READY", "READY", 6),
(22,"Newmar", "Dutch Star 4328", "P-309g", 120425, "READY", "", "READY", "READY", 6), 
(23,"Newmar", "Essex 4551", "P-815S", 150585, "READY", "", "READY", "READY", 6), 
(24,"Newmar", "Essex 4551", "P-6210", 140855, "READY", "", "READY", "READY", 6), 
(25,"NeXus", "Bentley Diamond", "P-6210", 120585, "READY", "", "READY", "READY", 7), 
(26, "NeXus", "Bentley Diamond", "P-168c", 130695, "READY", "", "READY", "READY", 7),
(27, "NeXus", "Evoque", "P-1224", 68745, "READY", "", "READY", "READY", 7),
(28, "NeXus", "Evoque", "P-707t", 235500, "READY", "", "READY", "READY", 7),
(29, "Tiffin Allegro", "40IP", "D-7671", 95400, "READY", "", "READY", "READY", 8),
(30,"Tiffin Allegro", "40IP", "D-783m", 97480, "READY", "", "READY", "READY", 8 ),
(31, "Winnebago Adventurer", "36Z", "D-540N", 65280, "READY", "", "READY", "READY", 8),
(32, "Winnebago Adventurer", "36Z", "D-6320", 360450, "READY", "", "READY", "READY", 8);
```
by David Hards @dshards <https://github.com/dshards>
```bash
INSERT INTO seasons (season_name, season_start, season_end, season_rate)
VALUES("Low Season", '12-01', '02-28', 1),
("Mid Season", '03-01', '05-31', 1.3),
("High Season", '06-01', '09-30', 1.6),
("Mid Season", '10-01', '11-30', 1.3);
```
```bash
INSERT INTO cancellations(days_out, minimum_fee, cancellation_rate)
VALUES(50, 200, 0.2),
(15, 200, 0.5),
(1, 200, 0.8),
(0, 200, 0.95);
```
