package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PET_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_BREED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_BREED_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NOTE_CUTE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NOTE_CUTE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_SPECIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_SPECIES_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.model.person.Name;
import seedu.address.testutil.EditPetDescriptorBuilder;

public class EditPetCommandParserTest {

    private static final String MESSAGE_NO_INDEX_PASSED = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditPetCommand.MESSAGE_NO_INDEX_PASSED + System.lineSeparator() + EditPetCommand.MESSAGE_USAGE);
    private static final String MESSAGE_NOT_EDITED = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditPetCommand.MESSAGE_NOT_EDITED + System.lineSeparator() + EditPetCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_INDEX = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ParserUtil.MESSAGE_INVALID_INDEX + System.lineSeparator() + EditPetCommand.MESSAGE_USAGE);
    private static final String MESSAGE_MANY_WORDS = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditPetCommand.MESSAGE_MANY_WORDS + System.lineSeparator() + EditPetCommand.MESSAGE_USAGE);
    private EditPetCommandParser parser = new EditPetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PET_NAME_DESC, MESSAGE_NO_INDEX_PASSED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_NO_INDEX_PASSED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_PET_NAME_DESC, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + VALID_PET_NAME_DESC, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_MANY_WORDS);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_MANY_WORDS);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_PET_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + VALID_PET_NAME_DESC + VALID_PET_SPECIES_DESC
                + VALID_PET_BREED_DESC + VALID_PET_NOTE_CUTE_DESC;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_PET_NAME)
                .withSpecies(VALID_PET_SPECIES)
                .withBreed(VALID_PET_BREED).withNote(VALID_PET_NOTE_CUTE).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + VALID_PET_BREED_DESC + VALID_PET_SPECIES_DESC;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder()
                .withSpecies(VALID_PET_SPECIES)
                .withBreed(VALID_PET_BREED).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + VALID_PET_NAME_DESC;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder()
                .withName(VALID_PET_NAME).build();
        EditPetCommand expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // species
        userInput = targetIndex.getOneBased() + VALID_PET_SPECIES_DESC;
        descriptor = new EditPetDescriptorBuilder().withSpecies(VALID_PET_SPECIES).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // breed
        userInput = targetIndex.getOneBased() + VALID_PET_BREED_DESC;
        descriptor = new EditPetDescriptorBuilder().withBreed(VALID_PET_BREED).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // note
        userInput = targetIndex.getOneBased() + VALID_PET_NOTE_CUTE_DESC;
        descriptor = new EditPetDescriptorBuilder().withNote(VALID_PET_NOTE_CUTE).build();
        expectedCommand = new EditPetCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddPersonCommandParserTest#parse_repeatedNonTagValue_failure()

        // repeated field
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + VALID_PET_NAME_DESC + VALID_PET_NAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple repeated fields
        userInput = targetIndex.getOneBased() + VALID_PET_NAME_DESC + VALID_PET_NAME_DESC
                + VALID_PET_SPECIES_DESC + VALID_PET_SPECIES_DESC
                + VALID_PET_BREED_DESC + VALID_PET_BREED_DESC
                + VALID_PET_NOTE_CUTE_DESC + VALID_PET_NOTE_CUTE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME,
                        PREFIX_SPECIES, PREFIX_BREED, PREFIX_NOTE));
    }
}
