package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;

/**
 * Adds a person to the address book.
 */
public class AddPetCommand extends Command {

    public static final String COMMAND_WORD = "addpet";
    public static final String ALIAS = "ap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pet to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "OWNER's PHONE "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_NOTE + "NOTES "
            + PREFIX_PHOTO + "PHOTO PATH "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Doggy "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_SPECIES + "Dog "
            + PREFIX_BREED + "Labrador "
            + PREFIX_NOTE + "hyper and requires extra care "
            + PREFIX_PHOTO + "C:\\Users\\DummyUser\\Photos\\doggy.png";

    public static final String MESSAGE_SUCCESS = "New pet added: %1$s";
    public static final String MESSAGE_NONEXISTENT_PERSON = "There is no client with this phone number";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists in the address book";

    private final Pet toAdd;
    private final Phone ownerPhone;

    /**
     * Creates an AddPersonCommand to add the specified {@code Person}
     */
    public AddPetCommand(Pet pet, Phone phone) {
        requireAllNonNull(pet, phone);
        toAdd = pet;
        ownerPhone = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPhone(ownerPhone)) {
            throw new CommandException(MESSAGE_NONEXISTENT_PERSON);
        }

        if (model.hasPet(ownerPhone, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }

        model.addPet(toAdd, ownerPhone);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
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
        return toAdd.equals(otherAddPetCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("addPet", toAdd)
                .toString();
    }
}
