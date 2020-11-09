package org.sampratistaana;

import static org.sampratistaana.Messages.getMessage;
import static org.sampratistaana.Messages.getResource;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mainwindow extends Application {
	private static Scene scene;	
	
	public static Scene getScene() {
		return scene;
	}

	@Override
    public void start(Stage stage) throws IOException {
		new CreditManager().loadBookInventory();
        scene = new Scene(loadFXML("MainWindow"),1000,500);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle(getMessage("window.title"));
        stage.centerOnScreen();
//        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Mainwindow.class.getResource(fxml + ".fxml"),getResource());
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static synchronized void loadForm(String formName) throws IOException {
    	VBox box=(VBox)scene.lookup("#body");
    	box.getChildren().clear();
    	box.getChildren().add(loadFXML(formName));
    }
}
