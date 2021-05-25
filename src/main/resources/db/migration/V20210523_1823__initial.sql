
CREATE SEQUENCE employees_seq
  MINVALUE 1
  START WITH 1000
  INCREMENT BY 10;

-- don't use SERIAL for primary keys
-- https://vladmihalcea.com/postgresql-serial-column-hibernate-identity/
CREATE TABLE employees (
    id bigint PRIMARY KEY,
    uuid uuid NOT NULL,
    name varchar(2048) CHECK (name <> ''),
    email varchar(512)
);

CREATE SEQUENCE departments_seq
  MINVALUE 1
  START WITH 10
  INCREMENT BY 1;

CREATE TABLE departments (
    id bigint PRIMARY KEY,
    name varchar(2048) CHECK (name <> '')
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
