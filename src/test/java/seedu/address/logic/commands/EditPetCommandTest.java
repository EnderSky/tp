package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DOGGY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SNOOPY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_PERSIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PETNAME_DOGGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_CAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPets.FLUFFY;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.testutil.EditPetDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PetBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditPetCommand.
 */
public class EditPetCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // Create a person with a pet to edit
        Person personWithPet = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Pet> pets = new LinkedHashSet<>();
        pets.add(SNOOPY);
        Person personWithAddedPet = new PersonBuilder(personWithPet).withPetSet(pets).build();
        model.setPerson(personWithPet, personWithAddedPet);

        // Build edited pet from SNOOPY to preserve DOB
        Pet editedPet = new PetBuilder(SNOOPY).withName(VALID_PETNAME_DOGGY)
                .withSpecies(VALID_SPECIES_CAT).withBreed(VALID_BREED_PERSIAN).build();
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(editedPet).build();
        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1), descriptor);

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet), personWithAddedPet.getName().fullName);

        Set<Pet> expectedPets = new LinkedHashSet<>();
        expectedPets.add(editedPet);
        Person expectedPerson = new PersonBuilder(personWithAddedPet).withPetSet(expectedPets).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithAddedPet, expectedPerson);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                expectedPerson, editedPet, expectedPerson);
        assertCommandSuccess(editPetCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // Create a person with a pet to edit
        Person personWithPet = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Pet> pets = new LinkedHashSet<>();
        pets.add(SNOOPY);
        Person personWithAddedPet = new PersonBuilder(personWithPet).withPetSet(pets).build();
        model.setPerson(personWithPet, personWithAddedPet);

        Pet editedPet = new PetBuilder(SNOOPY).withName(VALID_PETNAME_DOGGY).build();
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_PETNAME_DOGGY).build();
        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1), descriptor);

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet), personWithAddedPet.getName().fullName);

        Set<Pet> expectedPets = new LinkedHashSet<>();
        expectedPets.add(editedPet);
        Person expectedPerson = new PersonBuilder(personWithAddedPet).withPetSet(expectedPets).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithAddedPet, expectedPerson);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                expectedPerson, editedPet, expectedPerson);
        assertCommandSuccess(editPetCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        // Create a person with a pet to edit
        Person personWithPet = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Pet> pets = new LinkedHashSet<>();
        pets.add(SNOOPY);
        Person personWithAddedPet = new PersonBuilder(personWithPet).withPetSet(pets).build();
        model.setPerson(personWithPet, personWithAddedPet);

        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1),
                new EditPetDescriptor());

        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(SNOOPY), personWithAddedPet.getName().fullName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // When no fields are edited, the person stays the same
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                personWithAddedPet, SNOOPY, personWithAddedPet);
        assertCommandSuccess(editPetCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicatePet_failure() {
        // Create a person with two pets
        Person personWithPets = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Pet> pets = new LinkedHashSet<>();
        pets.add(SNOOPY);
        pets.add(FLUFFY);
        Person personWithAddedPets = new PersonBuilder(personWithPets).withPetSet(pets).build();
        model.setPerson(personWithPets, personWithAddedPets);

        // Try to edit second pet to have same name as first pet
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName("Snoopy").build();
        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2), descriptor);

        assertCommandFailure(editPetCommand, model, EditPetCommand.MESSAGE_DUPLICATE_PET);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_PETNAME_DOGGY).build();
        EditPetCommand editPetCommand = new EditPetCommand(outOfBoundIndex, Index.fromOneBased(1), descriptor);

        assertCommandFailure(editPetCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPetIndexUnfilteredList_failure() {
        // Create a person with a pet
        Person personWithPet = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Pet> pets = new LinkedHashSet<>();
        pets.add(SNOOPY);
        Person personWithAddedPet = new PersonBuilder(personWithPet).withPetSet(pets).build();
        model.setPerson(personWithPet, personWithAddedPet);

        Index outOfBoundIndex = Index.fromOneBased(2); // Only one pet exists
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(VALID_PETNAME_DOGGY).build();
        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, outOfBoundIndex, descriptor);

        assertCommandFailure(editPetCommand, model, Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPetCommand standardCommand = new EditPetCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1),
                DESC_SNOOPY);

        // same values -> returns true
        EditPetDescriptor copyDescriptor = new EditPetDescriptor(DESC_SNOOPY);
        EditPetCommand commandWithSameValues = new EditPetCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1),
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different client index -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1),
                DESC_SNOOPY)));

        // different pet index -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2),
                DESC_SNOOPY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1),
                DESC_DOGGY)));
    }

    @Test
    public void toStringMethod() {
        Index clientIndex = Index.fromOneBased(1);
        Index petIndex = Index.fromOneBased(1);
        EditPetDescriptor editPetDescriptor = new EditPetDescriptor();
        EditPetCommand editPetCommand = new EditPetCommand(clientIndex, petIndex, editPetDescriptor);
        String expected = EditPetCommand.class.getCanonicalName() + "{clientIndex=" + clientIndex
                + ", petIndex=" + petIndex + ", editPetDescriptor=" + editPetDescriptor + "}";
        assertEquals(expected, editPetCommand.toString());
    }
}
