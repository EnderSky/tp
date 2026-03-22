package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPet.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;


public class JsonAdaptedPetTest {
    private static final String INVALID_NAME = "  ";
    private static final String INVALID_SPECIES = "  ";
    private static final String INVALID_BREED = "  ";
    private static final String INVALID_TAG = "  ";

    private static final String VALID_PET_NAME = SNOOPY.getName().toString();
    private static final String VALID_PET_SPECIES = SNOOPY.getSpecies().toString();
    private static final String VALID_PET_BREED = SNOOPY.getBreed().toString();
    private static final String VALID_PET_NOTE = SNOOPY.getNote().toString();

    @Test
    public void toModelType_validPetDetails_returnsPet() throws Exception {
        JsonAdaptedPet pet = new JsonAdaptedPet(SNOOPY);
        assertEquals(SNOOPY, pet.toModelType());
    }

    @Test
    public void toModelType_invalidPetName_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(INVALID_NAME, VALID_PET_SPECIES,
                VALID_PET_BREED, VALID_PET_NOTE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullPetName_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(null, VALID_PET_SPECIES, VALID_PET_BREED, VALID_PET_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidPetSpecies_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, INVALID_SPECIES, VALID_PET_BREED, VALID_PET_NOTE);
        String expectedMessage = String.format(Name.MESSAGE_CONSTRAINTS);
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullPetSpecies_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, null, VALID_PET_BREED, VALID_PET_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Species");
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidPetBreed_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, INVALID_BREED, VALID_PET_NOTE);
        String expectedMessage = String.format(Name.MESSAGE_CONSTRAINTS);
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullPetBreed_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, null, VALID_PET_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Breed");
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidPetNote_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, VALID_PET_BREED, INVALID_TAG);
        String expectedMessage = String.format(Name.MESSAGE_CONSTRAINTS);
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullPetNote_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, VALID_PET_BREED, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Note");
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }
}


