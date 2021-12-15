CREATE TABLE exchangerates (
    id int IDENTITY PRIMARY KEY,
    currency_code varchar(3),
    currency_title varchar(100),
    buy_rate decimal(9,4),
    sell_rate decimal(9,4)
);