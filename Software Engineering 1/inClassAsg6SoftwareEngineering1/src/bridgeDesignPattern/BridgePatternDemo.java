package bridgeDesignPattern;

public class BridgePatternDemo {
	   public static void main(String[] args) {
	      Shape redCircle = new Circle(100,100, 10, new RedCircle());
	      Shape greenCircle = new Circle(100,100, 10, new GreenCircle());

	      //red Circle
	      redCircle.draw();
	      redCircle.resize(80);
	      redCircle.draw();

	      
	      //Green Circle
	      greenCircle.draw();
	      greenCircle.resize(90);
	      greenCircle.draw();
	   }
	}

