package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pet's species in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSpecies(String)}
 */
public class Species {

    public static final String MESSAGE_CONSTRAINTS =
            "Species should not be blank";

    /*
     * The species must not be blank or start with whitespace.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Species}.
     *
     * @param species A valid species.
     */
    public Species(String species) {
        requireNonNull(species);
        checkArgument(isValidSpecies(species), MESSAGE_CONSTRAINTS);
        value = species.trim();
    }

    /**
     * Returns true if a given string is a valid species.
     */
    public static boolean isValidSpecies(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Species)) {
            return false;
        }

        Species otherSpecies = (Species) other;
        return value.equalsIgnoreCase(otherSpecies.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}
