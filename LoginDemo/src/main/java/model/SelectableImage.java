package model;

import java.io.IOException;

import controller.FeaturesController;
import controller.ImageChangeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Callback;

public class SelectableImage extends ImageView {
	private ImageView imageView;
	private StackPane imageContainer;

	private Pane ParentRoot;

	private double originX;
	private double originY;
	private ImageView selectedElement;
	private String colorOfSelectedElement;
	private HBox root;
	private CanvasPane parentRootElem;
	public SelectableImage(Image image) {

		super(image);

	}

	public void setImage(SelectableImage image) {
		this.imageView = image;

	}

	public void setParentRoot(Pane parentroot) {
		this.ParentRoot = parentroot;
	

	}

//
//	@FXML
	public void initialize() {
		imageView.setOnMouseClicked(e -> {
			if (selectedElement == null) {
				System.out.println("IT IS NULL");
				select(imageView);
			} else {
				unselect();
			}
		});
		imageView.setOnMousePressed(e -> {
			originX = e.getX();
			originY = e.getY();
		});

		imageView.setOnMouseDragged(e -> {
			double dx = e.getX() - originX;
			double dy = e.getY() - originY;
			move(imageView, dx, dy);
		});
	}

	private static void move(Node content, double dx, double dy) {
		double x = content.getBoundsInParent().getMinX();
		double y = content.getBoundsInParent().getMinY();

		content.relocate(x + dx, y + dy);
	}

	public void select(ImageView node) {
		selectedElement = node;
		colorOfSelectedElement = selectedElement.getStyle();
		imageView.setStyle("-fx-border-color:red");
		feature(node);

	}

	public void feature(ImageView node) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/imageChange.fxml"));

//	 Customize controller instance
			Callback<Class<?>, Object> controllerFactory = param -> {
				return new ImageChangeController(node);
			};

			loader.setControllerFactory(controllerFactory);


			root = loader.load();


			ImageChangeController controller = loader.getController();
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

//
	public void unselect() {

		imageView.setStyle(colorOfSelectedElement);
		selectedElement = null;
		ParentRoot.getChildren().remove(root);

	}
	public void setParentRootforElem(CanvasPane parentRootforElements){
		this.parentRootElem=parentRootforElements;
	}
	public void  deleteElement() {
		
		parentRootElem.getChildren().remove(root);
	}
}
