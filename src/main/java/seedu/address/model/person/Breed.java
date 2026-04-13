package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Breed name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBreed(String)}
 */
public class Breed {

    public static final String MESSAGE_CONSTRAINTS =
            "Breed name should not be blank";
    public static final String VALIDATION_REGEX = "^(\\S.*)?$";

    public final String breedName;

    /**
     * Constructs a {@code Breed}.
     *
     * @param breed A valid breed.
     */
    public Breed(String breed) {
        requireNonNull(breed);
        checkArgument(isValidBreed(breed), MESSAGE_CONSTRAINTS);
        breedName = breed;
    }

    /**
     * Returns true if a given string is a valid breed.
     */
    public static boolean isValidBreed(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return breedName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Breed)) {
            return false;
        }

        Breed otherBreed = (Breed) other;
        return breedName.equals(otherBreed.breedName);
    }

    @Override
    public int hashCode() {
        return breedName.hashCode();
    }

}
