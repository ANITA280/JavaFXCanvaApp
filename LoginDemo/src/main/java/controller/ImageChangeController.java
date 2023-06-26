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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.CanvasPane;

public class ImageChangeController {
	private ImageView selectedElement;
	private Stage stage;
	@FXML
	private Button change;
	@FXML
	private Button delete;
	private CanvasPane parentRootElem;
	private Pane parentroot;
	private HBox root;

	public ImageChangeController(ImageView shapes) {
		selectedElement = shapes;
	}

	@FXML
	public void initialize() {
		change.setOnAction(e -> {
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
					selectedElement.setImage(new Image(fileInputStream));

				} catch (IOException ex) {
					System.out.println("File not found");
				}
			}
		});

	}

	public void showStage(Pane root) {

		Scene scene = new Scene(root, 800, 800);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Image Edit");
		stage.showAndWait();
	}

	public void setParentRootforElem(CanvasPane parentRootforElements) {
		this.parentRootElem = parentRootforElements;
	}

	public void deleteElement() {
		delete.setOnAction(e -> {
			System.out.println("DELETING");

			parentRootElem.getChildren().remove(selectedElement);
			parentroot.getChildren().remove(root);

		});

	}

	public void setRoot(HBox root) {
		this.root = root;

	}

	public void setParentRoot(Pane parent) {
		this.parentroot = parent;
	}

}
