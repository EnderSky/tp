package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PetTest {

    @Test
    public void equals() {
        Pet pet = new Pet(new Name("Barkus"), "", "");

        // same name -> returns true
        Pet similarPet = new Pet(new Name("Barkus"), "Dog", "Bulldog");
        assertTrue(pet.equals(similarPet));

        // same object -> returns true
        assertTrue(pet.equals(pet));

        // null -> returns false
        assertFalse(pet.equals(null));

        // different type -> returns false
        assertFalse(pet.equals(5));

        // different name -> returns false
        Pet differentPet = new Pet(new Name("Meowy"), "Cat", "Siamese");
        assertFalse(pet.equals(differentPet));
    }

    @Test
    public void toStringMethod() {
        Pet pet = new Pet(new Name("Barkus"), "", "");
        String expected = "[" + "Barkus" + "]";
        assertEquals(expected, pet.toString());
    }
}
