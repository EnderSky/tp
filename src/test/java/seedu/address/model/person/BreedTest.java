package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BreedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Breed(null));
    }

    @Test
    public void constructor_invalidBreed_throwsIllegalArgumentException() {
        String invalidBreed = "  ";
        assertThrows(IllegalArgumentException.class, () -> new Breed(invalidBreed));
    }

    @Test
    public void isValidBreed() {
        // null name
        assertThrows(NullPointerException.class, () -> Breed.isValidBreed(null));

        // invalid name
        assertFalse(Breed.isValidBreed("  ")); // spaces only
        assertFalse(Breed.isValidBreed(" ^")); // starts with space

        // valid name
        assertTrue(Breed.isValidBreed("")); // empty string
        assertTrue(Breed.isValidBreed("Xoloitzcuintli"));
        assertTrue(Breed.isValidBreed("Lagotto Romagnolo")); // long name
    }

    @Test
    public void equals() {
        Breed breed = new Breed("Valid Breed");

        // same values -> returns true
        assertTrue(breed.equals(new Breed("Valid Breed")));

        // same object -> returns true
        assertTrue(breed.equals(breed));

        // null -> returns false
        assertFalse(breed.equals(null));

        // different types -> returns false
        assertFalse(breed.equals(5.0f));

        // different values -> returns false
        assertFalse(breed.equals(new Breed("Other Valid Breed")));
    }

}
