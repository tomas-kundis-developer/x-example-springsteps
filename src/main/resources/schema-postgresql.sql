DROP TABLE IF EXISTS inquiries;

CREATE TABLE inquiries
(
    id           uuid PRIMARY KEY DEFAULT uuidv7(),
    full_name    VARCHAR(1000),
    email        VARCHAR(2000),
    phone_number VARCHAR(50),
    text         VARCHAR(20000),
    CONSTRAINT required_minimum_data CHECK (
        (email IS NOT NULL) OR (phone_number IS NOT NULL))
);