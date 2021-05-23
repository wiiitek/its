
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    uuid uuid NOT NULL,
    name varchar(2048) CHECK (name <> ''),
    email varchar(512)
);

CREATE TABLE departments (
    id SERIAL PRIMARY KEY,
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
