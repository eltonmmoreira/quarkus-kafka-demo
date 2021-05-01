CREATE TABLE ORDERS (
    id varchar not null primary key,
    ticker varchar(10) not null,
    operation varchar(6) not null,
    quantity NUMERIC not null,
    price NUMERIC(15, 2) not null,
    total_Price NUMERIC(15, 2) not null,
    date_Negotiation TIMESTAMP not null,
    account_id varchar(35) not null
);