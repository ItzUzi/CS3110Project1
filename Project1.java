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
	
	public static String prompt() {
		System.out.printf("Enter a number or press \'q\' to quit\n(accepted format 111.32e4f)\n");
		Scanner kbInput = new Scanner(System.in);
		return kbInput.next().toLowerCase();
	}
	
	private static boolean q0(char[] usrInput, int index, int max) {
		if(index == max)
			return false;
		
		char c = usrInput[index];

		if (digits.contains(c)) {
			stack.push(c);
			return q1(usrInput, index+1, max);
		} else if (c == '.')
			return q8(usrInput, index+1, max);
		else
			return false;
	}
	
	private static boolean q8(char[] usrInput, int index, int max) {
		if (index == max)
			return false;
		char c = usrInput[index];
		if (digits.contains(c)) {
			stack.push(c);
			return q5(usrInput, index+1, max);
		}else
			return false;
	}

	private static boolean q1(char[] usrInput, int index, int max) {
		if (index == max) {
			stack.clear();
			return false;
		}
		char c = usrInput[index];
		if (digits.contains(c)) {
			stack.push(c);
			return q1(usrInput, index+1, max);
		} else if (c == '.') {
			getSum();
			return q2(usrInput, index+1, max);
		} else if (endings.contains(c)) {
			getSum();
			return q3(usrInput, index+1, max);
		} else if (c == 'e') {
			getSum();
			return q4(usrInput, index+1, max);
		} else		
			return false;
	}
	
	private static boolean q2(char[] usrInput, int index, int max) {
		if (index == max)
			return true;
		char c = usrInput[index];
		
		if (digits.contains(c)) {
			stack.push(c);
			return q5(usrInput, index+1,max);
		} else if (endings.contains(c))
			return q3(usrInput, index+1, max);
		else if (c == 'e')
			return q4(usrInput, index+1, max);
		else
			return false;
	}
	
	private static boolean q5(char[] usrInput, int index, int max) {
		if (index == max) {
			getDecimal();
			return true;
		}
		char c = usrInput[index];
		
		if (digits.contains(c)) {
			stack.push(c);
			return q5(usrInput, index+1, max);
		} else if (c == 'e') {
			getDecimal();
			return q4(usrInput, index+1, max);
		} else if (endings.contains(c)) {
			getDecimal();
			return q3(usrInput, index+1, max);
		} else
			return false;
	}
	
	private static boolean q3(char[] usrInput, int index, int max) {
		if (index == max)
			return true;
		else
			return false;
	}

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

	private static boolean q7(char[] usrInput, int index, int max) {
		if (index == max) {
			getExponent();
			return true;
		}
		char c = usrInput[index];
		
		if (digits.contains(c)) {
			stack.push(c);
			return q7(usrInput, index+1, max);
		} else if (endings.contains(c)) {
			getExponent();
			return q3(usrInput, index+1, max);
		} else
			return false;
	}

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

	private static void getExponent() {
		exponent = 0;
		int max = stack.size();
		for (int i = 0; i < max; i++)
			exponent += getNum() * Math.pow(10, i);
	}
	
	private static void getSum() {
		int max = stack.size();
		for (int i = 0; i < max; i++) {
			total += getNum() * Math.pow(10, i);
		}
	}
	
	private static void getDecimal() {
		int max = stack.size();
		for (int i = max; i > 0; i--)
			total += getNum() * Math.pow(10, (-1 * i));
	}
	
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
			int negativeOne = -1;
			
			if (q0(usrInput, index, usrInput.length)) {
				if(isNegative)
					System.out.println(total * Math.pow(10,  exponent * negativeOne));
				else
					System.out.println(total * Math.pow(10,  exponent));
			} 
			else
				System.out.println("Invalid format");
			total = 0;
			exponent = 0;
			isNegative = false;
			input = prompt();
		}
	}
}
