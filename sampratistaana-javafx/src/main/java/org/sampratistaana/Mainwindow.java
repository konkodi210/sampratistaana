package org.sampratistaana;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Mainwindow extends Application {
	private static Scene scene;
	private static ResourceBundle res;
	
	public static Scene getScene() {
		return scene;
	}

	@Override
    public void start(Stage stage) throws IOException {
		res=ResourceBundle.getBundle(getClass().getPackageName()+".Message",new Locale("KN"));
        scene = new Scene(loadFXML("MainWindow"),1000,500);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle(res.getString("window.title"));
        stage.centerOnScreen();
//        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Mainwindow.class.getResource(fxml + ".fxml"),res);
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
