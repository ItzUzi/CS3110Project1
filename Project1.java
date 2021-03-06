import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Project1 {
	
	static List<Character> digits = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
	static List<Character> endings = Arrays.asList('f', 'd');
	static double total = 0;
	static double exponent = 0;
	static boolean isNegative = false;
	static Stack<Character> stack = new Stack<Character>();
	
	/**
	 * Prompts user to input floating point literal
	 * @return user inputed string
	 */
	public static String prompt() {
		System.out.printf("Enter a floating point literal or press \'q\' to quit\n(accepted format 111.32e4f)\n");
		Scanner kbInput = new Scanner(System.in);
		return kbInput.next().toLowerCase();
	}
	
	/**
	 * start state, goes to state 1 and state 2
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
	 * goes to q4, q5, q9
	 * @return true if String has ended, or if input is accepted, else false
	 */
	private static boolean q3(char[] usrInput, int index, int max) {
		if (index == max)
			return true;
		
		char c = usrInput[index];
		
		if (digits.contains(c)) {
			stack.push(c);
			return q9(usrInput, index+1,max);
		} else if (c == 'e') {
			return q4(usrInput, index+1, max);
		} else if (endings.contains(c)) {
			return q5(usrInput, index+1, max);
		} else
			return false;
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
			isNegative = true;
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
	 * Accept State
	 * @return true if string has ended, else false
	 */
	private static boolean q5(char[] usrInput, int index, int max) {
		if (index == max)
			return true;
		else
			return false;
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
	 * goes to q5, q7, q11
	 * @return true if reached end of string or if input is accepted,
	 * else false
	 */
	private static boolean q7(char[] usrInput, int index, int max) {
		if (index == max) {
			getExponent();
			return true;
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
		} else
			return false;
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
	 * goes to q4, q5, q9, q10
	 * @return true if input ends on accept state, else false
	 */
	private static boolean q9(char[] usrInput, int index, int max) {
		if (index == max) {
			getDecimal();
			return true;
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
		} else
			return false;
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
		switch (i) {
		case '1':
			return 1.0;
		case '2':
			return 2.0;
		case '3':
			return 3.0;
		case '4':
			return 4.0;
		case '5':
			return 5.0;
		case '6':
			return 6.0;
		case '7':
			return 7.0;
		case '8':
			return 8.0;
		case '9':
			return 9.0;
		default:
			return 0;
		}
	}
	
	public static void main(String[] args) {
		String input = prompt();
		
		while (!input.equals("q")) {
			char[] usrInput = input.toCharArray();
			int index = 0;
			
			if (q0(usrInput, index, usrInput.length)) {
				if(isNegative)
					System.out.println(total * Math.pow(10,  exponent * -1));
				else
					System.out.println(total * Math.pow(10,  exponent));
			} 
			else
				System.out.println("Invalid format");
			stack.clear();
			total = 0;
			exponent = 0;
			isNegative = false;
			input = prompt();
		}
		System.out.println("Exited number identifier.");
	}
}