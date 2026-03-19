package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * A UI component that displays a client (person) as a header row,
 * with their pets displayed as thumbnail cards below in a horizontally wrapping layout.
 */
public class ClientGroupCard extends UiPart<Region> {

    private static final String FXML = "ClientGroupCard.fxml";

    public final Person client;
    private final int clientIndex;

    @FXML
    private VBox groupPane;

    @FXML
    private HBox clientHeader;

    @FXML
    private Label clientIndexLabel;

    @FXML
    private Label clientNameLabel;

    @FXML
    private Label clientPhoneLabel;

    @FXML
    private Label clientEmailLabel;

    @FXML
    private Label clientAddressLabel;

    @FXML
    private FlowPane clientTagsPane;

    @FXML
    private FlowPane petsContainer;

    @FXML
    private Label noPetsLabel;

    /**
     * Creates a {@code ClientGroupCard} with the given {@code Person} and index.
     *
     * @param client The client (person) to display.
     * @param clientIndex The 1-based index of the client in the list.
     */
    public ClientGroupCard(Person client, int clientIndex) {
        super(FXML);
        this.client = client;
        this.clientIndex = clientIndex;

        // Set client header info
        clientIndexLabel.setText(clientIndex + ".");
        clientNameLabel.setText(client.getName().fullName);
        clientPhoneLabel.setText(client.getPhone().value);
        clientEmailLabel.setText(client.getEmail().value);

        // Truncate address if too long
        String address = client.getAddress().value;
        if (address.length() > 30) {
            address = address.substring(0, 27) + "...";
        }
        clientAddressLabel.setText(address);

        // Add client tags
        clientTagsPane.getChildren().clear();
        client.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("client-tag");
                    clientTagsPane.getChildren().add(tagLabel);
                });

        // Add pet cards or show "no pets" message
        List<Pet> pets = List.copyOf(client.getPets());
        if (pets.isEmpty()) {
            noPetsLabel.setVisible(true);
            noPetsLabel.setManaged(true);
            petsContainer.setVisible(false);
            petsContainer.setManaged(false);
        } else {
            noPetsLabel.setVisible(false);
            noPetsLabel.setManaged(false);
            petsContainer.setVisible(true);
            petsContainer.setManaged(true);

            int petIndex = 1;
            for (Pet pet : pets) {
                PetThumbnailCard petCard = new PetThumbnailCard(pet, clientIndex, petIndex);
                petsContainer.getChildren().add(petCard.getRoot());
                petIndex++;
            }
        }
    }

    /**
     * Returns the client index for this group card.
     */
    public int getClientIndex() {
        return clientIndex;
    }
}
