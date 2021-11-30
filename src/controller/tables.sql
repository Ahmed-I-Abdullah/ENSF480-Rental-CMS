CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS POSTING_FEE
(
  ID INT NOT NULL DEFAULT 1,
  Amount INT NOT NULL,
  Duration INT NOT NULL,
  PRIMARY KEY(ID)
);


CREATE TABLE IF NOT EXISTS PERSON
(
  Email VARCHAR(64) NOT NULL,
  Name VARCHAR(64) NOT Null,
  Hashed_password VARCHAR(60) NOT NULL,
  Role INT NOT NULL,
  Posting_fee_id INT,
  PRIMARY KEY(Email),
  FOREIGN KEY(Posting_fee_id) REFERENCES POSTING_FEE(ID)
);

CREATE TABLE IF NOT EXISTS RENTER
(
  Email VARCHAR(64) NOT NULL,
  Is_subscribed BIT DEFAULT '0',
  Is_furnished BIT,
  City_quadrant CHAR(2),
  No_bedrooms INT,
  No_bathrooms INT,
  PRIMARY KEY(Email),
  FOREIGN KEY (Email) REFERENCES PERSON(Email)
);

CREATE TABLE IF NOT EXISTS PROPERTY
(
  ID UUID NOT NULL DEFAULT uuid_generate_v1(),
  Property_type VARCHAR(32),
  State INT,
  No_bedrooms INT,
  No_bathrooms INT,
  Is_furnished BIT,
  City_quadrant CHAR(2),
  Country VARCHAR(30),
  Province VARCHAR(30),
  Street_address VARCHAR(70),
  Postal_code Varchar(15),
  PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS EMAIL_MESSAGE
(
  ID UUID NOT NULL DEFAULT uuid_generate_v1(),
  Subject VARCHAR(64),
  Content VARCHAR NOT NULL,
  Renter_email VARCHAR(64) NOT NULL,
  Landlord_email VARCHAR(64) NOT NULL,
  Property_id UUID NOT NULL,
  PRIMARY KEY(ID),
  FOREIGN KEY (Renter_email) REFERENCES RENTER(Email),
  FOREIGN KEY (Landlord_email) REFERENCES PERSON(Email),
  FOREIGN KEY (Property_id) REFERENCES PROPERTY(ID)
);
