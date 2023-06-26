//package controller;
//
//import application.Circle;
//import application.Color;
//import application.Rectangle;
//import javafx.scene.Node;
//import javafx.scene.shape.Shape;
//
//public class ShapeController  extends Shape{
// private Node node;
// 
// 
// public void initialize() {
//		node.setOnMouseClicked(e -> {
//			unselect();
//			select(node);
//		}); 
//
//}
//	public void select(Node node) {
//		selectedElement = node;
//		colorOfSelectedElement = (Color) selectedElement.getStroke();
//		if (selectedElement instanceof Circle) {
//			circle.setStroke(Color.RED);
//		} else if (selectedElement instanceof Rectangle) {
//			rect.setStroke(Color.RED);
//		}
//	}
//
//	public void unselect() {
//		if (selectedElement instanceof Circle) {
//			circle.setStroke(colorOfSelectedElement);
//		} else if (selectedElement instanceof Rectangle) {
//			rect.setStroke(colorOfSelectedElement);
//		}
//		selectedElement = null;
//	}
//}
