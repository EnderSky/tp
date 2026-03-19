package seedu.address.ui;

import java.io.File;
import java.util.prefs.Preferences;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Utility class for handling file chooser dialogs.
 */
public class FileChooserUtil {

    private static final String PREF_LAST_DIRECTORY = "lastPhotoDirectory";
    private static final Preferences prefs = Preferences.userNodeForPackage(FileChooserUtil.class);

    /**
     * Opens a file chooser dialog for selecting an image file.
     * Returns the absolute path of the selected file, or null if the user cancelled.
     *
     * @param ownerStage The stage that owns this dialog (for modal positioning)
     * @return The absolute path of the selected image file, or null if cancelled
     */
    public static String showImageFileChooser(Stage ownerStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Pet Photo");

        // Set up file filters to show only image files
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Image Files (*.png, *.jpg, *.jpeg, *.gif, *.bmp)",
                "*.png", "*.PNG",
                "*.jpg", "*.JPG",
                "*.jpeg", "*.JPEG",
                "*.gif", "*.GIF",
                "*.bmp", "*.BMP"
        );
        FileChooser.ExtensionFilter allFilesFilter = new FileChooser.ExtensionFilter(
                "All Files",
                "*.*"
        );

        fileChooser.getExtensionFilters().addAll(imageFilter, allFilesFilter);
        fileChooser.setSelectedExtensionFilter(imageFilter); // Default to image files

        // Set initial directory to last used directory, if available
        String lastDirectoryPath = prefs.get(PREF_LAST_DIRECTORY, null);
        if (lastDirectoryPath != null) {
            File lastDirectory = new File(lastDirectoryPath);
            if (lastDirectory.exists() && lastDirectory.isDirectory()) {
                fileChooser.setInitialDirectory(lastDirectory);
            }
        }

        // Show the file chooser dialog and wait for user selection
        File selectedFile = fileChooser.showOpenDialog(ownerStage);

        if (selectedFile != null) {
            // Save the directory for next time
            File parentDirectory = selectedFile.getParentFile();
            if (parentDirectory != null) {
                prefs.put(PREF_LAST_DIRECTORY, parentDirectory.getAbsolutePath());
            }

            return selectedFile.getAbsolutePath();
        }

        return null; // User cancelled the dialog
    }

    /**
     * Clears the saved last directory preference.
     * This is useful for testing or resetting the file chooser state.
     */
    public static void clearLastDirectory() {
        prefs.remove(PREF_LAST_DIRECTORY);
    }
}
