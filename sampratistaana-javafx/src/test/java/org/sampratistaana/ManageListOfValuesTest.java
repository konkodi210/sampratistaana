package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.sampratistaana.TestUtils.getProperty;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.sampratistaana.controllers.LovAdminController.DisplayProp;
import org.sampratistaana.controllers.LovAdminController.LovProp;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

public class ManageListOfValuesTest extends BaseApplicationTest {
	@Before
	public void setup() {
		clickOn("#admin");
		clickOn("#adminDropdown");
	}
	
	@Test
	public void testListOfValues() {
		ComboBox<LovProp> lovComboBox=find("#lovComboBox");
		Button addBtn = find("#addBtn");
		Button deleteBtn = find("#deleteBtn");
		Button saveBtn = find("#saveBtn");
		TableView<DisplayProp> table = find("#lovTable");
		
		assertThat("Must have values populated", lovComboBox.getItems(),not(empty()));
		assertThat("Delete Button Disabled", deleteBtn.isDisabled(),equalTo(true));
		assertThat("Add Button Enabled", addBtn.isDisabled(),equalTo(false));
		assertThat("save Button Enabled", saveBtn.isDisabled(),equalTo(false));
		assertThat("Must have rows populated", table.getItems(),not(empty()));
		
		TableViewSelectionModel<DisplayProp> model = table.getSelectionModel();
		model.select(0);
		assertThat("Delete Button Disabled", deleteBtn.isDisabled(),equalTo(false));
		clickOn(addBtn);
		model.select(0);
		SimpleStringProperty cellVal =(SimpleStringProperty)getProperty(model.getSelectedItem(),"lovVal");
		cellVal.set(UUID.randomUUID().toString());
		clickOn(saveBtn);
		assertThat("New Row inserted",
				hasValueInFundType(lovComboBox.getSelectionModel().getSelectedItem(), cellVal.get()),
				equalTo(true));
		model.select(0);
		cellVal.set(UUID.randomUUID().toString());
		clickOn(saveBtn);
		assertThat("Row Updated",
				hasValueInFundType(lovComboBox.getSelectionModel().getSelectedItem(), cellVal.get()),
				equalTo(true));
		model.select(0);
		clickOn(deleteBtn);
		assertThat("Row delete",
				hasValueInFundType(lovComboBox.getSelectionModel().getSelectedItem(), cellVal.get()),
				equalTo(false));
		
	}
	
	private boolean hasValueInFundType(LovProp lovProp,String val) {
		return new ListOfValues()
				.getProperties((String)getProperty(lovProp,"propertyName"), (String)getProperty(lovProp,"propertyKey"))
				.stream()
				.filter(x->x.getPropertyValue().equals(val))
				.count() > 0;
	}
}
