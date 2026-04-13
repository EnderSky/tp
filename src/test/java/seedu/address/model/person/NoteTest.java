package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidNote_throwsIllegalArgumentException() {
        String invalidNote = "  ";
        assertThrows(IllegalArgumentException.class, () -> new Note(invalidNote));
    }

    @Test
    public void isValidNote() {
        // null name
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        // invalid name
        assertFalse(Note.isValidNote("  ")); // spaces only
        assertFalse(Note.isValidNote(" ^")); // starts with space

        // valid name
        assertTrue(Note.isValidNote("")); // empty string
        assertTrue(Note.isValidNote("This is a note."));
        assertTrue(Note.isValidNote("This is a very long note that should still be considered valid.")); // long name
    }

    @Test
    public void equals() {
        Note note = new Note("Valid Note");
        // same values -> returns true
        assertTrue(note.equals(new Note("Valid Note")));

        // same object -> returns true
        assertTrue(note.equals(note));

        // null -> returns false
        assertFalse(note.equals(null));

        // different types -> returns false
        assertFalse(note.equals(5.0f));

        // different values -> returns false
        assertFalse(note.equals(new Note("Other Valid Note")));
    }
}
