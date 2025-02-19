package euromillions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;

public class EuromillionsDrawTest {

	private CuponEuromillions sampleCuppon = new CuponEuromillions();

	public EuromillionsDrawTest() {
		sampleCuppon.addDipToCuppon(Dip.generateRandomDip());
		sampleCuppon.addDipToCuppon(Dip.generateRandomDip());
		sampleCuppon.addDipToCuppon(new Dip(new int[] { 1, 2, 3, 48, 49 }, new int[] { 1, 9 }));
	}

	@BeforeEach
	public void setUp() throws Exception {
		sampleCuppon = new CuponEuromillions();
		sampleCuppon.addDipToCuppon(Dip.generateRandomDip());
		sampleCuppon.addDipToCuppon(Dip.generateRandomDip());
		sampleCuppon.addDipToCuppon(new Dip(new int[] { 1, 2, 3, 48, 49 }, new int[] { 1, 9 }));
	}

	@Test
	@DisplayName("find existing matches")
	public void testFindMatches() {
		Dip expected, actual;

		// test for perfect match on the 3rd dip
		expected = sampleCuppon.getDipByIndex(2);
		EuromillionsDraw testDraw = new EuromillionsDraw(expected);
		List<Dip> matches = testDraw.findMatches(sampleCuppon);
		assertTrue(matches.size() >= 3, "Não há elementos suficientes na lista de matches.");
		actual = matches.get(2);

		assertEquals(sampleCuppon.countDips(), testDraw.findMatches(sampleCuppon).size());
		assertEquals(expected, actual);

	}

	@Test
	@DisplayName("finding no matches")
	public void testFindNoMatches() {
		Dip expected, actual;

		EuromillionsDraw testDraw = new EuromillionsDraw(new Dip(new int[] { 11, 12, 13, 14, 15 }, new int[] { 2, 3 }));
		expected = new Dip(); // should return an empty Dip, since there are no matches in draw and the
								// sampleCuppon.3
		actual = testDraw.findMatches(sampleCuppon).get(2);

		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Verifica que generateRandomDraw() executa corretamente e retorna um objeto válido")
	public void testGenerateRandomDraw() {
		EuromillionsDraw draw = assertDoesNotThrow(() -> EuromillionsDraw.generateRandomDraw(),
				"A geração de um sorteio aleatório não deve lançar exceções.");

		assertNotNull(draw, "O sorteio gerado não deve ser null.");
		assertNotNull(draw.getDrawResults(), "O resultado do sorteio não deve ser null.");
		assertEquals(5, draw.getDrawResults().getNumbersColl().size(), "O sorteio deve conter exatamente 5 números.");
		assertEquals(2, draw.getDrawResults().getStarsColl().size(), "O sorteio deve conter exatamente 2 estrelas.");
	}

	@Test
@DisplayName("Verifica que o método format() retorna a string formatada corretamente para múltiplos dips")
public void testFormat() {
    CuponEuromillions cupon = new CuponEuromillions();
    cupon.addDipToCuppon(new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2}));
    cupon.addDipToCuppon(new Dip(new int[]{6, 7, 8, 9, 10}, new int[]{3, 4}));

    String formattedOutput = cupon.format();

    assertTrue(formattedOutput.contains("Dip #1:"), "O formato deve conter Dip #1");
    assertTrue(formattedOutput.contains("Dip #2:"), "O formato deve conter Dip #2");
    assertTrue(formattedOutput.contains("N[  1  2  3  4  5] S[  1  2]"), 
        "O primeiro Dip deve estar corretamente formatado.");
    assertTrue(formattedOutput.contains("N[  6  7  8  9 10] S[  3  4]"), 
        "O segundo Dip deve estar corretamente formatado.");
}


}
