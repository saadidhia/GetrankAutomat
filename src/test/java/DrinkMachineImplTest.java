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

    @BeforeEach
    void setup() {
        drinkMachine = new DrinkMachineImpl();
        cola=new Product("cola",120);
        drinkMachine.fill(cola, 5);
    }

    @Test
    public void buyProduct(){
        Coin[] coins={new Coin(200)};
        DrinkMachineResponse response =drinkMachine.buy(cola, coins);
        assertEquals("Successful", response.getMessage());
        assertEquals(Arrays.asList(new Coin(50),new Coin(20),new Coin(10)),  response.getCoins());
        assertEquals(cola,response.getProduct());
    }

    @Test
    public void notEnoughMoney(){
        Coin[] coins={new Coin(100)};
        DrinkMachineResponse response =drinkMachine.buy(cola, coins);
        assertEquals("Not Enough Money", response.getMessage());
        assertEquals(Arrays.asList(coins),response.getCoins());
        assertNull(response.getProduct());
    }


}
