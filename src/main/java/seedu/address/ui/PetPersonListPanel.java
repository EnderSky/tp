package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of clients with their pets grouped underneath.
 * Uses a grouped pets-first layout where each client is a header row
 * with horizontally wrapping pet cards below.
 */
public class PetPersonListPanel extends UiPart<Region> {
    private static final String FXML = "PetPersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PetPersonListPanel.class);

    @FXML
    private ListView<Person> petPersonListView;

    /**
     * Creates a {@code PetPersonListPanel} with the given {@code ObservableList}.
     */
    public PetPersonListPanel(ObservableList<Person> personList) {
        super(FXML);

        petPersonListView.setItems(personList);
        petPersonListView.setCellFactory(listView -> new ClientGroupListCell());
    }

    /**
     * Custom {@code ListCell} that displays a client with their pets
     * using a {@code ClientGroupCard}.
     */
    class ClientGroupListCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ClientGroupCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
