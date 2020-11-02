DROP TABLE debts;
DROP TABLE users;
DROP TABLE paymenthistory;

CREATE TABLE debts (
    debt_id      INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    debt_name    VARCHAR(40) NOT NULL,
    debtor_mail  VARCHAR(40) NOT NULL,
    description  VARCHAR(200),
    cost         INTEGER NOT NULL,
    users_id     INTEGER 
);

ALTER TABLE debts ADD CONSTRAINT debts_pk PRIMARY KEY ( debt_id );

CREATE TABLE paymenthistory (
    payment_id     INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    cost           VARCHAR(40) NOT NULL,
    debts_debt_id  INTEGER 
);

ALTER TABLE paymenthistory ADD CONSTRAINT paymenthistory_pk PRIMARY KEY ( payment_id  );

CREATE TABLE users (
    id         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    email      VARCHAR(40) NOT NULL,
    password   VARCHAR(40) NOT NULL,
    firstname  VARCHAR(40) NOT NULL,
    lastname   VARCHAR(40) NOT NULL,
    tel        VARCHAR(40) NOT NULL
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

ALTER TABLE debts
    ADD CONSTRAINT debts_users_fk FOREIGN KEY ( users_id )
        REFERENCES users ( id );

ALTER TABLE paymenthistory
    ADD CONSTRAINT paymenthistory_debt_fk FOREIGN KEY ( debts_debt_id )
        REFERENCES debts ( debt_id );



