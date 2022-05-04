import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Project2 {
	
	static List<Character> digits = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
	static List<Character> endings = Arrays.asList('f', 'd');
	static List<Character> operations = Arrays.asList('+', '-', '*', '/', '(', ')');
	static double total = 0;
	static double exponent = 0;
	static int sign = 1;
	static Stack<Character> stack = new Stack<>();
	static Stack<Character> operators = new Stack<>();
	static Stack<Double> operands = new Stack<>();
	
	/**
	 * Prompts user to input floating point literal
	 * @return user inputted string
	 */
	public static String prompt() {
		System.out.println("Enter a floating point literal or equation or press 'q' to quit");
		Scanner kbInput = new Scanner(System.in);
		return kbInput.nextLine().toLowerCase();
	}
	
	/**
	 * start state, goes to q1, q2, q17
	 * @return true if input ends on accept state, else false
	 */
	private static boolean q0(char[] usrInput, int index, int max) {
		if(index == max)
			return false;	
		char c = usrInput[index];
		
		if (digits.contains(c)) {
			stack.push(c);
			return q1(usrInput, index+1, max);
		} else if (c == '.')
			return q2(usrInput, index+1, max);
		else if (c == '('){
			operators.push(c);
			return q17(usrInput, index+1, max);
		}
		else
			return false;
	}
	
	/**
	 * q1, goes to q1, q3, q4, q5, q8
	 * @return true if input is accepted, false else
	 */
	private static boolean q1(char[] usrInput, int index, int max) {
		if (index == max)
			return false;
		char c = usrInput[index];
		
		if (c == '_')
			return q8(usrInput, index+1, max);
		else if (digits.contains(c)) {
			stack.push(c);
			return q1(usrInput, index+1, max);
		} else if (c == '.') {
			getSum();
			return q3(usrInput, index+1, max);
		} else if (endings.contains(c)) {
			getSum();
			return q5(usrInput, index+1, max);
		} else if (c == 'e') {
			getSum();
			return q4(usrInput, index+1, max);
		} else
			return false;
	}

	/**
	 * goes to q9
	 * @return true if input accepted, else false
	 */
	private static boolean q2(char[] usrInput, int index, int max) {
		if (index == max)
			return false;
		char c = usrInput[index];
		
		if (digits.contains(c)) {
			stack.push(c);
			return q9(usrInput, index+1, max);
		}else
			return false;
	}
	
	/**
	 * Accept State
	 * goes to q4, q5, q9, q12, q16
	 * @return true if String has ended, or if input is accepted, else false
	 */
	private static boolean q3(char[] usrInput, int index, int max) {
		if (index == max) {
			pushTotal();
			return q16();
		}
		
		char c = usrInput[index];
		
		if (digits.contains(c)) {
			stack.push(c);
			return q9(usrInput, index+1,max);
		} else if (c == 'e') {
			return q4(usrInput, index+1, max);
		} else if (endings.contains(c)) {
			return q5(usrInput, index+1, max);
		} else
			return q12(usrInput, index, max);
	}
	
	/**
	 * goes to q6, q7
	 * @return true if input ends on accept state, else false
	 */
	private static boolean q4(char[] usrInput, int index, int max) {
		if (index == max)
			return false;	
		char c = usrInput[index];
		if (c == '-') {
			sign = -1;
			return q6(usrInput, index+1, max);
		} else if (c == '+')
			return q6(usrInput, index+1, max);
		else if (digits.contains(c)) {
			stack.push(c);
			return q7(usrInput, index+1, max);
		} else
			return false;
	}
	
	/**
	 * Accept State, goes to q12, q16
	 * @return true if string has ended, else false
	 */
	private static boolean q5(char[] usrInput, int index, int max) {
		if (index == max){
			pushTotal();
			return q16();
		}
		else
			return q12(usrInput, index, max);
	}
	
	/**
	 * goes to q7
	 * @return true if input ends on accept state, else false
	 */
	private static boolean q6(char[] usrInput, int index, int max) {
		if (index == max)
			return false;
		char c = usrInput[index];
		
		if (digits.contains(c)) {
			stack.push(c);
			return q7(usrInput, index+1, max);
		} else
			return false;
	}

	/**
	 * Accept State
	 * goes to q5, q7, q11, q12, q16
	 * @return true if reached end of string or if input is accepted,
	 * else false
	 */
	private static boolean q7(char[] usrInput, int index, int max) {
		if (index == max) {
			getExponent();
			pushTotal();
			return q16();
		}
		char c = usrInput[index];
		if (c == '_')
			return q11(usrInput, index+1, max);
		else if (digits.contains(c)) {
			stack.push(c);
			return q7(usrInput, index+1, max);
		} else if (endings.contains(c)) {
			getExponent();
			return q5(usrInput, index+1, max);
		} else {
			getExponent();
			return q12(usrInput, index, max);
		}
	}
	
	/**
	 * goes to q1, q8
	 * @return true if input ends on accept state, else false
	 */
	private static boolean q8(char[] usrInput, int index, int max) {
		if (index == max) {
			return false;
		}
		char c = usrInput[index];
		if (c == '_')
			return q8(usrInput, index+1, max);
		else if (digits.contains(c)) {
			stack.push(c);
			return q1(usrInput, index+1, max);
		} else
			return false;
	}
	
	/**
	 * Accept state
	 * goes to q4, q5, q9, q10, q12, q16
	 * @return true if input ends on accept state, else false
	 */
	private static boolean q9(char[] usrInput, int index, int max) {
		if (index == max) {
			getDecimal();
			pushTotal();
			return q16();
		}
		char c = usrInput[index];
		if (c == '_')
			return q10(usrInput, index+1, max);
		else if (digits.contains(c)) {
			stack.push(c);
			return q9(usrInput, index+1, max);
		} else if (c == 'e') {
			getDecimal();
			return q4(usrInput, index+1, max);
		} else if (endings.contains(c)) {
			getDecimal();
			return q5(usrInput, index+1, max);
		} else {
			getDecimal();
			return q12(usrInput, index, max);
		}
	}
	
	/**
	 * goes to q9, q10
	 * @return true if input ends on accept state, else false
	 */
	private static boolean q10(char[] usrInput, int index, int max) {
		if (index == max) {
			return false;
		}	
		char c = usrInput[index];
		if (c == '_')
			return q10(usrInput, index+1, max);
		else if (digits.contains(c)) {
			stack.push(c);
			return q9(usrInput, index+1, max);
		} else
			return false;
	}
	
	/**
	 * goes to q7, q11
	 * @return true if input ends on accept state, else false
	 */
	private static boolean q11(char[] usrInput, int index, int max) {
		if (index == max)
			return false;
		char c = usrInput[index];
		if (c == '_')
			return q11(usrInput, index+1, max);
		else if (digits.contains(c)) {
			stack.push(c);
			return q7(usrInput, index+1, max);
		} else 
			return false;
	}

	/**
	 * goes to states q12, q13
	 * checks if char is ' ' or an operator
	 * @param usrInput input being checked
	 * @param index index of char being checked
	 * @param max length of input
	 * @return false if char not accepted or max reached, return next state
	 */
	private static boolean q12(char[] usrInput, int index, int max) {
		if (total != 0)
			pushTotal();
		if (index == max)
			return false;
		char c = usrInput[index];
		if (c == ' ')
			return q12(usrInput, index+1, max);
		else if (operations.contains(c))
			return q13(usrInput, index, max);
		else
			return false;
	}

	/**
	 * goes to q14, q15
	 * Checks char to see if operator, else return false
	 * @param usrInput input being checked
	 * @param index index of char being checked
	 * @param max length of usrInput
	 * @return false if not enough operands to do operations, else return next states boolean value
	 */
	private static boolean q13(char[] usrInput, int index, int max) {
		char c = usrInput[index];
		if (operators.isEmpty()){
			operators.push(c);
			return q14(usrInput, index+1, max); // goes to q14 where it checks next char
		} else if (c == ')') {
			return q15(usrInput, index, max);
		}
		else if (precedence(c) <= precedence(operators.peek()) && operators.peek() != '('){
			if (operands.size() < 2)
				return false;
			double num1 = operands.pop();
			double num2 = operands.pop();
			operands.push(operation(num1, num2)); // method that calculates operation
			operators.push(c);
			return q14(usrInput, index+1, max); // goes to q14 where it checks next char
		} else {
			operators.push(c);
			return q14(usrInput, index+1, max); //goes to q14 where it checks next char
		}
	}

	/**
	 * goes to q0, q13, q14, q15, q16
	 * Checks if next char is any acceptable char
	 * @param usrInput input being checks
	 * @param index index of char being checked
	 * @param max length of usrInput
	 * @return false if char is not accepted, else returns true if operands.size==1 & operators.isEmpty
	 * or next state's return value
	 */
	private static boolean q14(char[] usrInput, int index, int max) {
		if (index != max) {
			char c = usrInput[index];
			if (digits.contains(c) || c == '.')
				return q0(usrInput, index, max);
			else if (c == '('){
				operators.push(c);
				return q14(usrInput, index+1, max);
			} else if (c == ' ')
				return q14(usrInput, index+1, max);
			else if (c == ')')
				return q15(usrInput, index, max);
			else if (operations.contains(c))
				return q13(usrInput, index, max);
			else
				return false;
		}
		else
			if (operators.isEmpty() && operands.size() == 1)
				return true;
			else
				return q16(); // goes to q16, tries to empty stacks, if one fails to meet requirement, returns false
	}

	/**
	 * goes to q14, q15
	 * Empties stack until it reaches a '('
	 * @param usrInput input being checked
	 * @param index index being passed onto next state
	 * @param max	input length
	 * @return	false if operators.stack is empty, operands.size < 2, else return true if next state resturns true
	 */
	private static boolean q15(char[] usrInput, int index, int max) {
		if (operators.isEmpty())
			return false;
		else if (operators.peek() == '(') {
			operators.pop();
			return q14(usrInput, index+1, max);
		} else {
			if (operands.size() < 2)
				return false;
			double num1 = operands.pop();
			double num2 = operands.pop();

			operands.push(operation(num1, num2));
			return q15(usrInput, index, max);
		}
	}

	/**
	 * goes to q16
	 * reached max already and empties operands and operators stack
	 */
	private static boolean q16() {
		if (operators.isEmpty() && operands.size() == 1)
			return true;
		if (operands.size() < 2 || operators.isEmpty())
			return false;
		double num1 = operands.pop();
		double num2 = operands.pop();
		operands.push(operation(num1, num2));
		return q16();

	}

	/**
	 * goes to q0, q17
	 * comes from q0(), checks if next char is ' ', digit, '(', '.'
	 * @param usrInput usrInput being checked
	 * @param index	Index currently checking
	 * @param max	Length of string
	 * @return	False if max is reached or input is not accepted, else returns true if next state is true
	 */
	private static boolean q17(char[] usrInput, int index, int max) {
		if (index == max)
			return false;
		char c = usrInput[index];
		if (c == ' ')
			return q17(usrInput, index+1, max);
		else if (c == '(') {
			operators.push(c);
			return q17(usrInput, index + 1, max);
		}
		else if (digits.contains(c) || c == '.')
			return q0(usrInput, index, max);
		else
			return false;
	}

	/**
	 * Empties stack and sets exponent value
	 */
	private static void getExponent() {
		exponent = 0;
		int max = stack.size();
		for (int i = 0; i < max; i++)
			exponent += getNum() * Math.pow(10, i);
	}
	
	/**
	 * Empties stack and sets whole number value
	 */
	private static void getSum() {
		int max = stack.size();
		for (int i = 0; i < max; i++) {
			total += getNum() * Math.pow(10, i);
		}
	}
	
	/**
	 * Empties stack and sets decimal value
	 */
	private static void getDecimal() {
		int max = stack.size();
		for (int i = max; i > 0; i--)
			total += getNum() * Math.pow(10, (-1 * i));
	}
	
	/**
	 * @return parses char into double 
	 */
	private static double getNum() {
		char i = stack.pop();
		return switch (i) {
			case '1' -> 1.0;
			case '2' -> 2.0;
			case '3' -> 3.0;
			case '4' -> 4.0;
			case '5' -> 5.0;
			case '6' -> 6.0;
			case '7' -> 7.0;
			case '8' -> 8.0;
			case '9' -> 9.0;
			default -> 0;
		};
	}

	/**
	 * @param c char to be checked
	 * @return precedence value based on operation
	 */
	private static int precedence(char c){
		return switch (c) {
			case '(', ')' -> 3;
			case '*', '/' -> 2;
			case '+', '-' -> 1;
			default -> -1;
		};
	}

	/**
	 * @param num1 first operand
	 * @param num2 second operand
	 * @return value of operation being done, dependent on operator stack
	 */
	private static double operation(double num1, double num2){
		return switch (operators.pop()) {
			case '*' -> num2 * num1;
			case '/' -> num2 / num1;
			case '+' -> num2 + num1;
			case '-' -> num2 - num1;
			default -> -1;
		};
	}

	/**
	 * Pushes current number value onto operands stack
	 */
	private static void pushTotal() {
		total *= Math.pow(10, exponent*sign);
		operands.push(total);
		total = 0;
		exponent = 0;
		sign = 1;
	}

	public static void main(String[] args) {
		String input = prompt();
		
		while (!input.equals("q")) {
			char[] usrInput = input.toCharArray();
			int index = 0;
			if (q0(usrInput, index, usrInput.length)) {
				System.out.println(operands.pop());
			}
			else
				System.out.println("Invalid format");
			stack.clear();
			operands.clear();
			operators.clear();
			exponent = 0;
			total = 0;
			sign = 1;
			input = prompt();
		}
		System.out.println("Exited number identifier.");
	}
}