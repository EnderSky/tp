package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object.
 * Handles both CLIENT_INDEX format (for viewing clients) and CLIENT_INDEX.PET_INDEX format (for viewing pets).
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     *
     * @param args The arguments string. Can be:
     *             - A single index (e.g., "1") for viewing a client
     *             - A client.pet index format (e.g., "1.2") for viewing a pet
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        try {
            // Check if it's a pet index format (contains a dot)
            if (trimmedArgs.contains(".")) {
                // Parse as CLIENT_INDEX.PET_INDEX format
                Index[] indices = ParserUtil.parsePetIndex(trimmedArgs);
                return new ViewCommand(indices[0], indices[1]);
            } else {
                // Parse as single CLIENT_INDEX format
                Index clientIndex = ParserUtil.parseIndex(trimmedArgs);
                return new ViewCommand(clientIndex);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }
    }
}
