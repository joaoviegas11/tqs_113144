package stocks.portfolio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StocksPortfolio {
    private List<Stock> stocks=new ArrayList<Stock>();
    private IStockmarketService stockmarket;
    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
    }
    

    public void addStock(Stock stock){
        stocks.add(stock);
    }
    public double totalValue(){
        double total=0;
        for (Stock stock : stocks) {
            total+=stockmarket.lookUpPrice(stock.getLabel())*stock.getQuantity();
        }
        return total;
    }

     /**
     * Returns the top N most valuable stocks in the portfolio.
     * @param topN the number of most valuable stocks to return
     * @return a list with the topN most valuable stocks in the portfolio
     */
    public List<Stock> mostValuableStocks(int topN) {
        return stocks.stream()
                .sorted(Comparator.comparingDouble((Stock stock) -> 
                        stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity())
                        .reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }
    
}
