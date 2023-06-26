package controller;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CanvasPane;
import model.Model;
import model.TextBox;

//to control the text element on the canvas
public class textController {
	private TextBox text;
	@FXML
	private TextField textContent;
	@FXML
	private ColorPicker bgPicker;
	@FXML
	private ComboBox fontList;
	@FXML
	private Button close;
	@FXML
	private Button delete;
	private Text selectedElement;

	private Color colorOfSelectedElement;
	private CanvasPane parentRootElem;
	private Stage stage;
	private Object parentStage;
	private Model model;
	private Pane parentroot;
	private Pane root;
	// Original coordinates of the label before each dragging
	private double originX;
	private double originY;

	public textController(Stage stage, Model model, GridPane parentroot) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.parentroot = parentroot;
	}

	public textController(Text shapes) {
		selectedElement = shapes;
	}

	public Color getcolorOfSelectedElement() {
		return this.colorOfSelectedElement;
	}

	@FXML
	public void initialize() {
		// setting the content and chaging the content of the text
		textContent.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (k.getCode().equals(KeyCode.ENTER)) {
					setnewText();
				}
				System.out.println("text");
			}
		});

		fontList.setItems(FXCollections.observableArrayList(Font.getFontNames()));
		bgPicker.setOnAction(e -> {

			if (selectedElement != null) {
				System.out.println("COLOR WORKS");
				colorOfSelectedElement = (Color) bgPicker.getValue();
				System.out.println(colorOfSelectedElement);

				selectedElement.setStroke(colorOfSelectedElement);

			}
		});
	}

//setting the font of the text 
	@FXML
	public void setFont() {

		fontList.setOnAction(e -> {
			System.out.println("FONTSET");
			try {
				selectedElement.setFont(Font.font((String) fontList.getValue()));

			} catch (NumberFormatException exception) {
//							message.setText("Wrong angle");
				System.out.println("Wrong angle");
			}

		});

	}

//changing the text to new text
	public void setnewText() {
		selectedElement.setText(textContent.getText());
		System.out.println("text ");

	}

//adding the text to the canvas 
	public void addStage(Pane root) {
		this.root = root;
		root.layoutXProperty().bind(parentroot.widthProperty().subtract(10));
		root.layoutYProperty().bind(parentroot.heightProperty().subtract(10));

	}

//setting the default text 
	public TextBox set() {
		text = new TextBox(10, 20, "This is a text sample");
		text.setFont(Font.font("Verdana", 20));
		text.setFill(Color.RED);
		return text;

	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Welcome");
		stage.show();
	}

	// closing the controller pane
	public void closeStage(Pane root) {
		stage.close();
	}

	public void setParentRootforElem(CanvasPane parentRootforElements) {
		this.parentRootElem = parentRootforElements;
	}

	// for deleting the text element from the canvas
	public void deleteElement() {
		delete.setOnAction(e -> {
			System.out.println("DELETING");
			parentRootElem.getChildren().remove(selectedElement);
			parentroot.getChildren().remove(root);

		});

	}

	// for closing the controller pane
	public void close() {
		close.setOnAction(e -> {
			parentroot.getChildren().remove(root);

		});

	}

	public void setRoot(AnchorPane root) {
		this.root = root;

	}

	public void setParentRoot(Pane parent) {
		this.parentroot = parent;
	}

}
