package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pet in the address book.
 * Guarantees: immutable;
 */
public class Pet {
    private final Name petName;

    /**
     * Constructs a {@code Pet}.
     *
     * @param petName A valid pet name.
     */
    public Pet(Name petName) {
        requireNonNull(petName);
        this.petName = petName;
    }

    public Name getName() {
        return petName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Pet)) {
            return false;
        }

        Pet otherPet = (Pet) other;
        return petName.equals(otherPet.petName);
    }

    @Override
    public int hashCode() {
        return petName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + petName + "]";
    }
}
