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
        Coin[] coins={Coin.COIN_200};
        DrinkMachineResponse response =drinkMachine.buy(cola, coins);
        assertEquals("Successful Purchase", response.getMessage());
        assertEquals(Arrays.asList(Coin.COIN_50,Coin.COIN_20,Coin.COIN_10),  response.getCoins());
        assertEquals(cola,response.getProduct());
    }

    @Test
    public void notEnoughMoney(){
        Coin[] coins={Coin.COIN_100};
        DrinkMachineResponse response =drinkMachine.buy(cola, coins);
        assertEquals("Not Enough Money", response.getMessage());
        assertEquals(Arrays.asList(coins),response.getCoins());
        assertNull(response.getProduct());
    }


}
