package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPets.MEOWY;
import static seedu.address.testutil.TypicalPets.SNOOPY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.testutil.EditPetDescriptorBuilder;
import seedu.address.testutil.PetBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditPetCommand.
 */
public class EditPetCommandTest {

    private static final String FIRST_PERSON_PHONE = "94351253";
    private static final String SECOND_PERSON_PHONE = "98765432";
    private static final String DEFAULT_PET_NAME = PetBuilder.DEFAULT_NAME;
    private static final String DIFFERENT_PET_NAME = "not same as petbuilder";

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addPet(SNOOPY, new Phone(FIRST_PERSON_PHONE));

        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(MEOWY).build();
        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(MEOWY));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPet(MEOWY, new Phone(FIRST_PERSON_PHONE));

        assertCommandSuccess(editPetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // edit name
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Pet original = new PetBuilder().build();
        model.addPet(original, new Phone(FIRST_PERSON_PHONE));

        Pet editedPet = new PetBuilder().withName(DIFFERENT_PET_NAME).build();
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(DIFFERENT_PET_NAME).build();
        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPet(editedPet, new Phone(FIRST_PERSON_PHONE));

        assertCommandSuccess(editPetCommand, model, expectedMessage, expectedModel);

        // edit species
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addPet(original, new Phone(FIRST_PERSON_PHONE));
        editedPet = new PetBuilder().withSpecies(DIFFERENT_PET_NAME).build();
        descriptor = new EditPetDescriptorBuilder().withSpecies(DIFFERENT_PET_NAME).build();
        editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, descriptor);
        expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet));

        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPet(editedPet, new Phone(FIRST_PERSON_PHONE));

        assertCommandSuccess(editPetCommand, model, expectedMessage, expectedModel);

        // edit breed
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addPet(original, new Phone(FIRST_PERSON_PHONE));
        editedPet = new PetBuilder().withBreed(DIFFERENT_PET_NAME).build();
        descriptor = new EditPetDescriptorBuilder().withBreed(DIFFERENT_PET_NAME).build();
        editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, descriptor);
        expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet));

        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPet(editedPet, new Phone(FIRST_PERSON_PHONE));

        assertCommandSuccess(editPetCommand, model, expectedMessage, expectedModel);

        // edit note
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addPet(original, new Phone(FIRST_PERSON_PHONE));
        editedPet = new PetBuilder().withNote(DIFFERENT_PET_NAME).build();
        descriptor = new EditPetDescriptorBuilder().withNote(DIFFERENT_PET_NAME).build();
        editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, descriptor);
        expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet));

        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPet(editedPet, new Phone(FIRST_PERSON_PHONE));

        assertCommandSuccess(editPetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        // pet not changed if descriptor empty
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Pet original = new PetBuilder().build();
        model.addPet(original, new Phone(FIRST_PERSON_PHONE));

        Pet editedPet = new PetBuilder().build();
        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON, new EditPetDescriptor());
        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPet(editedPet, new Phone(FIRST_PERSON_PHONE));

        assertCommandSuccess(editPetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        // filter second pet to position 1 and edit position 1
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Pet distraction = new PetBuilder().withName("distraction").build();
        Pet target = new PetBuilder().build();
        model.addPet(distraction, new Phone(FIRST_PERSON_PHONE));
        model.addPet(target, new Phone(SECOND_PERSON_PHONE));

        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Pet editedPet = new PetBuilder().withName(DIFFERENT_PET_NAME).build();
        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON,
                new EditPetDescriptorBuilder().withName(DIFFERENT_PET_NAME).build());
        String expectedMessage = String.format(EditPetCommand.MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPet(distraction, new Phone(FIRST_PERSON_PHONE));
        expectedModel.addPet(editedPet, new Phone(SECOND_PERSON_PHONE));
        showPersonAtIndex(expectedModel, INDEX_SECOND_PERSON);
        assertCommandSuccess(editPetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePetUnfilteredList_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Pet toEdit = new PetBuilder().build();
        Pet existing = new PetBuilder().withName(DIFFERENT_PET_NAME).build();
        model.addPet(toEdit, new Phone(FIRST_PERSON_PHONE));
        model.addPet(existing, new Phone(FIRST_PERSON_PHONE));

        EditPetCommand editPetCommand = new EditPetCommand(INDEX_FIRST_PERSON,
                new EditPetDescriptorBuilder().withName(DIFFERENT_PET_NAME).build());

        assertCommandFailure(editPetCommand, model, EditPetCommand.MESSAGE_DUPLICATE_PET);
    }

    @Test
    public void execute_invalidPetIndexUnfilteredList_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(100000);
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(DIFFERENT_PET_NAME).build();
        EditPetCommand editPetCommand = new EditPetCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editPetCommand, model, EditPetCommand.MESSAGE_INDEX_TOO_LARGE);
    }

    @Test
    public void equals() {
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder().withName(DIFFERENT_PET_NAME).build();
        final EditPetCommand standardCommand = new EditPetCommand(INDEX_FIRST_PERSON, descriptor);

        // same values -> returns true
        EditPetDescriptor copyDescriptor = new EditPetDescriptorBuilder().withName(DIFFERENT_PET_NAME).build();
        EditPetCommand commandWithSameValues = new EditPetCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_SECOND_PERSON, descriptor)));

        // different descriptor -> returns false
        EditPetDescriptor differentDescriptor = new EditPetDescriptorBuilder().withName(DEFAULT_PET_NAME).build();
        assertFalse(standardCommand.equals(new EditPetCommand(INDEX_FIRST_PERSON, differentDescriptor)));

        // different photopath -> returns false
        EditPetDescriptor descriptorWithPhoto1 = new EditPetDescriptorBuilder().withPhotoPath("/images/clock.png")
                .build();
        EditPetDescriptor descriptorWithPhoto2 = new EditPetDescriptorBuilder().withPhotoPath("/images/fail.png")
                .build();

        EditPetCommand commandWithPhoto1 = new EditPetCommand(INDEX_FIRST_PERSON, descriptorWithPhoto1);
        EditPetCommand commandWithPhoto2 = new EditPetCommand(INDEX_FIRST_PERSON, descriptorWithPhoto2);

        assertFalse(commandWithPhoto1.equals(commandWithPhoto2));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPetDescriptor editPetDescriptor = new EditPetDescriptor();
        EditPetCommand editPetCommand = new EditPetCommand(index, editPetDescriptor);
        String expected = EditPetCommand.class.getCanonicalName() + "{index=" + index + ", editPetDescriptor="
                + editPetDescriptor + "}";
        assertEquals(expected, editPetCommand.toString());
    }

}
