package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * A UI component that displays information about a command in the help system.
 * Each card shows the command word, alias, description, usage, example, and notes.
 */
public class CommandCard extends UiPart<Region> {

    private static final String FXML = "CommandCard.fxml";

    private final CommandInfo commandInfo;
    private String resolvedExample;

    @FXML
    private VBox cardPane;
    @FXML
    private Label commandWordLabel;
    @FXML
    private Label aliasLabel;
    @FXML
    private Button copyButton;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label usageLabel;
    @FXML
    private VBox exampleSection;
    @FXML
    private Label exampleLabel;
    @FXML
    private VBox notesSection;
    @FXML
    private Label notesLabel;

    /**
     * Creates a {@code CommandCard} with the given {@code CommandInfo}.
     *
     * @param commandInfo The command information to display
     * @param clientIndex A sample client index for examples (e.g., "1")
     * @param petIndex A sample pet index for examples (e.g., "1.2")
     */
    public CommandCard(CommandInfo commandInfo, String clientIndex, String petIndex) {
        super(FXML);
        this.commandInfo = commandInfo;

        // Set command word
        commandWordLabel.setText(commandInfo.getCommandWord());

        // Set alias if present
        if (commandInfo.hasAlias()) {
            aliasLabel.setText("(" + commandInfo.getAlias() + ")");
            aliasLabel.setVisible(true);
            aliasLabel.setManaged(true);
        } else {
            aliasLabel.setVisible(false);
            aliasLabel.setManaged(false);
        }

        // Set description
        descriptionLabel.setText(commandInfo.getDescription());

        // Set usage
        usageLabel.setText(commandInfo.getUsage());

        // Set example with resolved indices
        if (commandInfo.hasExample()) {
            resolvedExample = resolveExample(commandInfo.getExampleTemplate(), clientIndex, petIndex);
            exampleLabel.setText(resolvedExample);
            exampleSection.setVisible(true);
            exampleSection.setManaged(true);
        } else {
            exampleSection.setVisible(false);
            exampleSection.setManaged(false);
        }

        // Set notes if present
        if (commandInfo.hasNotes()) {
            notesLabel.setText("💡 " + commandInfo.getNotes());
            notesSection.setVisible(true);
            notesSection.setManaged(true);
        } else {
            notesSection.setVisible(false);
            notesSection.setManaged(false);
        }
    }

    /**
     * Creates a {@code CommandCard} with default example indices.
     */
    public CommandCard(CommandInfo commandInfo) {
        this(commandInfo, "1", "1.1");
    }

    /**
     * Resolves placeholder tokens in the example template with actual indices.
     *
     * @param template The example template with placeholders
     * @param clientIndex The client index to substitute
     * @param petIndex The pet index to substitute (CLIENT.PET format)
     * @return The resolved example string
     */
    private String resolveExample(String template, String clientIndex, String petIndex) {
        if (template == null) {
            return "";
        }
        return template
                .replace("{CLIENT_INDEX}", clientIndex)
                .replace("{PET_INDEX}", petIndex);
    }

    /**
     * Copies the example command to the clipboard.
     */
    @FXML
    private void handleCopy() {
        String textToCopy;
        if (resolvedExample != null && !resolvedExample.isEmpty()) {
            textToCopy = resolvedExample;
        } else {
            // Fall back to usage if no example
            textToCopy = commandInfo.getUsage();
        }

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(textToCopy);
        clipboard.setContent(content);

        // Provide visual feedback
        String originalText = copyButton.getText();
        copyButton.setText("✓ Copied!");
        copyButton.setDisable(true);

        // Reset after 1.5 seconds
        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            javafx.application.Platform.runLater(() -> {
                copyButton.setText(originalText);
                copyButton.setDisable(false);
            });
        }).start();
    }

    /**
     * Returns the command info associated with this card.
     */
    public CommandInfo getCommandInfo() {
        return commandInfo;
    }
}
