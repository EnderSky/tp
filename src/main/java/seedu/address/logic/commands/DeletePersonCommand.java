package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NO_CLIENTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeletePersonCommand extends Command {

    public static final String COMMAND_WORD = "deleteclient";
    public static final String ALIAS = "dc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the client with the given POSITION.\n"
            + "User inputs: POSITION\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Client: %1$s";
    public static final String MESSAGE_NO_INDEX_PASSED = "No POSITION was detected.";
    public static final String MESSAGE_ADD_CLIENT_FIRST = "Add a client to the list first.";
    public static final String MESSAGE_INDEX_TOO_SMALL = "The POSITION provided should be 1 or more";
    public static final String MESSAGE_INDEX_TOO_LARGE = "The POSITION provided is too large. "
            + "Choose a number between 1 and %s.";

    private final Index targetIndex;

    public DeletePersonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_CLIENTS + " " + MESSAGE_ADD_CLIENT_FIRST
                    + System.lineSeparator() + MESSAGE_USAGE);
        }
        int noOfClientsShown = lastShownList.size();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            String indexMessage = String.format(MESSAGE_INDEX_TOO_LARGE, noOfClientsShown);
            throw new CommandException(indexMessage + System.lineSeparator() + MESSAGE_USAGE);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePersonCommand)) {
            return false;
        }

        DeletePersonCommand otherDeletePersonCommand = (DeletePersonCommand) other;
        return targetIndex.equals(otherDeletePersonCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
