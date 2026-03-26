package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_IMAGE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;

import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;
import seedu.address.model.person.PhotoPath;

/**
 * A utility class to help with building Pet objects.
 */
public class PetBuilder {

    public static final String DEFAULT_NAME = "Snoopy";
    public static final String DEFAULT_SPECIES = "Dog";
    public static final String DEFAULT_BREED = "Labrador";
    public static final String DEFAULT_NOTE = "Very friendly";
    public static final String DEFAULT_PHOTO_PATH = PLACEHOLDER_IMAGE_PATH;

    private Name name;
    private Name species;
    private Name breed;
    private Name note;
    private PhotoPath photoPath;

    /**
     * Creates a {@code PetBuilder} with the default details.
     */
    public PetBuilder() {
        name = new Name(DEFAULT_NAME);
        species = new Name(DEFAULT_SPECIES);
        breed = new Name(DEFAULT_BREED);
        note = new Name(DEFAULT_NOTE);
        photoPath = new PhotoPath(DEFAULT_PHOTO_PATH);
    }

    /**
     * Initializes the PetBuilder with the data of {@code petToCopy}.
     */
    public PetBuilder(Pet petToCopy) {
        name = petToCopy.getName();
        species = petToCopy.getSpecies();
        breed = petToCopy.getBreed();
        note = petToCopy.getNote();
        photoPath = petToCopy.getPhotoPath();
    }

    /**
     * Sets the {@code Name} of the {@code Pet} that we are building.
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
     * Sets the {@code PhotoPath} of the {@code Person} that we are building.
     */
    public PetBuilder withPhotoPath(String photoPath) {
        this.photoPath = new PhotoPath(photoPath);
        return this;
    }

    /**
     * Builds the Pet object.
     */
    public Pet build() {
        return new Pet(name, species, breed, note, photoPath);
    }

    /**
     * Returns user input to recreate descriptor.
     */
    public String getCommandFormat() {
        return PREFIX_NAME.toString() + name + " "
                + PREFIX_SPECIES + species + " "
                + PREFIX_BREED + breed + " "
                + PREFIX_NOTE + note + " "
                + PREFIX_PHOTO + photoPath;
    }

}
