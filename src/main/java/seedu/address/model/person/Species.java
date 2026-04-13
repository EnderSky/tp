package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Species type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSpecies(String)}
 */
public class Species {

    public static final String MESSAGE_CONSTRAINTS =
            "Species name should not be blank";
    public static final String VALIDATION_REGEX = "^(\\S.*)?$";

    public final String speciesName;

    /**
     * Constructs a {@code Species}.
     *
     * @param species A valid species.
     */
    public Species(String species) {
        requireNonNull(species);
        checkArgument(isValidSpecies(species), MESSAGE_CONSTRAINTS);
        speciesName = species;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidSpecies(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return speciesName;
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
        return speciesName.equals(otherSpecies.speciesName);
    }

    @Override
    public int hashCode() {
        return speciesName.hashCode();
    }

}
