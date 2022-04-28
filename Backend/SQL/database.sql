

CREATE TABLE `cart` (
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `cart` (`user_id`, `product_id`) VALUES
(1, 14),
(1, 25),
(4, 14),
(4, 15),
(4, 25);


CREATE TABLE `ordering` (
  `ordering_id` int(11) NOT NULL,
  `order_number` int(11) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `status` text,
  `name_on_card` text,
  `card_number` text,
  `expiration_date` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `ordering` (`ordering_id`, `order_number`, `order_date`, `status`, `name_on_card`, `card_number`, `expiration_date`, `user_id`, `product_id`) VALUES
(18, 55470123, '2020-06-15', 'shipped', 'Marwa', '111111111111', '2022-06-01', 4, 18),
(19, 66595700, '2020-06-15', 'shipped', 'Marwa', '222222222222', '2023-07-01', 4, 24),
(20, 55789264, '2020-06-15', 'shipped', 'Nora', '333333333333', '2022-08-01', 1, 14,
(21, 66116913, '2020-06-15', 'shipped', 'Nora', '111111111111', '2028-04-01', 1, 28),
(22, 55524173, '2020-06-16', 'shipped', 'Nora', '222222222222', '2022-08-01', 1, 15),
(23, 55506266, '2020-06-16', 'shipped', 'Nora', '444444444444', '2024-04-01', 1, 25);



CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `product_name` text,
  `price` double(11,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `supplier` text NOT NULL,
  `image` text NOT NULL,
  `category` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



INSERT INTO `product` (`id`, `product_name`, `price`, `quantity`, `supplier`, `image`, `category`) VALUES
(14, 'Iphone XS Max ', 15989.00, 300, 'Apple', 'localhost:8080/1.jpeg', 'mobile'),
(15, 'Xiaomi Mi A1', 2555.00, 400, 'Xiaomi', 'localhost:8080/2.jpeg', 'mobile'),
(16, 'Samsung Galaxy S10', 1266.60, 450, 'Samsung ', 'localhost:8080/3.jpeg', 'mobile'),
(17, 'Huawei P30 Lite', 4695.00, 450, 'Huawei ', 'localhost:8080/4.jpeg', 'mobile'),
(18, 'Apple iPhone 6S ', 5319.00, 300, 'Apple', 'localhost:8080/5.jpeg', 'mobile'),
(24, 'Apple MacBook Air MQD32', 16499.00, 700, 'Apple', 'localhost:8080/6.jpeg', 'laptop'),
(25, 'Apple MacBook MNYF2 ', 20200.00, 700, 'Apple', 'localhost:8080/7.jpeg', 'laptop'),
(26, 'Lenovo V145 Laptop', 3249.00, 700, 'Lenovo', 'localhost:8080/8.jpeg', 'laptop'),
(27, 'Lenovo V130 Laptop', 4799.00, 676, 'Lenovo', 'localhost:8080/9.jpeg', 'laptop'),
(28, 'HP PRO Book 450', 8850.00, 676, 'HP', 'localhost:8080/10.jpeg', 'laptop');




CREATE TABLE `shipping` (
  `shipping_id` int(11) NOT NULL,
  `address` text,
  `city` text,
  `country` text,
  `zip` text,
  `phone` text,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `shipping` (`shipping_id`, `address`, `city`, `country`, `zip`, `phone`, `user_id`, `product_id`) VALUES
(18, 'Bengaluru', 'India', 'India', '4545', '012564988', 4, 18),
(19, 'Bengaluru', 'India', 'India', '4545', '01079559906', 4, 24),
(20, 'Bengaluru', 'India', 'India', '3030', '0128686888', 1, 14),
(21, 'Bengaluru', 'Bengaluru', 'India', '3030', '8655686', 1, 28),
(22, 'Bengaluru', 'Bengaluru', 'India', '3030', '8568690', 1, 15);



CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `email` text NOT NULL,
  `password` text NOT NULL,
  `gender` text NOT NULL,
  `age` int(11) NOT NULL,
  `image` text NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `user` (`id`, `name`, `email`, `password`, `gender`, `age`, `image`, `isAdmin`) VALUES
(1, 'Jahnavi', 'jahnavi@gmail.com', 'snowwhite', 'female', 20, '', 0),
(2, 'keerthi', 'keerthi@gmail.com', 'snowwhite', '', 20, '', 0),
(3, 'kirtan', 'kirtan@gmail.com', 'snowwhite', '', 0, '', 0),
(4, 'raghav', 'raghav@yahoo.com', 'snowwhite', '', 0, '', 0),
(5, 'rahul', 'rahul@gmail.com', 'snowwhite', 'male', 0, '', 0),
(6, 'rahul radha', 'rahulradha@gmail.com', 'snowwhite', 'male', 0, '', 0),
(7, 'vivek', 'vivek@gmail.com', 'snowwhite', 'undertermined', 0, '', 0),
(10, 'vinod', 'vinod@gmail.com', 'snowwhite', 'male', 0, '', 0);


ALTER TABLE `cart`
  ADD PRIMARY KEY (`user_id`,`product_id`),
  ADD UNIQUE KEY `cart_constraint` (`user_id`,`product_id`),
  ADD KEY `product_id` (`product_id`);


ALTER TABLE `ordering`
  ADD PRIMARY KEY (`ordering_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `shipping`
  ADD PRIMARY KEY (`shipping_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);


ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `ordering`
  MODIFY `ordering_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;


ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;


ALTER TABLE `shipping`
  MODIFY `shipping_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ordering`
  ADD CONSTRAINT `ordering_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ordering_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE `shipping`
  ADD CONSTRAINT `shipping_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `shipping_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
