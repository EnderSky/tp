package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Breed;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Species;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddPetCommand object
 */
public class AddPetCommandParser implements Parser<AddPetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPetCommand
     * and returns an AddPetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_SPECIES,
                        PREFIX_BREED, PREFIX_DOB, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPetCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_SPECIES,
                PREFIX_BREED, PREFIX_DOB);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());

        // Parse optional fields
        Species species = null;
        if (argMultimap.getValue(PREFIX_SPECIES).isPresent()) {
            species = ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_SPECIES).get());
        }

        Breed breed = null;
        if (argMultimap.getValue(PREFIX_BREED).isPresent()) {
            breed = ParserUtil.parseBreed(argMultimap.getValue(PREFIX_BREED).get());
        }

        DateOfBirth dateOfBirth = null;
        if (argMultimap.getValue(PREFIX_DOB).isPresent()) {
            dateOfBirth = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB).get());
        }

        Set<Tag> tags = new HashSet<>();
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        }

        Pet pet = new Pet(name, species, breed, dateOfBirth, tags);

        return new AddPetCommand(pet, phone);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
