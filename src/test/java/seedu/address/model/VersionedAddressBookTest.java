package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.AddressBookBuilder;

public class VersionedAddressBookTest {

    private VersionedAddressBook versionedAddressBook;

    @BeforeEach
    public void setUp() {
        versionedAddressBook = new VersionedAddressBook(new AddressBook());
    }

    @Test
    public void constructor() {
        assertEquals(new AddressBook(), new AddressBook(versionedAddressBook));
        assertFalse(versionedAddressBook.canUndo());
        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void constructor_withAddressBook() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        VersionedAddressBook versioned = new VersionedAddressBook(addressBook);
        assertEquals(addressBook, new AddressBook(versioned));
        assertFalse(versioned.canUndo());
        assertFalse(versioned.canRedo());
    }

    @Test
    public void commit_singleCommit_canUndo() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        assertTrue(versionedAddressBook.canUndo());
        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void commit_multipleCommits_canUndo() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(BENSON);
        versionedAddressBook.commit();

        assertTrue(versionedAddressBook.canUndo());
        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void undo_noCommits_throwsException() {
        assertThrows(RuntimeException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void undo_singleCommit_success() {
        // Initial state: empty
        AddressBook initialState = new AddressBook(versionedAddressBook);

        // Add person and commit
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();

        // Undo should restore to initial state
        versionedAddressBook.undo();
        assertEquals(initialState, new AddressBook(versionedAddressBook));
        assertFalse(versionedAddressBook.canUndo());
        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void undo_multipleCommits_success() {
        // State 0: empty
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit(); // State 1: has ALICE

        AddressBook stateWithAlice = new AddressBook(versionedAddressBook);

        versionedAddressBook.addPerson(BENSON);
        versionedAddressBook.commit(); // State 2: has ALICE and BENSON

        // Undo should go back to state with just ALICE
        versionedAddressBook.undo();
        assertEquals(stateWithAlice, new AddressBook(versionedAddressBook));
        assertTrue(versionedAddressBook.canUndo());
        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void redo_noUndo_throwsException() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        assertThrows(RuntimeException.class, () -> versionedAddressBook.redo());
    }

    @Test
    public void redo_afterUndo_success() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();

        AddressBook stateWithAlice = new AddressBook(versionedAddressBook);

        versionedAddressBook.undo();
        versionedAddressBook.redo();

        assertEquals(stateWithAlice, new AddressBook(versionedAddressBook));
        assertTrue(versionedAddressBook.canUndo());
        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void commitAfterUndo_redoHistoryCleared() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit(); // State 1

        versionedAddressBook.addPerson(BENSON);
        versionedAddressBook.commit(); // State 2

        versionedAddressBook.undo(); // Back to State 1
        assertTrue(versionedAddressBook.canRedo());

        // Make a new change and commit (should clear redo history)
        versionedAddressBook.addPerson(CARL);
        versionedAddressBook.commit(); // New State 2

        assertFalse(versionedAddressBook.canRedo());
        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void multipleUndoRedo_consistency() {
        // Build up history
        AddressBook state0 = new AddressBook(versionedAddressBook); // Empty

        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        AddressBook state1 = new AddressBook(versionedAddressBook); // Has ALICE

        versionedAddressBook.addPerson(BENSON);
        versionedAddressBook.commit();
        AddressBook state2 = new AddressBook(versionedAddressBook); // Has ALICE, BENSON

        versionedAddressBook.addPerson(CARL);
        versionedAddressBook.commit();
        AddressBook state3 = new AddressBook(versionedAddressBook); // Has ALICE, BENSON, CARL

        // Test undo sequence
        versionedAddressBook.undo(); // Should be at state2
        assertEquals(state2, new AddressBook(versionedAddressBook));

        versionedAddressBook.undo(); // Should be at state1
        assertEquals(state1, new AddressBook(versionedAddressBook));

        versionedAddressBook.undo(); // Should be at state0
        assertEquals(state0, new AddressBook(versionedAddressBook));

        // Test redo sequence
        versionedAddressBook.redo(); // Should be at state1
        assertEquals(state1, new AddressBook(versionedAddressBook));

        versionedAddressBook.redo(); // Should be at state2
        assertEquals(state2, new AddressBook(versionedAddressBook));

        versionedAddressBook.redo(); // Should be at state3
        assertEquals(state3, new AddressBook(versionedAddressBook));
    }

    @Test
    public void canUndo_initialState_false() {
        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_initialState_false() {
        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void canUndo_afterCommit_true() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_afterUndo_true() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.undo();
        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void equals() {
        VersionedAddressBook other = new VersionedAddressBook(new AddressBook());

        // Both empty - should be equal
        assertTrue(versionedAddressBook.equals(other));

        // Add same person to both
        versionedAddressBook.addPerson(ALICE);
        other.addPerson(ALICE);
        assertTrue(versionedAddressBook.equals(other));

        // Different persons - should not be equal
        versionedAddressBook.addPerson(BENSON);
        assertFalse(versionedAddressBook.equals(other));
    }
}
