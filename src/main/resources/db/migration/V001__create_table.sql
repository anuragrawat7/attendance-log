CREATE TABLE attendance_log (
   transaction_id INT AUTO_INCREMENT NOT NULL,
   user_id VARCHAR(255) NULL,
   date_time datetime NULL,
   CONSTRAINT pk_attendance_log PRIMARY KEY (transaction_id)
);
CREATE TABLE user_master (
   id INT AUTO_INCREMENT NOT NULL,
   user_id VARCHAR(255) NULL,
   emp_code VARCHAR(255) NULL,
   CONSTRAINT pk_user_master PRIMARY KEY (id)
);

ALTER TABLE user_master ADD CONSTRAINT uc_user_master_emp_code UNIQUE (emp_code);

ALTER TABLE user_master ADD CONSTRAINT uc_user_master_user UNIQUE (user_id);