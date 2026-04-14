package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PetBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddPetCommand}.
 */
public class AddPetCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPet_success() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        Pet validPet = new PetBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPet(validPet, personInList.getPhone());

        assertCommandSuccess(new AddPetCommand(validPet, personInList.getPhone()), model,
                String.format(AddPetCommand.MESSAGE_SUCCESS, Messages.format(validPet)),
                expectedModel);
    }

    @Test
    public void execute_nonexistentPerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        Pet validPet = new PetBuilder().build();

        assertCommandFailure(new AddPetCommand(validPet, validPerson.getPhone()), model,
                AddPetCommand.MESSAGE_NONEXISTENT_PERSON + System.lineSeparator() + AddPetCommand.MESSAGE_USAGE);
    }

}
