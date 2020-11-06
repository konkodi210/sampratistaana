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
  ,	INVENTRY_TYPE TEXT NOT NULL
  ,	UNIT_NAME TEXT NOT NULL
  ,	UNIT_PRICE INTEGER NOT NULL
  ,	INVENTROY_COUNT INTEGER NOT NULL
  , FOREIGN KEY ([LEDGER_ENTRY_NO]) REFERENCES [LEDGER] ([ENTRY_NO]) 
		ON DELETE NO ACTION ON UPDATE NO ACTION  
);