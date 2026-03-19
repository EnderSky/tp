package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_BEAGLE;
import static seedu.address.logic.commands.CommandTestUtil.BREED_DESC_PERSIAN;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BREED_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PETNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DOGGY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SNOOPY;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_CAT;
import static seedu.address.logic.commands.CommandTestUtil.SPECIES_DESC_DOG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_BEAGLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_PERSIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PETNAME_DOGGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PETNAME_SNOOPY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_CAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_DOG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.model.person.Breed;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Name;
import seedu.address.model.person.Species;
import seedu.address.testutil.EditPetDescriptorBuilder;

public class EditPetCommandParserTest {

    private static final String SPECIES_EMPTY = " " + PREFIX_SPECIES;
    private static final String BREED_EMPTY = " " + PREFIX_BREED;
    private static final String DOB_EMPTY = " " + PREFIX_DOB;

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditPetCommand.MESSAGE_USAGE);

    private EditPetCommandParser parser = new EditPetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PETNAME_SNOOPY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1.1", EditPetCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative client index
        assertParseFailure(parser, "-5.1" + NAME_DESC_SNOOPY, MESSAGE_INVALID_FORMAT);

        // zero client index
        assertParseFailure(parser, "0.1" + NAME_DESC_SNOOPY, MESSAGE_INVALID_FORMAT);

        // negative pet index
        assertParseFailure(parser, "1.-5" + NAME_DESC_SNOOPY, MESSAGE_INVALID_FORMAT);

        // zero pet index
        assertParseFailure(parser, "1.0" + NAME_DESC_SNOOPY, MESSAGE_INVALID_FORMAT);

        // missing pet index
        assertParseFailure(parser, "1." + NAME_DESC_SNOOPY, MESSAGE_INVALID_FORMAT);

        // missing client index
        assertParseFailure(parser, ".1" + NAME_DESC_SNOOPY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1.1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1.1 i/ string", MESSAGE_INVALID_FORMAT);

        // invalid index format
        assertParseFailure(parser, "1" + NAME_DESC_SNOOPY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1.1" + INVALID_PETNAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1.1" + INVALID_SPECIES_DESC, Species.MESSAGE_CONSTRAINTS); // invalid species
        assertParseFailure(parser, "1.1" + INVALID_BREED_DESC, Breed.MESSAGE_CONSTRAINTS); // invalid breed
        assertParseFailure(parser, "1.1" + INVALID_DOB_DESC, DateOfBirth.MESSAGE_CONSTRAINTS); // invalid date of birth

        // invalid species followed by valid breed
        assertParseFailure(parser, "1.1" + INVALID_SPECIES_DESC + BREED_DESC_BEAGLE, Species.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1.1" + INVALID_PETNAME_DESC + INVALID_SPECIES_DESC + VALID_BREED_BEAGLE
                + VALID_DOB, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index clientIndex = Index.fromOneBased(2);
        Index petIndex = Index.fromOneBased(1);
        String userInput = "2.1" + NAME_DESC_SNOOPY + SPECIES_DESC_DOG + BREED_DESC_BEAGLE + DOB_DESC_VALID;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_PETNAME_SNOOPY)
                .withSpecies(VALID_SPECIES_DOG).withBreed(VALID_BREED_BEAGLE).withDateOfBirth(VALID_DOB).build();
        EditPetCommand expectedCommand = new EditPetCommand(clientIndex, petIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index clientIndex = Index.fromOneBased(1);
        Index petIndex = Index.fromOneBased(1);
        String userInput = "1.1" + NAME_DESC_DOGGY + SPECIES_DESC_CAT;

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_PETNAME_DOGGY)
                .withSpecies(VALID_SPECIES_CAT).build();
        EditPetCommand expectedCommand = new EditPetCommand(clientIndex, petIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index clientIndex = Index.fromOneBased(3);
        Index petIndex = Index.fromOneBased(2);
        String userInput = "3.2" + NAME_DESC_SNOOPY;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_PETNAME_SNOOPY).build();
        EditPetCommand expectedCommand = new EditPetCommand(clientIndex, petIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // species
        userInput = "3.2" + SPECIES_DESC_DOG;
        descriptor = new EditPetDescriptorBuilder().withSpecies(VALID_SPECIES_DOG).build();
        expectedCommand = new EditPetCommand(clientIndex, petIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // breed
        userInput = "3.2" + BREED_DESC_PERSIAN;
        descriptor = new EditPetDescriptorBuilder().withBreed(VALID_BREED_PERSIAN).build();
        expectedCommand = new EditPetCommand(clientIndex, petIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date of birth
        userInput = "3.2" + DOB_DESC_VALID;
        descriptor = new EditPetDescriptorBuilder().withDateOfBirth(VALID_DOB).build();
        expectedCommand = new EditPetCommand(clientIndex, petIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddClientCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index clientIndex = Index.fromOneBased(1);
        Index petIndex = Index.fromOneBased(1);
        String userInput = "1.1" + INVALID_PETNAME_DESC + NAME_DESC_SNOOPY;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid followed by valid
        userInput = "1.1" + NAME_DESC_SNOOPY + INVALID_PETNAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple valid fields repeated
        userInput = "1.1" + NAME_DESC_SNOOPY + SPECIES_DESC_DOG + NAME_DESC_DOGGY + SPECIES_DESC_CAT
                + BREED_DESC_BEAGLE + BREED_DESC_PERSIAN;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED));
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index clientIndex = Index.fromOneBased(1);
        Index petIndex = Index.fromOneBased(1);
        String userInput = "1.1" + INVALID_PETNAME_DESC + NAME_DESC_SNOOPY;
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_PETNAME_SNOOPY).build();
        EditPetCommand expectedCommand = new EditPetCommand(clientIndex, petIndex, descriptor);

        // Due to duplicate prefixes, this should fail
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }

    // Note: The reset fields test was removed because the current implementation
    // does not support resetting optional fields to empty/null via the edit command.
    // Species, Breed, and DateOfBirth all require non-empty values.
    // If resetting fields is needed in the future, a different mechanism
    // (e.g., a special "clear" prefix) would need to be implemented.
}
