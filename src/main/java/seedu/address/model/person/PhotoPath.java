package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a photo path for a Pet in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidPhotoPath(String)}
 */
public class PhotoPath {

    public static final String MESSAGE_CONSTRAINTS = "Photo must be a valid file path "
            + "to an existing image file stored within data/photos/ and cannot be empty "
            + "(Example: doggy.png). "
            + "Accepted file extensions: .jpg, .jpeg, .jfif, .png, .gif, .bmp";

    private static final Path DATA_PHOTOS_DIR = Paths.get("data", "photos");

    public final String value;

    /**
     * Constructs a {@code PhotoPath}.
     *
     * @param path A valid file path to an image.
     */
    public PhotoPath(String path) {
        requireNonNull(path);
        checkArgument(isValidPhotoPath(path), MESSAGE_CONSTRAINTS);
        value = path;
    }

    /**
     * Returns true if a given string is a valid photo path.
     * The path must not be blank and the file must exist.
     */
    public static boolean isValidPhotoPath(String test) {
        if (test == null || test.isBlank()) {
            return false;
        }

        // Only accept the following file extensions: .jpg, .jpeg, .png, .gif, .bmp, .jfif
        int dotIndex = test.lastIndexOf(".");
        if (dotIndex == -1) {
            return false;
        }
        String extension = test.substring(dotIndex).toLowerCase();
        if (!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".png")
                && !extension.equals(".gif") && !extension.equals(".bmp") && !extension.equals(".jfif")) {
            return false;
        }

        // Attempt classpath resource loading
        InputStream stream = seedu.address.MainApp.class.getResourceAsStream(test);
        if (stream != null) {
            return true;
        }

        // Then, try as a filesystem path using java.nio.file.Path
        try {
            Path path = Paths.get(test);
            path = DATA_PHOTOS_DIR.resolve(path).normalize();
            if (!path.startsWith(DATA_PHOTOS_DIR)) {
                return false;
            }
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (InvalidPathException e) {
            return false;
        }
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
