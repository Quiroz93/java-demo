package controller;

import model.LogicalExpression;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba de integración para LogicExpressionBuilder.
 */
public class LogicExpressionBuilderIT {

    @BeforeAll
    public static void setUpClass() {
        // Configuraciones iniciales si son necesarias
    }

    @AfterAll
    public static void tearDownClass() {
        // Limpiar recursos si es necesario
    }

    @BeforeEach
    public void setUp() {
        // Inicialización antes de cada prueba
    }

    @AfterEach
    public void tearDown() {
        // Limpiar después de cada prueba
    }

    /**
     * Test de expresiones válidas.
     */
    @Test
    void testValidExpressions() {
        assertValidExpression("A");
        assertValidExpression("¬A");
        assertValidExpression("A ∧ B");
        assertValidExpression("A ∨ B");
        assertValidExpression("A → B");
        assertValidExpression("A ↔ B");
        assertValidExpression("¬(A ∧ B) ∨ (C → D)");
        assertValidExpression("{A ∧ (B ∨ C)} → D");
    }

    /**
     * Verifica que la expresión se analice correctamente.
     */
    private void assertValidExpression(String expression) {
        LogicalExpression result = LogicExpressionBuilder.parseExpression(expression);
        assertNotNull(result);
        // Aquí puedes agregar más validaciones según la estructura de LogicalExpression
        // Por ejemplo, verificar el tipo de operación o los operandos
    }

    /**
     * Test de expresiones inválidas que deberían lanzar excepciones.
     */
    @Test
void testInvalidExpressions() {
    System.out.println("Testing: A ∧ B # C");
    assertThrows(IllegalArgumentException.class, () -> LogicExpressionBuilder.parseExpression("A ∧ B # C"));

    System.out.println("Testing: ¬");
    assertThrows(IllegalArgumentException.class, () -> LogicExpressionBuilder.parseExpression("¬"));

    System.out.println("Testing: A ∧");
    assertThrows(IllegalArgumentException.class, () -> LogicExpressionBuilder.parseExpression("A ∧"));

    System.out.println("Testing: empty string");
    assertThrows(IllegalArgumentException.class, () -> LogicExpressionBuilder.parseExpression(""));
    
    System.out.println("Testing: A ∨ (B ∧ )");
    assertThrows(IllegalArgumentException.class, () -> LogicExpressionBuilder.parseExpression("A ∨ (B ∧ )"));

    System.out.println("Testing: {A ∧ (B ∨ C) → D)");
    assertThrows(IllegalArgumentException.class, () -> LogicExpressionBuilder.parseExpression("{A ∧ (B ∨ C) → D)"));
    
    System.out.println("Testing: (A ∧ B");
    assertThrows(IllegalArgumentException.class, () -> LogicExpressionBuilder.parseExpression("(A ∧ B"));
}


    /**
     * Test de expresiones con espacios en blanco.
     */
    @Test
    void testExpressionsWithWhitespace() {
        assertValidExpression("  A  ");
        assertValidExpression("   ¬A   ");
        assertValidExpression("  A ∧ B  ");
        assertValidExpression("   {A ∨ B} → C   ");
    }

    /**
     * Test de expresiones que combinan operaciones lógicas.
     */
    @Test
    void testComplexExpressions() {
        assertValidExpression("¬(A ∨ B) ∧ (C → D)");
        assertValidExpression("{A ∧ ¬B} ↔ (C ∨ D)");
        assertValidExpression("((A → B) ∧ (C ↔ D)) ∨ E");
    }
}
