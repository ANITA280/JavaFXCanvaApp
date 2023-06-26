package controller;

import java.io.ByteArrayInputStream;
import javafx.scene.control.Slider;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.CanvasPane;
import model.DrawableCanvas;
import model.Model;
import model.SelectableImage;
import model.SelectableRectangle;
import model.SelectableShapes;
import model.TextBox;
import model.User;

//controller to control the whole profile of the user 
public class ProfileController implements Initializable {

	@FXML
	private Menu menu;
	@FXML
	private Text username;
	@FXML
	private Button text;
	@FXML
	private MenuItem newCanvas;
	@FXML
	private MenuItem edit;
	@FXML
	private MenuItem clearCanvas;

	private Text textBox;
	@FXML
	private Button image;
	@FXML
	private Button rectangle;
	@FXML
	private Button circle;
	@FXML
	private ImageView logoView;

	@FXML
	private MenuItem save;
	@FXML
	private Button logout;
	@FXML
	private MenuItem about;
	@FXML
	private Label color;
	@FXML
	private ColorPicker canvaColor;

	private CanvasPane canvas;
	private Stage stage;
	private Stage parentStage;
	private Model model;
	private Pane parentroot;
	private DialogBoxController SetCanvas;
	private DialogBoxController profileController;
	private Image Profilelogo;
	private User user;
	private EditController editController;
	private boolean clicked = false;
	private GridPane parentRootforElements;

	@FXML
	private Slider slider;

	@FXML
	private Label label;

	public void setRoot(Pane root) {
		this.parentroot = root;
	}

	public ProfileController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;

	}

	// setting the canvas based on the dialogbox
	public void setCanvas() {
		this.canvas = profileController.setCanvas();
	}

	// setting the profile of the user retrieving from the database
	public void setProfile(User user) {
		this.user = user;

		String path = System.getProperty("user.dir");

		Profilelogo = new Image("file:" + user.getlogo());

		logoView.setImage(Profilelogo);
		username.setText(user.getFirstName() + " " + user.getLastName());

	}

	// calling all the different fucntions based on the clicks of the users on
	// different feautres of the empty board
	@FXML
	public void menuFile() {

		menu.setOnShowing(event -> {
			System.out.println("contains" + parentroot.getChildren().contains(canvas));
			if (profileController != null) {
				setCanvas();
			}
			clearCanvas.setDisable(true);

			if (parentroot.getChildren().contains(canvas) == true) {
				clearCanvas.setDisable(false);
			}

		});
		// invoking the edit pane on clicking edit from the menu
		edit.setOnAction(event -> {
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit.fxml"));

				// Customize controller instance
				Callback<Class<?>, Object> controllerFactory = param -> {
					return new EditController(stage, model, parentroot);
				};
				loader.setControllerFactory(controllerFactory);
				AnchorPane root = loader.load();
				editController = loader.getController();
				editController.setEditPane(logoView.getImage(), username.getText(), user);
				editController.showStage(root);

				if (editController != null && editController.clicked() == true) {
					user = editController.editedUser();
					setProfile(user);
				}

			} catch (IOException e) {
//				message.setText(e.getMessage());

			}

		});
		// creating the newcanvas invoking the dialogBox and adding it to the board
		newCanvas.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/createNewCanvasDialogBox.fxml"));

				// Customize controller instance
				Callback<Class<?>, Object> controllerFactory = param -> {
					return new DialogBoxController(stage, model, parentroot);
				};

				loader.setControllerFactory(controllerFactory);
				GridPane root = loader.load();

				profileController = loader.getController();

				profileController.showStage(root);
				setCanvas();
				initialize();
				color.setDisable(false);

				canvaColor.setDisable(false);

