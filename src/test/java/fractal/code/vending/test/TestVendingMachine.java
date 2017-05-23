package fractal.code.vending.test;

import fractal.code.vending.Coin;
import fractal.code.vending.VendingMachine;
import fractal.code.vending.inventory.CoinInventory;
import fractal.code.vending.inventory.CoinInventoryFactory;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sorin.nica in May 2017
 */
public class TestVendingMachine {

    @Test
    public void testVendingMachine() {
        CoinInventoryFactory factory = new CoinInventoryFactory();
        CoinInventory infiniteInventory = factory.loadInventory();
        VendingMachine vendingMachine = new VendingMachine(infiniteInventory);

        int pence = 133;

        Collection<Coin> change = vendingMachine.getOptimalChangeFor(pence);

        assertEquals(pence, change.stream().mapToInt(Coin::getDenomination).sum());

        CoinInventory limitedInventory = factory.loadInventory("src/main/resources/coin-inventory.properties");
        VendingMachine limitedVendingMachine = new VendingMachine(limitedInventory);

        Collection<Coin> change2 = limitedVendingMachine.getOptimalChangeFor(pence);
        assertTrue(change.size() < change2.size());

        assertEquals(0, limitedVendingMachine.getOptimalChangeFor(0).size());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMaxAmountException() {
        CoinInventoryFactory factory = new CoinInventoryFactory();
        CoinInventory limitedInventory = factory.loadInventory("src/main/resources/coin-inventory.properties");
        VendingMachine limitedVendingMachine = new VendingMachine(limitedInventory);

        limitedVendingMachine.getOptimalChangeFor(50_000);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNegativeAmountException() {
        CoinInventoryFactory factory = new CoinInventoryFactory();
        CoinInventory limitedInventory = factory.loadInventory("src/main/resources/coin-inventory.properties");
        VendingMachine limitedVendingMachine = new VendingMachine(limitedInventory);

        limitedVendingMachine.getOptimalChangeFor(-3);
    }

}
