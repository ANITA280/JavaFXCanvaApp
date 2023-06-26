package controller;

//about class to set the default about us information on the pop up window
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Model;

public class AboutController {
	private Stage stage;
	private Stage parentStage;
	@FXML
	private ImageView imageView;
	String path = System.getProperty("user.home");
//setting the relative path of the image 
	Image image = new Image("file:" + "src/main/Images/about.jpg");

	public AboutController(Stage stage) {
		this.stage = new Stage();
		parentStage = stage;

		System.out.println(image);

	}

//setting the image on the about us page
	public void setImage() {
		imageView.setImage(image);

	}
//showing the about us pane 
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("About Me");
		stage.show();
	}
}
