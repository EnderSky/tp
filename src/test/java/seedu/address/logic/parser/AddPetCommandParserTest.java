package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_LABRADOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PET_BREED_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PET_SPECIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SNOOPY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_DOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NAME_SNOOPY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPetCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PetBuilder;

public class AddPetCommandParserTest {
    private AddPetCommandParser parser = new AddPetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Pet expectedPet = new PetBuilder(SNOOPY).build();
        Phone expectedPhone = new Phone(VALID_PHONE_AMY);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_SNOOPY
                        + SPECIES_DESC_DOG + BREED_DESC_LABRADOR + PHONE_DESC_AMY,
                new AddPetCommand(expectedPet, expectedPhone));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedPetString = NAME_DESC_SNOOPY + SPECIES_DESC_DOG + BREED_DESC_LABRADOR + PHONE_DESC_AMY;

        // multiple names
        assertParseFailure(parser, NAME_DESC_SNOOPY + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple species
        assertParseFailure(parser, SPECIES_DESC_DOG + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES));

        // multiple breed
        assertParseFailure(parser, BREED_DESC_LABRADOR + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BREED));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid species
        assertParseFailure(parser, INVALID_PET_SPECIES_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SPECIES));

        // invalid breed
        assertParseFailure(parser, INVALID_PET_BREED_DESC + validExpectedPetString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BREED));
    }

    @Test void parse_optionalFieldsMissing_success() {
        Pet expectedPet = new PetBuilder(SNOOPY).withSpecies("Unknown").withBreed("Unknown").withNote("None").build();
        Phone expectedPhone = new Phone(VALID_PHONE_AMY);

        // missing species, breed and note
        assertParseSuccess(parser, NAME_DESC_SNOOPY + PHONE_DESC_AMY,
                new AddPetCommand(expectedPet, expectedPhone));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPetCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_PET_NAME_SNOOPY + PHONE_DESC_AMY, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_SNOOPY + VALID_PHONE_AMY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_AMY,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid species
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_AMY + INVALID_PET_SPECIES_DESC,
                String.format(Name.MESSAGE_CONSTRAINTS));

        // invalid breed
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_AMY + INVALID_PET_BREED_DESC,
                String.format(Name.MESSAGE_CONSTRAINTS));

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PHONE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_SNOOPY + PHONE_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPetCommand.MESSAGE_USAGE));
    }
}
