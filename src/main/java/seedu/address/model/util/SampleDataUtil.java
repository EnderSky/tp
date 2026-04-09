package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("Regular"))
                .addPet(new Pet(new Name("Milo"), new Species("Cat"), new Breed("Siamese"),
                        new Note("Cute"), new PhotoPath("/images/pets/milo.jpg"))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet())
                .addPet(new Pet(new Name("Carrots"), new Species("Rabbit"), new Breed("Orange"),
                        new Note("Munch"), new PhotoPath("/images/pets/carrots.jpg"))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"))
                .addPet(new Pet(new Name("Bella"), new Species("Dog"), new Breed("Labrador"),
                        new Note("Loves ear rubs"), new PhotoPath("/images/pets/bella.jpg"))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"))
                .addPet(new Pet(new Name("Dove"), new Species("Dog"), new Breed("Poodle"),
                        new Note("Lovely"), new PhotoPath("/images/pets/dove.jpg")))
                .addPet(new Pet(new Name("Snoopy"), new Species("Dog"), new Breed("Beagle"),
                        new Note("Loves belly rubs"), new PhotoPath("/images/pets/snoopy.jfif"))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
                .addPet(new Pet(new Name("Fluffy"), new Species("Dog"), new Breed("Shih Tzu"),
                        new Note("Shy"), new PhotoPath("/images/pets/fluffy.jpg")))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
