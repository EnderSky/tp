package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.PetNameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book who have pets whose names contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPetCommand extends Command {

    public static final String COMMAND_WORD = "find pet";
    public static final String COMMAND_WORD_ALIAS = "fp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all pets whose names contain any of "
            + "the specified keywords (case-insensitive) and displays their owners as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " fluffy whiskers buddy\n"
            + "Alias: " + COMMAND_WORD_ALIAS;

    public static final String MESSAGE_PETS_FOUND = "%1$d client(s) with matching pets found!";

    private final PetNameContainsKeywordsPredicate predicate;

    public FindPetCommand(PetNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(MESSAGE_PETS_FOUND, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindPetCommand)) {
            return false;
        }

        FindPetCommand otherFindPetCommand = (FindPetCommand) other;
        return predicate.equals(otherFindPetCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
