package seedu.address.testutil;


import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;

/**
 * A utility class to help with building Person objects.
 */
public class PetBuilder {

    public static final String DEFAULT_NAME = "Snoopy";
    public static final String DEFAULT_SPECIES = "Dog";
    public static final String DEFAULT_BREED = "Labrador";
    public static final String DEFAULT_NOTE = "Very friendly";

    private Name name;
    private Name species;
    private Name breed;
    private Name note;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PetBuilder() {
        name = new Name(DEFAULT_NAME);
        species = new Name(DEFAULT_SPECIES);
        breed = new Name(DEFAULT_BREED);
        note = new Name(DEFAULT_NOTE);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PetBuilder(Pet petToCopy) {
        name = petToCopy.getName();
        species = petToCopy.getSpecies();
        breed = petToCopy.getBreed();
        note = petToCopy.getNote();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PetBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Species} of the {@code Person} that we are building.
     */
    public PetBuilder withSpecies(String species) {
        this.species = new Name(species);
        return this;
    }

    /**
     * Sets the {@code Breed} of the {@code Person} that we are building.
     */
    public PetBuilder withBreed(String breed) {
        this.breed = new Name(breed);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PetBuilder withNote(String note) {
        this.note = new Name(note);
        return this;
    }

    /**
     * Builds the Pet object.
     */
    public Pet build() {
        return new Pet(name, species, breed, note);
    }
}
