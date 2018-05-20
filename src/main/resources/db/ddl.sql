CREATE TABLE user (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  email VARCHAR(255),
  pass VARCHAR(255),
  smtp_host VARCHAR(255),
  imap_host VARCHAR(255)
);

CREATE TABLE campaign (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  user_id INTEGER,
  name VARCHAR(255),
  message_template_id INTEGER,
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (message_template_id) REFERENCES message_template(id)
);

CREATE TABLE message_template (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  subject VARCHAR(255),
  template TEXT
);

CREATE TABLE keyword (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  INDEX (name)
);

CREATE TABLE addressee (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  email VARCHAR(255),
  INDEX (email)
);

CREATE TABLE addressee_keyword (
  addressee_id INTEGER,
  keyword_id INTEGER,
  FOREIGN KEY (addressee_id) REFERENCES addressee(id),
  FOREIGN KEY (keyword_id) REFERENCES keyword(id)
);

CREATE TABLE campaign_keyword (
  campaign_id INTEGER,
  keyword_id INTEGER,
  FOREIGN KEY (campaign_id) REFERENCES campaign(id),
  FOREIGN KEY (keyword_id) REFERENCES keyword(id)
);