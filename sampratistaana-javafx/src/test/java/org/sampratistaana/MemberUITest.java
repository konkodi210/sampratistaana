package org.sampratistaana;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

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
	
	@Test
	public void testNewMember() {
		clickOn("#newMember");
		Label lab=lookup("#memberNo").query();
		assertThat("member No is zero",lab.getText(), equalTo("0"));
	}
}
