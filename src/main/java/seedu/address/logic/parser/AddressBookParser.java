package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddPetCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.DeletePetCommand;
import seedu.address.logic.commands.EditClientCommand;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ViewClientCommand;
import seedu.address.logic.commands.ViewPetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     * Supports both single-word commands (e.g., "list") and two-word commands (e.g., "add pet").
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Pattern TWO_WORD_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+\\s+\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final String trimmedInput = userInput.trim();

        if (trimmedInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        // First, try to match two-word commands
        final Matcher twoWordMatcher = TWO_WORD_COMMAND_FORMAT.matcher(trimmedInput);
        if (twoWordMatcher.matches()) {
            final String twoWordCommand = twoWordMatcher.group("commandWord").toLowerCase();
            final String twoWordArguments = twoWordMatcher.group("arguments");

            Command command = parseTwoWordCommand(twoWordCommand, twoWordArguments);
            if (command != null) {
                return command;
            }
        }

        // Fall back to single-word commands
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmedInput);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        return parseSingleWordCommand(commandWord, arguments);
    }

    /**
     * Parses two-word commands (e.g., "add pet", "delete client").
     *
     * @return the parsed Command, or null if the command word is not a recognized two-word command
     */
    private Command parseTwoWordCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        case AddPetCommand.COMMAND_WORD:
        case AddPetCommand.COMMAND_WORD_ALIAS:
            return new AddPetCommandParser().parse(arguments);

        case AddPersonCommand.COMMAND_WORD:
        case AddPersonCommand.COMMAND_WORD_ALIAS:
            return new AddPersonCommandParser().parse(arguments);

        case DeletePersonCommand.COMMAND_WORD:
        case DeletePersonCommand.COMMAND_WORD_ALIAS:
            return new DeletePersonCommandParser().parse(arguments);

        case DeletePetCommand.COMMAND_WORD:
        case DeletePetCommand.COMMAND_WORD_ALIAS:
            return new DeletePetCommandParser().parse(arguments);

        case EditClientCommand.COMMAND_WORD:
        case EditClientCommand.COMMAND_WORD_ALIAS:
            return new EditClientCommandParser().parse(arguments);

        case EditPetCommand.COMMAND_WORD:
        case EditPetCommand.COMMAND_WORD_ALIAS:
            return new EditPetCommandParser().parse(arguments);

        case ViewClientCommand.COMMAND_WORD:
        case ViewClientCommand.COMMAND_WORD_ALIAS:
            return new ViewClientCommandParser().parse(arguments);

        case ViewPetCommand.COMMAND_WORD:
        case ViewPetCommand.COMMAND_WORD_ALIAS:
            return new ViewPetCommandParser().parse(arguments);

        default:
            return null; // Not a recognized two-word command
        }
    }

    /**
     * Parses single-word commands (e.g., "list", "help").
     */
    private Command parseSingleWordCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD_ALIAS:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD_ALIAS:
            return new HelpCommand();

        // Command hints for ambiguous commands
        case "add":
            throw new ParseException("Did you mean:\n"
                    + "  • add client (ac) - Add a new client\n"
                    + "  • add pet (ap) - Add a pet to a client");

        case "edit":
            throw new ParseException("Did you mean:\n"
                    + "  • edit client (ec) - Edit an existing client\n"
                    + "  • edit pet (ep) - Edit an existing pet");

        case "delete":
            throw new ParseException("Did you mean:\n"
                    + "  • delete client (dc) - Delete a client\n"
                    + "  • delete pet (dp) - Delete a pet");

        case "view":
            throw new ParseException("Did you mean:\n"
                    + "  • view client (vc) - View client details\n"
                    + "  • view pet (vp) - View pet details");

        default:
            logger.finer("This user input caused a ParseException: " + commandWord + arguments);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
