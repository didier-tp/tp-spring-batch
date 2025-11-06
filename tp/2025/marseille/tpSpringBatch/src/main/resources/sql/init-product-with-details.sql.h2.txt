DROP TABLE IF EXISTS product_with_details;

CREATE TABLE product_with_details  (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    main_category VARCHAR(50),
    sub_category VARCHAR(50),
    label VARCHAR(50),
    price double,
    time_stamp VARCHAR(30),
    f_color VARCHAR(32),
    f_weight double,
    f_size VARCHAR(16),
    f_description VARCHAR(128)
);


