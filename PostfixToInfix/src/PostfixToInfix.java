import java.util.*;
import java.util.Stack;

public class PostfixToInfix {
    public static String postfixToInfix(String postExp) {
        Stack<String> operands = new Stack<>();

        for (int i = 0; i < postExp.length(); i++) {
            char ch = postExp.charAt(i);

            if (ch == ' ') {
                continue;
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') {
                String operand2;
                String operand1;
                try {
                    operand2 = operands.pop();
                    operand1 = operands.pop();
                } catch (Exception e) {
                    System.out.println("Error: Invalid postfix expression");
                    return "";
                }
                String subExp = "(" + operand1 + ch + operand2 + ")";
                operands.push(subExp);
            } else {
                StringBuilder operand = new StringBuilder();
                while (i < postExp.length() && Character.isDigit(postExp.charAt(i))) {
                    operand.append(postExp.charAt(i));
                    i++;
                }
                i--;
                operands.push(operand.toString());
            }
        }

        if (operands.size() != 1) {
            System.out.println("Error: Invalid postfix expression");
            return "";
        }
        return operands.pop();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.printf("Enter a postfix expression: ");
            PostfixToInfix infix = new PostfixToInfix();
            System.out.println("Infix expression: " + infix.postfixToInfix(input.nextLine()));
        }
    }
}

/*
//CS3345 HW2 Question 10 Part C - Vijay Karthikeya Raja
import java.util.*;
import java.lang.*;
import java.io.*;

class PostfixToInfix{
    //This method is used to make sure that the character found would be a viable character as an operand
    boolean operandFinder(char operand) {
        if(operand >= '0' && operand <= '9') {
            return true;
        }
        else if(operand >= 'a' && operand <= 'z') {
            return true;
        }
        else if(operand >= 'A' && operand <= 'Z') {
            return true;
        }
        //returns false if it is not an operand (will be an operator)
        return false;
    }
    String PostfixToInfix(String expression) {
        Stack <String> infixNotation = new Stack<>();
        //iterates through the expression entered by user and evaluates whether it is an operator or operand
        for (int i = 0; i < expression.length(); i++)
        {
            //In this case, when evaluating an unknown character, it checks if it is an operand through the method above.
            char operator = expression.charAt(i);
            if (operandFinder(operator)){
                //if it is an operand, it would add it as a part of the stack.
                infixNotation.push(operator + "");
            }
            //if it is not an operand, it would be assumed to be an operator, in which case it would add the previous operands and
            //sets it as an infix operation and separates it with parenthesis to know where the priority goes.
            else{
                String firstOperand = infixNotation.pop();
                String secondOperand = infixNotation.pop();

                infixNotation.push("(" + secondOperand + operator + firstOperand + ")");
            }
        }
        //returns the final expression
        return infixNotation.pop();
    }
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);
        PostfixToInfix infix = new PostfixToInfix();
        System.out.println(infix.PostfixToInfix(input.nextLine()));
    }
}
*/