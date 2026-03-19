package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.PetFilterPredicate;

/**
 * Filters the displayed list of clients based on pet species and/or tags.
 * Filters are additive - each new filter adds to existing criteria.
 * Use 'list' command to clear all filters.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_WORD_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters clients by pet species and/or tags.\n"
            + "Filters are additive (they stack). Use 'list' to clear all filters.\n"
            + "Parameters: [" + PREFIX_SPECIES + "SPECIES] [" + PREFIX_TAG + "TAG]...\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " " + PREFIX_SPECIES + "Dog\n"
            + "  " + COMMAND_WORD + " " + PREFIX_TAG + "Anxious\n"
            + "  " + COMMAND_WORD + " " + PREFIX_SPECIES + "Cat " + PREFIX_TAG + "VIP\n"
            + "Alias: " + COMMAND_WORD_ALIAS;

    public static final String MESSAGE_FILTER_SUCCESS = "Showing %1$d client(s) (filtered: %2$s)";
    public static final String MESSAGE_NO_FILTER = "No filter criteria specified. Use "
            + PREFIX_SPECIES + "SPECIES or " + PREFIX_TAG + "TAG to filter.";

    private final PetFilterPredicate predicate;

    /**
     * Creates a FilterCommand to filter by the specified criteria.
     */
    public FilterCommand(PetFilterPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!predicate.hasFilters()) {
            return new CommandResult(MESSAGE_NO_FILTER);
        }

        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(MESSAGE_FILTER_SUCCESS,
                        model.getFilteredPersonList().size(),
                        predicate.getFilterDescription()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
