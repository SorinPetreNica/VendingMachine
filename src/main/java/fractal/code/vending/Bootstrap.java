package fractal.code.vending;

import fractal.code.vending.inventory.CoinInventory;
import fractal.code.vending.inventory.CoinInventoryFactory;

import java.io.IOException;

/**
 * Created by sorin.nica in May 2017
 */
public class Bootstrap {

    public static void main(String[] args) throws IOException {
        int pence = Integer.parseInt(args[0]);
        String inventoryFilePath = args.length == 2 ? args[1] : null;

        CoinInventoryFactory inventoryFactory = new CoinInventoryFactory();
        CoinInventory coinInventory = inventoryFactory.loadInventory(inventoryFilePath);

        VendingMachine vendingMachine = new VendingMachine(coinInventory);

        vendingMachine.getOptimalChangeFor(pence);
        inventoryFactory.persistInventory(coinInventory, inventoryFilePath);
    }

}
