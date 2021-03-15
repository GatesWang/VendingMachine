package vending;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * This represents a snack.
 */
@Getter
@Setter
@AllArgsConstructor
public class Snack {
    @NonNull private Integer amount;
    @NonNull private Double price;
    @NonNull private String name;
}
