package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.PetAndPerson;

/**
 * Panel containing the list of persons.
 */
public class PetPersonListPanel extends UiPart<Region> {
    private static final String FXML = "PetPersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PetPersonListPanel.class);

    @FXML
    private ListView<PetAndPerson> petPersonListView;

    private final ObservableList<PetAndPerson> petPersonList = FXCollections.observableArrayList();

    /**
     * Creates a {@code PetPersonListPanel} with the given {@code ObservableList}.
     */
    public PetPersonListPanel(ObservableList<Person> personList) {
        super(FXML);

        createPetPersonList(personList);

        personList.addListener((ListChangeListener<Person>) change -> {
            createPetPersonList(personList);
        });

        petPersonListView.setItems(petPersonList);
        petPersonListView.setCellFactory(listView -> new PetPersonListViewCell());
    }

    /**
     * Clears the current PetAndPerson list and creates it from the Person list.
     */
    private void createPetPersonList(ObservableList<Person> personList) {
        petPersonList.clear(); // remove the old data

        for (Person person : personList) {
            if (person.getPets().isEmpty()) {
                Pet noPet = new Pet(new Name("No pets for this person"), "", "");
                petPersonList.add(new PetAndPerson(noPet, person));
            } else {
                for (Pet pet : person.getPets()) {
                    petPersonList.add(new PetAndPerson(pet, person));
                }
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Pet} and {@code Person} using
     * a {@code PetPersonCard}.
     */
    class PetPersonListViewCell extends ListCell<PetAndPerson> {
        @Override
        protected void updateItem(PetAndPerson item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PetPersonCard(item.getPet(), item.getPerson(), getIndex() + 1).getRoot());
            }
        }
    }
}
