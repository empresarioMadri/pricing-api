DROP TABLE IF EXISTS PRICES;

CREATE TABLE PRICES (
                        ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                        BRAND_ID INT NOT NULL,
                        START_DATE TIMESTAMP NOT NULL,
                        END_DATE TIMESTAMP NOT NULL,
                        PRICE_LIST INT NOT NULL,
                        PRODUCT_ID BIGINT NOT NULL,
                        PRIORITY INT NOT NULL,
                        PRICE DECIMAL(10,2) NOT NULL,
                        CURR VARCHAR(3) NOT NULL
);

CREATE INDEX IDX_PRICE_LOOKUP
    ON PRICES (PRODUCT_ID, BRAND_ID, START_DATE, END_DATE, PRIORITY);