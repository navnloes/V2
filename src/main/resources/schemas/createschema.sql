/* Need to switch of FK check for MySQL since there are crosswise FK references */
SET FOREIGN_KEY_CHECKS = 0;;
CREATE DATABASE IF NOT EXISTS pisu;
USE pisu;

CREATE TABLE IF NOT EXISTS Game (
  gameID int NOT NULL UNIQUE AUTO_INCREMENT,
  
  name varchar(255),

  phase tinyint,
  step tinyint,
  currentPlayer tinyint NULL,
  
  PRIMARY KEY (gameID),
  FOREIGN KEY (gameID, currentPlayer) REFERENCES Player(gameID, playerID)
);;
  
CREATE TABLE IF NOT EXISTS Player (
  gameID int NOT NULL,
  playerID tinyint NOT NULL,

  name varchar(255),
  colour varchar(31),
  
  positionX int,
  positionY int,
  heading tinyint,
  
  PRIMARY KEY (gameID, playerID),
  FOREIGN KEY (gameID) REFERENCES Game(gameID)
);;

CREATE TABLE IF NOT EXISTS PlayerHandCard (
gameID int NOT NULL,
playerID tinyint NOT NULL,
cardIndex int NOT NULL,
cardId int NOT NULL,

PRIMARY KEY (gameID, playerID, cardIndex),
FOREIGN KEY (gameID, playerID) REFERENCES Player(gameID, playerID)
);;

CREATE TABLE IF NOT EXISTS CardField (
gameID int NOT NULL,
playerID tinyint NOT NULL,
type int NOT NULL,
position tinyint NOT NULL,

visible BIT NOT NULL,
command tinyint,

PRIMARY KEY (gameID, playerID, type, position),
FOREIGN KEY (gameID) references Game(gameID),
FOREIGN KEY (gameID, playerID) REFERENCES Player(gameID, playerID)

);;

SET FOREIGN_KEY_CHECKS = 1;;
