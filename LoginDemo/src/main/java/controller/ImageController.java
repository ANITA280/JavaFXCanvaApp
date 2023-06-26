package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Model;

public class ImageController {
	@FXML
	private ImageView image;

	private Stage stage;
	private Stage parentStage;
	private Model model;

	private Pane parentroot;

	public ImageController(Stage parentStage, Model model, Pane parentRoot) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.parentroot = parentRoot;
	}

//	@FXML
	public void initialize() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg");

		fileChooser.getExtensionFilters().add(extFilter);

		// Show a file open dialog
		File selectedFile = fileChooser.showOpenDialog(stage);

		InputStream fileInputStream;
		if (selectedFile != null) {

			try {
				fileInputStream = new FileInputStream(selectedFile);
				image.setImage(new Image(fileInputStream));
//				parentroot.getChildren().add(image);

//				clearImage.setDisable(false);
			} catch (IOException e) {
				System.out.println("File not found");
			}
		}
	}

	public ImageView set() {
		image.setX(100);
		return this.image;
	}

	public void test() {
	image.setOnMouseClicked(event -> {
		System.out.println("PASSED");
	
	});
	}
	

//		clearImage.setOnAction(event -> {
//			image.setImage(null);
//		});

//	public void setStage(Stage stage) {
//		this.stage = stage;
//	}
}
