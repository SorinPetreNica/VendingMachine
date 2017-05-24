package fractal.code.vending.inventory;

import fractal.code.vending.Coin;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by sorin.nica in May 2017
 */
public class CoinInventoryFactory {

    public CoinInventory loadInventory() {
        return loadInventory(null);
    }

    public CoinInventory loadInventory(String sourceFilePath) {
        System.out.println(Paths.get("").toAbsolutePath());

        if (sourceFilePath == null) return getDefaultCoinInventory();
        else {
            CoinInventory coinInventory = new LimitedCoinInventory();
            Path inventoryPath = Paths.get(sourceFilePath);
            try {
                Files.newBufferedReader(inventoryPath)
                        .lines()
                        .map(pair -> pair.split("="))
                        .map(pair -> new int[]{Integer.parseInt(pair[0]), Integer.parseInt(pair[1])})
                        .forEach(supply -> coinInventory.addSupply(new Coin(supply[0]), supply[1]));

                return coinInventory;
            } catch (IOException | UncheckedIOException e) {
                throw new IllegalArgumentException("Could not read input file: " + sourceFilePath, e);
            } catch(NumberFormatException nfe) {
                throw new IllegalArgumentException("Input file must have the following structure: (int)denomination=(int)supply", nfe);
            }
        }
    }

    public void persistInventory(CoinInventory inventory, String outputFilePath) {
        if (outputFilePath != null)
            try {
                Files.write(Paths.get(outputFilePath + ".updated"), inventory.toString().getBytes());
            } catch (IOException | UncheckedIOException e) {
                throw new IllegalArgumentException("Could not write inventory to path: " + outputFilePath, e);
            }
    }

    private CoinInventory getDefaultCoinInventory() {
        return new InfiniteCoinInventory()
                .addDenomination(new Coin(1))
                .addDenomination(new Coin(2))
                .addDenomination(new Coin(5))
                .addDenomination(new Coin(10))
                .addDenomination(new Coin(20))
                .addDenomination(new Coin(50))
                .addDenomination(new Coin(100));
    }
}
