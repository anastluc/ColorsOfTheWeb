CREATE USER 'colorsotw'@'localhost' IDENTIFIED BY '***';
GRANT USAGE ON *.* TO 'colorsotw'@'localhost' IDENTIFIED BY '***' 
WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;

CREATE DATABASE IF NOT EXISTS `colorsotw`;
GRANT ALL PRIVILEGES ON `colorsotw`.* TO 'colorsotw'@'localhost';

CREATE TABLE IF NOT EXISTS `frequencies` (
  `id_image` int(11) NOT NULL,
  `r_value` tinyint(3) unsigned NULL,
  `g_value` tinyint(3) unsigned NULL,
  `b_value` tinyint(3) unsigned NULL,
  `frequency` int(11) unsigned NOT NULL,
  `frequency_perc` double unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;