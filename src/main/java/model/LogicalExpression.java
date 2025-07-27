package model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author José Quiroz
 * 
 * Representa una expresión lógica como un árbol de operadores.
 */
public class LogicalExpression {
    
    public enum LogicalOperator { 
        NOT, AND, OR, IMPLIES, IFF 
    }
    
    public LogicalOperator operator;
    public LogicalExpression left;
    public LogicalExpression right;

    public LogicalExpression(LogicalOperator operator, LogicalExpression left, LogicalExpression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    
     // Atributo para almacenar el nombre de la variable lógica
    public String variable;

    // Constructor para variables
    public LogicalExpression(String variable) {
        this.variable = variable;
        this.operator = null; // Asumimos que no hay operador para una variable sola
    }

    public LogicalExpression(LogicalOperator operator, LogicalExpression left) {
        this(operator, left, null);
    }

    /**
     * Evalúa la expresión lógica para el conjunto de valores dado.
     * @param values
     * @return operator
     */
public Map<String, Boolean> evaluateWithResults(boolean[] values) {
    Map<String, Boolean> results = new HashMap<>();
    boolean finalResult = evaluateExpression(this, values, results);
    results.put(toString(), finalResult); // Almacenar el resultado final
    return results;
}

// Método auxiliar para la evaluación recursiva con almacenamiento de resultados
private boolean evaluateExpression(LogicalExpression expression, boolean[] values, Map<String, Boolean> results) {
    if (expression.operator == null) {
        int index = expression.variable.charAt(0) - 'A';
        return values[index];
    }

    boolean leftResult = expression.left != null ? evaluateExpression(expression.left, values, results) : false;
    boolean rightResult = expression.right != null ? evaluateExpression(expression.right, values, results) : false;

    boolean result;
    switch (expression.operator) {
        case NOT:
            result = !leftResult;
            break;
        case AND:
            result = leftResult && rightResult;
            break;
        case OR:
            result = leftResult || rightResult;
            break;
        case IMPLIES:
            result = !leftResult || rightResult;
            break;
        case IFF:
            result = leftResult == rightResult;
            break;
        default:
            throw new IllegalStateException("Operador desconocido: " + expression.operator);
    }

    results.put(expression.toString(), result); // Almacenar resultado de la subexpresión
    return result;
}

// Método para representar la expresión como una cadena legible
@Override
public String toString() {
    if (operator == null) return variable;
    String leftStr = left != null ? left.toString() : "";
    String rightStr = right != null ? right.toString() : "";
    return switch (operator) {
        case NOT -> "¬" + leftStr;
        case AND -> "(" + leftStr + " ∧ " + rightStr + ")";
        case OR -> "(" + leftStr + " ∨ " + rightStr + ")";
        case IMPLIES -> "(" + leftStr + " → " + rightStr + ")";
        case IFF -> "(" + leftStr + " ↔ " + rightStr + ")";
    };
}




}
