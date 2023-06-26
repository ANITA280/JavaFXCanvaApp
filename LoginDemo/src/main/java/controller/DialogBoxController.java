package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.CanvasPane;
import model.DrawableCanvas;
import model.Model;

public class DialogBoxController {
//DialogBox to control the size of the canvas and add canvas to the board
	@FXML
	private Button okayButton;
	private Stage stage;
	private Stage parentStage;
	private Model model;
	@FXML
	private TextField width;
	@FXML
	private TextField height;

	private CanvasPane canvas;
	private Pane root;
	@FXML
	private Label area;
	private Pane parentroot;

	double widthofCanvas = 0;
	double heightofCanvas = 0;

	public DialogBoxController(Stage parentStage, Model model, Pane parentRoot) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.parentroot = parentRoot;
	}

	public void setRoot(Pane root) {
		this.root = root;
	}

	@FXML
	public void buttonClicked() {
		// adding the canvas on button click on the parent scene
		okayButton.setOnMouseClicked((MouseEvent event) -> {
			try {

				widthofCanvas = Double.parseDouble(width.getText());
				heightofCanvas = Double.parseDouble(height.getText());
				canvas = new CanvasPane(widthofCanvas, heightofCanvas);
				canvas.setLayoutX(100);
				canvas.setLayoutY(90);
				canvas.doDrawing(Color.BLUE, null, null);
				parentroot.getChildren().add(canvas);

				this.stage.close();

				parentStage.show();

			} catch (Exception e) {
				area.setText("Invalid inputs.");
			}

		});

	}

	// returning the canvas created
	public CanvasPane setCanvas() {

		if (canvas != null) {
			System.out.println("width" + canvas.getWidth());
		}
		return this.canvas;
	}

	// to show the dialogbox
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Smart Canvas Size");
		stage.showAndWait();
	}

}
