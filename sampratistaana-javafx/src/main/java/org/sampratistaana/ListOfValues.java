package org.sampratistaana;

import static org.sampratistaana.ConnectionFactory.dbSession;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sampratistaana.beans.BankAccount;
import org.sampratistaana.beans.Property;

public class ListOfValues {

	public List<Property> getFundTypes(){
		return getProperties("FUND","FUND_TYPE");
	}

	public List<Property> getExpenseTypes(){
		return getProperties("EXPENSE","EXPENSE_TYPE");
	}
	
	public List<Property> getBookSaleTypes(){
		return getProperties("BOOK_SALE", "BOOK_SALE_TYPE");
	}
	
	public List<Property> getShopNames(){
		return getProperties("SHOP", "SHOP_NAME");
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

	public List<Object[]> getLovs(){
		try(Session session = dbSession()){
			return session
					.createQuery("SELECT DISTINCT propertyName,propertyKey FROM Property",Object[].class)
					.getResultList();
		}
	}

	public void saveLov(List<Property> lovValList,List<Property> deleteList) {
		if((lovValList==null || lovValList.size()==0) && (deleteList==null || deleteList.size() == 0)) {
			return;
		}
		try(Session session=dbSession()){
			Transaction tran = session.beginTransaction();
			if(lovValList!=null) {
				lovValList.forEach(lov -> session.saveOrUpdate(lov));
			}
			if(deleteList!=null) {
				deleteList.forEach(lov -> session.remove(lov));
			}
			tran.commit();
		}
	}
}
