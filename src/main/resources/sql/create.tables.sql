CREATE TABLE Customer (
  id NUMERIC IDENTITY PRIMARY KEY,
  firstName VARCHAR(255),
  lastName VARCHAR(255),
  fatherName VARCHAR(255),
  phone VARCHAR(255)
);

CREATE TABLE Mechanic (
  id NUMERIC IDENTITY PRIMARY KEY,
  firstName VARCHAR(255),
  lastName VARCHAR(255),
  fatherName VARCHAR(255),
  payPerHour INT
);

CREATE TABLE OrderStatus (
  id NUMERIC PRIMARY KEY,
  status VARCHAR(255)
);

CREATE TABLE SalesOrder (
  id NUMERIC IDENTITY PRIMARY KEY,
  description VARCHAR(255),
  customer NUMERIC,
  mechanic NUMERIC,
  startDate DATE,
  dueDate DATE,
  cost INT,
  orderStatus NUMERIC,
  FOREIGN KEY (customer) REFERENCES Customer(id),
  FOREIGN KEY (mechanic) REFERENCES Mechanic(id),
  FOREIGN KEY (orderStatus) REFERENCES OrderStatus(id)
);
