CREATE DATABASE IF NOT EXISTS popcorn;
USE popcorn;

DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products (
  productID int(11) NOT NULL AUTO_INCREMENT,
  item varchar(40) NOT NULL,
  size varchar(40) NOT NULL,
  cost decimal(6,2) NOT NULL,
  numInStock int(11) NOT NULL,
  category varchar(15) NOT NULL,
  PRIMARY KEY (productID) 
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=latin1;



INSERT INTO products (item, size, cost, numInStock, category) VALUES
('Unpoped popcorn', '1 lb', 3.00, 120, 'Popcorn'),
('Caramel popcorn', '2 lb canister', 3.50, 150, 'Popcorn'),
('Caramel Nut popcorn', '2 lb canister', 4.50, 130, 'Popcorn'),
('Toffy Nut popcorn', '2 lb canister', 5.00, 170, 'Popcorn'),
('Peanuts Salted Roasted in the Shell', '16 oz package', 3.45, 2000, 'Peanuts'),
('Peanuts Unsalted Roasted in the Shell', '16 oz package', 3.65, 2000, 'Peanuts');



DROP TABLE IF EXISTS buyers;
CREATE TABLE IF NOT EXISTS buyers (
  buyerID int(11) NOT NULL AUTO_INCREMENT,
  firstName varchar(40) NOT NULL,
  lastName varchar(40) NOT NULL,
  email varchar(40) NOT NULL,
  PRIMARY KEY (buyerID)
) ENGINE=InnoDB AUTO_INCREMENT= 500 DEFAULT CHARSET=latin1;


INSERT INTO buyers (firstName, lastName, email) VALUES
('Donald', 'Duck',  'dduck@disney.com'),
('Daisey', 'Duck',   'daiseyduck@disney.com'),
('Mickey', 'Mouse',  'mickmouse@disney.com' ),
('Minnie', 'Mouse',  'minniemouse@disney.com'),
('Captain', 'Hook', 'hookme@neverland.com'),
('Peter', 'Pan', 'littleboy@neverland.com');





DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders (
  orderID int(11) NOT NULL AUTO_INCREMENT,
  orderDate date NOT NULL,
  custID int(11) NOT NULL,
  PRIMARY KEY (orderID),
  CONSTRAINT custID_fk FOREIGN KEY (custID) REFERENCES buyers (buyerID)
) ENGINE=InnoDB AUTO_INCREMENT = 9000 DEFAULT CHARSET=latin1;



INSERT INTO orders (orderDate, custID) VALUES
('2021-05-05', 500),
('2021-05-25', 502),
('2021-07-13', 503),
('2021-08-01', 504),
('2021-07-19', 505),
('2021-05-21', 502),
('2021-09-13', 504),
('2021-05-14', 505),
('2021-05-27', 502),
('2021-07-30', 503);





DROP TABLE IF EXISTS lineitem;
CREATE TABLE IF NOT EXISTS lineitem (
  orderID int(11) NOT NULL,
  productID int(11) NOT NULL,
  numOrdered int(11) NOT NULL,
  price decimal(6,2) NOT NULL,
  PRIMARY KEY (orderID,productID),
  CONSTRAINT productID_fk FOREIGN KEY (productID) REFERENCES products (productID),
  CONSTRAINT orderID_fk FOREIGN KEY (orderID) REFERENCES orders (orderID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO lineitem (orderID, productID, numOrdered, price) VALUES
(9000, 1001, 3, 3.00),
(9000, 1002, 6, 3.50),
(9001, 1004, 8, 5.00),
(9002, 1001, 6, 3.00),
(9002, 1002, 5, 3.50),
(9002, 1003, 7, 4.50),
(9003, 1003, 2, 4.50),
(9004, 1001, 5, 3.00),
(9004, 1002, 6, 3.50),
(9004, 1003, 8, 4.50),
(9005, 1004, 12, 5.00),
(9006, 1001, 2, 3.00),
(9006, 1003, 8, 4.50),
(9007, 1001, 12, 3.00),
(9007, 1003, 10, 4.50),
(9008, 1001, 9, 3.00),
(9008, 1002, 10, 3.50),
(9008, 1003, 10, 4.50),
(9009, 1003, 2, 4.50),
(9009, 1004, 2, 5.00),
(9000, 1005, 20, 3.45),
(9003, 1006, 25, 3.65),
(9001, 1005, 25, 3.45),
(9001, 1006, 15, 3.65),
(9005, 1005, 10, 3.45),
(9006, 1006, 20, 3.65);
