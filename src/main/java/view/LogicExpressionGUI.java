package view;

import controller.LogicExpressionBuilder;
import model.LogicalExpression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que extiende JFrame para permitir la creación y evaluación
 * de expresiones lógicas a través de una interfaz gráfica.
 */
public class LogicExpressionGUI extends JFrame {
    private JTextField expressionField;
    private JButton generateButton;
    private JTextArea truthTableArea;

    public LogicExpressionGUI() {
        setTitle("Generador de Tabla de Verdad");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para ingresar la expresión lógica
        JPanel inputPanel = new JPanel();
        expressionField = new JTextField(20);
        generateButton = new JButton("Generar Tabla de Verdad");

        inputPanel.add(new JLabel("Expresión Lógica:"));
        inputPanel.add(expressionField);
        inputPanel.add(generateButton);

        add(inputPanel, BorderLayout.NORTH);

        // Área de texto para mostrar la tabla de verdad
        truthTableArea = new JTextArea();
        truthTableArea.setEditable(false);
        add(new JScrollPane(truthTableArea), BorderLayout.CENTER);

        // Acción del botón
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTruthTable();
            }
        });
    }

private void generateTruthTable() {
    String expression = expressionField.getText();
    try {
        LogicalExpression logicalExpression = LogicExpressionBuilder.parseExpression(expression);
        Set<String> variables = new HashSet<>();
        collectVariables(logicalExpression, variables);
        List<String> variableList = new ArrayList<>(variables);
        int totalCombinations = 1 << variableList.size();

        // Construir el encabezado de la tabla de verdad
        StringBuilder truthTable = new StringBuilder();
        for (String variable : variableList) {
            truthTable.append(variable).append("\t");
        }

        // Obtener las subexpresiones para el encabezado de la tabla
        Map<String, Boolean> sampleResults = logicalExpression.evaluateWithResults(new boolean[variableList.size()]);
        List<String> subexpressions = new ArrayList<>(sampleResults.keySet());
        for (String subexpr : subexpressions) {
            truthTable.append(subexpr).append("\t");
        }
        truthTable.append("Resultado Final\n");

        boolean isTautology = true;
        boolean isContradiction = true;

        // Generar filas para cada combinación de valores de verdad
        for (int i = 0; i < totalCombinations; i++) {
            boolean[] values = new boolean[variableList.size()];
            for (int j = 0; j < variableList.size(); j++) {
                values[j] = (i & (1 << j)) != 0;
                truthTable.append(values[j] ? "True\t" : "False\t");
            }

            // Evaluar cada subexpresión con los valores actuales
            Map<String, Boolean> results = logicalExpression.evaluateWithResults(values);
            for (String subexpr : subexpressions) {
                truthTable.append(results.get(subexpr) ? "True\t" : "False\t");
            }

            // Agregar el resultado final y verificar si es tautología o contradicción
            boolean finalResult = results.get(logicalExpression.toString());
            truthTable.append(finalResult ? "True\n" : "False\n");

            if (!finalResult) isTautology = false;
            if (finalResult) isContradiction = false;
        }

        // Clasificar la expresión como Tautología, Contradicción o Contingencia
        if (isTautology) {
            truthTable.append("\nLa expresión es una Tautología.\n");
        } else if (isContradiction) {
            truthTable.append("\nLa expresión es una Contradicción.\n");
        } else {
            truthTable.append("\nLa expresión es una Contingencia.\n");
        }

        truthTableArea.setText(truthTable.toString());
    } catch (Exception e) {
        truthTableArea.setText("Error al analizar la expresión: " + e.getMessage());
        Logger.getLogger(LogicExpressionGUI.class.getName()).log(Level.SEVERE, null, e);
    }
}





    /**
     * Recopila las variables de una expresión lógica en un conjunto.
     *
     * @param expression La expresión lógica a analizar.
     * @param variables  Conjunto que almacenará las variables encontradas.
     */
    private void collectVariables(LogicalExpression expression, Set<String> variables) {
    if (expression == null) return;
    // Si es una variable (sin operadores), añadimos su nombre al conjunto
    if (expression.operator == null) {
        variables.add(expression.variable); // Usar `variable` en lugar de `operator`
    } else {
        // Si es un operador, recorremos los hijos de la expresión
        collectVariables(expression.left, variables);
        collectVariables(expression.right, variables);
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LogicExpressionGUI gui = new LogicExpressionGUI();
            gui.setVisible(true);
        });
    }
}
