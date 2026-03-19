package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Pet in the address book.
 * Guarantees: immutable; name is present and valid.
 */
public class Pet {
    private final Name petName;
    private final Species species;
    private final Breed breed;
    private final DateOfBirth dateOfBirth;

    /**
     * Constructs a {@code Pet} with only a name.
     * Species, breed, and date of birth are set to null (optional fields).
     *
     * @param petName A valid pet name.
     */
    public Pet(Name petName) {
        requireNonNull(petName);
        this.petName = petName;
        this.species = null;
        this.breed = null;
        this.dateOfBirth = null;
    }

    /**
     * Constructs a {@code Pet} with all fields.
     *
     * @param petName A valid pet name.
     * @param species The pet's species (can be null).
     * @param breed The pet's breed (can be null).
     * @param dateOfBirth The pet's date of birth (can be null).
     */
    public Pet(Name petName, Species species, Breed breed, DateOfBirth dateOfBirth) {
        requireNonNull(petName);
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
    }

    public Name getName() {
        return petName;
    }

    public Optional<Species> getSpecies() {
        return Optional.ofNullable(species);
    }

    public Optional<Breed> getBreed() {
        return Optional.ofNullable(breed);
    }

    public Optional<DateOfBirth> getDateOfBirth() {
        return Optional.ofNullable(dateOfBirth);
    }

    /**
     * Returns true if both pets have the same name.
     * This defines a weaker notion of equality between two pets.
     */
    public boolean isSamePet(Pet otherPet) {
        if (otherPet == this) {
            return true;
        }

        return otherPet != null
                && otherPet.getName().equals(getName());
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
        return petName.equals(otherPet.petName)
                && Objects.equals(species, otherPet.species)
                && Objects.equals(breed, otherPet.breed)
                && Objects.equals(dateOfBirth, otherPet.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petName, species, breed, dateOfBirth);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(petName);

        if (species != null || breed != null) {
            sb.append(" (");
            if (species != null) {
                sb.append(species);
                if (breed != null) {
                    sb.append(", ");
                }
            }
            if (breed != null) {
                sb.append(breed);
            }
            sb.append(")");
        }

        return sb.toString();
    }
}
