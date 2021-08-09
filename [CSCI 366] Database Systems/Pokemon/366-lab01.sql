--Nicholas Bellman


-- Question 1 --
CREATE TABLE pokemon (
  name VARCHAR(50),
  type_1 VARCHAR(10),
  type_2 VARCHAR(10),
  total INTEGER,
  hit_points INTEGER,
  attack INTEGER,
  defense INTEGER,
  special_attack INTEGER,
  special_defense INTEGER,
  speed INTEGER,
  generation VARCHAR(3),
  legendary BOOLEAN,
    PRIMARY KEY (name));
-- psql response: CREATE TABLE


-- Question 3 --
INSERT INTO pokemon (
  name, 
  type_1, 
  total, 
  hit_points, 
  attack, 
  defense, 
  special_attack, 
  special_defense, 
  speed, 
  generation, 
  legendary)
  VALUES (
    'Marauder', 
    'Fighting', 
    400, 
    100, 
	70, 
	50, 
	100, 
	30, 
	50, 
	'6', 
	TRUE);
-- psql response: INSERT 0 1


-- Question 4 --
SELECT name 
  FROM pokemon 
  WHERE legendary = TRUE 
    AND generation = '1';
/* psql response:
        name
---------------------
 Articuno
 Zapdos
 Moltres
 Mewtwo
 MewtwoMega Mewtwo X
 MewtwoMega Mewtwo Y
(6 rows)
*/

-- Question 5 --
SELECT name
  FROM pokemon
  WHERE speed > 150
    AND attack > defense;
/* psql response:
       name
-------------------
 Ninjask
 DeoxysSpeed Forme
(2 rows)
*/

-- Question 6 --
SELECT type_1, type_2 
  FROM pokemon
  WHERE speed > 40 
    AND speed < 45;
/* psql response:
  type_1  | type_2
----------+--------
 Water    |
 Poison   |
 Psychic  |
 Water    |
 Rock     | Ground
 Rock     | Grass
 Dragon   | Ground
 Normal   |
 Normal   | Flying
 Bug      | Grass
 Bug      | Grass
 Ice      |
 Fairy    |
 Fighting |
 Poison   | Dragon
 Water    |
 Ghost    | Grass
(17 rows)
*/


-- Question 7 --
SELECT COUNT(*) 
  FROM pokemon;
/* psql response:
 count
-------
   801
(1 row)
*/


-- Question 8 --
SELECT MAX(hit_points)
  FROM pokemon
  WHERE generation = '3';
/* psql response:
 max
-----
 170
(1 row)
*/


-- Question 9 --
SELECT name
  FROM pokemon 
  WHERE name LIKE 'Z%';
/* psql response:
       name
------------------
 Zubat
 Zapdos
 Zigzagoon
 Zangoose
 Zebstrika
 Zorua
 Zoroark
 Zweilous
 Zekrom
 Zygarde50% Forme
(10 rows)
*/
                                                                               

-- Question 10 --
DELETE FROM pokemon 
  WHERE name = 'Marauder';
-- psql response: DELETE 1