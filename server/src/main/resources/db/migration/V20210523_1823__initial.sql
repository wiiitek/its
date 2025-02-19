
CREATE SEQUENCE employees_seq
  MINVALUE 1
  START WITH 1000
  INCREMENT BY 10;

-- don't use SERIAL for primary keys
-- https://vladmihalcea.com/postgresql-serial-column-hibernate-identity/
CREATE TABLE employees (
    id bigint PRIMARY KEY,
    uuid uuid UNIQUE NOT NULL,
    -- column name being a keyword needs to be in quotes for H2
    -- https://stackoverflow.com/a/19758863
    -- comparison to empty string is not always safe,
    -- but we have integration tests to make sure this check works
    "name" TEXT NOT NULL CHECK ("name" <> ''),
    email TEXT
);

CREATE SEQUENCE departments_seq
  MINVALUE 1
  START WITH 10
  INCREMENT BY 1;

CREATE TABLE departments (
    id bigint PRIMARY KEY,
    -- comparison to empty string is not always safe,
    -- but we have integration tests to make sure this check works
    "name" TEXT NOT NULL CHECK ("name" <> '')
);

CREATE TABLE employees_departments (
    employee_id int NOT NULL,
    department_id int NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    FOREIGN KEY (department_id) REFERENCES departments (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
