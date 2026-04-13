package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_IMAGE_PATH;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Address;
import seedu.address.model.person.Breed;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhotoPath;
import seedu.address.model.person.Species;

public class MessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes_multiplePrefixes_success() {
        String message = Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE);
        assertTrue(message.startsWith(Messages.MESSAGE_DUPLICATE_FIELDS));
        assertTrue(message.contains(CliSyntax.PREFIX_NAME.toString()));
        assertTrue(message.contains(CliSyntax.PREFIX_PHONE.toString()));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_duplicateInputs_deduplicatesInMessage() {
        String message = Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_NAME);
        String expected = Messages.MESSAGE_DUPLICATE_FIELDS + CliSyntax.PREFIX_NAME;
        assertEquals(expected, message);
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_customPrefix_success() {
        Prefix customPrefix = new Prefix("x/");
        String expected = Messages.MESSAGE_DUPLICATE_FIELDS + customPrefix;
        assertEquals(expected, Messages.getErrorMessageForDuplicatePrefixes(customPrefix));
    }

    @Test
    public void format_personWithTags_success() {
        Person person = new Person(new Name("Amy"), new Phone("9123"), new Email("amy@example.com"),
                new Address("Jurong"), new HashSet<>());
        person = new seedu.address.testutil.PersonBuilder(person).withTags("vip").build();

        String formatted = Messages.format(person);
        assertTrue(formatted.contains("Amy"));
        assertTrue(formatted.contains("Phone: 9123"));
        assertTrue(formatted.contains("Email: amy@example.com"));
        assertTrue(formatted.contains("Address: Jurong"));
        assertTrue(formatted.contains("[vip]"));
    }

    @Test
    public void format_personWithoutTags_hasTagsLabelOnly() {
        Person person = new Person(new Name("Bob"), new Phone("9988"), new Email("bob@example.com"),
                new Address("Road"), new HashSet<>());
        String expected = "Bob; Phone: 9988; Email: bob@example.com; Address: Road; Tags: ";
        assertEquals(expected, Messages.format(person));
    }

    @Test
    public void format_petWithPlaceholderPhoto_showsNoPictureProvided() {
        Pet pet = new Pet(new Name("Snoopy"), new Species("Dog"), new Breed("Beagle"), new Note("Friendly"),
                new PhotoPath(PLACEHOLDER_IMAGE_PATH));
        String formatted = Messages.format(pet);
        assertTrue(formatted.contains("Snoopy"));
        assertTrue(formatted.contains("Species: Dog"));
        assertTrue(formatted.contains("Breed: Beagle"));
        assertTrue(formatted.contains("Notes: Friendly"));
        assertTrue(formatted.contains("Picture: No picture provided"));
    }

    @Test
    public void format_petWithNonPlaceholderPhoto_showsPath() {
        Pet pet = new Pet(new Name("Milo"), new Species("Cat"), new Breed("Siamese"), new Note("Cute"),
                new PhotoPath("/images/clock.png"));
        String formatted = Messages.format(pet);
        assertTrue(formatted.contains("Picture: /images/clock.png"));
        assertFalse(formatted.contains("No picture provided"));
        assertNotEquals(Messages.format(new Pet(new Name("Milo"), new Species("Cat"), new Breed("Siamese"),
                new Note("Cute"), new PhotoPath(PLACEHOLDER_IMAGE_PATH))), formatted);
    }
}
