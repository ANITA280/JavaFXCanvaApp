package model;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

//for creating the CanvasPane object for adding to the Board accessed by the controller classes 
public class CanvasPane extends Pane {

	public CanvasPane(double width, double height) {
		super.setPrefSize(width, height);
	}

	// invoking the super class constructor
	public CanvasPane() {
		super();
		// TODO Auto-generated constructor stub
	}

	// to draw a default canvas
	public void doDrawing(Paint fill, CornerRadii radii, Insets insets) {
		super.setBackground(new Background(new BackgroundFill(fill, radii, insets)));

	}

	// to clear the canvas from the scene
	public void clearCanvas(CanvasPane canvas, Pane Parentroot) {
		Parentroot.getChildren().remove(canvas);

	}
}