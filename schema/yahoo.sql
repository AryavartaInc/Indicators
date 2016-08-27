-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.14


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema stockquote
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ stockquote;
USE stockquote;

CREATE TABLE IF NOT EXISTS `stockquote`.`^GSPC` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`^IXIC` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`^NSEI` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ABIRLANUV.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ACC.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ACL.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ADANIPOWE.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ALLBANKSL.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ALOKTEXT.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ANDHRABAN.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ARVIND.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ASHOKLEY.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`AXISBANK.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`AUROPHARM.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BAJAJAUT.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BAJAJHIND.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BHARTIART.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BATAINDIA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BPCL.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BOB.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BOSCHL.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BANKINDIA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BFUTI.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`BRFL.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`CAIRN.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`CHAMBAL.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`CIPLA.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`COALINDIA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`CORPBANK.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`DABUR.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`DENABANK.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`DLF.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`DISHTV.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`DRREDDY.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`EXIDEIND.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`GAIL.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`GLAXOCON.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`GTLINFRA.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`GTL.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`GRASIM.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`HCLTECH.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`HDFC.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`HDFCBANK.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`HINDALCO.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`HINDUNILV.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`HPCL.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ICICIBANK.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`IDFC.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`IFCI.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`INFY.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ITC.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`IDEA.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`IDBI.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`INDHOTELS.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`IGL.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`IOC.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`JBFIND.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`JETAIRWAY.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`JINDALSTE.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`JPASSOCIA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`JPPOWER.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`KOTAKBANK.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`LITL.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`LT.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`MNM.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`MARUTI.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`NEYVELILI.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`NHPC.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`NTPC.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ONGC.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ONMOBILE.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`OPTOCIRCU.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`PANTALOON.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`PFC.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`PHILIPCAR.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`PIDILITIN.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`PNB.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`POWERGRID.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`POLARIS.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`PTC.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`RANBAXY.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`RCOM.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`RECLTD.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`RELCAPITA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`RELIANCE.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`RELINFRA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`RENUKA.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`RPOWER.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`SAIL.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`SBIN.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`SCI.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`SESAGOA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`SIEMENS.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`SOBHA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`SUZLON.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`SUNPHARMA.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`TATAMOTOR.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`TATAPOWER.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`TATASTEEL.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`TCS.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`TULIP.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`TVSMOTOR.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`UCO.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ULTRACEMC.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`UNITECH.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`VIDEOCON.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`VIJAYABAN.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`VIPIND.BO` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`VOLTAS.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`WIPRO.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`WELCORP.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`YESBANK.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `stockquote`.`ZEEL.NS` (
 `ID` bigint(20) NOT NULL AUTO_INCREMENT, 
 TradeDate datetime NOT NULL, 
 OpenPrice FLOAT(9,2) NOT NULL, 
 HighPrice FLOAT(9,2) NOT NULL, 
 LowPrice FLOAT(9,2) NOT NULL, 
 LastTradedPrice FLOAT(9,2) NOT NULL, 
 ClosePrice FLOAT(9,2) NOT NULL, 
 TotalTradedQuantity int NOT NULL, 
 Turnover FLOAT(9,2) NOT NULL, 
 PRIMARY KEY (`ID`) 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

