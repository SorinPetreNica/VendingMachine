package fractal.code.vending.inventory;

import fractal.code.vending.Change;
import fractal.code.vending.Coin;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sorin.nica in May 2017
 */
public class LimitedCoinInventory implements CoinInventory {

    private final SortedMap<Coin, Integer> supply = new TreeMap<>();

    @Override
    public void addSupply(Coin coin, Integer quantity) {
        if (supply.containsKey(coin)) supply.put(coin, supply.get(coin) + quantity);
        else supply.put(coin, quantity);
    }

    @Override
    public SortedSet<Coin> getAvailableDenominations() {
        return supply.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public boolean canServe(Change change) {
        return change.stream().allMatch(entry -> supply.get(entry.getKey()) >= entry.getValue());
    }

    @Override
    public void take(Change change) {
        change.forEach(this::removeSupply);
    }

    @Override
    public int sum() {
        return supply.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getDenomination() * entry.getValue())
                .sum();
    }

    @Override
    public boolean has(Coin coin, int multiplier) {
        return supply.containsKey(coin) && supply.get(coin) >= multiplier;
    }

    private void removeSupply(Coin coin, Integer quantity) {
        if (supply.containsKey(coin)) {
            int currentSupply = supply.get(coin);
            if (currentSupply >= quantity) supply.put(coin, currentSupply - quantity);
            else throw new IllegalArgumentException("Requested quantity is not available.");
        }
    }

    @Override
    public String toString() {
        return supply.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((pair1, pair2) -> pair1 + "\n" + pair2)
                .orElse("");
    }

}
