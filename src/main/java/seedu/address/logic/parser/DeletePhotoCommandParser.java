package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePhotoCommand object.
 */
public class DeletePhotoCommandParser implements Parser<DeletePhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePhotoCommand
     * and returns a DeletePhotoCommand object for execution.
     * Format: CLIENT_INDEX.PET_INDEX
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePhotoCommand parse(String args) throws ParseException {
        try {
            Index[] indices = ParserUtil.parsePetIndex(args);
            return new DeletePhotoCommand(indices[0], indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePhotoCommand.MESSAGE_USAGE), pe);
        }
    }
}
