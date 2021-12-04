CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS POSTING_FEE
(
  ID UUID NOT NULL DEFAULT uuid_generate_v1(),
  Amount INT NOT NULL,
  Duration INT NOT NULL,
  Date_updated TIMESTAMP NOT NULL DEFAULT NOW(),
  PRIMARY KEY(ID)
);


CREATE TABLE IF NOT EXISTS PERSON
(
  Email VARCHAR(64) NOT NULL,
  Name VARCHAR(64) NOT NULL,
  Hashed_password VARCHAR NOT NULL,
  Role INT NOT NULL,
  PRIMARY KEY(Email)
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
  Landlord_email VARCHAR(64) NOT NULL,
  Property_type VARCHAR(32),
  Current_state INT NOT NULL,
  No_bedrooms INT,
  No_bathrooms INT,
  Is_furnished BIT,
  City_quadrant CHAR(2),
  Country VARCHAR(30),
  Province VARCHAR(30),
  Street_address VARCHAR(70),
  Postal_code Varchar(15),
  PRIMARY KEY(ID),
  FOREIGN KEY (Landlord_email) REFERENCES PERSON(Email)
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

CREATE TABLE IF NOT EXISTS PAID_BY
(
  Landlord_email VARCHAR(64) NOT NULL,
  Fee_id UUID NOT NULL,
  Property_id UUID NOT NULL,
  Start_date TIMESTAMP NOT NULL DEFAULT NOW(),
  Num_periods INT NOT NULL,
  PRIMARY KEY(Landlord_email, Fee_id, Property_id),
  FOREIGN KEY (Landlord_email) REFERENCES PERSON(Email),
  FOREIGN KEY (Fee_id) REFERENCES POSTING_FEE(ID),
  FOREIGN KEY (Property_id) REFERENCES PROPERTY(ID)
);

CREATE TABLE IF NOT EXISTS PROPERTY_STATE
(
  Property_id UUID NOT NULL,
  State_date TIMESTAMP NOT NULL,
  State INT NOT NULL,
  PRIMARY KEY(Property_id, State_date),
  FOREIGN KEY (Property_id) REFERENCES PROPERTY(ID)
);

CREATE OR REPLACE FUNCTION insert_state() RETURNS TRIGGER AS
  '
  BEGIN
      IF NEW.ID IS NULL THEN
              RAISE EXCEPTION ''property id cannot be null'';
      END IF;        
            
      IF NEW.Current_state IS NULL THEN
              RAISE EXCEPTION ''property state cannot be null'';
      END IF;
    
      INSERT INTO PROPERTY_STATE(Property_id, State_date, state)
             VALUES(NEW.ID, NOW(), NEW.Current_state);

       RETURN NULL; 
  END;
  '
 LANGUAGE PLPGSQL;


DROP TRIGGER IF EXISTS insert_state_trigger ON PROPERTY;

CREATE TRIGGER insert_state_trigger
     AFTER INSERT OR UPDATE ON PROPERTY
     FOR EACH ROW
     EXECUTE PROCEDURE insert_state();


