package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class AppUtilTest {

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
    public void loadImage_relativePathInDataPhotos_loadsImageSuccessfully() throws IOException {
        Path dataPhotos = Paths.get("data", "photos");
        Files.createDirectories(dataPhotos);
        Path tempImage = Files.createTempFile(dataPhotos, "pet-", ".png");

        try (InputStream input = AppUtil.class.getResourceAsStream("/images/placeholder-pet-logo.png")) {
            Files.copy(input, tempImage, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        assertNotNull(AppUtil.loadImage(tempImage.getFileName().toString()));
        Files.deleteIfExists(tempImage);
    }

    @Test
    public void loadImage_directoryPath_loadsFallbackImage(@TempDir File tempDir) {
        assertNotNull(AppUtil.loadImage(tempDir.getAbsolutePath()));
    }

    @Test
    public void loadImage_invalidPathString_loadsFallbackImage() {
        assertNotNull(AppUtil.loadImage("invalid\0path.png"));
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
        // Get absolute path of /images/placeholder-pet-logo.png for comparison
        String classpathImagePath = AppUtil.class.getResource("/images/placeholder-pet-logo.png").getPath();
        assertNotNull(AppUtil.loadImage(classpathImagePath));
    }

    @Test
    public void loadImage_classpathResources_loadSuccessfully() {
        // Classpath resources starting with "/" should load via getResourceAsStream
        assertNotNull(AppUtil.loadImage("/images/placeholder-pet-logo.png"));
        assertNotNull(AppUtil.loadImage("/images/address_book_32.png"));
    }

    @Test
    public void loadImage_fileSystemPaths_loadSuccessfully(@TempDir File tempDir) throws IOException {
        // Create a temporary image file
        File tempImage = new File(tempDir, "fs-test-image.png");
        try (InputStream input = AppUtil.class.getResourceAsStream("/images/placeholder-pet-logo.png");
             FileOutputStream output = new FileOutputStream(tempImage)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        // Filesystem path should not be mistaken for a classpath resource
        assertNotNull(AppUtil.loadImage(tempImage.getAbsolutePath()));
    }

    @Test
    public void loadImage_nonExistentFileSystemPath_loadsFallbackImage(@TempDir File tempDir) {
        // A filesystem path that doesn't exist and isn't a classpath resource
        String nonExistentPath = tempDir.getAbsolutePath() + "/does_not_exist.png";
        assertNotNull(AppUtil.loadImage(nonExistentPath));
    }
}
