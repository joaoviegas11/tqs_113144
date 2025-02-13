/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 *
 * @author ico0
 */
public class DipTest {

    private Dip dip = new Dip(new int[] { 10, 20, 30, 40, 50 }, new int[] { 1, 2 });

    @BeforeEach
    public void setUp() {
        dip = new Dip(new int[] { 10, 20, 30, 40, 50 }, new int[] { 1, 2 });
    }

    @AfterEach
    public void tearDown() {
        dip = null;
    }

    @Test
    public void testConstructorFromBadArrays() {
        // todo: instantiating a dip passing invalid arrays should raise an exception
        assertThrows(IllegalArgumentException.class,
                () -> new Dip(new int[] { 10, 20, 30, -40, 50 }, new int[] { 1, 2 }));
        assertThrows(IllegalArgumentException.class,
                () -> new Dip(new int[] { 10, 20, 30, 40, 50 }, new int[] { -1, 2 }));
    }

    @Test
    @DisplayName("pretty format of a dip")
    public void testPrettyFormat() {
        // note: correct the implementation, not the test ;)
        String result = dip.format();
        assertEquals("N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");
    }

    @Test
    @DisplayName("Verifica que exceção é lançada para número incorreto de elementos no Dip")
    public void testDipConstructorInvalidSize() {
        int[] invalidNumbers = { 1, 2, 3 }; // Apenas 3 números, deveria ser 5
        int[] invalidStars = { 1 }; // Apenas 1 estrela, deveria ser 2

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Dip(invalidNumbers, invalidStars),
                "Deve lançar IllegalArgumentException para tamanho incorreto dos arrays.");

        assertEquals("wrong number of elements in numbers/stars", exception.getMessage(),
                "A mensagem da exceção deve ser a esperada.");
    }

    @Test
    @DisplayName("Verifica que dois objetos Dip com os mesmos números e estrelas são considerados iguais")
    public void testDipEquals() {
        Dip dip1 = new Dip(new int[] { 1, 2, 3, 4, 5 }, new int[] { 1, 2 });
        Dip dip2 = new Dip(new int[] { 1, 2, 3, 4, 5 }, new int[] { 1, 2 });
        Dip dipDifferentNumbers = new Dip(new int[] { 6, 7, 8, 9, 10 }, new int[] { 1, 2 });
        Dip dipDifferentStars = new Dip(new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 4 });

        assertEquals(dip1, dip2, "Dips com os mesmos números e estrelas devem ser iguais.");
        assertNotEquals(dip1, dipDifferentNumbers, "Dips com números diferentes não devem ser iguais.");
        assertNotEquals(dip1, dipDifferentStars, "Dips com estrelas diferentes não devem ser iguais.");
        assertNotEquals(dip1, null, "Comparação com null deve retornar false.");
        assertNotEquals(dip1, new Object(), "Comparação com objeto de outra classe deve retornar false.");
    }

}
