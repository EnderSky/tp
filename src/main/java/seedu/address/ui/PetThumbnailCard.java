package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Pet;

/**
 * A compact UI component that displays a pet as a thumbnail card.
 * Used in the grouped pets-first layout within client groups.
 */
public class PetThumbnailCard extends UiPart<Region> {

    private static final String FXML = "PetThumbnailCard.fxml";

    public final Pet pet;
    private final int clientIndex;
    private final int petIndex;

    @FXML
    private VBox cardPane;

    @FXML
    private Label indexLabel;

    @FXML
    private ImageView thumbnailView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private FlowPane tagsPane;

    /**
     * Creates a {@code PetThumbnailCard} with the given {@code Pet} and indices.
     *
     * @param pet The pet to display.
     * @param clientIndex The 1-based index of the client (owner).
     * @param petIndex The 1-based index of the pet within the client's pet list.
     */
    public PetThumbnailCard(Pet pet, int clientIndex, int petIndex) {
        super(FXML);
        this.pet = pet;
        this.clientIndex = clientIndex;
        this.petIndex = petIndex;

        // Set the index label (e.g., "1.2")
        indexLabel.setText(clientIndex + "." + petIndex);

        // Set pet name
        nameLabel.setText(pet.getName().fullName);

        // Set species/breed info
        StringBuilder info = new StringBuilder();
        pet.getSpecies().ifPresent(s -> info.append(s.value));
        pet.getBreed().ifPresent(b -> {
            if (info.length() > 0) {
                info.append(", ");
            }
            info.append(b.value);
        });
        if (info.length() > 0) {
            infoLabel.setText(info.toString());
            infoLabel.setVisible(true);
            infoLabel.setManaged(true);
        } else {
            infoLabel.setVisible(false);
            infoLabel.setManaged(false);
        }

        // TODO: Load pet thumbnail image when photo feature is implemented
        // For now, hide the image view
        thumbnailView.setVisible(false);
        thumbnailView.setManaged(false);

        // Pet tags - currently pets don't have tags, so hide for now
        tagsPane.setVisible(false);
        tagsPane.setManaged(false);
    }

    /**
     * Returns the client index for this pet card.
     */
    public int getClientIndex() {
        return clientIndex;
    }

    /**
     * Returns the pet index for this pet card.
     */
    public int getPetIndex() {
        return petIndex;
    }
}
