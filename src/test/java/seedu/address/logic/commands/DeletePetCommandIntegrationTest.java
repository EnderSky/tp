package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.testutil.PetBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code DeletePetCommand}.
 */
public class DeletePetCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person personInList = model.getAddressBook().getPersonList().get(0);
        Pet validPet = new PetBuilder().build();
        model.addPet(validPet, personInList.getPhone());

        assertCommandSuccess(new DeletePetCommand(INDEX_FIRST_PERSON), model,
                String.format(DeletePetCommand.MESSAGE_SUCCESS, Messages.format(validPet)),
                expectedModel);
    }

    @Test
    public void execute_outOfBoundIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(100000);

        String expectedMessage = String.format(DeletePetCommand.MESSAGE_INDEX_TOO_LARGE,
                model.getTotalPets()) + System.lineSeparator() + DeletePetCommand.MESSAGE_USAGE;

        assertCommandFailure(new DeletePetCommand(outOfBoundIndex), model, expectedMessage);
    }

}
