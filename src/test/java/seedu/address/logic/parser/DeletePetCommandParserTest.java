package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePetCommand;

public class DeletePetCommandParserTest {
    private DeletePetCommandParser parser = new DeletePetCommandParser();

    @Test
    public void parse_validArgs_returnsDeletePetCommand() {
        // Valid CLIENT.PET index format
        assertParseSuccess(parser, "1.1", new DeletePetCommand(Index.fromOneBased(1), Index.fromOneBased(1)));
        assertParseSuccess(parser, " 2.3 ", new DeletePetCommand(Index.fromOneBased(2), Index.fromOneBased(3)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePetCommand.MESSAGE_USAGE);

        // No dot separator
        assertParseFailure(parser, "1", expectedMessage);

        // Invalid format - missing pet index
        assertParseFailure(parser, "1.", expectedMessage);

        // Invalid format - missing client index
        assertParseFailure(parser, ".1", expectedMessage);

        // Non-numeric client index
        assertParseFailure(parser, "a.1", expectedMessage);

        // Non-numeric pet index
        assertParseFailure(parser, "1.a", expectedMessage);

        // Zero client index
        assertParseFailure(parser, "0.1", expectedMessage);

        // Zero pet index
        assertParseFailure(parser, "1.0", expectedMessage);

        // Negative indices
        assertParseFailure(parser, "-1.1", expectedMessage);
        assertParseFailure(parser, "1.-1", expectedMessage);

        // Empty string
        assertParseFailure(parser, "", expectedMessage);

        // Too many dots
        assertParseFailure(parser, "1.2.3", expectedMessage);
    }
}
