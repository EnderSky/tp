package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Pet in the address book.
 * Guarantees: immutable;
 */
public class Pet {
    private final Name petName;
    private final Name species;
    private final Name breed;
    private final Name note;
    private final PhotoPath photoPath;

    /**
     * Constructs a {@code Pet}.
     *
     * @param petName A valid pet name.
     */
    public Pet(Name petName, Name species, Name breed, Name note, PhotoPath photoPath) {
        requireNonNull(petName);
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.note = note;
        this.photoPath = photoPath;
    }

    public Name getName() {
        return petName;
    }

    public Name getSpecies() {
        return species;
    }

    public Name getBreed() {
        return breed;
    }

    public Name getNote() {
        return note;
    }

    public PhotoPath getPhotoPath() {
        return photoPath;
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
