package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_IMAGE_PATH;
import static seedu.address.storage.JsonAdaptedPet.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;
import seedu.address.model.person.PhotoPath;

public class JsonAdaptedPetTest {
    private static final String INVALID_NAME = "  ";
    private static final String INVALID_SPECIES = "  ";
    private static final String INVALID_BREED = "  ";
    private static final String INVALID_TAG = "  ";

    private static final String VALID_PET_NAME = SNOOPY.getName().toString();
    private static final String VALID_PET_SPECIES = SNOOPY.getSpecies().toString();
    private static final String VALID_PET_BREED = SNOOPY.getBreed().toString();
    private static final String VALID_PET_NOTE = SNOOPY.getNote().toString();
    private static final String VALID_PET_PHOTO_PATH = SNOOPY.getPhotoPath().toString();

    @Test
    public void toModelType_validPetDetails_returnsPet() throws Exception {
        JsonAdaptedPet pet = new JsonAdaptedPet(SNOOPY);
        assertEquals(SNOOPY, pet.toModelType());
    }

    @Test
    public void toModelType_invalidPetName_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(INVALID_NAME, VALID_PET_SPECIES,
                VALID_PET_BREED, VALID_PET_NOTE, VALID_PET_PHOTO_PATH);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullPetName_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(null, VALID_PET_SPECIES, VALID_PET_BREED, VALID_PET_NOTE,
                VALID_PET_PHOTO_PATH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidPetSpecies_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, INVALID_SPECIES, VALID_PET_BREED, VALID_PET_NOTE,
                VALID_PET_PHOTO_PATH);
        String expectedMessage = String.format(Name.MESSAGE_CONSTRAINTS);
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullPetSpecies_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, null, VALID_PET_BREED, VALID_PET_NOTE,
                VALID_PET_PHOTO_PATH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Species");
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidPetBreed_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, INVALID_BREED, VALID_PET_NOTE,
                VALID_PET_PHOTO_PATH);
        String expectedMessage = String.format(Name.MESSAGE_CONSTRAINTS);
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullPetBreed_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, null, VALID_PET_NOTE,
                VALID_PET_PHOTO_PATH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Breed");
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_invalidPetNote_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, VALID_PET_BREED, INVALID_TAG,
                VALID_PET_PHOTO_PATH);
        String expectedMessage = String.format(Name.MESSAGE_CONSTRAINTS);
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    @Test
    public void toModelType_nullPetNote_throwsIllegalValueException() {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, VALID_PET_BREED, null,
                VALID_PET_PHOTO_PATH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Note");
        assertThrows(IllegalValueException.class, expectedMessage, pet::toModelType);
    }

    // Invalid photo paths will be replaced with the placeholder image path, so no exception is thrown.
    @Test
    public void toModelType_invalidPetPhotoPath_returnsPetWithPlaceholderImage() throws Exception {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, VALID_PET_BREED, VALID_PET_NOTE,
                "invalid/photo/path.png");
        Pet expectedPet = new Pet(new Name(VALID_PET_NAME), new Name(VALID_PET_SPECIES), new Name(VALID_PET_BREED),
                new Name(VALID_PET_NOTE), new PhotoPath(PLACEHOLDER_IMAGE_PATH.toString()));
        assertEquals(expectedPet, pet.toModelType());
    }

    @Test
    public void toModelType_nullPetPhotoPath_returnsPetWithPlaceholderImage() throws Exception {
        JsonAdaptedPet pet = new JsonAdaptedPet(VALID_PET_NAME, VALID_PET_SPECIES, VALID_PET_BREED, VALID_PET_NOTE,
                null);
        Pet expectedPet = new Pet(new Name(VALID_PET_NAME), new Name(VALID_PET_SPECIES), new Name(VALID_PET_BREED),
                new Name(VALID_PET_NOTE), new PhotoPath(PLACEHOLDER_IMAGE_PATH.toString()));
        assertEquals(expectedPet, pet.toModelType());
    }
}
