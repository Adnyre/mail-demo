CREATE TABLE user (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255)
);

CREATE TABLE campaign (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  user_id INTEGER REFERENCES user(id),
  FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE keyword (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255)
);

CREATE TABLE addressee (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  email VARCHAR(255)
);

CREATE TABLE addressee_keyword (
  addresse_id INTEGER REFERENCES addressee(id),
  keyword_id INTEGER REFERENCES keyword(id),
  FOREIGN KEY (addresse_id) REFERENCES addressee(id),
  FOREIGN KEY (keyword_id) REFERENCES keyword(id)
);

CREATE TABLE campaign_keyword (
  campaign_id INTEGER REFERENCES campaign(id),
  keyword_id INTEGER REFERENCES keyword(id),
  FOREIGN KEY (campaign_id) REFERENCES campaign(id),
  FOREIGN KEY (keyword_id) REFERENCES keyword(id)
);