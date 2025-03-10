package application;

import java.util.Stack;

/**
 * Utility class to evaluate mathematical expressions in string format.
 */
public class EvaluateString {

	/**
	 * Evaluates a given arithmetic expression.
	 * @param expression A mathematical expression as a string.
	 * @return The result of the evaluated expression.
	 */
	public static int evaluate(String expression) {
		char[] tokens = expression.toCharArray();
		Stack<Integer> values = new Stack<>(); // Stack to store numbers
		Stack<Character> ops = new Stack<>(); // Stack to store operators

		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] == ' ')
				continue; // Skip whitespace

			// If the token is a number, extract the full number and push to values stack
			if (Character.isDigit(tokens[i])) {
				StringBuilder sbuf = new StringBuilder();
				while (i < tokens.length && Character.isDigit(tokens[i])) {
					sbuf.append(tokens[i++]);
				}
				values.push(Integer.parseInt(sbuf.toString()));
				i--; // Adjust for loop increment
			}

			// If an opening parenthesis, push to ops stack
			else if (tokens[i] == '(') {
				ops.push(tokens[i]);
			}

			// If a closing parenthesis, solve the expression within the parentheses
			else if (tokens[i] == ')') {
				while (ops.peek() != '(') {
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));
				}
				ops.pop(); // Remove the '('
			}

			// If an operator, process based on precedence
			else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
				while (!ops.isEmpty() && hasPrecedence(tokens[i], ops.peek())) {
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));
				}
				ops.push(tokens[i]); // Push current operator
			}
		}

		// Apply remaining operators to the remaining values
		while (!ops.isEmpty()) {
			values.push(applyOp(ops.pop(), values.pop(), values.pop()));
		}

		return values.pop(); // Final result
	}

	/**
	 * Determines if an operator has higher or equal precedence than another.
	 * @param op1 The current operator.
	 * @param op2 The operator on top of the stack.
	 * @return true if op2 has higher or equal precedence, otherwise false.
	 */
	public static boolean hasPrecedence(char op1, char op2) {
		if (op2 == '(' || op2 == ')') return false;
		return !((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'));
	}

	/**
	 * Applies an arithmetic operation.
	 * @param op The operator.
	 * @param b Second operand.
	 * @param a First operand.
	 * @return The result of the operation.
	 */
	public static int applyOp(char op, int b, int a) {
		switch (op) {
			case '+': return a + b;
			case '-': return a - b;
			case '*': return a * b;
			case '/':
				if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
				return a / b;
			default: return 0;
		}
	}
}
