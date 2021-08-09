/* Nicholas Bellman */
/* Lab 02: CD (basic) */

/* PROBLEM 1 BEGIN */
CREATE TABLE label (
    lbltitle VARCHAR(35),
    lblstreet VARCHAR(50),
    lblbcity VARCHAR(20),
    lblstate VARCHAR(2),
    lblpostcode VARCHAR(5),
    lblnation VARCHAR(3),
	PRIMARY KEY (lbltitle)
);

CREATE TABLE cd (
    cdid INTEGER,
    cdlblid VARCHAR(15),
    cdtitle VARCHAR(50),
    cdyear INTEGER,
    lbltitle VARCHAR(35),
	PRIMARY KEY (cdid),
	FOREIGN KEY (lbltitle) REFERENCES label(lbltitle)
);

CREATE TABLE track (
    trkid INTEGER,
    trknum INTEGER,
    trktitle VARCHAR(100),
    trklength NUMERIC(4,2),
    cdid INTEGER,
	PRIMARY KEY (trkid),
	FOREIGN KEY (cdid) REFERENCES cd(cdid)
);
/* PROBLEM 1 END */

/* PROBLEM 2 BEGIN */
SELECT trktitle, cdtitle, trklength
FROM track JOIN cd ON track.cdid = cd.cdid
ORDER BY cdtitle ASC, trklength ASC;
/* PROBLEM 2 END */

/* PROBLEM 3 BEGIN */
SELECT trktitle, trklength
FROM track
WHERE track.cdid = (SELECT cd.cdid
                     FROM cd
                     WHERE cdtitle = 'Swing');
/* PROBLEM 3 END */

/* PROBLEM 4 BEGIN */
SELECT cdtitle, trktitle, trklength
FROM track JOIN cd ON track.cdid = cd.cdid
WHERE trklength IN (SELECT MAX(trklength)
                    FROM track
                    WHERE track.cdid = cd.cdid);
/* PROBLEM 4 END */

/* PROBLEM 5 BEGIN */
SELECT cdtitle, COUNT(trkid) , SUM(trklength)
FROM track JOIN cd ON track.cdid = cd.cdid
GROUP BY cdtitle
ORDER BY COUNT(trkid) DESC;
/* PROBLEM 5 END */

/* PROBLEM 6 BEGIN */
SELECT label.lbltitle, lblnation, cdtitle, SUM(trklength) AS cdlength
FROM label JOIN cd ON label.lbltitle = cd.lbltitle
           JOIN track ON track.cdid = cd.cdid
GROUP BY label.lbltitle, lblnation, cdtitle
HAVING SUM(trklength) > 40.00;
/* PROBLEM 6 END */

/* PROBLEM 7 BEGIN */
SELECT cdtitle, trktitle, trklength
FROM track JOIN cd ON track.cdid = cd.cdid
ORDER BY trklength ASC
LIMIT 3;
/* PROBLEM 7 END */

/* PROBLEM 8 BEGIN */
CREATE VIEW CDView 
    (cdid, cdlblid, cdtitle, cdyear, cdlength)
    AS SELECT cd.cdid, cdlblid, cdtitle, cdyear, SUM(trklength)
    FROM cd JOIN track ON cd.cdid = track.cdid
    GROUP BY cd.cdid, cdlblid, cdtitle, cdyear;
/* PROBLEM 8 END */

/* PROBLEM 9 BEGIN */
SELECT trktitle, trklength, cdtitle
FROM track JOIN cd ON track.cdid = cd.cdid
WHERE trktitle LIKE 'C%';
/* PROBLEM 9 END */
