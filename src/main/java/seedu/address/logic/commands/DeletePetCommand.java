package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
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
 * Deletes a pet identified by CLIENT_INDEX.PET_INDEX notation.
 */
public class DeletePetCommand extends Command {

    public static final String COMMAND_WORD = "delete pet";
    public static final String COMMAND_WORD_ALIAS = "dp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the pet identified by CLIENT_INDEX.PET_INDEX notation.\n"
            + "Parameters: CLIENT_INDEX.PET_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1.2 (deletes the 2nd pet of the 1st client)";

    public static final String MESSAGE_DELETE_PET_SUCCESS = "Deleted pet: %1$s (Owner: %2$s)";
    public static final String MESSAGE_INVALID_PET_INDEX = "The pet index provided is invalid for this client.";

    private final Index clientIndex;
    private final Index petIndex;

    /**
     * Creates a DeletePetCommand to delete the pet at the specified client and pet indices.
     */
    public DeletePetCommand(Index clientIndex, Index petIndex) {
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

        Pet petToDelete = pets.get(petIndex.getZeroBased());

        // Create a new pet set without the deleted pet
        Set<Pet> updatedPets = new HashSet<>(owner.getPets());
        updatedPets.remove(petToDelete);

        // Create updated person with the pet removed
        Person updatedOwner = new Person(owner, updatedPets);

        model.setPerson(owner, updatedOwner);

        return new CommandResult(String.format(MESSAGE_DELETE_PET_SUCCESS,
                Messages.format(petToDelete), owner.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePetCommand)) {
            return false;
        }

        DeletePetCommand otherDeletePetCommand = (DeletePetCommand) other;
        return clientIndex.equals(otherDeletePetCommand.clientIndex)
                && petIndex.equals(otherDeletePetCommand.petIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("petIndex", petIndex)
                .toString();
    }
}
