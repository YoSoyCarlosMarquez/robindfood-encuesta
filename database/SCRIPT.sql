CREATE TABLE ENCUESTA (
	ENCID INTEGER PRIMARY KEY AUTO_INCREMENT,
	ENCTITULO VARCHAR(120) NOT NULL,
	ENCDESCRIPCION VARCHAR(360) NULL,
	ENCFECHA DATE NOT NULL DEFAULT CURRENT_DATE,
	ENCESTADO CHAR(1) NOT NULL DEFAULT 'A',
	CONSTRAINT CHECK_ENCESTADO 
		CHECK (ENCESTADO='A' OR ENCESTADO='I')	
);


CREATE TABLE TIPO_PREGUNTA (
	TIPPREID INTEGER PRIMARY KEY AUTO_INCREMENT,
	TIPPRENOMBRE VARCHAR(50) NOT NULL,
	TIPPREESTADO CHAR(1) NOT NULL DEFAULT 'A',
	CONSTRAINT CHECK_TIPPREESTADO 
		CHECK (TIPPREESTADO='A' OR TIPPREESTADO='I')	
);
INSERT INTO TIPO_PREGUNTA (TIPPRENOMBRE) VALUES ('ABIERTA');
INSERT INTO TIPO_PREGUNTA (TIPPRENOMBRE) VALUES ('UNICA');


CREATE TABLE PREGUNTA (
	PREID INTEGER PRIMARY KEY AUTO_INCREMENT,
	PREENCUESTA INTEGER NOT NULL,
	PREPREGUNTA VARCHAR(360) NOT NULL,	
	PRETIPO INTEGER NOT NULL DEFAULT 1, 
	PREESTADO CHAR(1) NOT NULL DEFAULT 'A',
	CONSTRAINT CHECK_PREESTADO 
		CHECK (PREESTADO='A' OR PREESTADO='I'),
	CONSTRAINT FK_PREENCUESTA FOREIGN KEY (PREENCUESTA)
		REFERENCES ENCUESTA (ENCID),
	CONSTRAINT FK_PRETIPO FOREIGN KEY (PRETIPO)
		REFERENCES TIPO_PREGUNTA (TIPPREID)
);

CREATE TABLE RESPUESTA_PREGUNTA (
	RESPREID INTEGER PRIMARY KEY AUTO_INCREMENT,
	RESPREPREGUNTA INTEGER NOT NULL,
	RESPRETEXTO VARCHAR(360) NOT NULL,
	RESPREESTADO CHAR(1) NOT NULL DEFAULT 'A',
	CONSTRAINT CHECK_RESPREESTADO 
		CHECK (RESPREESTADO='A' OR RESPREESTADO='I'),
	CONSTRAINT FK_RESPREPREGUNTA FOREIGN KEY (RESPREPREGUNTA)
		REFERENCES PREGUNTA (PREID)
);

CREATE TABLE RESPUESTA_ENCUESTA (
	RESPENCID INTEGER PRIMARY KEY AUTO_INCREMENT,
	RESPENCPREGUNTA INTEGER NOT NULL,
	RESPENCRESPUESTA VARCHAR(360) NOT NULL,
	CONSTRAINT FK_RESPENCPREGUNTA FOREIGN KEY (RESPENCPREGUNTA)
		REFERENCES PREGUNTA (PREID)	
);


CREATE TABLE RESPUESTA_SELECCIONADA (
	RESPSELID INTEGER PRIMARY KEY AUTO_INCREMENT,
	RESPSELRESPENCID INTEGER NOT NULL,
	RESPSELRESPREID INTEGER NOT NULL,
	CONSTRAINT FK_RESPSELRESPENCID FOREIGN KEY (RESPSELRESPENCID)
		REFERENCES RESPUESTA_ENCUESTA (RESPENCID),
	CONSTRAINT FK_RESPSELRESPREID FOREIGN KEY (RESPSELRESPREID)
		REFERENCES RESPUESTA_PREGUNTA (RESPREID)	
);