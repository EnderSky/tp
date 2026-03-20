package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Breed;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Species;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Pet objects.
 */
public class PetBuilder {

    public static final String DEFAULT_NAME = "Snoopy";

    private Name name;
    private Species species;
    private Breed breed;
    private DateOfBirth dateOfBirth;
    private Set<Tag> tags;

    /**
     * Creates a {@code PetBuilder} with the default details.
     */
    public PetBuilder() {
        name = new Name(DEFAULT_NAME);
        species = null;
        breed = null;
        dateOfBirth = null;
        tags = new HashSet<>();
    }

    /**
     * Initializes the PetBuilder with the data of {@code petToCopy}.
     */
    public PetBuilder(Pet petToCopy) {
        name = petToCopy.getName();
        species = petToCopy.getSpecies().orElse(null);
        breed = petToCopy.getBreed().orElse(null);
        dateOfBirth = petToCopy.getDateOfBirth().orElse(null);
        tags = new HashSet<>(petToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Pet} that we are building.
     */
    public PetBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Species} of the {@code Pet} that we are building.
     */
    public PetBuilder withSpecies(String species) {
        this.species = new Species(species);
        return this;
    }

    /**
     * Sets the {@code Breed} of the {@code Pet} that we are building.
     */
    public PetBuilder withBreed(String breed) {
        this.breed = new Breed(breed);
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code Pet} that we are building.
     */
    public PetBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = new DateOfBirth(dateOfBirth);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Pet} that we are building.
     */
    public PetBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Pet build() {
        return new Pet(name, species, breed, dateOfBirth, tags);
    }
}
