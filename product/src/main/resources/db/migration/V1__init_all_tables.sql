-- PRODUCT table
DROP TABLE IF EXISTS PRODUCT;
DROP SEQUENCE IF EXISTS PRODUCT_ID_PK_SEQ;
-- CREATE SEQUENCE PRODUCT_ID_PK_SEQ;
CREATE TABLE PRODUCT  (
	-- ID INTEGER DEFAULT NEXTVAL('PRODUCT_ID_PK_SEQ'),
	ID VARCHAR(255) NOT NULL,
    TITLE VARCHAR(500) NOT NULL,
    CATEGORY_ID INTEGER,
    SUB_CATEGORY_ID INTEGER,
    SELLING_PRICE DECIMAL DEFAULT 0,
    IMAGE VARCHAR(3000) DEFAULT NULL,
    TAGS VARCHAR(500) DEFAULT NULL,
    CREATED_DATE TIMESTAMP WITH TIME ZONE,
    CREATED_BY VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP WITH TIME ZONE,
    LAST_MODIFIED_BY VARCHAR(255),
	PRIMARY KEY (ID)
);
