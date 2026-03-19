package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PetFilterPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SPECIES, PREFIX_TAG);

        // Parse optional species filter
        Optional<String> speciesFilter = Optional.empty();
        if (argMultimap.getValue(PREFIX_SPECIES).isPresent()) {
            String species = argMultimap.getValue(PREFIX_SPECIES).get().trim();
            if (!species.isEmpty()) {
                speciesFilter = Optional.of(species);
            }
        }

        // Parse tag filters
        Set<Tag> tagFilters = argMultimap.getAllValues(PREFIX_TAG).stream()
                .filter(s -> !s.trim().isEmpty())
                .map(s -> {
                    try {
                        return ParserUtil.parseTag(s);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        PetFilterPredicate predicate = new PetFilterPredicate(speciesFilter, tagFilters);

        return new FilterCommand(predicate);
    }
}
