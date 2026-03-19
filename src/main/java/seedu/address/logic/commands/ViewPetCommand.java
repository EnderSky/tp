package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * Views detailed information of a pet identified by CLIENT_INDEX.PET_INDEX notation.
 */
public class ViewPetCommand extends Command {

    public static final String COMMAND_WORD = "view pet";
    public static final String COMMAND_WORD_ALIAS = "vp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the pet identified by CLIENT_INDEX.PET_INDEX notation.\n"
            + "Parameters: CLIENT_INDEX.PET_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1.2 (views the 2nd pet of the 1st client)";

    public static final String MESSAGE_VIEW_PET_SUCCESS = "Viewing pet: %1$s (Owner: %2$s)";
    public static final String MESSAGE_INVALID_PET_INDEX = "The pet index provided is invalid for this client.";

    private final Index clientIndex;
    private final Index petIndex;

    /**
     * Creates a ViewPetCommand to view the pet at the specified client and pet indices.
     */
    public ViewPetCommand(Index clientIndex, Index petIndex) {
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

        Pet petToView = pets.get(petIndex.getZeroBased());
        return CommandResult.withPetView(
                String.format(MESSAGE_VIEW_PET_SUCCESS, petToView.getName().fullName, owner.getName().fullName),
                petToView,
                owner);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewPetCommand)) {
            return false;
        }

        ViewPetCommand otherViewPetCommand = (ViewPetCommand) other;
        return clientIndex.equals(otherViewPetCommand.clientIndex)
                && petIndex.equals(otherViewPetCommand.petIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("petIndex", petIndex)
                .toString();
    }
}
