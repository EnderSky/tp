package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;

/**
 * Jackson-friendly version of {@link Pet}.
 */
public class JsonAdaptedPet {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pet's %s field is missing!";

    private final String petName;
    private final String species;
    private final String breed;
    private final String note;

    /**
     * Constructs a {@code JsonAdaptedPet} with the given {@code petName}.
     */
    @JsonCreator
    public JsonAdaptedPet(@JsonProperty("petName") String petName,
                          @JsonProperty("species") String species,
                          @JsonProperty("breed") String breed,
                          @JsonProperty("note") String note) {
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.note = note;
    }

    /**
     * Converts a given {@code Pet} into this class for Jackson use.
     */
    public JsonAdaptedPet(Pet source) {
        petName = source.getName().fullName;
        species = source.getSpecies().fullName;
        breed = source.getBreed().fullName;
        note = source.getNote().fullName;
    }

    /**
     * Converts this Jackson-friendly adapted pet object into the model's {@code Pet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pet.
     */
    public Pet toModelType() throws IllegalValueException {
        if (petName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(petName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelPetName = new Name(petName);

        if (species == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Species"));
        }
        if (!Name.isValidName(species)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelSpecies = new Name(species);

        if (breed == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Breed"));
        }
        if (!Name.isValidName(breed)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelBreed = new Name(breed);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Note"));
        }
        if (!Name.isValidName(note)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelNote = new Name(note);

        return new Pet(modelPetName, modelSpecies, modelBreed, modelNote);
    }
}
