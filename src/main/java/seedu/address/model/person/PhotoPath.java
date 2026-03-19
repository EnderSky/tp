package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;

/**
 * Represents a photo path for a Pet in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhotoPath(String)}
 */
public class PhotoPath {

    public static final String MESSAGE_CONSTRAINTS =
            "Photo path must be a valid file path to an existing image file.";

    public final String value;

    /**
     * Constructs a {@code PhotoPath}.
     *
     * @param path A valid file path to an image.
     */
    public PhotoPath(String path) {
        requireNonNull(path);
        checkArgument(isValidPhotoPath(path), MESSAGE_CONSTRAINTS);
        value = path.trim();
    }

    /**
     * Private constructor for creating PhotoPath from storage without file existence check.
     */
    private PhotoPath(String path, boolean skipValidation) {
        requireNonNull(path);
        value = path.trim();
    }

    /**
     * Creates a PhotoPath from storage without checking if the file exists.
     * Used when loading from JSON where the file may have been moved/deleted.
     *
     * @param path The stored file path.
     * @return A PhotoPath object, or null if path is invalid format.
     */
    public static PhotoPath fromStorage(String path) {
        if (!isValidPhotoPathFormat(path)) {
            return null;
        }
        return new PhotoPath(path, true);
    }

    /**
     * Returns true if a given string is a valid photo path.
     * The path must not be blank and the file must exist.
     */
    public static boolean isValidPhotoPath(String test) {
        if (test == null || test.trim().isEmpty()) {
            return false;
        }
        File file = new File(test.trim());
        return file.exists() && file.isFile();
    }

    /**
     * Returns true if a given string is a syntactically valid photo path format
     * (does not check if file exists). Used for storage validation.
     */
    public static boolean isValidPhotoPathFormat(String test) {
        return test != null && !test.trim().isEmpty();
    }

    /**
     * Returns true if the file at this path currently exists.
     */
    public boolean fileExists() {
        File file = new File(value);
        return file.exists() && file.isFile();
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

        if (!(other instanceof PhotoPath)) {
            return false;
        }

        PhotoPath otherPath = (PhotoPath) other;
        return value.equals(otherPath.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
