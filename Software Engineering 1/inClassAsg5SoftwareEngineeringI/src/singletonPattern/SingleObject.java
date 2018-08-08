package singletonPattern;

public class SingleObject {

   //create an object of SingleObject
   private static SingleObject instance = new SingleObject();


   //make the constructor private so that this class cannot be
   //instantiated
   private SingleObject(){}
   
   //create private int name counter
   
   private int counter = 0;

   //Get the only object available
   public static SingleObject getInstance(){
      return instance;
   }

   public void showMessage(){
	   counter++;
	   System.out.println("Current Value: " + counter);
      System.out.println("Carlos Alpuche");
   }
   
}