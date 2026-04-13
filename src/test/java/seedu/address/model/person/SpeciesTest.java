package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SpeciesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Species(null));
    }

    @Test
    public void constructor_invalidSpecies_throwsIllegalArgumentException() {
        String invalidSpecies = "  ";
        assertThrows(IllegalArgumentException.class, () -> new Species(invalidSpecies));
    }

    @Test
    public void isValidSpecies() {
        // null name
        assertThrows(NullPointerException.class, () -> Species.isValidSpecies(null));

        // invalid name
        assertFalse(Species.isValidSpecies("  ")); // spaces only
        assertFalse(Species.isValidSpecies(" ^")); // starts with space

        // valid name
        assertTrue(Species.isValidSpecies("")); // empty string
        assertTrue(Species.isValidSpecies("Parastratiosphecomyia stratiosphecomyioides"));
        assertTrue(Species.isValidSpecies("Arcticalymene rotteni")); // long name
    }

    @Test
    public void equals() {
        Species species = new Species("Valid Species");

        // same values -> returns true
        assertTrue(species.equals(new Species("Valid Species")));

        // same object -> returns true
        assertTrue(species.equals(species));

        // null -> returns false
        assertFalse(species.equals(null));

        // different types -> returns false
        assertFalse(species.equals(5.0f));

        // different values -> returns false
        assertFalse(species.equals(new Species("Other Valid Species")));
    }
}
