package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PetBuilder;

public class AddPetCommandTest {

    @Test
    public void constructor_nullAll_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPetCommand(null, null));
    }

    @Test
    public void constructor_nullPet_throwsNullPointerException() {
        Phone validPhone = new Phone("98765432");
        assertThrows(NullPointerException.class, () -> new AddPetCommand(null, validPhone));
    }

    @Test
    public void constructor_nullPhone_throwsNullPointerException() {
        Pet validPet = new PetBuilder().build();
        assertThrows(NullPointerException.class, () -> new AddPetCommand(validPet, null));
    }

    @Test
    public void execute_petAcceptedByModel_addSuccessful() throws Exception {
        Person validPerson = new PersonBuilder().build();
        ModelStubAcceptingPetAdded modelStub = new ModelStubAcceptingPetAdded(validPerson);
        Pet validPet = new PetBuilder().build();

        CommandResult commandResult = new AddPetCommand(validPet, validPerson.getPhone()).execute(modelStub);

        assertEquals(String.format(AddPetCommand.MESSAGE_SUCCESS, Messages.format(validPet)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPet), modelStub.petsAdded);
    }

    @Test
    public void equals() {
        Pet snoopy = new PetBuilder().withName("Snoopy").build();
        Pet doggy = new PetBuilder().withName("Doggy").build();
        Phone phone = new Phone("98765432");
        AddPetCommand addSnoopyCommand = new AddPetCommand(snoopy, phone);
        AddPetCommand addDoggyCommand = new AddPetCommand(doggy, phone);

        // same object -> returns true
        assertTrue(addSnoopyCommand.equals(addSnoopyCommand));

        // same values -> returns true
        AddPetCommand addSnoopyCommandCopy = new AddPetCommand(snoopy, phone);
        assertTrue(addSnoopyCommand.equals(addSnoopyCommandCopy));

        // different types -> returns false
        assertFalse(addSnoopyCommand.equals(1));

        // null -> returns false
        assertFalse(addSnoopyCommand.equals(null));

        // different person -> returns false
        assertFalse(addSnoopyCommand.equals(addDoggyCommand));
    }

    @Test
    public void toStringMethod() {
        Phone phone = new Phone("98765432");
        AddPetCommand addPetCommand = new AddPetCommand(SNOOPY, phone);
        String expected = AddPetCommand.class.getCanonicalName() + "{addPet=" + SNOOPY + "}";
        assertEquals(expected, addPetCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPet(Pet pet, Phone phone) {
            throw new AssertionError("This method should not be called."); }

        @Override
        public void removePet(Pet pet, Phone phone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPhone(Phone phone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPet(Phone phone, Pet pet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        protected Person getPerson() {
            return this.person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the pet being added.
     */
    private class ModelStubAcceptingPetAdded extends ModelStubWithPerson {
        final ArrayList<Pet> petsAdded = new ArrayList<>();

        ModelStubAcceptingPetAdded(Person person) {
            super(person);
        }

        @Override
        public boolean hasPhone(Phone phone) {
            requireNonNull(phone);
            return this.getPerson().getPhone().equals(phone);
        }

        @Override
        public void addPet(Pet pet, Phone phone) {
            requireNonNull(pet);
            petsAdded.add(pet);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
