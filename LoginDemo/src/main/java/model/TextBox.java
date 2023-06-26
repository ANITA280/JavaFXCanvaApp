package model;

import javafx.scene.text.Text;

import java.io.IOException;

import controller.FeaturesController;
import controller.textController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TextBox extends Text {

	@FXML
	private VBox pane;

	private Circle circle;

	private TextBox text;

	@FXML
	private ColorPicker borderPicker;
	@FXML
	private ColorPicker bgPicker;
	private FeaturesController features;
	private Text selectedElement;
	private Color colorOfSelectedElement;
	private AnchorPane root;
	private CanvasPane parentRootElem;
	private Pane ParentRoot;
	// Original coordinates of the label before each dragging
	private double originX;
	private double originY;

	public TextBox(double x, double y, String text) {
		super(x, y, text);
	}

	public void setParentRoot(Pane root) {
		this.ParentRoot = root;
	}

	public void setText(TextBox rect) {
		this.text = rect;
	}

//		@FXML
	public void initialize() {
		text.setOnMouseClicked(e -> {
			if (selectedElement == null) {
				select(text);
			} else {
				unselect();
			}
		});
		text.setOnMousePressed(e -> {
			originX = e.getX();
			originY = e.getY();
		});

		text.setOnMouseDragged(e -> {
			double dx = e.getX() - originX;
			double dy = e.getY() - originY;
			move(text, dx, dy);
		});

	}

	@FXML
	public void chooseColor() {
		borderPicker.setOnAction(e -> {
			if (selectedElement != null) {
				colorOfSelectedElement = (Color) borderPicker.getValue();

				selectedElement.setStroke(colorOfSelectedElement);
				colorOfSelectedElement = (Color) selectedElement.getStroke();

			}
		});
	}

	private static void move(Node content, double dx, double dy) {
		double x = content.getBoundsInParent().getMinX();
		double y = content.getBoundsInParent().getMinY();

		content.relocate(x + dx, y + dy);
	}

	public void select(Text node) {
		selectedElement = node;
		colorOfSelectedElement = (Color) selectedElement.getStroke();
		text.setStroke(Color.RED);
		feature(node);

	}

	public void setColor(Color color) {
		colorOfSelectedElement = color;
	}

	public void feature(Text node) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/textViewMain.fxml"));

//	 Customize controller instance

			Callback<Class<?>, Object> controllerFactory = param -> {
				return new textController(node);
			};

			loader.setControllerFactory(controllerFactory);

			root = loader.load();

			textController controller = loader.getController();
			controller.setParentRootforElem(parentRootElem);
			controller.setParentRoot(ParentRoot);
			controller.setRoot(root);
			ParentRoot.getChildren().add(root);

			root.setLayoutX(800);
			root.setLayoutY(100);

		} catch (IOException e) {
//			message.setText("Wrong angle");
			System.out.println("File not found");
		}
	}

	public Pane getRoot() {
		return this.root;
	}

	public void unselect() {
		text.setStroke(colorOfSelectedElement);
		selectedElement = null;
		ParentRoot.getChildren().remove(root);

	}

	public void setParentRootforElem(CanvasPane parentRootforElements) {
		this.parentRootElem = parentRootforElements;
	}

	public void deleteElement() {

		parentRootElem.getChildren().remove(root);
	}

}
