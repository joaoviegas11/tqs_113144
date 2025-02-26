package stocks.portfolio;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;;
@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {
    @Mock
    IStockmarketService market;
    @InjectMocks
    StocksPortfolio portfolio;
	
	@Test
	void testTotalValue() {
        
    // IStockmarketService market = Mockito.mock(IStockmarketService.class);

    // StocksPortfolio portfolio = new StocksPortfolio(market);

    Mockito.when(market.lookUpPrice("EBAY")).thenReturn(4.0);
    Mockito.when(market.lookUpPrice("MSFT")).thenReturn(1.5);
    Mockito.when(market.lookUpPrice("UBI")).thenReturn(12.2);
    // Mockito.when(market.lookUpPrice("MARVEL")).thenReturn(12.2);

    portfolio.addStock(new Stock("EBAY", 2));
    portfolio.addStock(new Stock("MSFT", 4));
    portfolio.addStock(new Stock("UBI", 41));
    portfolio.addStock(new Stock("OPENEI", 32));
    double result = portfolio.totalValue();

    assertEquals(514.2, result);
    assertThat(result,equalTo(514.2));
    Mockito.verify(market, Mockito.times(4)).lookUpPrice(Mockito.anyString());

	}

    @Test
    void testMostValuableStocks() {
        Mockito.when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        Mockito.when(market.lookUpPrice("MSFT")).thenReturn(1.5);
        Mockito.when(market.lookUpPrice("UBI")).thenReturn(12.2);
        Mockito.when(market.lookUpPrice("MARVEL")).thenReturn(132.2);
        Mockito.when(market.lookUpPrice("OPENEI")).thenReturn(712.2);
    
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 4));
        portfolio.addStock(new Stock("UBI", 41));
        portfolio.addStock(new Stock("MARVEL", 1));
        portfolio.addStock(new Stock("OPENEI", 2));

        List<Stock> topStocks = portfolio.mostValuableStocks(3);
        assertEquals(3, topStocks.size());
        assertEquals("OPENEI", topStocks.get(0).getLabel());
        assertEquals("UBI", topStocks.get(1).getLabel());
        assertEquals("MARVEL", topStocks.get(2).getLabel());

        verify(market, times(18)).lookUpPrice(anyString());
    }

}