//				message.setText("");

			} catch (IOException e) {
//				message.setText(e.getMessage());

			}
		});
		// changing the background color of the canvas
		canvaColor.setOnAction(e -> {
			Color color = (Color) canvaColor.getValue();
			canvas.doDrawing(color, null, null);
		});
		// clearing the whole canvas
		clearCanvas.setOnAction(event -> {

			canvas.clearCanvas(canvas, parentroot);
			color.setDisable(true);

			canvaColor.setDisable(true);

		});
		// on clicking the image button adding default image on the canvas
		image.setOnAction(event -> {

			SelectableImage image = new SelectableImage(new Image("file:" + "src/main/Images/ProfileDefault.jpeg"));

			image.setImage(image);
			image.initialize();
			setCanvas();
			canvas.getChildren().add(image);
			image.setParentRoot(parentroot);

			image.setParentRootforElem(canvas);

		});
		// on clicking rectangle adding a default rectangle on the canavs
		rectangle.setOnAction(event -> {

			SelectableRectangle rectangle = new SelectableRectangle(100, 200);

			rectangle.setRectangle(rectangle);
			rectangle.initialize();
			setCanvas();
			canvas.getChildren().add(rectangle);
			rectangle.setParentRoot(parentroot);
			rectangle.setParentRootforElem(canvas);

		});

		// for adding text element on the canvas
		text.setOnMouseClicked((MouseEvent event) -> {

			TextBox text = new TextBox(100, 100, "This is a sample text");
			// Customize controller instance
			text.setText(text);
			text.initialize();
			setCanvas();
			canvas.getChildren().add(text);
			text.setParentRoot(parentroot);
			text.setParentRootforElem(canvas);

		});
		circle.setOnAction(event -> {

			SelectableShapes circle = new SelectableShapes(100, 300, 100);
			circle.setCircle(circle);
			circle.initialize();
			setCanvas();
			canvas.getChildren().add(circle);

			circle.setParentRoot(parentroot);
			circle.setParentRootforElem(canvas);

		});

	}

	public void setNull() {

		this.profileController = null;
	}

	// for saving the whole canvas along with elements as a png image on clicking
	// save from the menu
	@FXML
	public void saveAsPng() {
		save.setOnAction(event -> {
			System.out.println("saved");
			WritableImage image = canvas.snapshot(new SnapshotParameters(), null);

			// TODO: probably use a file chooser here
			File file = new File("chart.png");

			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			} catch (IOException e) {
				System.out.println("cant save");
				// TODO: handle exception here
			}
		});
	}

	// for zoomin and zooming out from the canvas
	private void setPaneZoom(int percentage) {

		if (canvas != null) {
			System.out.println("ZOOM WORKS");
			canvas.setScaleX(percentage / 100.0);
			canvas.setScaleY(percentage / 100.0);
			label.setText("Zoom: " + percentage + "%");
		}
	}

	// for showoing the profile after login
	public void showStage(Pane root) {
		System.out.println("It worked");
		Scene scene = new Scene(root, 1100, 900);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Profile");
		stage.show();
	}

	public void initialize() {
		// slider to control the zoom function
		slider.valueProperty().addListener((ov, oldValue, newValue) -> {
			setPaneZoom(newValue.intValue());
		});
		setPaneZoom(100);
		slider.setValue(100);
	}

	// implementing the abstract method to invoke functions directly on loading the
	// window
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// for logging out of the profile
		logout.setOnAction(event -> {
			System.out.println("LOGOUT");
			parentStage.show();
			stage.close();
		});
		// for invoking the about us section on the pane
		about.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/About.fxml"));

				// Customize controller instance
				Callback<Class<?>, Object> controllerFactory = param -> {
					return new AboutController(stage);
				};

				loader.setControllerFactory(controllerFactory);

				AnchorPane root = loader.load();

				AboutController controller = loader.getController();

				controller.setImage();
				controller.showStage(root);
				this.stage.show();

//				message.setText("");

			} catch (IOException e) {
//				message.setText(e.getMessage());

			}
		});
		// for invoking the edit window on choosing edit from the help menu
		edit.setOnAction(event -> {
			System.out.println("Wokring");
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit.fxml"));

				// Customize controller instance
				Callback<Class<?>, Object> controllerFactory = param -> {
					return new EditController(stage, model, parentroot);
				};
				loader.setControllerFactory(controllerFactory);
				AnchorPane root = loader.load();
				editController = loader.getController();
				editController.setEditPane(logoView.getImage(), username.getText(), user);
				editController.showStage(root);

				if (editController != null && editController.clicked() == true) {
					user = editController.editedUser();
					setProfile(user);
				}

//				

			} catch (IOException e) {
//				message.setText(e.getMessage());

			}
		});

	}
}
