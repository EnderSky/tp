package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddNoteCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddPetCommand;
import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.commands.ClearClientsCommand;
import seedu.address.logic.commands.ClearPetsCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteNoteCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.DeletePetCommand;
import seedu.address.logic.commands.DeletePhotoCommand;
import seedu.address.logic.commands.EditClientCommand;
import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ViewCommand;
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

        case ViewCommand.COMMAND_WORD:
        case ViewCommand.COMMAND_WORD_ALIAS:
            return new ViewCommandParser().parse(arguments);

        case AddNoteCommand.COMMAND_WORD:
        case AddNoteCommand.COMMAND_WORD_ALIAS:
            return new AddNoteCommandParser().parse(arguments);

        case EditNoteCommand.COMMAND_WORD:
        case EditNoteCommand.COMMAND_WORD_ALIAS:
            return new EditNoteCommandParser().parse(arguments);

        case DeleteNoteCommand.COMMAND_WORD:
        case DeleteNoteCommand.COMMAND_WORD_ALIAS:
            return new DeleteNoteCommandParser().parse(arguments);

        case AddPhotoCommand.COMMAND_WORD:
        case AddPhotoCommand.COMMAND_WORD_ALIAS:
            return new AddPhotoCommandParser().parse(arguments);

        case DeletePhotoCommand.COMMAND_WORD:
        case DeletePhotoCommand.COMMAND_WORD_ALIAS:
            return new DeletePhotoCommandParser().parse(arguments);

        case ClearPetsCommand.COMMAND_WORD:
        case ClearPetsCommand.COMMAND_WORD_ALIAS:
            return new ClearPetsCommand();

        case ClearClientsCommand.COMMAND_WORD:
        case ClearClientsCommand.COMMAND_WORD_ALIAS:
            return new ClearClientsCommand();

        default:
            return null; // Not a recognized two-word command
        }
    }

    /**
     * Parses single-word commands (e.g., "list", "help").
     */
    private Command parseSingleWordCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD_ALIAS:
            return new ListCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD_ALIAS:
            return new HelpCommand();

        case ViewCommand.COMMAND_WORD:
        case ViewCommand.COMMAND_WORD_ALIAS:
            return new ViewCommandParser().parse(arguments);

        // Command hints for ambiguous commands
        case "add":
            throw new ParseException("Did you mean:\n"
                    + "  • add client (ac) - Add a new client\n"
                    + "  • add pet (ap) - Add a pet to a client\n"
                    + "  • add note (an) - Add grooming notes to a pet\n"
                    + "  • add photo (aph) - Add a photo to a pet");

        case "edit":
            throw new ParseException("Did you mean:\n"
                    + "  • edit client (ec) - Edit an existing client\n"
                    + "  • edit pet (ep) - Edit an existing pet\n"
                    + "  • edit note (en) - Edit grooming notes");

        case "delete":
            throw new ParseException("Did you mean:\n"
                    + "  • delete client (dc) - Delete a client\n"
                    + "  • delete pet (dp) - Delete a pet\n"
                    + "  • delete note (dn) - Delete grooming notes\n"
                    + "  • delete photo (dph) - Delete a pet photo");

        case "clear":
            throw new ParseException("Did you mean:\n"
                    + "  • clear pets (cp) - Remove all pets\n"
                    + "  • clear clients (cc) - Remove all clients");

        default:
            logger.finer("This user input caused a ParseException: " + commandWord + arguments);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
