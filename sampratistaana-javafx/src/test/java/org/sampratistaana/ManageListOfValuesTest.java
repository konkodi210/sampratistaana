package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
import static org.sampratistaana.TestUtils.getProperty;

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
		SimpleStringProperty lovVal =(SimpleStringProperty)getProperty(model.getSelectedItem(),"lovVal");
		lovVal.set(UUID.randomUUID().toString());
		clickOn(saveBtn);
		hasValueInFundType(lovComboBox.getSelectionModel().getSelectedItem(), lovVal.get());
	}
	
	private boolean hasValueInFundType(LovProp lovProp,String val) {
		return new ListOfValues()
				.getProperties((String)getProperty(lovProp,"propertyName"), (String)getProperty(lovProp,"propertyKey"))
				.stream()
				.filter(x->x.getPropertyValue().equals(val))
				.count() > 0;
	}
}
