package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";

    public static final String COMMAND_SUMMARY = """
            ============================================================
                           HAIRY PAWTER - COMMAND REFERENCE
            ============================================================

            CLIENT COMMANDS
            ---------------
            add client (ac)     Add a new client
                                Usage: add client n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...
                                Example: add client n/John Doe p/98765432 e/john@email.com a/123 Street

            edit client (ec)    Edit an existing client
                                Usage: edit client INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...
                                Example: edit client 1 p/91234567 e/newemail@example.com

            delete client (dc)  Delete a client
                                Usage: delete client INDEX
                                Example: delete client 1

            view client (vc)    View client details in side panel
                                Usage: view client INDEX
                                Example: view client 1

            PET COMMANDS
            ------------
            add pet (ap)        Add a pet to a client
                                Usage: add pet INDEX n/NAME [s/SPECIES] [b/BREED] [dob/DATE_OF_BIRTH]
                                Example: add pet 1 n/Buddy s/Dog b/Golden Retriever dob/2020-05-15

            delete pet (dp)     Delete a pet (uses CLIENT.PET notation)
                                Usage: delete pet CLIENT_INDEX.PET_INDEX
                                Example: delete pet 1.2 (deletes 2nd pet of 1st client)

            view pet (vp)       View pet details in side panel (uses CLIENT.PET notation)
                                Usage: view pet CLIENT_INDEX.PET_INDEX
                                Example: view pet 1.2 (views 2nd pet of 1st client)

            GENERAL COMMANDS
            ----------------
            list (l)            List all clients and pets

            find (f)            Find clients by name
                                Usage: find KEYWORD [MORE_KEYWORDS]...
                                Example: find john david

            clear               Clear all data

            help (h)            Show this help message

            exit                Exit the application

            ============================================================
            PREFIXES: n/ (name), p/ (phone), e/ (email), a/ (address),
                      t/ (tag), s/ (species), b/ (breed), dob/ (date of birth)
            ============================================================
            """;

    public static final String HELP_MESSAGE = "For detailed documentation: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TextArea commandSummary;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        if (commandSummary != null) {
            commandSummary.setText(COMMAND_SUMMARY);
            commandSummary.setEditable(false);
        }
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
