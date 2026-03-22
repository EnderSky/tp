package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Phone phone;

    // Data fields
    private final Name name;
    private final Email email;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Pet> pets = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Person person, List<Pet> pets) {
        requireAllNonNull(person, pets);
        this.name = person.getName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        this.address = person.getAddress();
        this.tags.addAll(person.getTags());
        this.pets.addAll(pets);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Person person, boolean isAdd, Pet pet) {
        requireAllNonNull(person, pet);
        this.name = person.getName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        this.address = person.getAddress();
        this.tags.addAll(person.getTags());
        this.pets.addAll(person.getPets());

        if (isAdd) {
            this.pets.add(pet);
        } else {
            this.pets.remove(pet);
        }
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns a mutable pet set.
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Adds a pet to the person's pet set and returns the updated person.
     */
    public Person addPet(Pet pet) {
        return new Person(this, true, pet);
    }

    /**
     * Removes a pet from the person's pet set and returns the updated person.
     */
    public Person removePet(Pet pet) {
        return new Person(this, false, pet);
    }

    /**
     * Returns true if both persons have the same phone.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && pets.equals(otherPerson.pets);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, pets);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("pets", pets)
                .toString();
    }

}
