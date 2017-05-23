package fractal.code.vending;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sorin.nica in May 2017
 */
@Data
@EqualsAndHashCode
public class Coin implements Comparable<Coin> {

    private final int denomination;

    public boolean factorsIn(int amount) {
        return amount % denomination == 0;
    }

    public int factorIn(int amount) {
        return amount / denomination;
    }

    @Override
    public int compareTo(Coin other) {
        return other.getDenomination() - getDenomination();
    }

    @Override
    public String toString() {
        return getDenomination() + "p";
    }


}
