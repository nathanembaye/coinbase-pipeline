CREATE KEYSPACE IF NOT EXISTS market 
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE market;

CREATE TABLE IF NOT EXISTS bitcoin(
    maker_order_id text,
    taker_order_id text,
    side text,
    product_id text,
    size double,
    price double,
    time timestamp,
    trade_id double,
    PRIMARY KEY(trade_id));