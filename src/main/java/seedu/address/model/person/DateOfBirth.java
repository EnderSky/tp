package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Pet's date of birth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
            "Date of birth should be in the format YYYY-MM-DD and should not be in the future";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public final LocalDate value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dateOfBirth A valid date of birth string in YYYY-MM-DD format.
     */
    public DateOfBirth(String dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(isValidDateOfBirth(dateOfBirth), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(dateOfBirth.trim(), FORMATTER);
    }

    /**
     * Constructs a {@code DateOfBirth} from a LocalDate.
     *
     * @param dateOfBirth A valid date of birth.
     */
    public DateOfBirth(LocalDate dateOfBirth) {
        requireNonNull(dateOfBirth);
        checkArgument(!dateOfBirth.isAfter(LocalDate.now()), MESSAGE_CONSTRAINTS);
        value = dateOfBirth;
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        try {
            LocalDate date = LocalDate.parse(test.trim(), FORMATTER);
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the date formatted as a string.
     */
    public String toStorageString() {
        return value.format(FORMATTER);
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ofPattern("d MMM yyyy"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateOfBirth)) {
            return false;
        }

        DateOfBirth otherDob = (DateOfBirth) other;
        return value.equals(otherDob.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
