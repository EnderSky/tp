package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents grooming notes for a Pet in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroomingNotes(String)}
 */
public class GroomingNotes {

    public static final String MESSAGE_CONSTRAINTS =
            "Grooming notes can contain any text, but cannot be empty or consist only of whitespace.";

    /*
     * Grooming notes must not be blank (empty or only whitespace).
     */
    public static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String value;

    /**
     * Constructs a {@code GroomingNotes}.
     *
     * @param notes A valid grooming notes string.
     */
    public GroomingNotes(String notes) {
        requireNonNull(notes);
        checkArgument(isValidGroomingNotes(notes), MESSAGE_CONSTRAINTS);
        value = notes.trim();
    }

    /**
     * Returns true if a given string is a valid grooming notes.
     */
    public static boolean isValidGroomingNotes(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroomingNotes)) {
            return false;
        }

        GroomingNotes otherNotes = (GroomingNotes) other;
        return value.equals(otherNotes.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
