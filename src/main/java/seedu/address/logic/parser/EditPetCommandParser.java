package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPetCommand object
 */
public class EditPetCommandParser implements Parser<EditPetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditPetCommand
     * and returns an EditPetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED, PREFIX_NOTE, PREFIX_PHOTO);
        Index index;

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditPetCommand.MESSAGE_NO_INDEX_PASSED + System.lineSeparator() + EditPetCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.multipleWords(argMultimap.getPreamble())) {
            //more than one word
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditPetCommand.MESSAGE_MANY_WORDS + System.lineSeparator() + EditPetCommand.MESSAGE_USAGE));
        }

        try {
            //index not number
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    pe.getMessage() + System.lineSeparator() + EditPetCommand.MESSAGE_USAGE),
                    pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED, PREFIX_NOTE, PREFIX_PHOTO);

        EditPetDescriptor editPetDescriptor = new EditPetDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPetDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_SPECIES).isPresent()) {
            editPetDescriptor.setSpecies(ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_SPECIES).get()));
        }
        if (argMultimap.getValue(PREFIX_BREED).isPresent()) {
            editPetDescriptor.setBreed(ParserUtil.parseBreed(argMultimap.getValue(PREFIX_BREED).get()));
        }
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editPetDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }
        if (argMultimap.getValue(PREFIX_PHOTO).isPresent()) {
            editPetDescriptor.setPhotoPath(ParserUtil.parsePhotoPath(argMultimap.getValue(PREFIX_PHOTO).get()));
        }

        return new EditPetCommand(index, editPetDescriptor);
    }
}
