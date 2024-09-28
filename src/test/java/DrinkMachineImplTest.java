import com.arvato.dtos.DrinkMachineResponse;
import com.arvato.models.Coin;
import com.arvato.models.Product;
import com.arvato.models.Stock;
import com.arvato.services.DrinkMachine;
import com.arvato.services.DrinkMachineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DrinkMachineImplTest {

    private DrinkMachine drinkMachine;
    private Product cola;
    private Stock stock;


    @BeforeEach
    void setup() {
        stock = new Stock(new HashMap<>());
        drinkMachine = new DrinkMachineImpl(stock);
        cola = new Product("cola", 120);
        drinkMachine.fill(cola, 5);
    }

    @Test
    public void buyProductSuccessfullyTest() {
        Coin[] coins = {Coin.COIN_200};
        DrinkMachineResponse response = drinkMachine.buy(cola, coins);
        assertEquals("Erfolgreicher Kauf.", response.message());
        assertEquals(Arrays.asList(Coin.COIN_50, Coin.COIN_20, Coin.COIN_10), response.coins());
        assertEquals(cola, response.product());
    }

    @Test
    public void notEnoughMoneyTest() {
        Coin[] coins = {Coin.COIN_100};
        DrinkMachineResponse response = drinkMachine.buy(cola, coins);
        assertEquals("Nicht genug Geld.", response.message());
        assertEquals(Arrays.asList(coins), response.coins());
        assertNull(response.product());
    }

    @Test
    public void outOfStockTest() {
        Coin[] coins = {Coin.COIN_100};
        Product spezi = new Product("spezi", 100);
        DrinkMachineResponse response = drinkMachine.buy(spezi, coins);
        assertEquals("Produkt ist nicht auf Lager.", response.message());
        assertEquals(Arrays.asList(coins), response.coins());
        assertNull(response.product());

    }

    @Test
    public void multiplePurchasesTest() {
        Coin[] coins1 = {Coin.COIN_200};
        Coin[] coins2 = {Coin.COIN_200};

        // Buy the first cola
        DrinkMachineResponse response1 = drinkMachine.buy(cola, coins1);
        assertEquals("Erfolgreicher Kauf.", response1.message());

        // Buy the second cola
        DrinkMachineResponse response2 = drinkMachine.buy(cola, coins2);
        assertEquals("Erfolgreicher Kauf.", response2.message());

        // Verify remaining stock
        assertTrue(stock.checkProductDisponibility(cola));
        assertEquals(3, stock.getQuantity(cola));
    }

    @Test
    public void invalidProductPurchaseTest() {
        Product invalidProduct = new Product("ice Tea", 150);
        Coin[] coins = {Coin.COIN_200};
        DrinkMachineResponse response = drinkMachine.buy(invalidProduct, coins);
        assertEquals("Produkt ist nicht auf Lager.", response.message());
        assertEquals(Arrays.asList(coins), response.coins());
        assertNull(response.product());
    }


}
