package seedu.address.ui;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private HBox cardPane;

    @FXML
    private Label indexLabel;

    @FXML
    private ImageView thumbnailView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label speciesLabel;

    @FXML
    private Label breedLabel;

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

        // Set species
        if (pet.getSpecies().isPresent()) {
            speciesLabel.setText(pet.getSpecies().get().value);
            speciesLabel.setVisible(true);
            speciesLabel.setManaged(true);
        } else {
            speciesLabel.setVisible(false);
            speciesLabel.setManaged(false);
        }

        // Set breed
        if (pet.getBreed().isPresent()) {
            breedLabel.setText(pet.getBreed().get().value);
            breedLabel.setVisible(true);
            breedLabel.setManaged(true);
        } else {
            breedLabel.setVisible(false);
            breedLabel.setManaged(false);
        }

        // Load pet thumbnail image if photo path exists
        loadPetPhoto();

        // Set pet tags (with truncation for card size constraints)
        tagsPane.getChildren().clear();
        if (!pet.getTags().isEmpty()) {
            tagsPane.setVisible(true);
            tagsPane.setManaged(true);

            // Sort tags alphabetically like client tags
            List<String> sortedTagNames = pet.getTags().stream()
                    .map(tag -> tag.tagName)
                    .sorted()
                    .collect(Collectors.toList());

            // Limit to maximum 3 tags to prevent overflow in thumbnail card
            int maxTags = Math.min(sortedTagNames.size(), 3);

            for (int i = 0; i < maxTags; i++) {
                Label tagLabel = new Label(sortedTagNames.get(i));
                tagLabel.getStyleClass().add("client-tag"); // Use same styling as client tags
                tagsPane.getChildren().add(tagLabel);
            }

            // Add "..." indicator if there are more tags
            if (sortedTagNames.size() > maxTags) {
                Label moreLabel = new Label("...");
                moreLabel.getStyleClass().add("client-tag");
                tagsPane.getChildren().add(moreLabel);
            }
        } else {
            tagsPane.setVisible(false);
            tagsPane.setManaged(false);
        }
    }

    /**
     * Loads and displays the pet's photo if available.
     * Uses appropriate placeholder images:
     * - "photo_not_found.png" if a photo path exists but the file cannot be found
     * - "pet_placeholder.png" if no photo is assigned to the pet
     */
    private void loadPetPhoto() {
        if (pet.getPhotoPath().isPresent()) {
            if (pet.getPhotoPath().get().fileExists()) {
                try {
                    String photoPath = pet.getPhotoPath().get().value;
                    File photoFile = new File(photoPath);
                    Image image = new Image(photoFile.toURI().toString());
                    configureImageViewWithCropping(image);
                } catch (Exception e) {
                    // If image loading fails, show file not found placeholder
                    loadFileNotFoundImage();
                }
            } else {
                // Photo path exists but file is missing - show file not found placeholder
                loadFileNotFoundImage();
            }
        } else {
            // No photo assigned - use default placeholder
            loadPlaceholderImage();
        }
    }

    /**
     * Configures the ImageView with proper scaling and viewport cropping.
     * Scales the image to 90px height and crops to 90px width, centering horizontally.
     */
    private void configureImageViewWithCropping(Image image) {
        if (image == null || image.isError()) {
            loadPlaceholderImage();
            return;
        }

        // The target height for the thumbnail
        double heightToFit = thumbnailView.getFitHeight();

        double originalWidth = image.getWidth();
        double originalHeight = image.getHeight();

        // Calculate scale factor to fit height to 90px
        double scaleFactor = heightToFit / originalHeight;
        double scaledWidth = originalWidth * scaleFactor;

        // Configure viewport for centered cropping
        double cropX = (scaledWidth - heightToFit) / 2 / scaleFactor; // Convert back to original image coordinates
        Rectangle2D viewport = new Rectangle2D(cropX, 0, heightToFit / scaleFactor, originalHeight);
        thumbnailView.setViewport(viewport);

        thumbnailView.setImage(image);
        thumbnailView.setVisible(true);
        thumbnailView.setManaged(true);
    }

    /**
     * Loads a placeholder image for pets without photos.
     */
    private void loadPlaceholderImage() {
        try {
            // Try to load a placeholder from resources
            Image placeholder = new Image(getClass().getResourceAsStream("/images/pet_placeholder.png"));
            configureImageViewWithCropping(placeholder);
        } catch (Exception e) {
            // If placeholder doesn't exist, hide the image view
            thumbnailView.setVisible(false);
            thumbnailView.setManaged(false);
        }
    }

    /**
     * Loads a "file not found" placeholder image for pets whose photo file is missing.
     */
    private void loadFileNotFoundImage() {
        try {
            Image fileNotFound = new Image(getClass().getResourceAsStream("/images/photo_not_found.png"));
            configureImageViewWithCropping(fileNotFound);
        } catch (Exception e) {
            // Fallback to regular placeholder if file not found image is missing
            loadPlaceholderImage();
        }
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
