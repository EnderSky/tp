package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PetBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePetCommand}.
 */
public class DeletePetCommandTest {

    private static final String FIRST_PERSON_PHONE = "94351253";
    private static final String SECOND_PERSON_PHONE = "98765432";

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Pet petToDelete = new PetBuilder().build();
        model.addPet(petToDelete, new Phone(FIRST_PERSON_PHONE));

        DeletePetCommand deletePetCommand = new DeletePetCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeletePetCommand.MESSAGE_SUCCESS,
                Messages.format(petToDelete));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(deletePetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(100000);
        DeletePetCommand deletePetCommand = new DeletePetCommand(outOfBoundIndex);

        assertCommandFailure(deletePetCommand, model, DeletePetCommand.MESSAGE_INDEX_TOO_LARGE);
    }

    @Test
    public void execute_filteredList_success() {
        // filter to first person, delete first pet from the filtered list
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Pet distraction = new PetBuilder().withName("distraction").build();
        Pet target = new PetBuilder().withName("Buddy")
                .withSpecies("Cat").withBreed("Persian").withNote("Calm").build();
        model.addPet(distraction, new Phone(FIRST_PERSON_PHONE));
        model.addPet(target, new Phone(FIRST_PERSON_PHONE));

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        DeletePetCommand deletePetCommand = new DeletePetCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeletePetCommand.MESSAGE_SUCCESS,
                Messages.format(distraction));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPet(target, new Phone(FIRST_PERSON_PHONE));
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(deletePetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeletePetCommand deleteFirstCommand = new DeletePetCommand(INDEX_FIRST_PERSON);
        DeletePetCommand deleteSecondCommand = new DeletePetCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePetCommand deleteFirstCommandCopy = new DeletePetCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeletePetCommand deletePetCommand = new DeletePetCommand(index);
        String expected = DeletePetCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, deletePetCommand.toString());
    }
}
