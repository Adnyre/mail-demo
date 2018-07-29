CREATE TABLE user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  email VARCHAR(255),
  pass VARCHAR(255),
  smtp_host VARCHAR(255),
  imap_host VARCHAR(255)
);

CREATE TABLE message_template (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  subject VARCHAR(255),
  template TEXT
);

CREATE TABLE campaign (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT,
  name VARCHAR(255),
  message_template_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (message_template_id) REFERENCES message_template(id)
);

CREATE TABLE keyword (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  INDEX (name),
  CONSTRAINT uq_keyword_name UNIQUE (name)
);

CREATE TABLE addressee (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  email VARCHAR(255),
  INDEX (email),
  CONSTRAINT uq_addressee_email UNIQUE (email)
);

CREATE TABLE addressee_keyword (
  addressee_id BIGINT,
  keyword_id BIGINT,
  FOREIGN KEY (addressee_id) REFERENCES addressee(id),
  FOREIGN KEY (keyword_id) REFERENCES keyword(id)
);

CREATE TABLE campaign_keyword (
  campaign_id BIGINT,
  keyword_id BIGINT,
  FOREIGN KEY (campaign_id) REFERENCES campaign(id),
  FOREIGN KEY (keyword_id) REFERENCES keyword(id)
);

CREATE TABLE campaign_addressee (
  campaign_id BIGINT,
  addressee_id BIGINT,
  FOREIGN KEY (campaign_id) REFERENCES campaign(id),
  FOREIGN KEY (addressee_id) REFERENCES addressee(id)
);

CREATE TABLE user_addressee (
  user_id BIGINT,
  addressee_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (addressee_id) REFERENCES addressee(id)
);