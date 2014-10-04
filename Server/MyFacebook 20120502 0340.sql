-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.41-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema my_facebook
--

CREATE DATABASE IF NOT EXISTS my_facebook;
USE my_facebook;

--
-- Definition of table `roster`
--

DROP TABLE IF EXISTS `roster`;
CREATE TABLE `roster` (
  `index` int(10) unsigned NOT NULL auto_increment,
  `user_id` varchar(45) NOT NULL,
  `roster_id` varchar(45) NOT NULL,
  `status` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`index`),
  KEY `FK_user_id` (`user_id`),
  KEY `FK_roster_id` (`roster_id`),
  CONSTRAINT `FK_roster_id` FOREIGN KEY (`roster_id`) REFERENCES `userproperties` (`user_id`),
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `userproperties` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roster`
--

/*!40000 ALTER TABLE `roster` DISABLE KEYS */;
INSERT INTO `roster` (`index`,`user_id`,`roster_id`,`status`) VALUES 
 (1,'riyad','sheta',1),
 (10,'riyad','a',1),
 (23,'sheta','riyad',1),
 (24,'a','riyad',1),
 (25,'a','b',1),
 (30,'b','a',1),
 (31,'z','b',1),
 (33,'b','z',1),
 (34,'c','z',1),
 (35,'z','c',1),
 (36,'riyad','b',1),
 (37,'b','riyad',1),
 (38,'riyad','c',1),
 (39,'c','riyad',1),
 (42,'z','riyad',1),
 (43,'riyad','z',1),
 (44,'riyad','x',1),
 (45,'x','riyad',1),
 (46,'riyad','y',1),
 (47,'y','riyad',1),
 (48,'riyad','w',1),
 (49,'w','riyad',1),
 (50,'c','a',1),
 (51,'a','c',1),
 (54,'w','a',1),
 (55,'x','a',1),
 (56,'a','x',1),
 (57,'a','w',1),
 (58,'a','y',0),
 (59,'q','p',1),
 (60,'p','q',1),
 (62,'s','q',1),
 (63,'q','s',1),
 (65,'r','s',1),
 (66,'s','r',1),
 (67,'r','riyad',1),
 (68,'riyad','r',1);
/*!40000 ALTER TABLE `roster` ENABLE KEYS */;


--
-- Definition of table `userproperties`
--

DROP TABLE IF EXISTS `userproperties`;
CREATE TABLE `userproperties` (
  `user_id` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `user_name` varchar(45) default NULL,
  `profession` varchar(45) default NULL,
  `city` varchar(45) default NULL,
  `company` varchar(45) default NULL,
  `college` varchar(45) default NULL,
  `graduation_year` varchar(45) default NULL,
  `status` varchar(45) default NULL,
  PRIMARY KEY  USING BTREE (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userproperties`
--

