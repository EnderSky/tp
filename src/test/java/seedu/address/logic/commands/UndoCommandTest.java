package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedAddressBook;

public class UndoCommandTest {

    private Model model;
    private UndoCommand undoCommand;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new VersionedAddressBook(new AddressBook()), new UserPrefs());
        undoCommand = new UndoCommand();
    }

    @Test
    public void execute_noUndoHistory_throwsCommandException() {
        assertThrows(CommandException.class,
                UndoCommand.MESSAGE_FAILURE, () -> undoCommand.execute(model));
    }

    @Test
    public void execute_withUndoHistory_success() throws Exception {
        // Store initial state
        AddressBook initialState = new AddressBook(model.getAddressBook());

        // Add a person and commit to create undo history
        model.addPerson(ALICE);
        model.commitAddressBook();

        CommandResult result = undoCommand.execute(model);

        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(initialState, model.getAddressBook());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
    }

    @Test
    public void execute_multipleUndos_success() throws Exception {
        // Create multiple states
        AddressBook emptyState = new AddressBook(model.getAddressBook());

        model.addPerson(ALICE);
        model.commitAddressBook();
        AddressBook stateWithAlice = new AddressBook(model.getAddressBook());

        model.addPerson(BENSON);
        model.commitAddressBook();
        AddressBook stateWithBoth = new AddressBook(model.getAddressBook());

        // First undo should go back to state with just ALICE
        CommandResult result1 = undoCommand.execute(model);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result1.getFeedbackToUser());
        assertEquals(stateWithAlice, model.getAddressBook());

        // Second undo should go back to empty state
        CommandResult result2 = undoCommand.execute(model);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result2.getFeedbackToUser());
        assertEquals(emptyState, model.getAddressBook());
    }

    @Test
    public void execute_undoAfterCommit_enablesRedo() throws Exception {
        model.addPerson(ALICE);
        model.commitAddressBook();

        // Before undo, should not be able to redo
        assertFalse(model.canRedoAddressBook());

        undoCommand.execute(model);

        // After undo, should be able to redo
        assertTrue(model.canRedoAddressBook());
    }

    @Test
    public void equals() {
        UndoCommand undoCommand1 = new UndoCommand();
        UndoCommand undoCommand2 = new UndoCommand();

        // Same object
        assertTrue(undoCommand1.equals(undoCommand1));

        // Different objects of same type
        assertTrue(undoCommand1.equals(undoCommand2));

        // Different type
        assertFalse(undoCommand1.equals(new RedoCommand()));

        // Null
        assertFalse(undoCommand1.equals(null));
    }

    @Test
    public void toString_success() {
        UndoCommand undoCommand = new UndoCommand();
        String expected = UndoCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, undoCommand.toString());
    }

    @Test
    public void canUndo_updatedCorrectly() throws Exception {
        // Initially cannot undo
        assertFalse(model.canUndoAddressBook());

        // After adding and committing, should be able to undo
        model.addPerson(ALICE);
        model.commitAddressBook();
        assertTrue(model.canUndoAddressBook());

        // After undo, should not be able to undo further (if only one commit)
        undoCommand.execute(model);
        assertFalse(model.canUndoAddressBook());
    }
}
