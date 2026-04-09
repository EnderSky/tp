package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PhotoPathTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PhotoPath(null));
    }

    @Test
    public void constructor_invalidPhotoPath_throwsIllegalArgumentException() {
        String invalidPhotoPath = "";
        assertThrows(IllegalArgumentException.class, () -> new PhotoPath(invalidPhotoPath));
    }

    @Test
    public void isValidPhotoPath() {
        // null photo path
        assertFalse(PhotoPath.isValidPhotoPath(null));

        // invalid photo path
        assertFalse(PhotoPath.isValidPhotoPath("")); // empty string
        assertFalse(PhotoPath.isValidPhotoPath("  ")); // spaces only
        assertFalse(PhotoPath.isValidPhotoPath(" ^")); // starts with space

        // invalid file extension
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.txt"));
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.pdf"));
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.docx"));

        // non-existing file path
        assertFalse(PhotoPath.isValidPhotoPath("/images/non_existing_image.png"));

        // valid file path but does not exist
        assertFalse(PhotoPath.isValidPhotoPath("images/address_book_32.png"));

        // valid file path and exists but is not a file
        assertFalse(PhotoPath.isValidPhotoPath("/images.test/"));

        // valid file path and file exists but is not regular file (e.g. directory)
        assertFalse(PhotoPath.isValidPhotoPath("images.test/"));

        // test all accepted file extensions with images that don't exist
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.jpeg"));
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.png"));
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.gif"));
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.bmp"));
        assertFalse(PhotoPath.isValidPhotoPath("/images/photo.jfif"));

        // valid photo path
        assertTrue(PhotoPath.isValidPhotoPath("/images/placeholder-pet-logo.png"));
    }

    @Test
    public void equals() {
        PhotoPath photoPath = new PhotoPath("/images/placeholder-pet-logo.png");

        // same values -> returns true
        assertTrue(photoPath.equals(new PhotoPath("/images/placeholder-pet-logo.png")));

        // same object -> returns true
        assertTrue(photoPath.equals(photoPath));

        // null -> returns false
        assertFalse(photoPath.equals(null));

        // different types -> returns false
        assertFalse(photoPath.equals(5.0f));

        // different values -> returns false
        assertFalse(photoPath.equals(new PhotoPath("/images/clock.png")));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        PhotoPath photoPath1 = new PhotoPath("/images/placeholder-pet-logo.png");
        PhotoPath photoPath2 = new PhotoPath("/images/placeholder-pet-logo.png");

        assertTrue(photoPath1.hashCode() == photoPath2.hashCode());
    }

    @Test
    public void isValidPhotoPath_directoryPath_returnsFalse(@TempDir File tempDir) {
        // Test that a directory path returns false even if it exists
        assertFalse(PhotoPath.isValidPhotoPath(tempDir.getAbsolutePath()));
    }

    @Test
    public void isValidPhotoPath_invalidPathCharacters_returnsFalse() {
        // Test to trigger InvalidPathException
        assertFalse(PhotoPath.isValidPhotoPath("invalid\0path.png"));
    }
}
