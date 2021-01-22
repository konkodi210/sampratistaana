package org.sampratistaana;

import static org.sampratistaana.Messages.getMessage;
import static org.sampratistaana.Messages.getResource;

import java.io.IOException;

import org.hibernate.Session;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mainwindow extends Application {
	private static Scene scene;
	private static Stage stage;
	
	public static Scene getScene() {
		return scene;
	}
	
	public static Stage getStage() {
		return stage;
	}

	@Override
    public void start(Stage stage) throws IOException {
		Mainwindow.stage=stage;
		Platform.runLater(() -> {try(Session session=ConnectionFactory.dbSession()){}});
        scene = new Scene(loadFXML("MainWindow"),1200,700);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle(getMessage("window.title"));
        stage.centerOnScreen();
        stage.sizeToScene();
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
    
    public static synchronized VBox loadForm(String formName) throws IOException {
    	VBox box=(VBox)scene.lookup("#body");
    	box.getChildren().clear();
    	box.getChildren().add(loadFXML(formName));
    	return box;
    }
}
