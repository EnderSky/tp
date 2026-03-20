package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears all clients (and their pets) from the address book.
 */
public class ClearClientsCommand extends Command {

    public static final String COMMAND_WORD = "clear clients";
    public static final String COMMAND_WORD_ALIAS = "cc";

    public static final String MESSAGE_SUCCESS = "All clients and their pets have been removed!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
