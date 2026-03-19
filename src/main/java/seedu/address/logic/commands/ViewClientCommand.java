package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Views detailed information of a client identified by index in the displayed list.
 */
public class ViewClientCommand extends Command {

    public static final String COMMAND_WORD = "view client";
    public static final String COMMAND_WORD_ALIAS = "vc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the client identified by the index number used in the displayed client list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_CLIENT_SUCCESS = "Viewing client: %1$s";

    private final Index targetIndex;

    /**
     * Creates a ViewClientCommand to view the client at the specified {@code Index}.
     */
    public ViewClientCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person clientToView = lastShownList.get(targetIndex.getZeroBased());
        return CommandResult.withClientView(
                String.format(MESSAGE_VIEW_CLIENT_SUCCESS, clientToView.getName().fullName),
                clientToView);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewClientCommand)) {
            return false;
        }

        ViewClientCommand otherViewClientCommand = (ViewClientCommand) other;
        return targetIndex.equals(otherViewClientCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
