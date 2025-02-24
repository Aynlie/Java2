/*Create an application that will get two numbers from the user and allow the user 
to enter the operation for the values. The operation can be addition, subtraction, 
multiplication and division. Create a method for each operation. */
import java.util.Scanner;
public class Compute{
	public static void main(String [] args){
		Scanner input = new Scanner(System.in);
		double num1 = 0;
		double num2 = 0;
		String p;
		char ch;
		double div = 0;
		display();
		System.out.print("Enter a number: ");
		num1 = input.nextDouble();
		System.out.print("Enter a number: ");
		num2 = input.nextDouble();
		input.nextLine(); //buffer
		System.out.print("Operation:\n\t[1] Addition\n\t[2] Subtraction\n\t[3] Multiplication\n\t[4] Division\nEnter the operation: ");
		p = input.nextLine();
		ch = p.charAt(0);
		switch(ch){
			case '1': 
				add(num1, num2); 
				break;
			case '2': 
				System.out.println("The difference of the numbers: " + subtract(num1, num2)); 
				break;
			case '3': 
				product(num1, num2); 
				break;
			case '4': 
				div = quotient(num1, num2); 
				System.out.println("The quotient of the numbers: " + div);
				break;
			default: 
				System.out.print("You've entered wrong operation."); break;
		}
	}
	//no parameter having void type
	public static void display(){
		System.out.println("Welcome to a simple calculator application: ");
	}
	//2 parameters having void type
	public static void add(double n1, double n2){
		System.out.print("The sum of the numbers: " + (n1 + n2));
	}
	//2 parameters having double return type
	public static double subtract(double n, double m){
		return(n - m);
	}
	//2 parameters having void type
	public static void product(double a, double b){
		double pro = a * b;
		System.out.print("The product of the numbers: " + pro);
	}
	//2 parameters having double return type
	public static double quotient(double x, double y){
		double quo = x / y;
		return quo;
	}
}