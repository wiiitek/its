
-- https://stackoverflow.com/a/2944561

ALTER TABLE employees   ALTER COLUMN id SET DEFAULT nextval('employees_seq');
ALTER TABLE departments ALTER COLUMN id SET DEFAULT nextval('departments_seq');
