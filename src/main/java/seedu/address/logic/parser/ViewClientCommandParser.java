package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewClientCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewClientCommand object.
 */
public class ViewClientCommandParser implements Parser<ViewClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewClientCommand
     * and returns a ViewClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewClientCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewClientCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewClientCommand.MESSAGE_USAGE), pe);
        }
    }
}
