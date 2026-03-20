package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An AddressBook that maintains a history of states for undo/redo functionality.
 * Extends {@code AddressBook} with an undo/redo history, stored internally as an
 * {@code addressBookStateList} and {@code currentStatePointer}.
 */
public class VersionedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    /**
     * Creates a VersionedAddressBook with the initial state.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current address book state to the history.
     * Undone states are removed (purged) when a new state is committed.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    /**
     * Removes all states after the current pointer.
     * This is called before committing a new state, since after a new commit,
     * previously undone states should no longer be accessible via redo.
     */
    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Restores the address book to the previous state in history.
     *
     * @throws NoUndoableStateException if there is no previous state to restore.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to a previously undone state in history.
     *
     * @throws NoRedoableStateException if there is no undone state to restore.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if there is a previous state to undo to.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if there is a state that was undone that can be redone.
     */
    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherVersionedAddressBook = (VersionedAddressBook) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && addressBookStateList.equals(otherVersionedAddressBook.addressBookStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
    }

    /**
     * Thrown when trying to undo but there is no previous state.
     */
    public static class NoUndoableStateException extends RuntimeException {
        public NoUndoableStateException() {
            super("Current state pointer is at the start of the state list. Unable to undo.");
        }
    }

    /**
     * Thrown when trying to redo but there is no undone state.
     */
    public static class NoRedoableStateException extends RuntimeException {
        public NoRedoableStateException() {
            super("Current state pointer is at the end of the state list. Unable to redo.");
        }
    }
}
