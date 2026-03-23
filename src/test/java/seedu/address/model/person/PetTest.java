package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPets.BARKUS;
import static seedu.address.testutil.TypicalPets.DOGGY;
import static seedu.address.testutil.TypicalPets.MEOWY;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PetBuilder;

public class PetTest {

    @Test
    public void equals() {
        Pet pet = new Pet(new Name("Barkus"), "Dog", "Bulldog");

        // same name -> returns true
        Pet similarPet = new Pet(new Name("Barkus"), "", "");
        assertTrue(pet.equals(similarPet));

        // same object -> returns true
        assertTrue(SNOOPY.equals(SNOOPY));

        // null -> returns false
        assertFalse(SNOOPY.equals(null));

        // different type -> returns false
        assertFalse(SNOOPY.equals(5));

        // different name -> returns false
        Pet differentNamePet = new Pet(new Name("Woofy"), "Dog", "Bulldog");
        assertFalse(pet.equals(differentNamePet));
    }

    @Test
    public void toStringMethod() {
        String expected = "[" + "Snoopy" + "]";
        assertEquals(expected, SNOOPY.toString());
    }
}
