package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
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
    private final Logger logger = LogsCenter.getLogger(PetPersonListPanel.class);

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

        personList.addListener((ListChangeListener<Person>) change -> {
            personListView.refresh();
        });
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

                setGraphic(new PetPersonCard(person, getIndex() + 1, startingPetIndex).getRoot());
            }
        }
    }
}
