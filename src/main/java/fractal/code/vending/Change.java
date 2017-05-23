package fractal.code.vending;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by sorin.nica in May 2017
 */
public class Change {

    private final Map<Coin, Integer> coins = new HashMap<>();

    public int getNrOfCoins() {
        return coins.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void add(Coin coin, Integer multiplier) {
        if (coins.containsKey(coin)) coins.put(coin, coins.get(coin) + multiplier);
        else coins.put(coin, multiplier);
    }

    public void add(Change other) {
        other.coins.forEach(this::add);
    }

    public Collection<Coin> asCollection() {
        return coins.entrySet().stream()
                .flatMap(entry -> {
                    Collection<Coin> acc = new ArrayList<>();
                    for (int i = 0; i < entry.getValue(); i++) acc.add(entry.getKey());
                    return acc.stream();
                })
                .collect(Collectors.toList());
    }

    public void forEach(BiConsumer<Coin, Integer> consumer) {
        coins.forEach(consumer);
    }

    public Stream<Map.Entry<Coin, Integer>> stream() {
        return coins.entrySet().stream();
    }

    @Override
    public String toString() {
        return stream()
                .sorted((entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey()))
                .map(entry -> "(" + entry.getValue() + " * " + entry.getKey() + ")")
                .reduce((denom1, denom2) -> denom1 + " + " + denom2)
                .orElse("0p");
    }

}
