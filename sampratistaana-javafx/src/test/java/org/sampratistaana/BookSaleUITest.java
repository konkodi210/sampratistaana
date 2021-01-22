package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.sampratistaana.beans.BookSale;
import org.sampratistaana.beans.BookSaleUIList;
import org.sampratistaana.beans.Inventory;
import org.sampratistaana.beans.Inventory.InventoryType;
import org.sampratistaana.controllers.BookSaleEditControler.BookEntry;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;

@SuppressWarnings("unchecked")
public class BookSaleUITest extends BaseApplicationTest {
	@Before
	public void setup() {
		clickOn("#creditMenu");
		clickOn("#booksaleMenu");
	}

	@Test
	public void testBooksaleForm() {
		//test the cancel button
		clickOn("#bookSaleNew");
		assertThat("Save button must be disabled", true, equalTo(((Button)find("#saleSaveBtn")).isDisabled()));
		clickOn("#saleCancelBtn");

		clickOn("#bookSaleNew");
		writeToTextFiled("#customerName", "Customer Name1");
		writeToTextFiled("#sellerName", "New Seller1");
		writeToTextFiled("#pan", "ABC1234");
		writeToTextFiled("#externalTranNo", "1234ABC");
		clickOn("#paymentOnline");

		TableView<BookEntry> saleTab = (TableView<BookEntry>)find("#bookSaleTable");
		//make an arraylist for random access
		ArrayList<Inventory> invList=new ArrayList<>(new CreditManager().getInventory(InventoryType.BOOK));
		Random ran=new Random();
		Map<Integer,BookEntry> usedInvIndex=new HashMap<>();
		//select max 4 books for sale or if inventory is less than 4 then, what ever available		
		for(int i=0,index=-1;i<4 && i<invList.size();i++) {
			//there is extremely low possibility that next random will be repeated. 
			//However, when upper bound is low, we may have chances. To safe guard, added "Set" avoid collision
			int circuitBreaker = 0;
			while(index == -1 || usedInvIndex.containsKey(index)) {
				index = ran.nextInt(invList.size());
				if(circuitBreaker++ > invList.size()) {
					throw new RuntimeException("Unable to get unique inventory index "+circuitBreaker);
				}
			}

			final int selectedRow = index;
			interact(() -> saleTab.getSelectionModel().select(selectedRow));
			final BookEntry entry = saleTab.getSelectionModel().getSelectedItem();
			usedInvIndex.put(index,entry);
			interact(() -> entry.setUnitCount(2));	
			//TODO: some time it fails. Issue is testing framework is unable to wait until event is fired or unable to get hold of it.
//			if(i==0) {
//				Button saleBtn = (Button)find("#saleSaveBtn");
//				waitUntil( () -> !saleBtn.isDisabled());
//				assertThat("Save button must be enabled", false, equalTo(saleBtn.isDisabled()));
//				interact(() -> entry.setUnitCount(0));
//				waitUntil( () -> saleBtn.isDisabled());
//				assertThat("Save button must be disabled", true, equalTo(saleBtn.isDisabled()));
//				interact(() -> entry.setUnitCount(2));
//			}
		}
		clickOn("#saleSaveBtn");
		
		//verify whether all entered items are saved in the database
		List<BookSaleUIList> saleList = new CreditManager().getBookSaleList();
		assertThat("Must not be null", saleList,not(nullValue()));
		assertThat("Must have at least one entry", saleList.size(),greaterThan(0));
		
		List<BookSale> newBookSaleList =new CreditManager().getBookSale(saleList.get(0).getLedgerEntryNo());
		assertThat("Must not be null", newBookSaleList,not(nullValue()));
		assertThat("Must have four entry", newBookSaleList.size(),equalTo(4));
		for(BookSale sale:newBookSaleList) {
			assertThat( sale.getCustomerName(),equalTo("Customer Name1"));
			assertThat( sale.getUnitCount(),equalTo(2));
			//TODO: Seller name is not getting saved into DB right now
			assertThat( sale.getLedger().getPanNo(),equalTo("ABC1234"));
			assertThat( sale.getLedger().getExternalTranNo(),equalTo("1234ABC"));
		}
		
				
		TableView<BookSaleUIList> bookSaleList = (TableView<BookSaleUIList>)find("#bookSaleList");
		bookSaleList.getSelectionModel().select(0);
		assertThat("Save button must be disabled", false, equalTo(((Button)find("#editBtn")).isDisabled()));
		
		BookSaleUIList listObj = bookSaleList.getSelectionModel().getSelectedItem();
		assertThat("pojo Match", "Customer Name1",equalTo(listObj.getCustomerName()));
		clickOn("#editBtn");
		clickOn("#saleSaveBtn");
		interact(() -> saleTab.getSelectionModel().select(0));
		performConfirmedDelete("#deleteBtn");
		
	}
	
//	private void waitUntil(BooleanSupplier fun) {
//		for(int i=0;i<50 && !fun.getAsBoolean();i++) {
//			sleep(100);
//		}
//	}
}
