package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;

import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;

/**
 * A utility class to help with building Pet objects.
 */
public class PetBuilder {

    public static final String DEFAULT_NAME = "Snoopy";
    public static final String DEFAULT_SPECIES = "dog";
    public static final String DEFAULT_BREED = "beagle";

    private Name name;
    private String species;
    private String breed;

    /**
     * Creates a {@code PetBuilder} with the default details.
     */
    public PetBuilder() {
        name = new Name(DEFAULT_NAME);
        species = DEFAULT_SPECIES;
        breed = DEFAULT_BREED;

    }

    /**
     * Initializes the PetBuilder with the data of {@code petToCopy}.
     */
    public PetBuilder(Pet petToCopy) {
        name = petToCopy.getName();
        species = petToCopy.getSpecies();
        breed = petToCopy.getBreed();
    }

    /**
     * Sets the {@code Name} of the {@code Pet} that we are building.
     */
    public PetBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code species} of the {@code Pet} that we are building.
     */
    public PetBuilder withSpecies(String species) {
        this.species = species;
        return this;
    }

    /**
     * Sets the {@code breed} of the {@code Pet} that we are building.
     */
    public PetBuilder withBreed(String breed) {
        this.breed = breed;
        return this;
    }

    /**
     * Returns a pet with the stored fields
     */
    public Pet build() {
        return new Pet(name, species, breed);
    }

    /**
     * Returns the string representation of the pet to be passed to the parser.
     */
    public String getCommandFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + name.fullName + " ");
        sb.append(PREFIX_BREED + breed + " ");
        sb.append(PREFIX_SPECIES + species);
        return sb.toString();
    }
}
