package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PetPersonListPanel extends UiPart<Region> {
    private static final String FXML = "PetPersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label petHeaderLabel;

    @FXML
    private Label clientHeaderLabel;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PetPersonListPanel} with the given {@code ObservableList}.
     */
    public PetPersonListPanel(ObservableList<Person> personList) {
        super(FXML);

        petHeaderLabel.setText("Pet");
        clientHeaderLabel.setText("Client");

        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PetPersonListViewCell());
    }

    /**
     * Rebuilds the {@code ListView} which contains all {@code Pet} and {@code Person} information.
     */
    public void refreshPersonList() {
        requireNonNull(personListView);
        personListView.setCellFactory(listView -> new PetPersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} and
     * all the {@code Pet}s they have
     */
    class PetPersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                int startingPetIndex = 1;
                ObservableList<Person> personList = personListView.getItems();

                for (int i = 0; i < getIndex(); i++) {
                    startingPetIndex += personList.get(i).getPets().size();
                }

                logger.fine("Creating PetPersonCard with personIndex: " + (getIndex() + 1)
                        + "and petIndex: " + startingPetIndex);
                setGraphic(new PetPersonCard(person, getIndex() + 1, startingPetIndex).getRoot());
            }
        }
    }
}
