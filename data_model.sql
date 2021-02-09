
DROP TABLE IF EXISTS BANK_ACCOUNTS;

CREATE TABLE BANK_ACCOUNTS(
  BANK_ACCOUNT_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  ACCOUNT_NO TEXT,
  BANK_NAME TEXT
);

DROP TABLE IF EXISTS LEDGER;

CREATE TABLE IF NOT EXISTS LEDGER (
	 ENTRY_NO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
   , ENTRY_TYPE TEXT NOT NULL
   , ENTRY_CATEGORY TEXT NOT NULL
   , ENTRY_VALUE NUMERIC NOT NULL
   , ENTRY_DATE INTEGER NOT NULL
   , MODE_OF_TRAN TEXT
   , EXTERNAL_TRAN_ID TEXT
   , PAN_NO TEXT
   , ENTRY_DESC TEXT
   , BANK_ACCOUNT_ID INTEGER
   , FUND_TYPE TEXT
   , FOREIGN KEY ([BANK_ACCOUNT_ID]) REFERENCES [BANK_ACCOUNTS] ([BANK_ACCOUNT_ID]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  
 );

DROP TABLE IF EXISTS "MEMBER";
CREATE TABLE IF NOT EXISTS "MEMBER" (
	MEMBER_NO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
  , MEMBER_ID TEXT
  , LEDGER_ENTRY_NO INTEGER NOT NULL	
  ,	NAME TEXT NOT NULL
  ,	NICK_NAME TEXT
  ,	ADDRESS TEXT
  ,	MEMBERSHIP_TYPE TEXT
  , MOBILE_NO TEXT
  , PHONE_NO TEXT
  , EMAIL TEXT
  , DATE_OF_BIRTH INTEGER
  , MEMBER_STATUS TEXT
  , AADHAR_NO TEXT
  , END_DATE INTEGER
  , FOREIGN KEY ([LEDGER_ENTRY_NO]) REFERENCES [LEDGER] ([ENTRY_NO]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  
 );


DROP TABLE IF EXISTS DONATION;
CREATE TABLE IF NOT EXISTS DONATION (
	DONATION_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
  ,	LEDGER_ENTRY_NO INTEGER NOT NULL
  ,	NAME TEXT NOT NULL
  ,	NICK_NAME TEXT
  ,	ADDRESS TEXT
  ,	MOBILE_NO TEXT
  ,	PHONE_NO TEXT
  , EMAIL TEXT
  ,	DATE_OF_BIRTH INTEGER
  , MEMBER_NO INTEGER
  , FOREIGN KEY ([LEDGER_ENTRY_NO]) REFERENCES [LEDGER] ([ENTRY_NO]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  
  , FOREIGN KEY ([MEMBER_NO]) REFERENCES [MEMBER] ([MEMBER_NO]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  	
);

DROP TABLE IF EXISTS INVENTORY;
CREATE TABLE IF NOT EXISTS INVENTORY (
	INVENTORY_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
--  ,	LEDGER_ENTRY_NO INTEGER NOT NULL
  ,	INVENTORY_TYPE TEXT NOT NULL
  ,	UNIT_NAME TEXT NOT NULL UNIQUE
  ,	UNIT_PRICE INTEGER NOT NULL
  ,	INVENTROY_COUNT INTEGER NOT NULL
--TODO: Things become complicated when we link to Ledger. 
--  , FOREIGN KEY ([LEDGER_ENTRY_NO]) REFERENCES [LEDGER] ([ENTRY_NO]) 
--		ON DELETE NO ACTION ON UPDATE NO ACTION  
);

DROP TABLE IF EXISTS BOOK_SALE;
CREATE TABLE IF NOT EXISTS BOOK_SALE (
	BOOK_SALE_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
  , LEDGER_ENTRY_NO INTEGER NOT NULL
  , INVENTORY_ID INTEGER NOT NULL
  , UNIT_COUNT INTEGER NOT NULL
  , CUSTOMER_NAME TEXT 
  , SELLER_NAME TEXT 
  , FOREIGN KEY ([LEDGER_ENTRY_NO]) REFERENCES [LEDGER] ([ENTRY_NO]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  
  , FOREIGN KEY ([INVENTORY_ID]) REFERENCES [INVENTORY] ([INVENTORY_ID]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION		
);

DROP TABLE IF EXISTS EXPENSE;
CREATE TABLE EXPENSE (
    EXPENSE_ID      INTEGER PRIMARY KEY AUTOINCREMENT
                            NOT NULL,
    LEDGER_ENTRY_NO INTEGER REFERENCES LEDGER (ENTRY_NO) 
                            NOT NULL,
    EXPENSE_TYPE    VARCHAR NOT NULL,
    FUND_TYPE       VARCHAR NOT NULL
);

DROP TABLE IF EXISTS PROPERTIES;
CREATE TABLE PROPERTIES (
    PROPERTY_ID    INTEGER PRIMARY KEY AUTOINCREMENT,
    PROPERTY_NAME  TEXT,
    PROPERTY_KEY   TEXT,
    PROPERTY_VALUE TEXT,
    PROPERTY_DESC  TEXT, 
    FLAG           CHAR,
    CONSTRAINT FLG_Y_N CHECK (FLAG IN ('Y', 'N') ) 
);

DROP TABLE IF EXISTS DYN_SQL_REPORT;
CREATE TABLE DYN_SQL_REPORT (
	DYN_SQL_REPORT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
	REPORT_NAME TEXT,
	REPORT_QUERY TEXT
);

DELETE FROM PROPERTIES  WHERE PROPERTY_KEY  IN ('EXPENSE_TYPE','FUND_TYPE','BOOK_SALE_TYPE','SHOP_NAME');

INSERT  INTO PROPERTIES (PROPERTY_NAME,PROPERTY_KEY,PROPERTY_VALUE,FLAG)
values
('FUND','FUND_TYPE','nidhi.art','Y'),
('FUND','FUND_TYPE','nidhi.book-print','Y'),
('FUND','FUND_TYPE','nidhi.daily-expense','Y'),
('FUND','FUND_TYPE','nidhi.karmakaanda','Y'),
('FUND','FUND_TYPE','nidhi.kattada','Y'),
('FUND','FUND_TYPE','nidhi.mm-bhat-prize','Y'),
('FUND','FUND_TYPE','nidhi.samparaka-library','Y'),
('FUND','FUND_TYPE','nidhi.sampradaana','Y'),
('FUND','FUND_TYPE','nidhi.samskrita','Y'),
('FUND','FUND_TYPE','nidhi.sangaveda','Y'),
('FUND','FUND_TYPE','nidhi.scholar-appriciation','Y'),
('FUND','FUND_TYPE','nidhi.social-work','Y'),
('FUND','FUND_TYPE','nidhi.speech','Y'),
('FUND','FUND_TYPE','nidhi.suvidha','Y'),
('FUND','FUND_TYPE','nidhi.swastya','Y'),
('FUND','FUND_TYPE','nidhi.symposium','Y'),
('FUND','FUND_TYPE','nidhi.uggappakodi-prize','Y'),
('FUND','FUND_TYPE','nidhi.yearly-celeberation','Y'),
('FUND','FUND_TYPE','nidhi.misc','Y'),

--('SAVINGS','BANK_ACCOUNT','bank.karanataka','Y'),
--('SAVINGS','BANK_ACCOUNT','bank.syndicate','Y'),

('EXPENSE','EXPENSE_TYPE','expense.book-printing','Y'),
('EXPENSE','EXPENSE_TYPE','expense.book-publishing','Y'),
('EXPENSE','EXPENSE_TYPE','expense.book-purchase','Y'),
('EXPENSE','EXPENSE_TYPE','expense.phone-bill','Y'),
('EXPENSE','EXPENSE_TYPE','expense.internet-bill','Y'),
('EXPENSE','EXPENSE_TYPE','expense.transportation','Y'),
('EXPENSE','EXPENSE_TYPE','expense.asset-purchase','Y'),
('EXPENSE','EXPENSE_TYPE','expense.stationery-or-printing','Y'),
('EXPENSE','EXPENSE_TYPE','expense.donation','Y'),
('EXPENSE','EXPENSE_TYPE','expense.events','Y'),
('EXPENSE','EXPENSE_TYPE','expense.others','Y'),
('BOOK_SALE','BOOK_SALE_TYPE','MEMBER','Y'),
('BOOK_SALE','BOOK_SALE_TYPE','SHOP','Y'),
('BOOK_SALE','BOOK_SALE_TYPE','OTHERS','Y');

INSERT  INTO PROPERTIES (PROPERTY_NAME,PROPERTY_KEY,PROPERTY_VALUE,PROPERTY_DESC,FLAG)
values
('SHOP','SHOP_NAME','Vedanta Book House','Jayanagar, Bangalore','Y');
INSERT INTO BANK_ACCOUNTS (ACCOUNT_NO,BANK_NAME)
VALUES
('1234','bank.karanataka'),
('1234','bank.syndicate');

INSERT INTO INVENTORY (INVENTORY_ID, INVENTORY_TYPE, UNIT_NAME, UNIT_PRICE, INVENTROY_COUNT) 
VALUES
(1,  '0', 'ಮಂತ್ರಮಂಜರೀ', 250, 100),
(2,  '0', 'ಶ್ಲೋಕಸಮಾಹಾರಃ  - ಸಂಪುಟ -೧', 250, 100),
(3,  '0', 'ಪುರಾಣಯಾನ', 250, 100),
(4,  '0', 'ಶ್ಲೋಕಸಮಾಹಾರಃ  - ಸಂಪುಟ -೨', 250, 100),
(5,  '0', 'ವ್ಯದಿಕಸಾಂಬಾರಃ', 250, 100),
(6,  '0', 'ಶಾರದನೂರಾದನಮ್', 250, 100),
(7,  '0', 'ಕನ್ನಡ ಸಂದ್ಯಾ ಭಾಷ್ಯ ', 250, 100),
(8,  '0', 'ಪುರಾಣಲೋಕ ', 250, 100),
(9,  '0', 'ಪಂಚಾಯತನ ದೇವರ ಪೂಜೆ', 250, 100),
(10, '0', 'ವೇದ-ವೇದಾಂಗಪಾರಿವಾರಃ', 250, 100),
(11, '0', 'ವ್ಯಧಿಕಾನುಬಂದಸಮುಚ್ಛಯ', 250, 100),
(12, '0', 'ಶ್ರೀ ವಿಷ್ಣುಪ್ರೋಕ್ತಸಹಸ್ರನಾಮ', 250, 100),
(13, '0', 'ಗಂಗಾಕಲ್ಪೋಕ್ತಪೂಜಾವಿಧಿ ', 250, 100),
(14, '0', 'ಆದಿತ್ಯದಿನವಗ್ರಹಪೂಜಾವಿಧಿಹಿ ', 250, 100),
(15, '0', 'ಸಂಧ್ಯಾವಂದನಮ್ ', 250, 100),
(16, '0', 'ಶ್ರೀ ವರದಶಂಕರಪೂಜಾವಿಧಿ ', 250, 100);


INSERT INTO DYN_SQL_REPORT (REPORT_QUERY,REPORT_NAME) VALUES('Select m.*,l.* from "MEMBER" M
join LEDGER l
on m.LEDGER_ENTRY_NO  = l.ENTRY_NO', 'ALL_MEMBER_REPORT');

INSERT INTO DYN_SQL_REPORT (REPORT_QUERY,REPORT_NAME) VALUES('select  case when MEMBERSHIP_TYPE = 0 then "LM" else "OM" end || MEMBER_NO as MEMBERSHIP_NO,NAME, ADDRESS ,PHONE_NO ,EMAIL, MEMBER_STATUS,MEMBERSHIP_TYPE from "MEMBER"', 'MEMBERSHIP_REPORT');

