package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Pet in the address book.
 * Guarantees: immutable;
 */
public class Pet {
    private final Name petName;
    private final String species;
    private final String breed;

    /**
     * Constructs a {@code Pet}.
     *
     * @param petName A valid pet name.
     */
    public Pet(Name petName, String species, String breed) {
        requireNonNull(petName);
        this.petName = petName;
        this.species = species;
        this.breed = breed;
    }

    public Name getName() {
        return petName;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
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
