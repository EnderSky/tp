package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a photo path for a Pet in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidPhotoPath(String)}
 */
public class PhotoPath {

    public static final String MESSAGE_CONSTRAINTS = "Photo path must be a valid file path to an existing image file.";

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
     * Returns true if a given string is a valid photo path.
     * The path must not be blank and the file must exist.
     */
    public static boolean isValidPhotoPath(String test) {
        if (test == null || test.trim().isEmpty()) {
            return false;
        }

        String trimmedPath = test.trim();

        // Check if given path is a classpath
        if (trimmedPath.startsWith("/")) {
            // If file has no extension, it's likely a directory
            if (!trimmedPath.contains(".")) {
                return false;
            }
            InputStream stream = seedu.address.MainApp.class.getResourceAsStream(trimmedPath);
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // Ignore
                }
                return true;
            }
            return false;
        }

        // Check if given path is a file system path
        File file = new File(trimmedPath);
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
