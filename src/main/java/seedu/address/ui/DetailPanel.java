package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * Panel showing detailed information about a selected pet or client.
 */
public class DetailPanel extends UiPart<Region> {

    private static final String FXML = "DetailPanel.fxml";
    private static final String DEFAULT_PET_IMAGE = "/images/default_pet.png";
    private static final String DEFAULT_CLIENT_IMAGE = "/images/default_client.png";

    private final Logger logger = LogsCenter.getLogger(DetailPanel.class);

    @FXML
    private VBox contentContainer;

    @FXML
    private VBox photoContainer;

    @FXML
    private ImageView photoView;

    @FXML
    private Label photoCaption;

    @FXML
    private VBox primaryInfoSection;

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private VBox detailsSection;

    @FXML
    private Label detail1Label;

    @FXML
    private Label detail2Label;

    @FXML
    private Label detail3Label;

    @FXML
    private Label detail4Label;

    @FXML
    private VBox tagsSection;

    @FXML
    private FlowPane tagsPane;

    @FXML
    private VBox secondaryInfoSection;

    @FXML
    private Label secondaryHeader;

    @FXML
    private VBox secondaryContent;

    @FXML
    private VBox emptyStateContainer;

    @FXML
    private Label emptyStateLabel;

    /**
     * Creates a {@code DetailPanel}.
     */
    public DetailPanel() {
        super(FXML);
        showEmptyState();
    }

    /**
     * Shows the empty state when no item is selected.
     */
    public void showEmptyState() {
        setAllSectionsVisible(false);
        emptyStateContainer.setVisible(true);
        emptyStateContainer.setManaged(true);
    }

    /**
     * Displays detailed information about a pet.
     *
     * @param pet The pet to display.
     * @param owner The pet's owner.
     */
    public void showPetDetails(Pet pet, Person owner) {
        logger.fine("Showing details for pet: " + pet.getName());

        setAllSectionsVisible(true);
        emptyStateContainer.setVisible(false);
        emptyStateContainer.setManaged(false);

        // Photo section
        // TODO: Load actual pet photo when photo feature is implemented
        photoCaption.setText(pet.getName().fullName);

        // Primary info
        nameLabel.setText(pet.getName().fullName);
        typeLabel.setText("Pet");

        // Details
        detail1Label.setText("Species: " + pet.getSpecies().map(s -> s.value).orElse("Not specified"));
        detail2Label.setText("Breed: " + pet.getBreed().map(b -> b.value).orElse("Not specified"));
        detail3Label.setText("Date of Birth: " + pet.getDateOfBirth()
                .map(d -> d.toString()).orElse("Not specified"));
        detail4Label.setVisible(false);
        detail4Label.setManaged(false);

        // Tags section - pets don't have tags yet, hide for now
        tagsSection.setVisible(false);
        tagsSection.setManaged(false);

        // Secondary info - owner details
        secondaryHeader.setText("OWNER");
        secondaryContent.getChildren().clear();
        secondaryContent.getChildren().addAll(
                createDetailLabel(owner.getName().fullName),
                createDetailLabel("Phone: " + owner.getPhone().value),
                createDetailLabel("Email: " + owner.getEmail().value),
                createDetailLabel("Address: " + owner.getAddress().value)
        );
    }

    /**
     * Displays detailed information about a client.
     *
     * @param client The client to display.
     */
    public void showClientDetails(Person client) {
        logger.fine("Showing details for client: " + client.getName());

        setAllSectionsVisible(true);
        emptyStateContainer.setVisible(false);
        emptyStateContainer.setManaged(false);

        // Photo section - hide for clients (or show default)
        photoContainer.setVisible(false);
        photoContainer.setManaged(false);

        // Primary info
        nameLabel.setText(client.getName().fullName);
        typeLabel.setText("Client");

        // Details
        detail1Label.setText("Phone: " + client.getPhone().value);
        detail2Label.setText("Email: " + client.getEmail().value);
        detail3Label.setText("Address: " + client.getAddress().value);
        detail4Label.setVisible(false);
        detail4Label.setManaged(false);

        // Tags section
        tagsPane.getChildren().clear();
        if (!client.getTags().isEmpty()) {
            tagsSection.setVisible(true);
            tagsSection.setManaged(true);
            client.getTags().forEach(tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.getStyleClass().add("detail-tag");
                tagsPane.getChildren().add(tagLabel);
            });
        } else {
            tagsSection.setVisible(false);
            tagsSection.setManaged(false);
        }

        // Secondary info - pets list
        if (!client.getPets().isEmpty()) {
            secondaryInfoSection.setVisible(true);
            secondaryInfoSection.setManaged(true);
            secondaryHeader.setText("PETS (" + client.getPets().size() + ")");
            secondaryContent.getChildren().clear();

            int petIndex = 1;
            for (Pet pet : client.getPets()) {
                String petInfo = petIndex + ". " + pet.getName().fullName;
                if (pet.getSpecies().isPresent() || pet.getBreed().isPresent()) {
                    petInfo += " - ";
                    if (pet.getSpecies().isPresent()) {
                        petInfo += pet.getSpecies().get().value;
                    }
                    if (pet.getBreed().isPresent()) {
                        if (pet.getSpecies().isPresent()) {
                            petInfo += ", ";
                        }
                        petInfo += pet.getBreed().get().value;
                    }
                }
                secondaryContent.getChildren().add(createDetailLabel(petInfo));
                petIndex++;
            }
        } else {
            secondaryInfoSection.setVisible(true);
            secondaryInfoSection.setManaged(true);
            secondaryHeader.setText("PETS");
            secondaryContent.getChildren().clear();
            secondaryContent.getChildren().add(createDetailLabel("No pets"));
        }
    }

    /**
     * Creates a label for displaying detail information.
     */
    private Label createDetailLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("detail-secondary-text");
        label.setWrapText(true);
        return label;
    }

    /**
     * Sets the visibility of all main sections.
     */
    private void setAllSectionsVisible(boolean visible) {
        photoContainer.setVisible(visible);
        photoContainer.setManaged(visible);
        primaryInfoSection.setVisible(visible);
        primaryInfoSection.setManaged(visible);
        detailsSection.setVisible(visible);
        detailsSection.setManaged(visible);
        tagsSection.setVisible(visible);
        tagsSection.setManaged(visible);
        secondaryInfoSection.setVisible(visible);
        secondaryInfoSection.setManaged(visible);
    }
}
