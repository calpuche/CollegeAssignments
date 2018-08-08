package plProject;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class SourceCode {
	//stack for stacks
	 static Stack stack = new Stack();
	 //our dictionary
     static String[] dictionary = {"add","subtract","multiply","divide","print","dupe","squareroot","clear","quit"};
    
    // "divide","multiply","subtract","squareroot","print","dupe"    
    
        
               
       
        public static void main(String[] args) {
        	Scanner input = new Scanner(System.in);  // Reading from System.in
    		System.out.println("Enter some code: ");
    		String part = "";
    		boolean inDict = false;
    		String word = "";
    		while (part != "quit"){
    		String n = input.nextLine();
    		String[] words = ventiLexer(n);
    		//take off after testing
    		//System.out.println(Arrays.toString(words)); 
    		for(int i = 0; i< words.length;i++){
    			part = words[i];
    			part = part.toLowerCase();
    			if(Arrays.asList(dictionary).contains(part)){
    				inDict = true;
    				word = part;
    				//fuctions(part);
    				//math(part);
    			}
    			else if (isNumeric(part)){
    				stack.push(new Integer(part));
    			}
    			
    			else{
    				System.out.println("Error running code");
    			}
    		}
    		if(inDict == true){
    			stack.push(new String(word));
    			if (functions(word)) { }
    			else {
    				math(word);
    			}
    			inDict = false;
    			
    		}
    		}
    		
           
        }
        public static String[] dictionary(){
        	
			return dictionary;
        	
        }
        public static boolean isNumeric(String s) {  
            return s.matches("[-+]?\\d*\\.?\\d+");  
        }  
        
        //splits words up by space
        public static String[] ventiLexer(String word) {
                String[] parts = word.split(" ");
                return parts;
            }

       //math functions
        public static boolean math(String mathFunction){
        	
        		
                if (mathFunction.equals("add")){
                        if(stack.size()>2){
                        
                        stack.pop();
                        int first = (int) stack.pop();
                        int second = (int) stack.pop();
                        
                        int result = first + second;
                       
                        stack.push(new Integer(result));
                      //  System.out.println(stack);
                        
                        }
                        else{
                        	stack.pop();
                                System.out.println("Not enough numbers");
                        
                        }
                        return true;
                        
                        
                }
                else if(mathFunction.equals("subtract")){
                        if(stack.size()>2){
                        stack.pop();
                        int second = (int) stack.pop();
                        int first = (int) stack.pop();
                        int result = first - second;
                        stack.push(result);
                        }
                        else{
                        	stack.pop();
                                System.out.println("Not enough numbers");
                        }
                        return true;
                }
                else if(mathFunction.equals("multiply")){
                        if(stack.size()>2){
                        stack.pop();
                        int first = (int) stack.pop();
                        int second = (int) stack.pop();
                        int result = first * second;
                        stack.push(result);
                        }
                        else{
                        	stack.pop();
                                System.out.println("Not enough numbers");
                        }
                        return true;
                }
                else if(mathFunction.equals("divide")){
                        if(stack.size()>2){
                        stack.pop();
                        int second = (int) stack.pop();
                        int first = (int) stack.pop();
                        int result = first / second;
                        stack.push(result);
                        }
                        else{
                        	stack.pop();
                            System.out.println("Note enough numbers");
                        }
                        return true;
                }
                else if(mathFunction.equals("squareroot")){
                        if(stack.size()<1){
                        	stack.pop();
                                System.out.println("Note enough numbers");
                        }
                        else{
                        	stack.pop();
                              int first = (int) stack.pop();
                              int result = (int) Math.sqrt(first);
                              stack.push(result);
                        }
                        return true;
                }
                return false;
                }
        
                //normal functions
                public static boolean functions(String functionName){
                	
                	if(functionName.equals("dupe")){
                		 if (stack.size() < 2){
                			 stack.pop();
                			 System.out.println("Not enough item");
                		 }
                		 else{
                		 stack.pop();
                         int tos = (int) stack.pop();
                         stack.push(tos);
                         stack.push(tos);
                		 }
                		 return true;
                	}
                	else if(functionName.equals("print")){
                		
                         if (stack.size() < 2){
                        	 stack.pop();
                                 System.out.println("Not Enough items on stack");
                         }
                         else{
                        	
                        	 stack.pop();
                        	 
                                 System.out.println(stack.peek());
                         }
                         return true;
                         
                	 }
                	else if(functionName.equals("clear")){
               		 
                        if (stack.size() < 1){
                        	stack.pop();
                                System.out.println("Already clear");
                        }
                        else{
                        	
                        	stack = new Stack();                      	 
                               
                        }
                        return true;
                        
               	 }
                	else if(functionName.equals("quit")){
                         if (stack.size() < 1){
                        	 stack.pop();
                                 System.out.println("Not Enough items on stack");
                         }
                         else{
                        	 System.exit(1);
                         }
                         return true;
                         
                	 }
                	return false;
                 }
                }

                
                