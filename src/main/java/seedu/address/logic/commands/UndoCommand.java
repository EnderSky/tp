package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the last command that modified the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_WORD_ALIAS = "u";

    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAILURE = "No commands to undo!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the last command that modified data.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Alias: " + COMMAND_WORD_ALIAS;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof UndoCommand; // instanceof handles nulls
    }

    @Override
    public String toString() {
        return UndoCommand.class.getCanonicalName() + "{}";
    }
}
