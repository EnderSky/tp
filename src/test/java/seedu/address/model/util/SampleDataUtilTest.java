package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsNonEmptyArray() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assertTrue(samplePersons.length > 0);
    }

    @Test
    public void getSamplePersons_allPersonsHavePets() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        for (Person person : samplePersons) {
            assertNotNull(person.getPets());
            assertTrue(person.getPets().size() > 0,
                    person.getName() + " should have at least one pet");
        }
    }

    @Test
    public void getSamplePersons_containsExpectedPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(5, samplePersons.length);

        // Verify known sample person names
        assertEquals("Alex Yeoh", samplePersons[0].getName().toString());
        assertEquals("Bernice Yu", samplePersons[1].getName().toString());
        assertEquals("Charlotte Oliveiro", samplePersons[2].getName().toString());
        assertEquals("Irfan Ibrahim", samplePersons[3].getName().toString());
        assertEquals("Roy Balakrishnan", samplePersons[4].getName().toString());
    }

    @Test
    public void getSamplePersons_irfanHasTwoPets() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        // Irfan Ibrahim (index 3) has two pets: Dove and Snoopy
        Person irfan = samplePersons[3];
        assertEquals(2, irfan.getPets().size());
    }

    @Test
    public void getSampleAddressBook_returnsNonNull() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAb);
    }

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(samplePersons.length, sampleAb.getPersonList().size());
    }

    @Test
    public void getTagSet_emptyInput_returnsEmptySet() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet();
        assertNotNull(tagSet);
        assertTrue(tagSet.isEmpty());
    }

    @Test
    public void getTagSet_singleTag_returnsSetWithOneTag() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friend");
        assertEquals(1, tagSet.size());
        assertTrue(tagSet.contains(new Tag("friend")));
    }

    @Test
    public void getTagSet_multipleTags_returnsSetWithAllTags() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friend", "colleague", "neighbour");
        assertEquals(3, tagSet.size());
        assertTrue(tagSet.contains(new Tag("friend")));
        assertTrue(tagSet.contains(new Tag("colleague")));
        assertTrue(tagSet.contains(new Tag("neighbour")));
    }
}
