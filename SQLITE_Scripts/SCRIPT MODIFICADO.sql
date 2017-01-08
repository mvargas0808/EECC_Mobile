-- -----------------------------------------------------
-- Table UserRoles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS UserRoles (
  UserRoleId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  RoleName VARCHAR(20) NOT NULL,
  Enabled BIT NOT NULL);

-- -----------------------------------------------------
-- Table Users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Users (
  UserId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Name VARCHAR(50) NOT NULL,
  Lastname VARCHAR(50) NOT NULL,
  Email VARCHAR(60) NOT NULL,
  Password VARBINARY(300) NOT NULL,
  CreationDate DATE NOT NULL,
  Enabled BIT NOT NULL,
  UserRoleId INTEGER NOT NULL,
    FOREIGN KEY (UserRoleId)
    REFERENCES UserRoles (UserRoleId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table StructureTypes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS StructureTypes (
  StructureTypeId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Name VARCHAR(40) NOT NULL,
  Enabled BIT NOT NULL);

-- -----------------------------------------------------
-- Table Countries
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Countries (
  CountryId INTEGER NOT NULL PRIMARY KEY ,
  CountryName VARCHAR(60) NOT NULL);

-- -----------------------------------------------------
-- Table Provinces
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Provinces (
  ProvinceId INTEGER NOT NULL PRIMARY KEY ,
  ProvinceName VARCHAR(30) NOT NULL,
  CountryId INTEGER NOT NULL,
    FOREIGN KEY (CountryId)
    REFERENCES Countries (CountryId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table Cantons
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Cantons (
  CantonId INTEGER NOT NULL PRIMARY KEY ,
  CantonName VARCHAR(60) NOT NULL,
  ProvinceId INTEGER NOT NULL,
    FOREIGN KEY (ProvinceId)
    REFERENCES Provinces (ProvinceId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table Districts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Districts (
  DistrictId INTEGER NOT NULL PRIMARY KEY ,
  DistrictName VARCHAR(60) NOT NULL,
  CantonId INTEGER NOT NULL,
    FOREIGN KEY (CantonId)
    REFERENCES Cantons (CantonId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table Projects
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Projects (
  ProjectId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Name VARCHAR(60) NOT NULL,
  ComponentDescription VARCHAR(1000) NOT NULL,
  StructureUseDescription VARCHAR(1000) NOT NULL,
  Latitude DECIMAL(13,10) NOT NULL,
  Longitude DECIMAL(13,10) NOT NULL,
  CreationDate DATE NOT NULL,
  StructureCreationDate DATE NULL,
  Token VARCHAR(50) NULL,
  Enabled BIT NOT NULL,
  UserId INTEGER NOT NULL,
  StructureTypeId INTEGER NOT NULL,
  DistrictId INTEGER NOT NULL,
    FOREIGN KEY (UserId)
    REFERENCES Users (UserId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (StructureTypeId)
    REFERENCES StructureTypes (StructureTypeId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (DistrictId)
    REFERENCES Districts (DistrictId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table Evaluations
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Evaluations (
  EvaluationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  CreationDate DATE NOT NULL,
  Enabled BIT NOT NULL,
  ProjectId INTEGER NOT NULL,
    FOREIGN KEY (ProjectId)
    REFERENCES Projects (ProjectId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table IAAInformation
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IAAInformation (
  IAAInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Row INTEGER NOT NULL,
  Description VARCHAR(150) NOT NULL,
  Enabled BIT NOT NULL);

-- -----------------------------------------------------
-- Table CorrosionDamageIndexes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS CorrosionDamageIndexes (
  CorrosionDamageIndexId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  IDC DECIMAL(5,3) NOT NULL,
  IAA DECIMAL(5,3) NOT NULL,
  ISC DECIMAL(5,3) NOT NULL,
  Enabled BIT NOT NULL,
  IAAIndicatorId INTEGER NOT NULL,
  EvaluationId INTEGER NOT NULL,
    FOREIGN KEY (IAAIndicatorId)
    REFERENCES IAAInformation (IAAInformationId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (EvaluationId)
    REFERENCES Evaluations (EvaluationId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table StructuralIndexes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS StructuralIndexes (
  StructuralIndexId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  IE DECIMAL(5,3) NOT NULL,
  Enabled BIT NOT NULL,
  EvaluationType BIT NOT NULL,
  EvaluationId INTEGER NOT NULL,
    FOREIGN KEY (EvaluationId)
    REFERENCES Evaluations (EvaluationId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table StructuralDamageIndexes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS StructuralDamageIndexes (
  StructuralDamageIndexId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  IDE VARCHAR(10) NOT NULL,
  StructuralIndex DECIMAL(5,3) NOT NULL,
  CorrosionIndex DECIMAL(5,3) NOT NULL,
  Enabled BIT NOT NULL,
  EvaluationId INTEGER NOT NULL,
    FOREIGN KEY (EvaluationId)
    REFERENCES Evaluations (EvaluationId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table IndicatorNames
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IndicatorNames (
  IndicatorNameId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Row INTEGER NOT NULL,
  Col INTEGER NOT NULL,
  Description VARCHAR(150) NOT NULL,
  Enabled BIT NOT NULL);

-- -----------------------------------------------------
-- Table IDCIndicators
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IDCIndicators (
  IndicatorNameId INTEGER NOT NULL,
  CorrosionDamageIndexId INTEGER NOT NULL,
  Value INTEGER NOT NULL,
  Enabled BIT NOT NULL,
    FOREIGN KEY (CorrosionDamageIndexId)
    REFERENCES CorrosionDamageIndexes (CorrosionDamageIndexId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (IndicatorNameId)
    REFERENCES IndicatorNames (IndicatorNameId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table IATInformation
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IATInformation (
  IATInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Row INTEGER NOT NULL,
  Col INTEGER NOT NULL,
  ElementType VARCHAR(20) NOT NULL,
  Description VARCHAR(100) NOT NULL,
  Enabled VARCHAR(45) NOT NULL);

-- -----------------------------------------------------
-- Table IALInformation
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IALInformation (
  IALInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Row INTEGER NOT NULL,
  Col INTEGER NOT NULL,
  ElementType VARCHAR(20) NOT NULL,
  Description VARCHAR(100) NOT NULL,
  Enabled VARCHAR(45) NOT NULL);

-- -----------------------------------------------------
-- Table ESInformation
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ESInformation (
  ESInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Row INTEGER NOT NULL,
  Col INTEGER NOT NULL,
  ElementType VARCHAR(20) NOT NULL,
  Description VARCHAR(100) NOT NULL,
  Enabled VARCHAR(45) NOT NULL);

-- -----------------------------------------------------
-- Table IDEInformation
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IDEInformation (
  IDEInformationId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  Row INTEGER NOT NULL,
  Col INTEGER NOT NULL,
  failureConsequence VARCHAR(20) NOT NULL,
  Description VARCHAR(100) NOT NULL,
  Enabled BIT NOT NULL);

-- -----------------------------------------------------
-- Table IAT
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IAT (
  IATInformationId INTEGER NOT NULL,
  StructuralIndexId INTEGER NOT NULL,
  IAT DECIMAL(5,3) NOT NULL,
  Enabled BIT NOT NULL,
    FOREIGN KEY (IATInformationId)
    REFERENCES IATInformation (IATInformationId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (StructuralIndexId)
    REFERENCES StructuralIndexes (StructuralIndexId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table IAL
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IAL (
  IALInformationId INTEGER NOT NULL,
  StructuralIndexId INTEGER NOT NULL,
  IAL DECIMAL(5,3) NOT NULL,
  Enabled BIT NOT NULL,
    FOREIGN KEY (IALInformationId)
    REFERENCES IALInformation (IALInformationId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (StructuralIndexId)
    REFERENCES StructuralIndexes (StructuralIndexId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table ES
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ES (
  ESInformationId INTEGER NOT NULL,
  StructuralIndexId INTEGER NOT NULL,
  ES DECIMAL(5,3) NOT NULL,
  Enabled BIT NOT NULL,
    FOREIGN KEY (ESInformationId)
    REFERENCES ESInformation (ESInformationId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (StructuralIndexId)
    REFERENCES StructuralIndexes (StructuralIndexId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- -----------------------------------------------------
-- Table IDE
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS IDE (
  IDEInformationId INTEGER NOT NULL,
  StructuralDamageIndexId INTEGER NOT NULL,
  Enabled BIT NOT NULL,
    FOREIGN KEY (IDEInformationId)
    REFERENCES IDEInformation (IDEInformationId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (StructuralDamageIndexId)
    REFERENCES StructuralDamageIndexes (StructuralDamageIndexId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);