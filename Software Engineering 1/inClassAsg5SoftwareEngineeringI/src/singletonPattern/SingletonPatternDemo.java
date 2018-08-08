package singletonPattern;

public class SingletonPatternDemo {
	   public static void main(String[] args) {

	      //illegal construct
	      //Compile Time Error: The constructor SingleObject() is not visible
	      //SingleObject object = new SingleObject();

	      //Get the only object available
	      SingleObject object = SingleObject.getInstance();

	      //show the message
	      object.showMessage();
	      object.showMessage();
	      object.showMessage();
	      
	      //get second instance and show message
	      SingleObject secondObject = SingleObject.getInstance();
	      secondObject.showMessage();
	      secondObject.showMessage();
	      secondObject.showMessage();
	      
	      SingleObject thirdObject = SingleObject.getInstance();
	      thirdObject.showMessage();
	      thirdObject.showMessage();
	      thirdObject.showMessage();
	   }
	}
