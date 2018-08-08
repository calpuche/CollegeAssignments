package bridgeDesignPattern;

public class Circle extends Shape {
	   private int x, y, radius;

	   public Circle(int x, int y, int radius, DrawAPI drawAPI) {
	      super(drawAPI);
	      this.x = x;  
	      this.y = y;  
	      this.radius = radius;
	   }

	   public void draw() {
	      drawAPI.drawCircle(radius,x,y);
	      System.out.println("Carlos Alpuche");
	   }

	public void resize(int r) {
		this.radius = (int) (this.radius *((float)r/100));
	}
	}
