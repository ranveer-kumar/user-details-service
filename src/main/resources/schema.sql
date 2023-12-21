DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS user_detail;


CREATE TABLE user_detail (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    gender VARCHAR(10) NOT NULL
);

CREATE TABLE address (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id INT NOT NULL,
    street VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(10),
    postcode INT NOT NULL,
    FOREIGN KEY (emp_id) REFERENCES user_detail(emp_id)
);

-- Create composite index on first_name and last_name
CREATE INDEX idx_user_detail_name ON user_detail (first_name, last_name);