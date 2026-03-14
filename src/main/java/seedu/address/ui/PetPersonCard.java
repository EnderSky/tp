package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * An UI component that displays information of a {@code Pet} on the left,
 * and a {@code Person} on the right.
 */
public class PetPersonCard extends UiPart<Region> {

    private static final String FXML = "PetPersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox petPersonCardPane;

    /**
     * Creates a {@code PetPersonCard} with the given {@code Pet}, {@code Person} and index to display.
     */
    public PetPersonCard(Pet pet, Person person, int displayedIndex) {
        super(FXML);
        PetCard petCard = new PetCard(pet, displayedIndex);
        PersonCard personCard = new PersonCard(person, displayedIndex);

        petPersonCardPane.getChildren().addAll(petCard.getRoot(), personCard.getRoot());
    }
}
