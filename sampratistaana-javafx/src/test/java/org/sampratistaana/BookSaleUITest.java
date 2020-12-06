package org.sampratistaana;

import org.junit.Before;
import org.junit.Test;

public class BookSaleUITest extends BaseApplicationTest {
	@Before
	public void setup() {
		clickOn("#creditMenu");
		clickOn("#booksaleMenu");
	}

	@Test
	public void testBooksaleForm() {
		clickOn("#bookSaleNew");
		writeToTextFiled("customerName", "Customer Name1");
		writeToTextFiled("sellerName", "New Seller1");
		writeToTextFiled("pan", "ABC1234");
		writeToTextFiled("externalTranNo", "1234ABC");
	}
}
