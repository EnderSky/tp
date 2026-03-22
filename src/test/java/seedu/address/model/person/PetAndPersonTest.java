package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

public class PetAndPersonTest {

    @Test
    public void equals() {
        Pet pet = new Pet(new Name("Barkus"), "Dog", "Labrador");
        PetAndPerson pair = new PetAndPerson(pet, ALICE, 1, 1);

        // same pet name -> returns true
        PetAndPerson similarPair = new PetAndPerson(new Pet(new Name("Barkus"), "Dog", "Labrador"), ALICE, 1, 1);
        assertTrue(pair.equals(similarPair));

        // same object -> returns true
        assertTrue(pair.equals(pair));

        // null -> returns false
        assertFalse(pair.equals(null));

        // null arguments -> returns false
        assertFalse(pair.equals(new PetAndPerson(null, ALICE, 1, 1)));
        assertFalse(pair.equals(new PetAndPerson(pet, null, 1, 1)));

        // different type -> returns false
        assertFalse(pair.equals(ALICE));

        // different name -> returns false
        Pet differentPet = new Pet(new Name("Meowy"), "Cat", "Siamese");
        assertFalse(pair.equals(new PetAndPerson(differentPet, ALICE, 1, 1)));
        assertFalse(pair.equals(new PetAndPerson(pet, BOB, 1, 1)));

        // different pet index or person index -> returns false
        assertFalse(pair.equals(new PetAndPerson(pet, ALICE, 2, 1)));
        assertFalse(pair.equals(new PetAndPerson(pet, ALICE, 1, 2)));
    }

    @Test
    public void hashCodeMethod() {
        Pet pet = new Pet(new Name("Barkus"), "Dog", "Labrador");
        PetAndPerson pair = new PetAndPerson(pet, ALICE, 1, 1);
        PetAndPerson sameValues = new PetAndPerson(
                new Pet(new Name("Barkus"), "Dog", "Labrador"),
                ALICE, 1, 1);
        assertEquals(sameValues.hashCode(), pair.hashCode());
    }

    @Test
    public void toStringMethod() {
        Pet pet = new Pet(new Name("Barkus"), "Dog", "Labrador");
        PetAndPerson pair = new PetAndPerson(pet, ALICE, 1, 1);
        String expected = "[" + "[Barkus], "
                + "seedu.address.model.person.Person{"
                + "name=Alice Pauline, phone=94351253, email=alice@example.com, "
                + "address=123, Jurong West Ave 6, #08-111, tags=[[friends]], pets=[]}" + "]";
        System.out.println(ALICE);
        assertEquals(expected, pair.toString());
    }
}
