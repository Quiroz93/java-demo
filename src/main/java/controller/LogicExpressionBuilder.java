package controller;

import model.LogicalExpression;
import java.util.*;
import static model.LogicalExpression.LogicalOperator.*;

/**
 *
 * @author José Quiroz
 */
public class LogicExpressionBuilder {

public static LogicalExpression parseExpression(String expression) {
    List<String> tokens = tokenize(expression);
    Stack<LogicalExpression.LogicalOperator> operators = new Stack<>();
    Stack<String> brackets = new Stack<>();  // Pila exclusiva para paréntesis y llaves
    Stack<LogicalExpression> operands = new Stack<>();

    Map<LogicalExpression.LogicalOperator, Integer> precedence = Map.of(
        NOT, 3,
        AND, 2,
        OR, 1,
        IMPLIES, 0,
        IFF, 0
    );

    for (String token : tokens) {
        switch (token) {
            case "¬" -> operators.push(NOT);
            case "∧" -> processOperator(AND, precedence, operators, operands);
            case "∨" -> processOperator(OR, precedence, operators, operands);
            case "→" -> processOperator(IMPLIES, precedence, operators, operands);
            case "↔" -> processOperator(IFF, precedence, operators, operands);
            case "(" -> brackets.push("(");
            case "{" -> brackets.push("{");
            case ")" -> {
                while (!operators.isEmpty() && operators.peek() != null) {
                    applyOperator(operators, operands);
                }
                if (!brackets.isEmpty() && brackets.peek().equals("(")) {
                    brackets.pop();  // Quitar el marcador de paréntesis "("
                } else {
                    throw new IllegalArgumentException("Paréntesis desequilibrados en la expresión.");
                }
            }
            case "}" -> {
                while (!operators.isEmpty() && operators.peek() != null) {
                    applyOperator(operators, operands);
                }
                if (!brackets.isEmpty() && brackets.peek().equals("{")) {
                    brackets.pop();  // Quitar el marcador de llave "{"
                } else {
                    throw new IllegalArgumentException("Llaves desequilibradas en la expresión.");
                }
            }
            default -> operands.push(new LogicalExpression(token));  // Manejo de variables
        }
    }

    // Aplicar cualquier operador restante
    while (!operators.isEmpty()) {
        applyOperator(operators, operands);
    }

    // Verificar si todas las llaves y paréntesis están balanceados
    if (!brackets.isEmpty()) {
        throw new IllegalArgumentException("Paréntesis o llaves desequilibrados en la expresión.");
    }

    if (operands.size() != 1) {
        throw new IllegalArgumentException("Expresión mal formada.");
    }

    return operands.pop();
}



    private static void processOperator(LogicalExpression.LogicalOperator operator,
                                        Map<LogicalExpression.LogicalOperator, Integer> precedence,
                                        Stack<LogicalExpression.LogicalOperator> operators,
                                        Stack<LogicalExpression> operands) {
        while (!operators.isEmpty() && operators.peek() != null &&
               precedence.get(operators.peek()) >= precedence.get(operator)) {
            applyOperator(operators, operands);
        }
        operators.push(operator);
    }

    private static void applyOperator(Stack<LogicalExpression.LogicalOperator> operators,
                                      Stack<LogicalExpression> operands) {
        LogicalExpression.LogicalOperator operator = operators.pop();
        if (operator == null) return; // Salir si el operador es nulo (marcador de paréntesis)

        LogicalExpression expression;
        if (operator == NOT) {
            if (operands.isEmpty()) {
                throw new IllegalArgumentException("El operador NOT necesita un operando.");
            }
            LogicalExpression operand = operands.pop();
            expression = new LogicalExpression(operator, operand); // Crear nodo operador para NOT
        } else {
            if (operands.size() < 2) {
                throw new IllegalArgumentException("Faltan operandos para el operador " + operator);
            }
            LogicalExpression right = operands.pop();
            LogicalExpression left = operands.pop();
            expression = new LogicalExpression(operator, left, right); // Crear nodo operador binario
        }
        operands.push(expression); // Agregar el nodo a la pila
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (char ch : expression.toCharArray()) {
            if (ch == ' ') continue;
            if ("¬∧∨→↔(){}".indexOf(ch) != -1) {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                tokens.add(String.valueOf(ch));
            } else if (Character.isLetter(ch)) {
                token.append(ch);
            } else {
                throw new IllegalArgumentException("Carácter inválido en la expresión: " + ch);
            }
        }
        if (token.length() > 0) tokens.add(token.toString());
        return tokens;
    }
}
