package org.sampratistaana;

import org.junit.After;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class BaseApplicationTest extends ApplicationTest {

	@Override
	public void start(Stage stage) throws Exception {
		new Mainwindow().start(stage);		
	}

	@After
	public void tearDown() throws Exception {
		FxToolkit.hideStage();
		release(new KeyCode[]{});
		release(new MouseButton[]{});
	}

	protected <T extends Node> T find(String id) {
		for(int i=0;i<10;i++) {
			try {
				return lookup(id).query();
			}catch(Exception e) {
				sleep(1000);
				if(i==9) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

	protected String getLabelText(String id) {
		return ((Label)find(id)).getText();
	}

	protected void writeToTextFiled(String id, String val) {
		((TextInputControl)find(id)).clear();
		clickOn(id);		
		write(val);
	}
}
