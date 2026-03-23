package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPets.DOGGY;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PetBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void petConstructor() {
        Person person = new PersonBuilder().build();
        Pet pet = new PetBuilder().build();
        Person newPerson = new Person(person, SampleDataUtil.getPetSet(pet));

        // same phone number -> returns true
        assertTrue(person.isSamePerson(newPerson));

        // different pet list -> returns false
        assertFalse(person.equals(newPerson));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same phone, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different phone, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // phone has trailing spaces, all other attributes same -> returns false
        String phoneWithTrailingSpaces = VALID_PHONE_BOB + " ";
        Person editedBob = new PersonBuilder(BOB).withPhone(phoneWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void addPet() {
        Person editedAlice = ALICE.addPet(SNOOPY).addPet(DOGGY);
        Set<Pet> expectedPets = new HashSet<Pet>();
        expectedPets.add(SNOOPY);
        expectedPets.add(DOGGY);
        assertEquals(expectedPets, editedAlice.getPets());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        //different pets ->returns false
        editedAlice = new PersonBuilder(editedAlice).withPets(SNOOPY).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", tags=" + ALICE.getTags() + ", pets=" + ALICE.getPets() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCodeMethod() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.hashCode() == aliceCopy.hashCode());

        // different person -> returns false
        assertFalse(ALICE.hashCode() == BOB.hashCode());

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.hashCode() == editedAlice.hashCode());

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.hashCode() == editedAlice.hashCode());

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.hashCode() == editedAlice.hashCode());

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.hashCode() == editedAlice.hashCode());

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.hashCode() == editedAlice.hashCode());

        //different pets ->returns false
        editedAlice = new PersonBuilder(editedAlice).withPets(DOGGY).build();
        assertFalse(ALICE.hashCode() == editedAlice.hashCode());
    }

    @Test
    public void removePet_existingPet_removesPet() {
        Person editedAlice = new PersonBuilder(ALICE).withPets(SNOOPY).build();
        Person aliceWithNoPet = editedAlice.removePet(SNOOPY);
        Person expectedPerson = new PersonBuilder(ALICE).build(); // without pets
        assertTrue(aliceWithNoPet.equals(expectedPerson));
    }

    @Test
    public void removePet_nonExistingPet_noChange() {
        Person personWithPet = new PersonBuilder(ALICE).withPets(SNOOPY).build();
        Person aliceWithRemovedPet = personWithPet.removePet(DOGGY);
        Person expectedPerson = new PersonBuilder(ALICE).withPets(SNOOPY).build();
        assertTrue(aliceWithRemovedPet.equals(expectedPerson));
    }
}
