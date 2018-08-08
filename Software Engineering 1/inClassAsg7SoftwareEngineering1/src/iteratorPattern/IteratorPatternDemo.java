package iteratorPattern;

public class IteratorPatternDemo {
	
	   public static void main(String[] args) {
	      NameRepository namesRepository = new NameRepository();
	      IntRepository intRepo = new IntRepository();

	      for(Iterator iter = namesRepository.getIterator(); iter.hasNext();){
	         String name = (String)iter.next();
	         System.out.println("Name : " + name);
	      } 	
	      for(Iterator iter = intRepo.getIterator(); iter.hasNext();){
		         int number = (Integer) iter.next();
		         System.out.println("Int : " + number);
		      } 
	   }
	}
