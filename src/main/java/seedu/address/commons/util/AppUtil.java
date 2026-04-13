package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_IMAGE_PATH;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    private static final Logger logger = LogsCenter.getLogger(AppUtil.class);
    private static final Path DATA_PHOTOS_DIR = Paths.get("data", "photos");

    /**
    * Loads an image from the specified path. If the image cannot be loaded, a
    * placeholder image will be loaded instead.
    *
    * @param imagePath The path to the image to load.
    * @return The loaded Image, or a placeholder Image if loading fails.
    */
    public static Image loadImage(String imagePath) {
        requireNonNull(imagePath);
        requireNonNull(PLACEHOLDER_IMAGE_PATH);
        Image image;
        try {
            // First, try loading as a classpath resource (default bundled images)
            InputStream stream = MainApp.class.getResourceAsStream(imagePath);
            if (stream != null) {
                image = new Image(stream);
            } else {
                // Try as a filesystem path (handles normalized forward-slash paths on all OSes)
                Path photoPath = Paths.get(imagePath);
                if (!photoPath.isAbsolute()) {
                    photoPath = DATA_PHOTOS_DIR.resolve(photoPath).normalize();
                }
                if (!Files.exists(photoPath) || !Files.isRegularFile(photoPath)) {
                    throw new IOException("Image file not found: " + imagePath);
                }
                image = new Image(photoPath.toUri().toString());
            }
        } catch (Exception e) {
            logger.warning("Failed to load image at " + imagePath + ": " + e.getMessage());
            logger.info("Loading placeholder image at " + PLACEHOLDER_IMAGE_PATH);
            InputStream fallbackStream = MainApp.class.getResourceAsStream(PLACEHOLDER_IMAGE_PATH);
            image = new Image(fallbackStream);
        }
        return image;
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
