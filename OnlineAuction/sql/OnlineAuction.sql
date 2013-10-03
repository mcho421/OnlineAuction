CREATE TABLE Users (
    id SERIAL,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    status INTEGER NOT NULL,
    confirmed BOOLEAN NOT NULL,
    nameMd5 TEXT NOT NULL,
    yearOfBirth TEXT,
    fname TEXT,
    lname TEXT,
    fullAddress TEXT,
    creditCard TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE Categories (
    id SERIAL,
    category TEXT NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE Items (
    id SERIAL,
    title TEXT NOT NULL, 
    category INTEGER NOT NULL,
    pictureName TEXT,
    picturePath TEXT,
    description TEXT,
    postageDetails TEXT,
    reservePrice INTEGER,
    biddingStartPrice INTEGER NOT NULL,
    biddingIncrements INTEGER NOT NULL,
    closingTime TIMESTAMP NOT NULL,
    seller INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (category) REFERENCES Categories(id),
    FOREIGN KEY (seller) REFERENCES Users(id)
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
    read BOOLEAN NOT NULL,
    owner INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES Users(id)
);

