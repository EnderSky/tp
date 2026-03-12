package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Pet;

/**
 * Jackson-friendly version of {@link Pet}.
 */
class JsonAdaptedPet {

    private final String petName;

    /**
     * Constructs a {@code JsonAdaptedPet} with the given {@code petName}.
     */
    @JsonCreator
    public JsonAdaptedPet(String petName) {
        this.petName = petName;
    }

    /**
     * Converts a given {@code Pet} into this class for Jackson use.
     */
    public JsonAdaptedPet(Pet source) {
        petName = source.getName();
    }

    /**
     * Converts this Jackson-friendly adapted pet object into the model's {@code Pet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pet.
     */
    public Pet toModelType() throws IllegalValueException {
        if (!Pet.isValidPetName(petName)) {
            throw new IllegalValueException(Pet.MESSAGE_CONSTRAINTS);
        }
        return new Pet(petName);
    }

}
