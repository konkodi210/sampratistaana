package org.sampratistaana;

import static org.sampratistaana.ConnectionFactory.dbSession;

import java.util.List;

import org.hibernate.Session;
import org.sampratistaana.beans.BankAccount;
import org.sampratistaana.beans.Property;

public class ListOfValues {
	public List<Property> getBankAccounts(){
		return getProperties("SAVINGS","BANK_ACCOUNT");
	}

	public List<Property> getFundTypes(){
		return getProperties("FUND","FUND_TYPE");
	}

	public List<Property> getExpenseTypes(){
		return getProperties("EXPENSE","EXPENSE_TYPE");
	}

	public List<Property> getProperties(String propertyName, String propertyKey){
		//TODO: Add caching. 
		try(Session session = dbSession()){
			return session
					.createQuery("SELECT p"
							+ " FROM Property p "
							+ " where p.propertyName = :propertyName "
							+ " AND p.propertyKey= :propertyKey "
							+ " AND p.flag='Y'",Property.class)
					.setParameter("propertyName", propertyName)
					.setParameter("propertyKey", propertyKey)
					.list();
		}
	}
	
	public List<BankAccount> getBankAccountTable(){
		try(Session session = dbSession()){
			return session.createQuery("FROM BankAccount",BankAccount.class).getResultList();
		}
	}
	
	public List<String> getLovs(){
		try(Session session = dbSession()){
			return session
					.createQuery("SELECT DISTINCT p.propertyName FROM Property p",String.class)
					.getResultList();
		}
	}
}
