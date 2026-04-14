package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.DeletePetCommand;

public class DeletePetCommandParserTest {
    private DeletePetCommandParser parser = new DeletePetCommandParser();

    @Test
    public void parse_validArgs_returnsDeletePetCommand() {
        assertParseSuccess(parser, "1", new DeletePetCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_INDEX
                                + System.lineSeparator() + DeletePersonCommand.MESSAGE_USAGE));
    }
}
