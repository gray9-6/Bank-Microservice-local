--During start up of application if we want to create the table then we can create in schema.sql file. So we can store the record in this table

CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `mobile_number` VARCHAR(20) NOT NULL UNIQUE,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(20) NOT NULL,
  `updated_at` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `accounts` (
  `account_number` INT AUTO_INCREMENT PRIMARY KEY,
  `customer_id` INT NOT NULL, -- F.K
  `account_type` VARCHAR(100) NOT NULL,
  `branch_address` VARCHAR(200) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(20) NOT NULL,
  `updated_at` DATETIME DEFAULT NULL,
  `updated_by` VARCHAR(20) DEFAULT NULL,
  CONSTRAINT fk_customer
    FOREIGN KEY (`customer_id`) REFERENCES `customer`(`customer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
