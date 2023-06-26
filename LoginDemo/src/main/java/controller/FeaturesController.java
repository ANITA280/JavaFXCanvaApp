package controller;

import javax.lang.model.element.Element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.CanvasPane;
import model.SelectableRectangle;
import model.SelectableShapes;

//controlling thr features of both Rectangle and Circle element on the canvas
public class FeaturesController {

	private Stage stage;
	@FXML
	private ColorPicker borderPicker;
	@FXML
	private ColorPicker bgPicker;
	@FXML
	private TextField borderWidth;
	private Shape selectedElement;
	private Color colorOfSelectedElement;
	private Color bordercolorOfSelectedElement;
	@FXML
	private TextField angle;
	@FXML
	private TextField resize;
	public boolean clicked = false;
	private CanvasPane parentRootElem;
	@FXML
	private Button delete;
	private Pane parentroot;
	private Pane root;
	@FXML
	private Button close;

	public FeaturesController(Shape shapes) {
		selectedElement = shapes;
	}

	@FXML
	public void initialize() {
		// for choosing hte borderColor of the Shapes
		borderPicker.setOnAction(e -> {
			if (selectedElement != null) {
				colorOfSelectedElement = (Color) borderPicker.getValue();
				selectedElement.setStroke(colorOfSelectedElement);
				clicked = true;

			}
		});
		// for choosing hte backgroundColor of the Shapes
		bgPicker.setOnAction(e -> {
			if (selectedElement != null) {
				bordercolorOfSelectedElement = (Color) bgPicker.getValue();
				selectedElement.setFill(bordercolorOfSelectedElement);

			}
		});
	}

	// for setting the borderWidth of the shapes
	@FXML
	public void setWidth() {
		borderWidth.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					setBorderWidth(selectedElement, Double.parseDouble(borderWidth.getText()));
//					message.setText("Angle is " + text.getText());
				} catch (NumberFormatException exception) {
//					message.setText("Wrong angle");
					System.out.println("Wrong angle");
				}
			}
		});
	}

	// helper function to setborder
	private static void setBorderWidth(Shape shape, double strokeValue) {

		shape.setStrokeWidth(strokeValue);
	}

	// function to resize the elements on Enter key pressed
	@FXML
	public void resize() {

		resize.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				try {
					if (selectedElement instanceof SelectableRectangle) {
						resize((Rectangle) selectedElement, Double.parseDouble(resize.getText()));
					}
					if (selectedElement instanceof SelectableShapes) {
						resize((Circle) selectedElement, Double.parseDouble(resize.getText()));
					}
//					message.setText("Angle is " + text.getText());
				} catch (NumberFormatException exception) {
//					message.setText("Wrong angle");
					System.out.println("Wrong angle");
				}
			}
		});
	}

	// helper function to resize
	private static void resize(Rectangle shape, double width) {

		shape.setWidth(width);

	}

	private static void resize(Circle shape, double width) {
		shape.setRadius(width);

	}

	// function to rotate the shapes on the cnavas
	@FXML
	public void rotate() {
		angle.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				try {
					rotate(selectedElement, Double.parseDouble(angle.getText()));
//					message.setText("Angle is " + text.getText());
				} catch (NumberFormatException exception) {
//					message.setText("Wrong angle");
					System.out.println("Wrong angle");
				}
			}
		});
	}

	private static void rotate(Shape shape, double angle) {

		shape.setRotate(angle);

	}

	public Color setBorderStroke() {
		return colorOfSelectedElement;
	}

	public boolean clicked() {
		return clicked;
	}

	// for shwoing the features controller pane
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 800, 800);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Profile");
		stage.showAndWait();
	}

	// setting the canvas as the parent Pane
	public void setParentRootforElem(CanvasPane parentRootforElements) {
		this.parentRootElem = parentRootforElements;
	}
	// FOR DELETING THE ELEMENTS

	public void deleteElement() {
		delete.setOnAction(e -> {
			parentRootElem.getChildren().remove(selectedElement);
			parentroot.getChildren().remove(root);

		});

	}
	// FOR CLOSING THE FEATURES CONTROLLER PANE

	public void close() {
		close.setOnAction(e -> {
			parentroot.getChildren().remove(root);

		});

	}

	public void setRoot(Pane root) {
		this.root = root;

	}

	public void setParentRoot(Pane parent) {
		this.parentroot = parent;
	}

}
