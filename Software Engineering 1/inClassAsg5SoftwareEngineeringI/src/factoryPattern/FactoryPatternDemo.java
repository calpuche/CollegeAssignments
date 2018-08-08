package factoryPattern;

public class FactoryPatternDemo {

	   public static void main(String[] args) {
	      ShapeFactory shapeFactory = new ShapeFactory();

	      //get an object of Circle and call its draw method.
	      Shape shape1 = shapeFactory.getShape("CIRCLE");

	      //call draw method of Circle
	      shape1.draw();

	      //get an object of Rectangle and call its draw method.
	      Shape shape2 = shapeFactory.getShape("RECTANGLE");

	      //call draw method of Rectangle
	      shape2.draw();

	      //get an object of Square and call its draw method.
	      Shape shape3 = shapeFactory.getShape("SQUARE");

	      //call draw method of circle
	      shape3.draw();
	      
	      Shape shape4 = shapeFactory.getShape("DIAMOND");
	      shape4.draw();
	      
	      Shape shape5 = shapeFactory.getShape("CIRCLE");
	      shape5.draw();
	      
	      Shape shape6 = shapeFactory.getShape("RECTANGLE");
	      shape6.draw();
	      
	      Shape shape7 = shapeFactory.getShape("SQUARE");
	      shape7.draw();
	      
	      Shape shape8 = shapeFactory.getShape("DIAMOND");
	      shape8.draw();
	   }
	}
