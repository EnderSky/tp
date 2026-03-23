package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;

/**
 * Deletes a pet from the address book.
 */
public class DeletePetCommand extends Command {

    public static final String COMMAND_WORD = "deletePet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a pet from the address book. "
            + "Parameters: "
            + PREFIX_NAME + "PET_NAME "
            + PREFIX_PHONE + "OWNER's PHONE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Doggy "
            + PREFIX_PHONE + "98765432 ";

    public static final String MESSAGE_SUCCESS = "Deleted pet: %1$s";
    public static final String MESSAGE_NONEXISTENT_PERSON = "The person with this phone number"
            + " does not exist in the address book";
    public static final String MESSAGE_NONEXISTENT_PET = "The person with this phone number"
            + " does not have a pet with this name";

    private final Pet toDelete;
    private final Phone ownerPhone;

    /**
     * Creates a DeletePetCommand to delete the specified {@code Pet}
     */
    public DeletePetCommand(Pet pet, Phone phone) {
        requireAllNonNull(pet, phone);
        toDelete = pet;
        ownerPhone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // possible failures
        // 1. no Person exists with the given Phone
        // 2. Person with the given Phone does not have a Pet with given name
        // 3. otherwise proceed

        if (!model.hasPhone(ownerPhone)) {
            throw new CommandException(MESSAGE_NONEXISTENT_PERSON);
        }

        if (!model.hasPet(ownerPhone, toDelete)) {
            throw new CommandException(MESSAGE_NONEXISTENT_PET);
        }

        model.removePet(toDelete, ownerPhone);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toDelete)));
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
        return toDelete.equals(otherDeletePetCommand.toDelete)
                && ownerPhone.equals(otherDeletePetCommand.ownerPhone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("pet", toDelete)
                .toString();
    }
}
