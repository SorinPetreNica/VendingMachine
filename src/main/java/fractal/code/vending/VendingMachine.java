package fractal.code.vending;

import fractal.code.vending.inventory.CoinInventory;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sorin.nica in May 2017
 */
@Slf4j
public class VendingMachine {

    private final CoinInventory coinInventory;

    private final Map<Integer, Change> cache = new HashMap<>();

    public VendingMachine(CoinInventory coinInventory) {
        this.coinInventory = coinInventory;
    }

    public Collection<Coin> getOptimalChangeFor(int pence) {
        if (pence < 0) throw new IllegalArgumentException("The amount must be equal or greater than zero.");
        if (pence > coinInventory.sum())
            throw new IllegalArgumentException("With the given inventory the maximum allowed amount is: " +
                    coinInventory.sum() + "p");

        Change change = getChangeRecursive(pence);
        coinInventory.take(change);

        log.info(pence + "p changes into: " + change);
        log.info("Number of coins used: " + change.getNrOfCoins());

        return change.asCollection();
    }

    public Collection<Coin> getChangeFor(int pence) {
        return getOptimalChangeFor(pence);
    }

    public Change getChangeRecursive(int pence) {
        Change change;
        if (cache.containsKey(pence)) return cache.get(pence);
        else {
            change = coinInventory.getAvailableDenominations().stream()
                    .filter(coin -> coin.getDenomination() <= pence)
                    .map(coin -> {
                        Change ch = new Change();

                        if (coin.factorsIn(pence) && coinInventory.has(coin, coin.factorIn(pence)))
                            ch.add(coin, coin.factorIn(pence));
                        else {
                            ch.add(coin, 1);
                            int difference = pence - coin.getDenomination();
                            ch.add(getChangeRecursive(difference));
                        }

                        return ch;
                    })
                    .filter(ch -> ch.sum() == pence)
                    .filter(coinInventory::canServe)
                    .sorted((change1, change2) -> change1.getNrOfCoins() - change2.getNrOfCoins())
                    .findFirst()
                    .orElse(new Change());

            cache.put(pence, change);
        }

        return change;
    }
}
