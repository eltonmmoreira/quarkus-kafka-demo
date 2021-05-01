CREATE TABLE BALANCE (
    id NUMERIC not null primary key,
    ticker varchar(10) not null,
    quantity NUMERIC not null,
    average_price NUMERIC(15, 2) not null,
    total_Price NUMERIC(15, 2) not null,
    account_id varchar(35) not null,
    version NUMERIC not null
);

ALTER TABLE BALANCE ADD CONSTRAINT UNQ_TICKER_ACCOUNT_ID UNIQUE (ticker, account_id);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;