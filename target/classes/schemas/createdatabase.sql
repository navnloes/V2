/* Need to switch of FK check for MySQL since there are crosswise FK references */
SET FOREIGN_KEY_CHECKS = 0;;

CREATE DATABASE IF NOT EXISTS pisu;;
USE pisu;;

SET FOREIGN_KEY_CHECKS = 1;;
