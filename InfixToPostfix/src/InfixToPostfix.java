import java.util.Stack;
import java.util.*;

public class InfixToPostfix {
    public static String infixToPostfix(String infExp) {
        // receive the infix expression as input and create a
        // stack that contains operators as characters
        Stack<Character> operators = new Stack<>();
        String postfix = "";

        for (int i = 0; i < infExp.length(); i++) { // loop through the given expression
            char ch = infExp.charAt(i); // get the character at the current index

            if (ch == ' ') { // if it's a space, then skip this index
                continue;
            } else if (ch == '(') { // left parens are pushed onto the stack
                operators.push(ch);
            } else if (ch == ')') { // when a right parens is found, pop the stack until a left is found
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix += operators.pop() + " ";   // add the popped operators to the expression
                }
                operators.pop();
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') {
                // if the char is an operator, it's precedence is determined by the pemdas function
                while (!operators.isEmpty() && pemdas(ch) <= pemdas(operators.peek())) {
                    // pop as long as there are operators to pop and the given operator is < the top operator
                    postfix += operators.pop() + " ";
                }
                operators.push(ch); // then push the new lowest precedence operator onto the stack
            } else {    // if the user is nice, then this is likely a number
                while (i < infExp.length() && Character.isDigit(infExp.charAt(i))) {
                    postfix += infExp.charAt(i) + " "; // loop through all of the digits/operands and put them together
                    i++;
                }
                i--;    // go back an iteration so that next non-digit isn't skipped
            }
        }

        while (!operators.isEmpty()) {
            // once the end of the expression is reached, pop everything remaining in the stack
            postfix += operators.pop() + " ";
        }

        return postfix;     // return the completed postfix expression
    }

    private static int pemdas(char operator) {
        // pemdas uses the precedence level of the operators to determine
        // the order that they are executed in. By assigning numerical values
        // to the operators, the program can compare them with different weights
        if (operator == '^') {
            return 3;
        }
        else if (operator == '*' || operator == '/') {
            return 2;
        }
        else if (operator == '+' || operator == '-') {
            return 1;
        }
        else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.printf("Enter an infix expression: ");
            InfixToPostfix postfix = new InfixToPostfix();
            System.out.println("Postfix expression: " + postfix.infixToPostfix(input.nextLine()));
        }
    }
}