package fractal.code.vending.inventory;

import fractal.code.vending.Change;
import fractal.code.vending.Coin;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by sorin.nica in May 2017
 */
public class InfiniteCoinInventory implements CoinInventory {

    private final SortedSet<Coin> supply = new TreeSet<>();

    public InfiniteCoinInventory addDenomination(Coin coin) {
        supply.add(coin);
        return this;
    }

    @Override
    public void addSupply(Coin coin, Integer quantity) {
        addDenomination(coin);
    }

    @Override
    public SortedSet<Coin> getAvailableDenominations() {
        return supply;
    }

    @Override
    public boolean canServe(Change change) {
        return true;
    }

    @Override
    public void take(Change change) {
        // NO OP
    }

    @Override
    public int sum() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean has(Coin coin, int multiplier) {
        return true;
    }

}
