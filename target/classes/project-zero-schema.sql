/*
 * Drop tables and views at the start of each script run
 */
DROP VIEW get_health_inspector_fridges ;
DROP TABLE food;
DROP TABLE h_inspector_fridge_junction;
DROP TABLE refrigerators;
DROP TABLE accounts;


/*
 * Schema for project 0 part 2
 */
CREATE TABLE accounts(
	acct_username VARCHAR(32) PRIMARY KEY,
	acct_password VARCHAR(32) NOT NULL
);

CREATE TABLE refrigerators(
	r_id SERIAL PRIMARY KEY,
	r_name VARCHAR(32) NOT NULL,
	owner_name VARCHAR(32) NOT NULL,
	FOREIGN KEY (owner_name) REFERENCES accounts (acct_username) ON DELETE CASCADE
);

CREATE TABLE food(
	f_id SERIAL PRIMARY KEY,
	food_name VARCHAR(32) NOT NULL,
	fridge_id INTEGER NOT NULL,
	FOREIGN KEY (fridge_id) REFERENCES refrigerators (r_id) ON DELETE CASCADE
);

CREATE TABLE h_inspector_fridge_junction(
	acct_name VARCHAR(32) NOT NULL,	
	fridge_id INTEGER NOT NULL,
	CONSTRAINT acct_fridge_ref_id PRIMARY KEY (acct_name, fridge_id),
	FOREIGN KEY (acct_name) REFERENCES accounts (acct_username) ON DELETE CASCADE,
	FOREIGN KEY (fridge_id) REFERENCES refrigerators (r_id) ON DELETE CASCADE
);


------Random data inserted for testing purposes
INSERT INTO accounts VALUES('colin', 'colin');
INSERT INTO accounts VALUES('knox', 'knox');
INSERT INTO accounts VALUES('health', 'health');
INSERT INTO accounts VALUES('inspector', 'inspector');

INSERT INTO refrigerators VALUES(DEFAULT, 'myFridge', 'colin');
INSERT INTO refrigerators VALUES(DEFAULT, 'myFridge2', 'colin');
INSERT INTO refrigerators VALUES(DEFAULT, 'myFridge3', 'colin');
INSERT INTO refrigerators VALUES(DEFAULT, 'myFridge', 'knox');

INSERT INTO h_inspector_fridge_junction VALUES('inspector', 1);
INSERT INTO h_inspector_fridge_junction VALUES('inspector', 2);

INSERT INTO food VALUES (DEFAULT, 'grapes', 1);
INSERT INTO food VALUES (DEFAULT, 'oranges', 1);
INSERT INTO food VALUES (DEFAULT, 'strawberries', 2);
INSERT INTO food VALUES (DEFAULT, 'eggs', 3);


-------VIEW TABLES
SELECT a.acct_username, r.r_id, r.r_name
FROM accounts a
INNER JOIN refrigerators r
ON r.owner_name = a.acct_username;

--DELETE FROM accounts WHERE acct_username = 'colin';

SELECT * FROM refrigerators r ;
SELECT * FROM accounts ;
SELECT * FROM food f ;
SELECT * FROM h_inspector_fridge_junction hifj ;

/*
 * Very important view for dao to obtain fridges assigned to
 * health inspectors
 */
CREATE VIEW get_health_inspector_fridges AS
SELECT DISTINCT a.acct_username, r.r_id, r.r_name , r.owner_name 
FROM accounts a
INNER JOIN h_inspector_fridge_junction h
ON a.acct_username = h.acct_name
INNER JOIN refrigerators r 
ON r.r_id = h.fridge_id
INNER JOIN food f 
ON r.r_id = f.fridge_id;

SELECT * FROM get_health_inspector_fridges
INNER JOIN food f 
ON r_id = f.fridge_id ;

SELECT *
FROM accounts a 
INNER JOIN refrigerators r
ON a.acct_username = r.owner_name;