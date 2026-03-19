package seedu.address.ui;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * Controller for the help window with tabbed interface, search, and keyboard shortcuts.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "For detailed documentation: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    // Keyboard shortcuts
    private static final KeyCodeCombination SHORTCUT_SEARCH =
            new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
    private static final KeyCodeCombination SHORTCUT_TAB_1 =
            new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN);
    private static final KeyCodeCombination SHORTCUT_TAB_2 =
            new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN);
    private static final KeyCodeCombination SHORTCUT_TAB_3 =
            new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN);
    private static final KeyCodeCombination SHORTCUT_TAB_4 =
            new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.CONTROL_DOWN);
    private static final KeyCodeCombination SHORTCUT_TAB_5 =
            new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.CONTROL_DOWN);

    private Model model;
    private String sampleClientIndex = "1";
    private String samplePetIndex = "1.1";

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab clientTab;
    @FXML
    private Tab petTab;
    @FXML
    private Tab notesPhotosTab;
    @FXML
    private Tab searchFilterTab;
    @FXML
    private Tab generalTab;
    @FXML
    private Tab searchResultsTab;

    @FXML
    private VBox clientContent;
    @FXML
    private VBox petContent;
    @FXML
    private VBox notesPhotosContent;
    @FXML
    private VBox searchFilterContent;
    @FXML
    private VBox generalContent;
    @FXML
    private VBox searchResultsContent;

    @FXML
    private TextField searchField;

    @FXML
    private Label prefixInfoLabel;
    @FXML
    private Label indexInfoLabel;
    @FXML
    private Label helpMessage;

    @FXML
    private Button copyButton;

    /**
     * Creates a new HelpWindow with a model reference for real data examples.
     *
     * @param root Stage to use as the root of the HelpWindow.
     * @param model The model to get real data from (can be null).
     */
    public HelpWindow(Stage root, Model model) {
        super(FXML, root);
        this.model = model;

        // Set reasonable window size constraints
        Stage stage = getRoot();
        stage.setWidth(800);
        stage.setHeight(650);
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(800);

        initializeSampleIndices();
        initializeTabs();
        initializeSearch();
        initializeKeyboardShortcuts();

        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow with a model reference.
     */
    public HelpWindow(Model model) {
        this(new Stage(), model);
    }

    /**
     * Creates a new HelpWindow without model (uses default examples).
     */
    public HelpWindow(Stage root) {
        this(root, null);
    }

    /**
     * Creates a new HelpWindow without model (uses default examples).
     */
    public HelpWindow() {
        this(new Stage(), null);
    }

    /**
     * Initializes sample indices from real data if available.
     */
    private void initializeSampleIndices() {
        if (model == null) {
            return;
        }

        try {
            ObservableList<Person> persons = model.getFilteredPersonList();
            if (!persons.isEmpty()) {
                // Use the first client's index
                sampleClientIndex = "1";

                // Find a client with at least one pet
                for (int i = 0; i < persons.size(); i++) {
                    Person person = persons.get(i);
                    Set<Pet> pets = person.getPets();
                    if (!pets.isEmpty()) {
                        sampleClientIndex = String.valueOf(i + 1);
                        samplePetIndex = (i + 1) + ".1";
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.fine("Could not get sample indices from model: " + e.getMessage());
        }
    }

    /**
     * Initializes all tabs with their command cards.
     */
    private void initializeTabs() {
        // Set tab titles with icons
        clientTab.setText(CommandCategory.CLIENT.getTabTitle());
        petTab.setText(CommandCategory.PET.getTabTitle());
        notesPhotosTab.setText(CommandCategory.NOTES_PHOTOS.getTabTitle());
        searchFilterTab.setText(CommandCategory.SEARCH_FILTER.getTabTitle());
        generalTab.setText(CommandCategory.GENERAL.getTabTitle());

        // Populate each tab with commands
        populateTabContent(clientContent, CommandCategory.CLIENT);
        populateTabContent(petContent, CommandCategory.PET);
        populateTabContent(notesPhotosContent, CommandCategory.NOTES_PHOTOS);
        populateTabContent(searchFilterContent, CommandCategory.SEARCH_FILTER);
        populateTabContent(generalContent, CommandCategory.GENERAL);

        // Hide search results tab initially
        searchResultsTab.setDisable(true);
        tabPane.getTabs().remove(searchResultsTab);
    }

    /**
     * Populates a tab's content with command cards for the given category.
     */
    private void populateTabContent(VBox content, CommandCategory category) {
        content.getChildren().clear();

        // Add category description in a styled pane
        VBox descriptionPane = new VBox();
        descriptionPane.getStyleClass().add("category-description-pane");

        Label categoryDesc = new Label(category.getDescription());
        categoryDesc.getStyleClass().add("category-description");
        descriptionPane.getChildren().add(categoryDesc);

        content.getChildren().add(descriptionPane);

        // Add command cards
        List<CommandInfo> commands = CommandRegistry.getCommandsByCategory(category);
        for (CommandInfo cmd : commands) {
            CommandCard card = new CommandCard(cmd, sampleClientIndex, samplePetIndex);
            content.getChildren().add(card.getRoot());
        }
    }

    /**
     * Initializes search functionality with live filtering.
     */
    private void initializeSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch(newValue);
        });
    }

    /**
     * Handles search query changes.
     */
    private void handleSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            // Clear search - hide results tab and show normal tabs
            if (tabPane.getTabs().contains(searchResultsTab)) {
                tabPane.getTabs().remove(searchResultsTab);
                tabPane.getSelectionModel().select(0);
            }
            return;
        }

        // Perform search
        List<CommandInfo> results = CommandRegistry.searchCommands(query);
        populateSearchResults(results, query);

        // Show and select search results tab
        if (!tabPane.getTabs().contains(searchResultsTab)) {
            tabPane.getTabs().add(0, searchResultsTab);
        }
        searchResultsTab.setDisable(false);
        searchResultsTab.setText("🔎 Results (" + results.size() + ")");
        tabPane.getSelectionModel().select(searchResultsTab);
    }

    /**
     * Populates the search results tab with matching commands.
     */
    private void populateSearchResults(List<CommandInfo> results, String query) {
        searchResultsContent.getChildren().clear();

        if (results.isEmpty()) {
            VBox noResultsPane = new VBox();
            noResultsPane.getStyleClass().add("category-description-pane");

            Label noResults = new Label("No commands found matching: \"" + query + "\"");
            noResults.getStyleClass().add("no-results");
            noResultsPane.getChildren().add(noResults);

            searchResultsContent.getChildren().add(noResultsPane);
            return;
        }

        VBox resultCountPane = new VBox();
        resultCountPane.getStyleClass().add("category-description-pane");

        Label resultCount = new Label("Found " + results.size() + " command(s) matching \"" + query + "\"");
        resultCount.getStyleClass().add("search-result-count");
        resultCountPane.getChildren().add(resultCount);

        searchResultsContent.getChildren().add(resultCountPane);

        for (CommandInfo cmd : results) {
            CommandCard card = new CommandCard(cmd, sampleClientIndex, samplePetIndex);
            searchResultsContent.getChildren().add(card.getRoot());
        }
    }

    /**
     * Initializes keyboard shortcuts for the help window.
     */
    private void initializeKeyboardShortcuts() {
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (SHORTCUT_SEARCH.match(event)) {
                searchField.requestFocus();
                searchField.selectAll();
                event.consume();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                if (!searchField.getText().isEmpty()) {
                    searchField.clear();
                    event.consume();
                }
            } else if (SHORTCUT_TAB_1.match(event)) {
                selectCategoryTab(0);
                event.consume();
            } else if (SHORTCUT_TAB_2.match(event)) {
                selectCategoryTab(1);
                event.consume();
            } else if (SHORTCUT_TAB_3.match(event)) {
                selectCategoryTab(2);
                event.consume();
            } else if (SHORTCUT_TAB_4.match(event)) {
                selectCategoryTab(3);
                event.consume();
            } else if (SHORTCUT_TAB_5.match(event)) {
                selectCategoryTab(4);
                event.consume();
            }
        });
    }

    /**
     * Selects a category tab by index (0-4), skipping the search results tab if present.
     */
    private void selectCategoryTab(int categoryIndex) {
        // Clear search when switching tabs via shortcut
        searchField.clear();

        // Calculate actual tab index (search results tab might be at index 0)
        ObservableList<Tab> tabs = tabPane.getTabs();
        int actualIndex = categoryIndex;

        // If search results tab is present, offset by 1
        if (tabs.contains(searchResultsTab) && tabs.indexOf(searchResultsTab) == 0) {
            actualIndex = categoryIndex + 1;
        }

        if (actualIndex >= 0 && actualIndex < tabs.size()) {
            tabPane.getSelectionModel().select(actualIndex);
        }
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
     * Updates the model reference (useful for refreshing examples with new data).
     */
    public void setModel(Model model) {
        this.model = model;
        initializeSampleIndices();
        // Refresh tabs with new sample indices
        initializeTabs();
    }
}
