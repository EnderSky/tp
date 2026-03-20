package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.UnifiedSearchPredicate;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PetBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        UnifiedSearchPredicate firstPredicate =
                new UnifiedSearchPredicate(Collections.singletonList("first"));
        UnifiedSearchPredicate secondPredicate =
                new UnifiedSearchPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        // Using "nonexistent" keyword that won't match any person
        String expectedMessage = String.format(FindCommand.MESSAGE_FIND_SUCCESS, 0, "nonexistent");
        UnifiedSearchPredicate predicate = preparePredicate("nonexistent");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        // Note: UnifiedSearchPredicate uses partial "contains" matching
        // so "Kurz", "Elle", "Kunz" will match persons with those substrings anywhere
        String expectedMessage = String.format(FindCommand.MESSAGE_FIND_SUCCESS, 3, "Kurz, Elle, Kunz");
        UnifiedSearchPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_petTagKeyword_personWithMatchingPetTagFound() {
        // Create a person with a pet that has specific tags
        Pet petWithTags = new PetBuilder().withName("Fluffy").withSpecies("Cat")
                .withTags("playful", "indoor").build();
        Person personWithTaggedPet = new PersonBuilder().withName("Alice Smith")
                .withAddress("123 Test Ave").withEmail("alice@test.com")
                .withPhone("91234567").withPetSet(Set.of(petWithTags)).build();

        // Create a separate model for this test
        Model testModel = new ModelManager();
        testModel.addPerson(personWithTaggedPet);

        // Search for pet tag "playful"
        String expectedMessage = String.format(FindCommand.MESSAGE_FIND_SUCCESS, 1, "playful");
        UnifiedSearchPredicate predicate = preparePredicate("playful");
        FindCommand command = new FindCommand(predicate);

        Model expectedTestModel = new ModelManager();
        expectedTestModel.addPerson(personWithTaggedPet);
        expectedTestModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, testModel, expectedMessage, expectedTestModel);
        assertEquals(Arrays.asList(personWithTaggedPet), testModel.getFilteredPersonList());
    }

    @Test
    public void execute_petTagKeyword_noPersonWithMatchingPetTagFound() {
        // Search for a pet tag that doesn't exist
        String expectedMessage = String.format(FindCommand.MESSAGE_FIND_SUCCESS, 0, "nonexistentpettag");
        UnifiedSearchPredicate predicate = preparePredicate("nonexistentpettag");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        UnifiedSearchPredicate predicate = new UnifiedSearchPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code UnifiedSearchPredicate}.
     */
    private UnifiedSearchPredicate preparePredicate(String userInput) {
        return new UnifiedSearchPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
