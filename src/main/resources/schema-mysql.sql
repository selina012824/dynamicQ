CREATE TABLE IF NOT EXIST `quiz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Qname` varchar(45) DEFAULT NULL,
  `Qexplain` varchar(200) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `Qsituation` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

