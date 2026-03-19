package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PetPersonListPanel petPersonListPanel;
    private DetailPanel detailPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane detailPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow(logic.getModel());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        petPersonListPanel = new PetPersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(petPersonListPanel.getRoot());

        detailPanel = new DetailPanel();
        detailPanelPlaceholder.getChildren().add(detailPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        // Set the split pane divider position to 80/20
        splitPane.setDividerPositions(0.8);

        // Set initial text on resultDisplay to welcome the user
        resultDisplay.setFeedbackToUser("Welcome to Hairy Pawter! Type 'help' to see available commands.");
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PetPersonListPanel getPersonListPanel() {
        return petPersonListPanel;
    }

    /**
     * Returns the detail panel.
     */
    public DetailPanel getDetailPanel() {
        return detailPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            // Handle file picker request
            if (commandResult.needsFilePicker()) {
                handleFilePickerRequest(commandResult);
                return commandResult;
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            // Handle view commands - update detail panel
            if (commandResult.hasClientToView()) {
                commandResult.getPersonToView().ifPresent(client -> {
                    detailPanel.showClientDetails(client);
                });
            } else if (commandResult.hasPetToView()) {
                commandResult.getPetToView().ifPresent(pet -> {
                    commandResult.getPetOwner().ifPresent(owner -> {
                        detailPanel.showPetDetails(pet, owner);
                    });
                });
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles a file picker request from a command.
     * Opens a file chooser dialog and re-executes the command with the selected file path.
     */
    private void handleFilePickerRequest(CommandResult commandResult) {
        String selectedPath = FileChooserUtil.showImageFileChooser(primaryStage);

        if (selectedPath != null) {
            // User selected a file - construct the internal command with the path
            String completeCommand = String.format(
                    "add photo %d.%d path/%s",
                    commandResult.getClientIndex().getOneBased(),
                    commandResult.getPetIndex().getOneBased(),
                    selectedPath
            );

            try {
                // Execute the command internally with the selected path
                CommandResult result = logic.executeWithPhotoPath(
                        commandResult.getClientIndex(),
                        commandResult.getPetIndex(),
                        selectedPath);
                logger.info("Photo added: " + result.getFeedbackToUser());
                resultDisplay.setFeedbackToUser(result.getFeedbackToUser());

                // Update detail panel if needed
                if (result.hasPetToView()) {
                    result.getPetToView().ifPresent(pet -> {
                        result.getPetOwner().ifPresent(owner -> {
                            detailPanel.showPetDetails(pet, owner);
                        });
                    });
                }
            } catch (CommandException e) {
                logger.info("Error adding photo: " + e.getMessage());
                resultDisplay.setFeedbackToUser(e.getMessage());
            }
        } else {
            // User cancelled the file picker
            resultDisplay.setFeedbackToUser("Photo selection cancelled.");
        }
    }
}
