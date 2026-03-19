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
 * Views detailed information of a client or pet.
 * Supports both CLIENT_INDEX format (for viewing clients) and CLIENT_INDEX.PET_INDEX format (for viewing pets).
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_WORD_ALIAS = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views a client or pet identified by index.\n"
            + "Parameters: INDEX (for clients) or CLIENT_INDEX.PET_INDEX (for pets)\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " 1 (views client 1)\n"
            + "  " + COMMAND_WORD + " 1.2 (views pet 2 of client 1)";

    public static final String MESSAGE_VIEW_CLIENT_SUCCESS = "Viewing client: %1$s";
    public static final String MESSAGE_VIEW_PET_SUCCESS = "Viewing pet: %1$s (Owner: %2$s)";
    public static final String MESSAGE_INVALID_PET_INDEX = "The pet index provided is invalid for this client.";

    private final Index clientIndex;
    private final Index petIndex; // null for client view, non-null for pet view
    private final boolean isPetView;

    /**
     * Creates a ViewCommand to view the client at the specified index.
     */
    public ViewCommand(Index clientIndex) {
        requireNonNull(clientIndex);
        this.clientIndex = clientIndex;
        this.petIndex = null;
        this.isPetView = false;
    }

    /**
     * Creates a ViewCommand to view the pet at the specified client and pet indices.
     */
    public ViewCommand(Index clientIndex, Index petIndex) {
        requireNonNull(clientIndex);
        requireNonNull(petIndex);
        this.clientIndex = clientIndex;
        this.petIndex = petIndex;
        this.isPetView = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person client = lastShownList.get(clientIndex.getZeroBased());

        if (isPetView) {
            // View pet
            List<Pet> pets = new ArrayList<>(client.getPets());

            if (petIndex.getZeroBased() >= pets.size()) {
                throw new CommandException(MESSAGE_INVALID_PET_INDEX);
            }

            Pet petToView = pets.get(petIndex.getZeroBased());
            return CommandResult.withPetView(
                    String.format(MESSAGE_VIEW_PET_SUCCESS, petToView.getName().fullName, client.getName().fullName),
                    petToView,
                    client);
        } else {
            // View client
            return CommandResult.withClientView(
                    String.format(MESSAGE_VIEW_CLIENT_SUCCESS, client.getName().fullName),
                    client);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        boolean clientIndexEqual = clientIndex.equals(otherViewCommand.clientIndex);
        boolean petIndexEqual = (petIndex == null && otherViewCommand.petIndex == null)
                || (petIndex != null && petIndex.equals(otherViewCommand.petIndex));
        boolean isPetViewEqual = isPetView == otherViewCommand.isPetView;

        return clientIndexEqual && petIndexEqual && isPetViewEqual;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("isPetView", isPetView);

        if (isPetView) {
            builder.add("petIndex", petIndex);
        }

        return builder.toString();
    }
}
