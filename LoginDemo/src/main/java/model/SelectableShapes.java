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

	public class SelectableShapes extends Circle{

		@FXML
		private VBox pane;

		
		private Circle circle;

	    @FXML
	    private Rectangle rect;

	    @FXML
	    private ColorPicker borderPicker;
	    @FXML
	    private ColorPicker bgPicker;

		private Shape selectedElement;
		private Color colorOfSelectedElement;
		private Pane ParentRoot;
		private Pane root;
		private CanvasPane parentRootElem;
		// Original coordinates of the label before each dragging
		private double originX;
		private double originY;
		public SelectableShapes(int i, int j, int k) {
			// TODO Auto-generated constructor stub
			super(i,j,k);
		}

		public void setParentRoot(Pane root) {
			this.ParentRoot = root;
		}

		public void setCircle(Circle circle) {
			this.circle =circle;
		}
		
//		@FXML
		public void initialize() {
			circle.setOnMouseClicked(e -> {
				if(selectedElement==null) {
				select(circle);
				}
				else {
					unselect();
				}
			}); 
			circle.setOnMousePressed(e -> {
				originX = e.getX();
				originY = e.getY();
			});

			circle.setOnMouseDragged(e -> {
				double dx = e.getX() - originX;
				double dy = e.getY() - originY;
				move(circle, dx, dy);
			});

		}

		private static void move(Node content, double dx, double dy) {
			double x = content.getBoundsInParent().getMinX();
			double y = content.getBoundsInParent().getMinY();
//			content.setText(String.format("x: %.2f y: %.2f", x + dx, y + dy));
			content.relocate(x + dx, y + dy);
		}
		

		public void select(Shape node) {
			selectedElement = node;
			colorOfSelectedElement = (Color) selectedElement.getStroke();
				circle.setStroke(Color.RED);
				feature(node);
			
		}
		public void feature(Shape node) {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/features.fxml"));

//		 Customize controller instance
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
			root.setLayoutY(500);
			if(controller.clicked()==true) {
				
				colorOfSelectedElement=controller.setBorderStroke();
				}



		}
			catch (IOException e)  {
//				message.setText("Wrong angle");
				System.out.println("File not found");
			}
		}
		public Pane getRoot() {
			return this.root;
		}
		public void unselect() {
				circle.setStroke(colorOfSelectedElement);
				selectedElement=null;
				ParentRoot.getChildren().remove(root);
		}
		public void setParentRootforElem(CanvasPane parentRootforElements){
			this.parentRootElem=parentRootforElements;
		}
		public void  deleteElement() {
			
			parentRootElem.getChildren().remove(root);
		}
	

}
