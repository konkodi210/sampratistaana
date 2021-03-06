package org.sampratistaana;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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

	protected void performConfirmedDelete(String id) {
		clickOn(id);
		clickOn(getDeleteConfirmationPane().lookupButton(ButtonType.NO));
		clickOn(id);
		clickOn(getDeleteConfirmationPane().lookupButton(ButtonType.YES));		 
	}
	
	private DialogPane getDeleteConfirmationPane() {
		Stage alert=(Stage)robotContext()
				.getWindowFinder()
				.listWindows()
				.stream()
				.filter(window -> window instanceof Stage 
						&& ((Stage)window).getTitle()==Messages.getMessage("common.delete-title"))
				.findFirst()
				.orElse(null);
		 assertNotNull(alert);
		 return (DialogPane) alert.getScene().getRoot();
	}
}
