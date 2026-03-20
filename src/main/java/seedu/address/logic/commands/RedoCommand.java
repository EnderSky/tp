package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redoes the last undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_WORD_ALIAS = "r";

    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_FAILURE = "No commands to redo!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redoes the last undone command.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Alias: " + COMMAND_WORD_ALIAS;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof RedoCommand; // instanceof handles nulls
    }

    @Override
    public String toString() {
        return RedoCommand.class.getCanonicalName() + "{}";
    }
}
