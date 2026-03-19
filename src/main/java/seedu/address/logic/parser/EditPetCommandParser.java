package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPetCommand object
 */
public class EditPetCommandParser implements Parser<EditPetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPetCommand
     * and returns an EditPetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED, PREFIX_DOB);

        Index[] indices;
        try {
            indices = ParserUtil.parsePetIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditPetCommand.MESSAGE_USAGE), pe);
        }

        Index clientIndex = indices[0];
        Index petIndex = indices[1];

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_SPECIES,
                PREFIX_BREED, PREFIX_DOB);

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
        if (argMultimap.getValue(PREFIX_DOB).isPresent()) {
            editPetDescriptor.setDateOfBirth(
                    ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB).get()));
        }

        if (!editPetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPetCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPetCommand(clientIndex, petIndex, editPetDescriptor);
    }
}
