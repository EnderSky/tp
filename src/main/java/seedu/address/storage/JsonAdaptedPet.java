package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;

/**
 * Jackson-friendly version of {@link Pet}.
 */
class JsonAdaptedPet {

    private final String petName;
    private final String species;
    private final String breed;

    /**
     * Constructs a {@code JsonAdaptedPet} with the given {@code petName}.
     */
    @JsonCreator
    public JsonAdaptedPet(@JsonProperty("petName") String petName,
                          @JsonProperty("species") String species,
                          @JsonProperty("breed") String breed) {
        this.petName = petName;
        this.species = species;
        this.breed = breed;
    }

    /**
     * Converts a given {@code Pet} into this class for Jackson use.
     */
    public JsonAdaptedPet(Pet source) {
        petName = source.getName().fullName;
        species = source.getSpecies();
        breed = source.getBreed();
    }

    /**
     * Converts this Jackson-friendly adapted pet object into the model's {@code Pet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pet.
     */
    public Pet toModelType() throws IllegalValueException {
        if (!Name.isValidName(petName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Pet(new Name(petName), species, breed);
    }
}
