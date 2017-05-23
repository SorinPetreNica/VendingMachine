package fractal.code.vending.inventory;

import fractal.code.vending.Change;
import fractal.code.vending.Coin;

import java.util.SortedSet;

/**
 * Created by sorin.nica in May 2017
 */
public interface CoinInventory {

    void addSupply(Coin coin, Integer quantity);

    SortedSet<Coin> getAvailableDenominations();

    boolean canServe(Change change);

    void take(Change change);

    int sum();

    boolean has(Coin coin, int multiplier);
}
