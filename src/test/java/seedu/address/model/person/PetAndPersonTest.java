package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPets.DOGGY;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PetAndPersonTest {

    @Test
    public void equals() {
        PetAndPerson pair = new PetAndPerson(SNOOPY, ALICE, 1, 1);

        // same pet and person name -> returns true
        PetAndPerson similarPair = new PetAndPerson(SNOOPY, ALICE, 1, 1);
        assertTrue(pair.equals(similarPair));

        // same object -> returns true
        assertTrue(pair.equals(pair));

        // null -> returns false
        assertFalse(pair.equals(null));

        // null arguments -> returns false
        assertFalse(pair.equals(new PetAndPerson(null, ALICE, 1, 1)));
        assertFalse(pair.equals(new PetAndPerson(SNOOPY, null, 1, 1)));

        // different type -> returns false
        assertFalse(pair.equals(ALICE));

        // different name -> returns false
        assertFalse(pair.equals(new PetAndPerson(DOGGY, ALICE, 1, 1)));
        assertFalse(pair.equals(new PetAndPerson(SNOOPY, BOB, 1, 1)));

        // different pet index or person index -> returns false
        assertFalse(pair.equals(new PetAndPerson(SNOOPY, ALICE, 2, 1)));
        assertFalse(pair.equals(new PetAndPerson(SNOOPY, ALICE, 1, 2)));
    }

    @Disabled("This test is disabled")
    @Test
    public void hashCodeMethod() {
        PetAndPerson pair = new PetAndPerson(SNOOPY, ALICE, 1, 1);
        int expected = -1072518421;
        assertEquals(expected, pair.hashCode());
    }

    @Test
    public void toStringMethod() {
        PetAndPerson pair = new PetAndPerson(SNOOPY, ALICE, 1, 1);
        String expected = "[" + "[Snoopy], "
                + "seedu.address.model.person.Person{"
                + "name=Alice Pauline, phone=94351253, email=alice@example.com, "
                + "address=123, Jurong West Ave 6, #08-111, tags=[[friends]], pets=[]}" + "]";
        System.out.println(ALICE);
        assertEquals(expected, pair.toString());
    }
}
