package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableCanvas extends Canvas {
	private GraphicsContext gc;
	public void getGraphicsContext(Canvas canvas) {
		 gc = canvas.getGraphicsContext2D();  
		
	}
public DrawableCanvas(double width, double height) {
	super( width,height);
} 

public DrawableCanvas() {
	super();
	// TODO Auto-generated constructor stub
}
public void doDrawing(Canvas canvas) {
	   getGraphicsContext(canvas);
	    gc.setStroke(Color.FORESTGREEN.brighter());
	    gc.setFill(Color.YELLOW);
	    gc.fillRect(
	            0, 
	            0, 
	            canvas.getWidth(), 
	            canvas.getHeight());
}
public void clearCanvas(Canvas canvas) {
gc.clearRect(
	        0,
	        0,
	        canvas.getWidth(),
	        canvas.getHeight());
	       canvas= null;
}
}
