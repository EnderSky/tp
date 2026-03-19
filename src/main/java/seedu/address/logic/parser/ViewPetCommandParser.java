package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewPetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewPetCommand object.
 */
public class ViewPetCommandParser implements Parser<ViewPetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewPetCommand
     * and returns a ViewPetCommand object for execution.
     * The argument should be in CLIENT_INDEX.PET_INDEX format (e.g., "1.2").
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewPetCommand parse(String args) throws ParseException {
        try {
            Index[] indices = ParserUtil.parsePetIndex(args);
            return new ViewPetCommand(indices[0], indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPetCommand.MESSAGE_USAGE), pe);
        }
    }
}
