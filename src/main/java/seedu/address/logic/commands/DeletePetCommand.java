package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * Deletes a pet identified using its displayed index from the address book.
 */
public class DeletePetCommand extends Command {

    public static final String COMMAND_WORD = "deletepet";
    public static final String ALIAS = "dp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the pet with the given POSITION.\n"
            + "User inputs: POSITION\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted Pet: %1$s";
    public static final String MESSAGE_INDEX_TOO_SMALL = "The POSITION provided should be 1 or more";
    public static final String MESSAGE_INDEX_TOO_LARGE = "The POSITION provided is too large";

    private final Index index;

    /**
     * Creates a DeletePetCommand to delete the pet at {@code index}
     */
    public DeletePetCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();

        Person owner = null;
        Pet petToDelete = null;
        int petCounter = 0;
        for (Person person : personList) {
            for (Pet pet : person.getPets()) {
                petCounter++;
                if (petCounter == index.getOneBased()) {
                    owner = person;
                    petToDelete = pet;
                }
            }
        }
        if (petToDelete == null) {
            throw new CommandException(MESSAGE_INDEX_TOO_LARGE);
        }

        model.setPerson(owner, owner.removePet(petToDelete));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(petToDelete)));
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
        return index.equals(otherDeletePetCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
