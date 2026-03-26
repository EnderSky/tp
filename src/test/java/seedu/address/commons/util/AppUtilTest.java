package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class AppUtilTest {

    @Test
    public void getImage_existingImage() {
        assertNotNull(AppUtil.getImage("/images/address_book_32.png"));
    }

    @Test
    public void getImage_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.getImage(null));
    }

    @Test
    public void loadImage_existingImage() {
        assertNotNull(AppUtil.loadImage("/images/address_book_32.png"));
    }

    @Test
    public void loadImage_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.loadImage(null));
    }

    @Test
    public void loadImage_nonExistingImage_loadsFallbackImage() {
        assertNotNull(AppUtil.loadImage("/images/non_existing_image.png"));
    }

    @Test
    public void loadImage_validClasspathImage_success() {
        assertNotNull(AppUtil.loadImage("/images/placeholder-pet-logo.png"));
    }

    @Test
    public void loadImage_validFilePathImage_success() {
        String validFilePath = System.getProperty("user.dir") + "/src/main/resources/images/placeholder-pet-logo.png";
        assertNotNull(AppUtil.loadImage(validFilePath));
    }

    @Test
    public void loadImage_invalidFilePathImage_loadsFallbackImage() {
        String invalidFilePath = System.getProperty("user.dir") + "/src/main/resources/images/non_existing_image.png";
        assertNotNull(AppUtil.loadImage(invalidFilePath));
    }

    @Test
    public void checkArgument_true_nothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
    }

    @Test
    public void checkArgument_falseWithoutErrorMessage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AppUtil.checkArgument(false));
    }

    @Test
    public void checkArgument_falseWithErrorMessage_throwsIllegalArgumentException() {
        String errorMessage = "error message";
        assertThrows(IllegalArgumentException.class, errorMessage, () -> AppUtil.checkArgument(false, errorMessage));
    }

    @Test
    public void loadImage_validFileSystemPath_loadsImageSuccessfully(@TempDir File tempDir) throws IOException {
        // Create a temporary image file in the temp directory
        File tempImage = new File(tempDir, "test-image.png");

        // Copy an existing image to the temporary file
        try (InputStream input = AppUtil.class.getResourceAsStream("/images/placeholder-pet-logo.png");
             FileOutputStream output = new FileOutputStream(tempImage)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        // Test with absolute path that doesn't start with "/" (Windows-style or relative path)
        String absolutePath = tempImage.getAbsolutePath();
        assertNotNull(AppUtil.loadImage(absolutePath));
    }

    @Test
    public void isClasspathResource_classpathPath_returnsTrue() throws Exception {
        // Use reflection to access the private isClasspathResource method
        Method method = AppUtil.class.getDeclaredMethod("isClasspathResource", String.class);
        method.setAccessible(true);

        // Test classpath resource paths (start with "/" and no drive letter)
        assertTrue((Boolean) method.invoke(null, "/images/placeholder-pet-logo.png"));
        assertTrue((Boolean) method.invoke(null, "/images/address_book_32.png"));
        assertTrue((Boolean) method.invoke(null, "/view/MainWindow.fxml"));
    }

    @Test
    public void isClasspathResource_fileSystemPath_returnsFalse() throws Exception {
        // Use reflection to access the private isClasspathResource method
        Method method = AppUtil.class.getDeclaredMethod("isClasspathResource", String.class);
        method.setAccessible(true);

        // Test filesystem paths (don't start with "/" or contain drive letters)
        assertFalse((Boolean) method.invoke(null, "src/main/resources/images/test.png"));
        assertFalse((Boolean) method.invoke(null, "relative/path/image.png"));
        assertFalse((Boolean) method.invoke(null, "C:/Users/test/image.png")); // Windows path with drive letter
        assertFalse((Boolean) method.invoke(null, "D:\\photos\\image.png")); // Windows path with backslash
        assertFalse((Boolean) method.invoke(null, "//network/share/image.png")); // UNC path
    }
}
