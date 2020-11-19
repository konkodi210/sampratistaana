package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class MemberUITest extends ApplicationTest{

	
	@Override
	public void start(Stage stage) throws Exception {
		new Mainwindow().start(stage);		
	}
	
	@Before
	public void setup() {
		clickOn("#credit");
		clickOn("#member");
	}

	@After
	public void tearDown() throws Exception {
		FxToolkit.hideStage();
	    release(new KeyCode[]{});
	    release(new MouseButton[]{});
	}
	
	private <T extends Node> T find(String id) {
		return lookup(id).query();
	}
	
	private String getLabelText(String id) {
		return ((Label)find(id)).getText();
	}
	
	@Test
	public void testNewMember() {
		clickOn("#newMember");		
		assertThat("member No is zero",getLabelText("#memberNo"), equalTo("0"));
	}
}
