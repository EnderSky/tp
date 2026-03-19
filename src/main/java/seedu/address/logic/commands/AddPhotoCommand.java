package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.PhotoPath;

/**
 * Adds a photo to a pet identified by CLIENT_INDEX.PET_INDEX notation.
 * The photo file will be selected via a GUI file chooser dialog.
 */
public class AddPhotoCommand extends Command {

    public static final String COMMAND_WORD = "add photo";
    public static final String COMMAND_WORD_ALIAS = "aph";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a photo to the pet identified by CLIENT_INDEX.PET_INDEX notation.\n"
            + "A file chooser dialog will open to select the photo file.\n"
            + "Parameters: CLIENT_INDEX.PET_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1.2";

    public static final String MESSAGE_ADD_PHOTO_SUCCESS = "Added photo to pet %1$s (Owner: %2$s): %3$s";
    public static final String MESSAGE_INVALID_PET_INDEX = "The pet index provided is invalid for this client.";
    public static final String MESSAGE_PET_ALREADY_HAS_PHOTO =
            "This pet already has a photo. Use 'delete photo' first to remove the existing photo.";
    public static final String MESSAGE_SELECT_PHOTO = "Please select a photo file for %1$s (Owner: %2$s)...";

    private final Index clientIndex;
    private final Index petIndex;
    private final PhotoPath photoPath;

    /**
     * Creates an AddPhotoCommand to request a file picker for the pet at the specified indices.
     */
    public AddPhotoCommand(Index clientIndex, Index petIndex) {
        requireNonNull(clientIndex);
        requireNonNull(petIndex);
        this.clientIndex = clientIndex;
        this.petIndex = petIndex;
        this.photoPath = null; // Will be filled in by file picker
    }

    /**
     * Creates an AddPhotoCommand to add a photo to the pet at the specified indices.
     * This constructor is used when the photo path has been selected via the file chooser.
     */
    public AddPhotoCommand(Index clientIndex, Index petIndex, PhotoPath photoPath) {
        requireNonNull(clientIndex);
        requireNonNull(petIndex);
        requireNonNull(photoPath);
        this.clientIndex = clientIndex;
        this.petIndex = petIndex;
        this.photoPath = photoPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person owner = lastShownList.get(clientIndex.getZeroBased());
        List<Pet> pets = new ArrayList<>(owner.getPets());

        if (petIndex.getZeroBased() >= pets.size()) {
            throw new CommandException(MESSAGE_INVALID_PET_INDEX);
        }

        Pet targetPet = pets.get(petIndex.getZeroBased());

        // Check if pet already has a photo
        if (targetPet.getPhotoPath().isPresent()) {
            throw new CommandException(MESSAGE_PET_ALREADY_HAS_PHOTO);
        }

        // If no photo path provided, request file picker from UI
        if (photoPath == null) {
            return CommandResult.withFilePickerRequest(
                    String.format(MESSAGE_SELECT_PHOTO, targetPet.getName(), owner.getName().fullName),
                    clientIndex,
                    petIndex);
        }

        // Create updated pet with photo
        Pet updatedPet = new Pet(
                targetPet.getName(),
                targetPet.getSpecies().orElse(null),
                targetPet.getBreed().orElse(null),
                targetPet.getDateOfBirth().orElse(null),
                targetPet.getGroomingNotes().orElse(null),
                photoPath
        );

        // Replace the pet in the set while preserving order
        Set<Pet> updatedPets = new LinkedHashSet<>();
        for (Pet p : owner.getPets()) {
            if (p.equals(targetPet)) {
                updatedPets.add(updatedPet);
            } else {
                updatedPets.add(p);
            }
        }

        // Create updated person with the modified pet
        Person updatedOwner = new Person(owner, updatedPets);
        model.setPerson(owner, updatedOwner);

        return new CommandResult(String.format(MESSAGE_ADD_PHOTO_SUCCESS,
                targetPet.getName(), owner.getName().fullName, photoPath.value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddPhotoCommand)) {
            return false;
        }

        AddPhotoCommand otherCommand = (AddPhotoCommand) other;
        boolean indicesEqual = clientIndex.equals(otherCommand.clientIndex)
                && petIndex.equals(otherCommand.petIndex);

        // Handle null photoPath case
        if (photoPath == null && otherCommand.photoPath == null) {
            return indicesEqual;
        }
        if (photoPath == null || otherCommand.photoPath == null) {
            return false;
        }

        return indicesEqual && photoPath.equals(otherCommand.photoPath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("petIndex", petIndex)
                .add("photoPath", photoPath)
                .toString();
    }
}
