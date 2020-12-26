package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.sampratistaana.beans.Property;

public class ListOfValuesTest {

	@Test
	public void testGetFundTypes() {
		assertThat("Must have element",new ListOfValues().getFundTypes(),not(empty()));
	}

	@Test
	public void testGetExpenseTypes() {
		assertThat("Must have element",new ListOfValues().getExpenseTypes(),not(empty()));
	}

	@Test
	public void testSaveLov() {
		List<Property> propList = new ListOfValues().getFundTypes();
		Property prop=new Property()
				.setFlag("Y")
				.setPropertyKey(propList.get(0).getPropertyKey())
				.setPropertyName(propList.get(0).getPropertyName())
				.setPropertyValue(UUID.randomUUID().toString());
		new ListOfValues().saveLov(Arrays.asList(prop), null);
		assertThat("new Record is inserted", hasValueInFundType(prop.getPropertyValue()),equalTo(true));
		
		prop.setPropertyValue(UUID.randomUUID().toString());		
		new ListOfValues().saveLov(Arrays.asList(prop), null);
		assertThat("Updated Record is saved", hasValueInFundType(prop.getPropertyValue()),equalTo(true));
		
		new ListOfValues().saveLov(null, Arrays.asList(prop));
		assertThat("record is deleted", hasValueInFundType(prop.getPropertyValue()),equalTo(false));
	}

	private boolean hasValueInFundType(String val) {
		return new ListOfValues()
				.getFundTypes()
				.stream()
				.filter(x->x.getPropertyValue().equals(val))
				.count() > 0;
	}
}
