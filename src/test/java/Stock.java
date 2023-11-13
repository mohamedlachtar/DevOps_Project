import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    void addStock() {
        Stock stockToSave = new Stock(1L, "Test Stock", Collections.emptySet());
        when(stockRepository.save(Mockito.any(Stock.class))).thenReturn(stockToSave);

        Stock savedStock = stockService.addStock(stockToSave);

        assertEquals(stockToSave, savedStock);
        verify(stockRepository).save(stockToSave);
    }

    @Test
    void retrieveStock() {
        long stockId = 1L;
        Stock stock = new Stock(stockId, "Test Stock", Collections.emptySet());
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        Stock retrievedStock = stockService.retrieveStock(stockId);

        assertEquals(stock, retrievedStock);
    }

    @Test
    void retrieveStockNotFound() {
        long nonExistentStockId = 999L;
        when(stockRepository.findById(nonExistentStockId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> stockService.retrieveStock(nonExistentStockId));
    }

    @Test
    void retrieveAllStock() {
        Stock stock1 = new Stock(1L, "Stock 1", Collections.emptySet());
        Stock stock2 = new Stock(2L, "Stock 2", Collections.emptySet());
        when(stockRepository.findAll()).thenReturn(List.of(stock1, stock2));

        List<Stock> allStock = stockService.retrieveAllStock();

        assertEquals(2, allStock.size());
        assertEquals(stock1, allStock.get(0));
        assertEquals(stock2, allStock.get(1));
    }
}
