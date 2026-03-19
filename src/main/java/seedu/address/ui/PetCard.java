package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Pet;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PetCard extends UiPart<Region> {

    private static final String FXML = "PetListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Pet pet;

    @FXML
    private HBox petCardPane;
    @FXML
    private Label petName;
    @FXML
    private Label petId;
    @FXML
    private Label petSpecies;
    @FXML
    private Label petBreed;

    /**
     * Creates a {@code PetCard} with the given {@code Pet} and index to display.
     */
    public PetCard(Pet pet, int displayedIndex) {
        super(FXML);
        this.pet = pet;
        if (displayedIndex != 0) {
            petId.setText(displayedIndex + ". ");
        }
        petName.setText(pet.getName().fullName);
        petSpecies.setText(pet.getSpecies());
        petBreed.setText(pet.getBreed());
    }
}
