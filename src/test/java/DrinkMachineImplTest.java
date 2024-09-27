import com.arvato.dtos.DrinkMachineResponse;
import com.arvato.models.Coin;
import com.arvato.models.Product;
import com.arvato.services.DrinkMachine;
import com.arvato.services.DrinkMachineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DrinkMachineImplTest {

    private DrinkMachine drinkMachine;
    private Product cola;
    private Product spezi;

    @BeforeEach
    void setup() {
        drinkMachine = new DrinkMachineImpl();
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
        spezi = new Product("spezi", 100);
        DrinkMachineResponse response = drinkMachine.buy(spezi, coins);
        assertEquals("Produkt ist nicht auf Lager.", response.message());
        assertEquals(Arrays.asList(coins), response.coins());
        assertNull(response.product());

    }


}
