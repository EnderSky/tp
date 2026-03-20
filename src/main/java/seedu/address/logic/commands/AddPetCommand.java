package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a pet to an existing client in the address book.
 */
public class AddPetCommand extends Command {

    public static final String COMMAND_WORD = "add pet";
    public static final String COMMAND_WORD_ALIAS = "ap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pet to a client in the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "OWNER'S PHONE "
            + "[" + PREFIX_SPECIES + "SPECIES] "
            + "[" + PREFIX_BREED + "BREED] "
            + "[" + PREFIX_DOB + "DATE_OF_BIRTH] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Fluffy "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_SPECIES + "Dog "
            + PREFIX_BREED + "Golden Retriever "
            + PREFIX_DOB + "2020-03-15 "
            + PREFIX_TAG + "Anxious";

    public static final String MESSAGE_SUCCESS = "New pet added: %1$s";
    public static final String MESSAGE_OWNER_NOT_FOUND = "No client found with phone number: %1$s";

    private final Pet toAdd;
    private final Phone ownerPhone;

    /**
     * Creates an AddPetCommand to add the specified {@code Pet} to the client with the given phone.
     */
    public AddPetCommand(Pet pet, Phone phone) {
        requireAllNonNull(pet, phone);
        toAdd = pet;
        ownerPhone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addPet(toAdd, ownerPhone);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_OWNER_NOT_FOUND, ownerPhone));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPetCommand)) {
            return false;
        }

        AddPetCommand otherAddPetCommand = (AddPetCommand) other;
        return toAdd.equals(otherAddPetCommand.toAdd)
                && ownerPhone.equals(otherAddPetCommand.ownerPhone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("pet", toAdd)
                .add("ownerPhone", ownerPhone)
                .toString();
    }
}
