package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PET;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePetCommand}.
 */
public class DeletePetCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndices_success() {
        Person personWithPets = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());


        // Ensure the person has at least one pet for testing
        if (personWithPets.getPets().isEmpty()) {
            // Skip test if no pets available
            return;
        }

        List<Pet> pets = new ArrayList<>(personWithPets.getPets());
        Pet petToDelete = pets.get(INDEX_FIRST_PET.getZeroBased());

        DeletePetCommand deletePetCommand = new DeletePetCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PET);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_DELETE_PET_SUCCESS,
                Messages.format(petToDelete), personWithPets.getName().fullName);

        // Create expected person without the deleted pet
        Set<Pet> updatedPets = new HashSet<>(personWithPets.getPets());
        updatedPets.remove(petToDelete);
        Person expectedPerson = new Person(personWithPets, updatedPets);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithPets, expectedPerson);

        assertCommandSuccess(deletePetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePetCommand deletePetCommand = new DeletePetCommand(outOfBoundIndex, INDEX_FIRST_PET);

        assertCommandFailure(deletePetCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPetIndex_throwsCommandException() {
        Person personWithPets = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());


        if (personWithPets.getPets().isEmpty()) {
            // Skip test if no pets available
            return;
        }

        Index outOfBoundPetIndex = Index.fromOneBased(personWithPets.getPets().size() + 1);
        DeletePetCommand deletePetCommand = new DeletePetCommand(INDEX_FIRST_PERSON, outOfBoundPetIndex);

        assertCommandFailure(deletePetCommand, model, DeletePetCommand.MESSAGE_INVALID_PET_INDEX);
    }

    @Test
    public void equals() {
        DeletePetCommand deleteFirstCommand = new DeletePetCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PET);
        DeletePetCommand deleteSecondCommand = new DeletePetCommand(INDEX_SECOND_PERSON, INDEX_SECOND_PET);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePetCommand deleteFirstCommandCopy = new DeletePetCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PET);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different indices -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        DeletePetCommand deletePetCommand = new DeletePetCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PET);
        String expected = DeletePetCommand.class.getCanonicalName()
                + "{clientIndex=" + INDEX_FIRST_PERSON
                + ", petIndex=" + INDEX_FIRST_PET + "}";
        assertEquals(expected, deletePetCommand.toString());
    }
}
