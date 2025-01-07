CREATE TABLE Cost
(
    id                INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    date              DATE,
    settlement_name   VARCHAR(255) NOT NULL,
    cost_per_min      DECIMAL      NOT NULL,
    preferential_cost DECIMAL
);

CREATE TABLE Client
(
    id                INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    date              DATE,
    full_name         VARCHAR(255) NOT NULL,
    address           VARCHAR(255),
    registration_date DATE         NOT NULL
);

CREATE TABLE Call
(
    id             INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    client_id      INT NOT NULL,
    date_of_call   TIMESTAMP,
    city_called_to VARCHAR(255) NOT NULL,
    phone_number   VARCHAR(15)  NOT NULL,
    call_duration  TIME         NOT NULL,
    CONSTRAINT unique_client_call_time UNIQUE (client_id, date_of_call),
    FOREIGN KEY (client_id) REFERENCES Client(id)
);

CREATE TABLE Receipt
(
    id              INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    conversation_id INTEGER,
    receipt_date    DATE,
    cost            DECIMAL NOT NULL,
    paid            BOOLEAN NOT NULL
);