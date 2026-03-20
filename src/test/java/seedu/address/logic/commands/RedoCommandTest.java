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

public class RedoCommandTest {

    private Model model;
    private RedoCommand redoCommand;
    private UndoCommand undoCommand;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new VersionedAddressBook(new AddressBook()), new UserPrefs());
        redoCommand = new RedoCommand();
        undoCommand = new UndoCommand();
    }

    @Test
    public void execute_noRedoHistory_throwsCommandException() {
        assertThrows(CommandException.class,
                RedoCommand.MESSAGE_FAILURE, () -> redoCommand.execute(model));
    }

    @Test
    public void execute_noRedoHistoryAfterCommit_throwsCommandException() throws Exception {
        // Even after making changes and committing, redo should not be available without undo
        model.addPerson(ALICE);
        model.commitAddressBook();

        assertThrows(CommandException.class,
                RedoCommand.MESSAGE_FAILURE, () -> redoCommand.execute(model));
    }

    @Test
    public void execute_withRedoHistoryAfterUndo_success() throws Exception {
        // Add person and commit
        model.addPerson(ALICE);
        model.commitAddressBook();
        AddressBook stateWithAlice = new AddressBook(model.getAddressBook());

        // Undo to create redo history
        undoCommand.execute(model);

        // Now redo should work and restore the state with ALICE
        CommandResult result = redoCommand.execute(model);

        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(stateWithAlice, model.getAddressBook());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
    }

    @Test
    public void execute_multipleRedos_success() throws Exception {
        // Create multiple states
        AddressBook emptyState = new AddressBook(model.getAddressBook());

        model.addPerson(ALICE);
        model.commitAddressBook();
        AddressBook stateWithAlice = new AddressBook(model.getAddressBook());

        model.addPerson(BENSON);
        model.commitAddressBook();
        AddressBook stateWithBoth = new AddressBook(model.getAddressBook());

        // Undo twice to go back to empty state
        undoCommand.execute(model);
        undoCommand.execute(model);
        assertEquals(emptyState, model.getAddressBook());

        // First redo should restore state with ALICE
        CommandResult result1 = redoCommand.execute(model);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result1.getFeedbackToUser());
        assertEquals(stateWithAlice, model.getAddressBook());

        // Second redo should restore state with both persons
        CommandResult result2 = redoCommand.execute(model);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result2.getFeedbackToUser());
        assertEquals(stateWithBoth, model.getAddressBook());
    }

    @Test
    public void execute_redoAfterUndo_disablesRedoWhenAtLatestState() throws Exception {
        model.addPerson(ALICE);
        model.commitAddressBook();

        // Undo and then redo
        undoCommand.execute(model);
        assertTrue(model.canRedoAddressBook());

        redoCommand.execute(model);

        // After redo, should be back at latest state with no more redo available
        assertFalse(model.canRedoAddressBook());
    }

    @Test
    public void execute_commitAfterUndo_clearsRedoHistory() throws Exception {
        model.addPerson(ALICE);
        model.commitAddressBook();

        model.addPerson(BENSON);
        model.commitAddressBook();

        // Undo once
        undoCommand.execute(model);
        assertTrue(model.canRedoAddressBook());

        // Make a new change and commit (should clear redo history)
        model.deletePerson(ALICE);
        model.commitAddressBook();

        // Redo should no longer be available
        assertFalse(model.canRedoAddressBook());
        assertThrows(CommandException.class,
                RedoCommand.MESSAGE_FAILURE, () -> redoCommand.execute(model));
    }

    @Test
    public void equals() {
        RedoCommand redoCommand1 = new RedoCommand();
        RedoCommand redoCommand2 = new RedoCommand();

        // Same object
        assertTrue(redoCommand1.equals(redoCommand1));

        // Different objects of same type
        assertTrue(redoCommand1.equals(redoCommand2));

        // Different type
        assertFalse(redoCommand1.equals(new UndoCommand()));

        // Null
        assertFalse(redoCommand1.equals(null));
    }

    @Test
    public void toString_success() {
        RedoCommand redoCommand = new RedoCommand();
        String expected = RedoCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, redoCommand.toString());
    }

    @Test
    public void canRedo_updatedCorrectly() throws Exception {
        // Initially cannot redo
        assertFalse(model.canRedoAddressBook());

        // After adding and committing, still cannot redo
        model.addPerson(ALICE);
        model.commitAddressBook();
        assertFalse(model.canRedoAddressBook());

        // After undo, should be able to redo
        undoCommand.execute(model);
        assertTrue(model.canRedoAddressBook());

        // After redo, should not be able to redo further
        redoCommand.execute(model);
        assertFalse(model.canRedoAddressBook());
    }

    @Test
    public void undoRedoSequence_maintainsConsistency() throws Exception {
        // Build up states
        AddressBook emptyState = new AddressBook(model.getAddressBook());

        model.addPerson(ALICE);
        model.commitAddressBook();
        AddressBook stateWithAlice = new AddressBook(model.getAddressBook());

        model.addPerson(BENSON);
        model.commitAddressBook();
        AddressBook stateWithBoth = new AddressBook(model.getAddressBook());

        // Test undo-redo sequence
        undoCommand.execute(model); // Back to state with ALICE
        assertEquals(stateWithAlice, model.getAddressBook());

        redoCommand.execute(model); // Forward to state with both
        assertEquals(stateWithBoth, model.getAddressBook());

        undoCommand.execute(model); // Back to state with ALICE
        assertEquals(stateWithAlice, model.getAddressBook());

        undoCommand.execute(model); // Back to empty state
        assertEquals(emptyState, model.getAddressBook());

        redoCommand.execute(model); // Forward to state with ALICE
        assertEquals(stateWithAlice, model.getAddressBook());

        redoCommand.execute(model); // Forward to state with both
        assertEquals(stateWithBoth, model.getAddressBook());
    }
}
