/* Nicholas Bellman */
/* Lab 03: CD (full) */

/* PROBLEM 1 BEGIN */
CREATE TABLE label (
    lbltitle VARCHAR(30),
    lblstreet VARCHAR(50),
    lblcity VARCHAR(30),
    lblstate VARCHAR(30),
    lblpostcode VARCHAR(11),
    lblnation VARCHAR(50),
    PRIMARY KEY (lbltitle)
);

CREATE TABLE cd (
    cdid INT,
    cdlblid VARCHAR (15),
    cdtitle VARCHAR(50),
    cdyear VARCHAR(4),
    lbltitle VARCHAR(30),
    PRIMARY KEY (cdid),
    FOREIGN KEY (lbltitle) REFERENCES label(lbltitle)
);

CREATE TABLE person (
    psnid INT,
    psnfname VARCHAR(30),
    psnlname VARCHAR(30),
    PRIMARY KEY (psnid)
);

CREATE TABLE composition (
    compid INT,
    comptitle VARCHAR(30),
    compyear VARCHAR(30),
    PRIMARY KEY (compid)
);

CREATE TABLE person_cd (
    psncdorder VARCHAR(10),
    psnid INT,
    cdid INT,
    PRIMARY KEY (psnid, cdid),
    FOREIGN KEY (psnid) REFERENCES person(psnid),
    FOREIGN KEY (cdid) REFERENCES cd(cdid)
);

CREATE TABLE person_composition (
    psncomprole VARCHAR(15),
    psncomporder VARCHAR(15),
    psnid INT,
    compid INT,
    PRIMARY KEY (psncomprole, psnid, compid),
    FOREIGN KEY (psnid) REFERENCES person(psnid),
    FOREIGN KEY (compid) REFERENCES composition(compid)
);

CREATE TABLE recording (
    rcdid INT UNIQUE,
    rcdlength NUMERIC(4,2),
    rcddate DATE,
    compid INT,
    PRIMARY KEY (rcdid, compid),
    FOREIGN KEY (compid) REFERENCES composition(compid)
);

CREATE TABLE track (
    cdid INT,
    trknum INT,
    rcdid INT,
    compid INT,
    PRIMARY KEY (cdid, trknum),
    FOREIGN KEY (rcdid, compid) REFERENCES recording(rcdid, compid),
    FOREIGN KEY (cdid) REFERENCES cd(cdid)
);

CREATE TABLE person_recording (
    psnrcdrole VARCHAR(30),
    psnid INT,
    compid INT,
    rcdid INT,
    PRIMARY KEY (psnrcdrole, rcdid, psnid),
    FOREIGN KEY (rcdid) REFERENCES recording(rcdid),
    FOREIGN KEY (compid) REFERENCES composition(compid),
    FOREIGN KEY (psnid) REFERENCES person(psnid)
);
/* PROBLEM 1 END */

/* PROBLEM 2 BEGIN */
SELECT trknum, comptitle
FROM track 
    NATURAL JOIN cd
    NATURAL JOIN composition
WHERE cdid = (
    SELECT cdid
    FROM cd
    WHERE cdtitle = 'Giant Steps'
);
/* PROBLEM 2 END */

/* PROBLEM 3 BEGIN */
SELECT psnfname, psnlname, psnrcdrole
FROM person
    NATURAL JOIN person_recording
    NATURAL JOIN recording
    NATURAL JOIN composition
WHERE rcddate = '1959-05-04'
    AND comptitle = 'Giant Steps'
;
/* PROBLEM 3 END */

/* PROBLEM 4 BEGIN */
SELECT psnfname, psnlname
FROM person
    NATURAL JOIN person_recording
    NATURAL JOIN person_composition
WHERE psncomprole = 'music'
    AND psnrcdrole = 'tenor sax';
/* PROBLEM 4 END */

/* PROBLEM 5 BEGIN */
SELECT comptitle, trknum, cdtitle
FROM composition
    NATURAL JOIN track
    NATURAL JOIN cd
    NATURAL JOIN (
        SELECT compid, cdid        
        FROM track
        GROUP BY compid, cdid
        HAVING COUNT(compid) > 1) AS is_there_any_other_way_to_implement_this_subquery
ORDER BY comptitle ASC, trknum ASC;
/* PROBLEM 5 END */

/* PROBLEM 6 BEGIN */
SELECT rcdid, rcddate
FROM recording
WHERE NOT EXISTS (
    SELECT * 
    FROM cd
    WHERE NOT EXISTS (
         SELECT * 
         FROM track
         WHERE recording.rcdid = track.rcdid
         AND track.cdid = cd.cdid
    )
);
/* PROBLEM 6 END */

/* PROBLEM 7 BEGIN */
SELECT rcdid, rcddate
FROM recording
    NATURAL JOIN track
GROUP BY rcdid, rcddate
    HAVING COUNT(DISTINCT cdid) = (
        SELECT COUNT(DISTINCT cdid)
        FROM cd
    );
/* PROBLEM 7 END */
