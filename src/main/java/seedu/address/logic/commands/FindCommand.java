package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.UnifiedSearchPredicate;

/**
 * Finds and lists all clients and pets whose attributes contain any of the argument keywords.
 * Searches across all attributes including client name, phone, email, address, tags,
 * and pet name, species, breed.
 * Keyword matching is case-insensitive and uses partial "contains" matching.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all clients and pets whose attributes "
            + "contain any of the specified keywords (case-insensitive, partial match).\n"
            + "Searches: client name, phone, email, address, tags, pet name, species, breed\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " alice - finds clients/pets with 'alice' in any field\n"
            + "  " + COMMAND_WORD + " dog golden - finds dogs or golden retrievers\n"
            + "  " + COMMAND_WORD + " 91234567 - finds by phone number\n"
            + "Alias: " + COMMAND_WORD_ALIAS;

    public static final String MESSAGE_FIND_SUCCESS = "%1$d client(s) found matching '%2$s'";

    private final UnifiedSearchPredicate predicate;

    public FindCommand(UnifiedSearchPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(MESSAGE_FIND_SUCCESS,
                        model.getFilteredPersonList().size(),
                        String.join(", ", predicate.getKeywords())));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
