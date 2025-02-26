package sets;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class SetOfNumbersTest {
	private SetOfNumbers setA = SetOfNumbers.fromArray(new int[] {});
	private SetOfNumbers setB = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
	private SetOfNumbers setC = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
	private SetOfNumbers setD = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });

	/**
	 * create some sets for testing
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		setA = new SetOfNumbers();
		setB = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });

		setC = new SetOfNumbers();
		for (int i = 5; i < 50; i++) {
			setC.add(i * 10);
		}
		setD = SetOfNumbers.fromArray(new int[] { 30, 40, 50, 60, 10, 20 });
	}

	@AfterEach
	public void tearDown() throws Exception {
		setA = setB = setC = setD = null;
	}

	@Test
	@DisplayName("add valid numbers to the set")
	public void testAdd() {

		setA.add(99);
		assertTrue(setA.contains(99), "add: added element not found in set.");
		assertEquals(1, setA.size());
		setA.add(11);
		assertTrue(setA.contains(11), "add: added element not found in set.");
		assertEquals(2, setA.size(), "add: elements count not as expected.");
	}

	@Test
	@DisplayName("two non overlaping sets report no intersection")
	public void testIntersectForNoIntersection() {
		assertFalse(setA.intersects(setB), "no intersection was reported as existing");

	}

	@Test
	@DisplayName("two sets with intersection report it")
	public void testIntersectsForIntersectionNotEmpty() {
		assertTrue(setB.intersects(setC), "failed to find existing intersection");
		assertTrue(setD.intersects(setB), "failed to find existing intersection");
	}

	@Test
	@DisplayName("Testar a operação de subtração de conjuntos")
	public void testSubtract() {
		setA = SetOfNumbers.fromArray(new int[] {30, 40, 50});
		setB.subtract(setA);
		
		assertEquals(3, setB.size());
		assertFalse(setB.contains(30));
		assertFalse(setB.contains(40));
		assertFalse(setB.contains(50));
		
		assertTrue(setB.contains(10));
		assertTrue(setB.contains(20));
		assertTrue(setB.contains(60));
	}
	

	@Test
	public void testContains() {
		assertTrue(setB.contains(10), "contains: expected value not found");
		assertTrue(setB.contains(60), "contains: expected value not found");
		assertFalse(setB.contains(-1), "contains: non existing value reported found");
		assertFalse(setB.contains(90), "contains: non existing value reported found");
	}

	@Test
	public void testNoDuplicates() {
		assertThrows(IllegalArgumentException.class, () -> setB.add(20)); // duplicate, must fail with an exception
	}


	@Test
    public void testHashCode() {
		setA = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
		setB = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
		setC = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 61 });
        assertEquals(setA.hashCode(), setB.hashCode(), "Os hashCodes de conjuntos iguais devem ser iguais.");
        assertNotEquals(setA.hashCode(), setC.hashCode(), "Os hashCodes de conjuntos diferentes devem ser diferentes.");
    }

    @Test
    public void testEquals() {
		setA = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
		setB = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 60 });
		setC = SetOfNumbers.fromArray(new int[] { 10, 20, 30, 40, 50, 61 });
        assertTrue(setA.equals(setB), "Dois conjuntos com os mesmos elementos devem ser iguais.");
        assertFalse(setA.equals(setC), "Conjuntos diferentes não devem ser considerados iguais.");
        assertFalse(setA.equals(null), "Comparação com null deve retornar false.");
        assertFalse(setA.equals(new Object()), "Comparação com outro tipo de objeto deve retornar false.");
    }
}
