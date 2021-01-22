package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.sampratistaana.TestUtils.getProperty;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.sampratistaana.beans.Inventory.InventoryType;
import org.sampratistaana.controllers.BookInventoryManagementContoller.DisplayInventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

public class BookInventoryManagementTest extends BaseApplicationTest {
	@Before
	public void setup() {
		clickOn("#admin");
		clickOn("#adminInventoryBooks");
	}
	
	@Test
	public void testBookInventoryManagement() {
		Button addBtn = find("#addBtn");
		Button deleteBtn = find("#deleteBtn");
		Button saveBtn = find("#saveBtn");
		TableView<DisplayInventory> table = find("#bookTable");
		
		assertThat("Delete Button Disabled", deleteBtn.isDisabled(),equalTo(true));
		assertThat("Add Button Enabled", addBtn.isDisabled(),equalTo(false));
		assertThat("save Button Enabled", saveBtn.isDisabled(),equalTo(false));
		assertThat("Must have rows populated", table.getItems(),not(empty()));
	
		TableViewSelectionModel<DisplayInventory> model = table.getSelectionModel();
		model.select(0);
		assertThat("Delete Button Disabled", deleteBtn.isDisabled(),equalTo(false));
		clickOn(addBtn);
		model.select(0);
		SimpleStringProperty name =(SimpleStringProperty)getProperty(model.getSelectedItem(),"name");
		SimpleDoubleProperty unitPrice =(SimpleDoubleProperty)getProperty(model.getSelectedItem(),"unitPrice");
		SimpleIntegerProperty count =(SimpleIntegerProperty)getProperty(model.getSelectedItem(),"count");
		
		name.set(UUID.randomUUID().toString());
		unitPrice.set(100);
		count.set(10);
		clickOn(saveBtn);
		assertThat("New Row inserted",
				checkInDb(name.get(),100,10),
				equalTo(true));
		
		model.select(0);
		name.set(UUID.randomUUID().toString());
		unitPrice.set(99.5);
		count.set(11);
		clickOn(saveBtn);
		assertThat("Row Updated",
				checkInDb(name.get(),99.5,11),
				equalTo(true));
		
		model.select(0);
		performConfirmedDelete("#deleteBtn");
		assertThat(" Row Deleted",
				checkInDb(name.get(),99.5,11),
				equalTo(false));
		
	}
	
	private boolean checkInDb(String name,double unitPrice,int count) {
		return new CreditManager().getInventory(InventoryType.BOOK)
				.stream()
				.filter(x-> name.equals(x.getUnitName()) && unitPrice==x.getUnitPrice() && count==x.getInventoryCount())
				.count() == 1;
	}
}
