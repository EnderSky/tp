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

/**
 * Deletes the photo from a pet identified by CLIENT_INDEX.PET_INDEX notation.
 */
public class DeletePhotoCommand extends Command {

    public static final String COMMAND_WORD = "delete photo";
    public static final String COMMAND_WORD_ALIAS = "dph";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the photo from the pet identified by CLIENT_INDEX.PET_INDEX notation.\n"
            + "Parameters: CLIENT_INDEX.PET_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1.2";

    public static final String MESSAGE_DELETE_PHOTO_SUCCESS = "Deleted photo from pet %1$s (Owner: %2$s)";
    public static final String MESSAGE_INVALID_PET_INDEX = "The pet index provided is invalid for this client.";
    public static final String MESSAGE_NO_PHOTO_TO_DELETE =
            "This pet does not have a photo to delete.";

    private final Index clientIndex;
    private final Index petIndex;

    /**
     * Creates a DeletePhotoCommand to delete the photo from the pet at the specified indices.
     */
    public DeletePhotoCommand(Index clientIndex, Index petIndex) {
        requireNonNull(clientIndex);
        requireNonNull(petIndex);
        this.clientIndex = clientIndex;
        this.petIndex = petIndex;
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

        // Check if pet has a photo to delete
        if (targetPet.getPhotoPath().isEmpty()) {
            throw new CommandException(MESSAGE_NO_PHOTO_TO_DELETE);
        }

        // Create updated pet without photo
        Pet updatedPet = new Pet(
                targetPet.getName(),
                targetPet.getSpecies().orElse(null),
                targetPet.getBreed().orElse(null),
                targetPet.getDateOfBirth().orElse(null),
                targetPet.getGroomingNotes().orElse(null),
                null // Remove photo
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
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_DELETE_PHOTO_SUCCESS,
                targetPet.getName(), owner.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeletePhotoCommand)) {
            return false;
        }

        DeletePhotoCommand otherCommand = (DeletePhotoCommand) other;
        return clientIndex.equals(otherCommand.clientIndex)
                && petIndex.equals(otherCommand.petIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("petIndex", petIndex)
                .toString();
    }
}
