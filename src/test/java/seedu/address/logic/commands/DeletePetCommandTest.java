package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PetBuilder;

public class DeletePetCommandTest {

    @Test
    public void constructor_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePetCommand(null, new Phone("99999999")));
    }

    @Test
    public void constructor_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePetCommand(new Pet(new Name("Dog"), "", ""), null));
    }

    @Test
    public void execute_petAcceptedByModel_deleteSuccessful() throws Exception {
        ModelStubAcceptingPetDeleted modelStub = new ModelStubAcceptingPetDeleted();
        Pet validPet = new PetBuilder().build();

        CommandResult commandResult = new DeletePetCommand(validPet, new Phone("99999999")).execute(modelStub);

        assertEquals(String.format(DeletePetCommand.MESSAGE_SUCCESS, Messages.format(validPet)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPet), modelStub.petsDeleted);
    }

    @Test
    public void equals() {
        Pet snoopy = new PetBuilder().withName("Snoopy").build();
        Pet doggy = new PetBuilder().withName("Doggy").build();
        DeletePetCommand deleteSnoopyCommand = new DeletePetCommand(snoopy, new Phone("99999999"));
        DeletePetCommand deleteDoggyCommand = new DeletePetCommand(doggy, new Phone("99999999"));

        // same object -> returns true
        assertTrue(deleteSnoopyCommand.equals(deleteSnoopyCommand));

        // same values -> returns true
        DeletePetCommand deleteSnoopyCommandCopy = new DeletePetCommand(snoopy, new Phone("99999999"));
        assertTrue(deleteSnoopyCommand.equals(deleteSnoopyCommandCopy));

        // different types -> returns false
        assertFalse(deleteSnoopyCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSnoopyCommand.equals(null));

        // different pet -> returns false
        assertFalse(deleteSnoopyCommand.equals(deleteDoggyCommand));
    }

    @Test
    public void toStringMethod() {
        Pet snoopy = new PetBuilder().withName("Snoopy").build();
        DeletePetCommand deletePetCommand = new DeletePetCommand(snoopy, new Phone("99999999"));
        String expected = DeletePetCommand.class.getCanonicalName() + "{pet=" + snoopy + "}";
        assertEquals(expected, deletePetCommand.toString());
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
            throw new AssertionError("This method should not be called.");
        }

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

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the pet being deleted.
     */
    private class ModelStubAcceptingPetDeleted extends ModelStub {
        final ArrayList<Pet> petsDeleted = new ArrayList<>();

        @Override
        public void removePet(Pet pet, Phone phone) {
            requireNonNull(pet);
            petsDeleted.add(pet);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
