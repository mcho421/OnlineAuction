CREATE TABLE users
(
  id serial NOT NULL,
  username text NOT NULL,
  password text NOT NULL,
  email text NOT NULL,
  status integer NOT NULL,
  confirmed boolean NOT NULL,
  namemd5 text NOT NULL,
  yearofbirth text,
  fname text,
  lname text,
  fulladdress text,
  creditcard text,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT users_email_key UNIQUE (email),
  CONSTRAINT users_username_key UNIQUE (username)
);

CREATE TABLE Categories (
    id SERIAL,
    category TEXT NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE items
(
  id serial NOT NULL,
  title text NOT NULL,
  category integer NOT NULL,
  picturename text,
  picturepath text,
  description text,
  postagedetails text,
  reserveprice integer,
  biddingstartprice integer NOT NULL,
  biddingincrements integer NOT NULL,
  closingtime timestamp without time zone NOT NULL,
  seller integer NOT NULL,
  halted boolean DEFAULT false,
  CONSTRAINT items_pkey PRIMARY KEY (id),
  CONSTRAINT items_category_fkey FOREIGN KEY (category)
      REFERENCES categories (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT items_seller_fkey FOREIGN KEY (seller)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE Bids (
    id SERIAL,
    bid INTEGER NOT NULL,
    bidTime TIMESTAMP NOT NULL,
    bidder INTEGER NOT NULL,
    item INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (bidder) REFERENCES Users(id),
    FOREIGN KEY (item) REFERENCES Items(id)
);

CREATE TABLE ProfileMessages (
    id SERIAL,
    message TEXT NOT NULL,
    read BOOLEAN NOT NULL DEFAULT false,
    owner INTEGER NOT NULL,
    senttime timestamp without time zone NOT NULL DEFAULT now(),
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES Users(id)
);

