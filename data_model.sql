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
 );

DROP TABLE IF EXISTS "MEMBER";
CREATE TABLE IF NOT EXISTS "MEMBER" (
	MEMBER_NO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
  , LEDGER_ENTRY_NO INTEGER NOT NULL	
  ,	NAME TEXT NOT NULL
  ,	NICK_NAME TEXT
  ,	ADDRESS TEXT
  ,	MEMBERSHIP_TYPE TEXT
  , MOBILE_NO TEXT
  , PHONE_NO TEXT
  , EMAIL TEXT
  , DATE_OF_BIRTH INTEGER
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
  , FOREIGN KEY ([LEDGER_ENTRY_NO]) REFERENCES [LEDGER] ([ENTRY_NO]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  
);

DROP TABLE IF EXISTS INVENTORY;
CREATE TABLE IF NOT EXISTS INVENTORY (
	INVENTORY_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
  ,	LEDGER_ENTRY_NO INTEGER NOT NULL
  ,	INVENTORY_TYPE TEXT NOT NULL
  ,	UNIT_NAME TEXT NOT NULL UNIQUE
  ,	UNIT_PRICE INTEGER NOT NULL
  ,	INVENTROY_COUNT INTEGER NOT NULL
  , FOREIGN KEY ([LEDGER_ENTRY_NO]) REFERENCES [LEDGER] ([ENTRY_NO]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  
);

DROP TABLE IF EXISTS BOOK_SALE;
CREATE TABLE IF NOT EXISTS BOOK_SALE (
	BOOK_SALE_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
  , LEDGER_ENTRY_NO INTEGER NOT NULL
  , INVENTORY_ID INTEGER NOT NULL
  , UNIT_COUNT INTEGER NOT NULL
  , CUSTOMER_NAME TEXT NOT NULL
  , FOREIGN KEY ([LEDGER_ENTRY_NO]) REFERENCES [LEDGER] ([ENTRY_NO]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  
  , FOREIGN KEY ([INVENTORY_ID]) REFERENCES [INVENTORY] ([INVENTORY_ID]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION		
);


