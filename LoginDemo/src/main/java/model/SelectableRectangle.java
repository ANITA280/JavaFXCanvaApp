package model;

import java.io.IOException;

import controller.FeaturesController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SelectableRectangle extends Rectangle {

	@FXML
	private VBox pane;

	private Circle circle;

	private Rectangle rect;

	@FXML
	private ColorPicker borderPicker;
	@FXML
	private ColorPicker bgPicker;
	private FeaturesController features;
	private Shape selectedElement;
	private Color colorOfSelectedElement;
	private Pane root;

	private Pane ParentRoot;
	// Original coordinates of the label before each dragging
	private double originX;
	private double originY;

	private CanvasPane parentRootElem;

	public SelectableRectangle(double width, double height) {
		// TODO Auto-generated constructor stub
		super(width, height);
	}

	public void setParentRoot(Pane root) {
		this.ParentRoot = root;
	}

	public void setRectangle(Rectangle rect) {
		this.rect = rect;
	}

//		@FXML
	public void initialize() {
		rect.setOnMouseClicked(e -> {
			if (selectedElement == null) {
				select(rect);
			} else {
				unselect();
			}
		});
		rect.setOnMousePressed(e -> {
			originX = e.getX();
			originY = e.getY();
		});

		rect.setOnMouseDragged(e -> {
			double dx = e.getX() - originX;
			double dy = e.getY() - originY;
			move(rect, dx, dy);
		});

	}


	

	private static void move(Node content, double dx, double dy) {
		double x = content.getBoundsInParent().getMinX();
		double y = content.getBoundsInParent().getMinY();

		content.relocate(x + dx, y + dy);
	}

	public void select(Shape node) {
		selectedElement = node;
		colorOfSelectedElement = (Color) selectedElement.getStroke();
		rect.setStroke(Color.RED);
		feature(node);

	}

	public void feature(Shape node) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/features.fxml"));

//	 Customize controller instance
		Callback<Class<?>, Object> controllerFactory = param -> {
			return new FeaturesController(node);
		};

		loader.setControllerFactory(controllerFactory);


			root = loader.load();


		FeaturesController controller = loader.getController();
		controller.setParentRootforElem(parentRootElem);
		controller.setParentRoot(ParentRoot);
		controller.setRoot(root);
		
		ParentRoot.getChildren().add(root);
		root.setLayoutX(800);
		root.setLayoutY(300);
		if(controller.clicked()==true) {
			
			colorOfSelectedElement=controller.setBorderStroke();
			}


	}
		catch (IOException e)  {

			System.out.println("File not found");
		}
	}

	public Pane getRoot() {
		return this.root;
	}

	public void unselect() {
	
		rect.setStroke(colorOfSelectedElement);
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
