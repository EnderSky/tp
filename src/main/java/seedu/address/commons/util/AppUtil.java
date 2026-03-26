package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_IMAGE_PATH;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    private static final Logger logger = LogsCenter.getLogger(AppUtil.class);

    /**
     * Gets an {@code Image} from the specified path.
     */
    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
    * Loads an image from the specified path. If the image cannot be loaded, a
    * placeholder image will be loaded instead.
    *
    * @param imagePath The path to the image to load.
    * @return The loaded Image, or a placeholder Image if loading fails.
    */
    public static Image loadImage(String imagePath) {
        return loadImage(imagePath, PLACEHOLDER_IMAGE_PATH);
    }

    private static Image loadImage(String imagePath, String placeholderPath) {
        requireNonNull(imagePath);
        requireNonNull(placeholderPath);
        Image image;
        try {
            if (isClasspathResource(imagePath)) {
                // Use classpath resource
                InputStream stream = MainApp.class.getResourceAsStream(imagePath);
                if (stream == null) {
                    throw new IOException("Image not found in resources: " + imagePath);
                }
                image = new Image(stream);
            } else {
                // Use file system
                File photo = new File(imagePath);
                if (!photo.exists() || !photo.isFile()) {
                    throw new IOException("Image file not found: " + imagePath);
                }
                image = new Image(photo.toURI().toString());
            }
        } catch (Exception e) {
            logger.warning("Failed to load image at " + imagePath + ": " + e.getMessage());
            logger.info("Loading placeholder image at " + placeholderPath);
            InputStream fallbackStream = MainApp.class.getResourceAsStream(placeholderPath);
            image = new Image(fallbackStream);
        }
        return image;
    }

    private static boolean isClasspathResource(String path) {
        return path.startsWith("/")
                && !path.contains(":") // Not Windows drive letter
                && !path.startsWith("//"); // Not UNC path
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
