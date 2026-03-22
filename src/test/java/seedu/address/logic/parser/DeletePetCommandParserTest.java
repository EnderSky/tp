package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePetCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PetBuilder;

public class DeletePetCommandParserTest {
    private DeletePetCommandParser parser = new DeletePetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Pet expectedPet = new PetBuilder().withSpecies("").withBreed("").build();

        // whitespace only preamble
        assertParseSuccess(parser, VALID_PET_NAME_DESC + PHONE_DESC_AMY,
                new DeletePetCommand(expectedPet, new Phone(VALID_PHONE_AMY)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePetCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, SNOOPY.getName().fullName + PHONE_DESC_AMY, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, VALID_PET_NAME_DESC + "999", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VALID_PET_NAME_DESC + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
    }
}