/*!40000 ALTER TABLE `userproperties` DISABLE KEYS */;
INSERT INTO `userproperties` (`user_id`,`password`,`user_name`,`profession`,`city`,`company`,`college`,`graduation_year`,`status`) VALUES 
 ('a','a','aaa aaa','a_profession','a_city','a_company','a_college','a_graduation_year','my profile status'),
 ('b','b','bbb bbb','b_profession','dhaka',NULL,'b_college',NULL,'Hello world'),
 ('c','c','ccc ccc','softEng',NULL,'sdsl','utsa','2001',NULL),
 ('m','m',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 ('p','p','pp ppp','p_profession','p_city','','p_college','',NULL),
 ('q','q','pp ppp','q_profession','p_city','','p_college','',NULL),
 ('r','r','rrr rrr','','r_city','','r_college','',NULL),
 ('riyad','riyad','riyad shairi','student','san antonio','samsung','utsa','2010',NULL),
 ('s','s','sss sss','s_prof','dhaka','','','s_year',NULL),
 ('sheta','sheta','rehan sheta','student','dhaka','utsa','du','2010',NULL),
 ('w','w','www www','w_profession','w_city','','w_college','',NULL),
 ('x','x','xxx xxx','','x_city','','x_college','',NULL),
 ('y','y','yyy yyy','','y_city','','y_college','',NULL),
 ('z','z','zzz zzz','z_profession','z_city','z_company','z_college','2111',NULL);
/*!40000 ALTER TABLE `userproperties` ENABLE KEYS */;


--
-- Definition of table `wall`
--

DROP TABLE IF EXISTS `wall`;
CREATE TABLE `wall` (
  `idwall` int(10) unsigned NOT NULL auto_increment,
  `sender` varchar(45) NOT NULL,
  `receiver` varchar(45) NOT NULL,
  `post` blob NOT NULL,
  PRIMARY KEY  (`idwall`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `wall`
--

/*!40000 ALTER TABLE `wall` DISABLE KEYS */;
INSERT INTO `wall` (`idwall`,`sender`,`receiver`,`post`) VALUES 
 (12,'a','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F3476CC90000000074000A6120746F207269796164740005726979616474000161),
 (18,'b','a',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F34D341A00000002707400016174000162),
 (19,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F36107A30000000074000B68656C6C6F20736865746174000573686574617400057269796164),
 (20,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F361D4EC0000000074000468656C6F74000573686574617400057269796164),
 (21,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F36205E500000000740008686920726979616474000572697961647400057368657461),
 (22,'b','z',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F66EC1E500000002707400017A74000162),
 (23,'z','c',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F67204E00000000270740001637400017A),
 (24,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F676481C0000000074001468656C6C6F20736865746120686F77207220753F74000573686574617400057269796164),
 (25,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F67762B3000000007400056C7576207574000572697961647400057368657461),
 (26,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F67BCC140000000074000B636861742065206173686F74000572697961647400057368657461),
 (27,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F67C2B4B000000007400106173746573692077616974206B6F726F74000573686574617400057269796164),
 (28,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F680C1600000000074001348692E2E2E486F77206172652075203F21212174000572697961647400057368657461),
 (29,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F681734C000000007400147269796164207368616972692077726974696E6774000573686574617400057269796164),
 (30,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136F69C6D7800000000740005746573743174000573686574617400057269796164),
 (31,'b','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB266FDB0000000270740005726979616474000162),
 (32,'c','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB2C4D330000000270740005726979616474000163),
 (33,'z','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB3342C2000000027074000572697961647400017A),
 (34,'riyad','z',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB35F8E900000002707400017A7400057269796164),
 (35,'z','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB37F70F0000000074000B68656C6C6F20726979616474000572697961647400017A),
 (36,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB3DB0660000000074001168656C6C6F207269796164732077616C6C74000572697961647400057368657461),
 (37,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB3E6F5F0000000074001468656C6C6F20736865746120686920736865746174000573686574617400057269796164),
 (38,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB7F77B70000000074000D63616C6C6261636B20706F737474000572697961647400057368657461),
 (39,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB7FFD8F00000000740005616761696E74000572697961647400057368657461),
 (40,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB83A1950000000074001463616C6C6261636B20636865636B20616761696E74000572697961647400057368657461),
 (41,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FB84231900000000740007646F6E65203A4474000572697961647400057368657461),
 (42,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBA248DC0000000074000568656C6C6F74000572697961647400057368657461),
 (43,'x','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBA61E950000000270740005726979616474000178),
 (44,'y','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBAFC1AF0000000270740005726979616474000179),
 (45,'w','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBB4A8A70000000270740005726979616474000177),
 (46,'b','a',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBD11D730000000074000268697400016174000162),
 (47,'b','a',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBD17B180000000074000568656C6C6F7400016174000162),
 (48,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBD581F00000000074000074000573686574617400057269796164),
 (49,'riyad','sheta',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBD65E9600000000740005616466617374000573686574617400057269796164),
 (50,'a','c',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FBFCA2FE00000002707400016374000161),
 (51,'sheta','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FC0811A10000000074000568656C6C6F74000572697961647400057368657461),
 (52,'a','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FC0E124B0000000074000761736466617364740005726979616474000161),
 (53,'a','riyad',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FC0E2E8C00000000740006617364666173740005726979616474000161),
 (54,'riyad','w',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FD306D00000000007400157777772063616E20666C792C2069736E742069743F740001777400057269796164),
 (55,'a','x',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FDBB118300000002707400017874000161),
 (56,'a','w',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E0001787000000136FDBB24A400000002707400017774000161),
 (57,'p','q',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E000178700000013700EC65C300000002707400017174000170),
 (58,'q','s',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E000178700000013700ED1E8400000002707400017374000171),
 (59,'q','p',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E000178700000013700EDE26F0000000074000968656C6C6F207070707400017074000171),
 (60,'s','q',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E000178700000013700EE1C0D0000000074000768656C6C6F20717400017174000173),
 (61,'s','r',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E000178700000013700EEEA0500000002707400017274000173),
 (62,'riyad','r',0xACED00057372000A73747562732E506F737451E688158B8107D90200054A000464617465490004747970654C0007636F6E74656E747400124C6A6176612F6C616E672F537472696E673B4C0008726563656976657271007E00014C000673656E64657271007E000178700000013700F297E80000000270740001727400057269796164);
/*!40000 ALTER TABLE `wall` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
