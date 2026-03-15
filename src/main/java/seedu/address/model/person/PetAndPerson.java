package seedu.address.model.person;

/**
 * Represents a Pet and Person in the address book.
 * Guarantees: immutable;
 */
public class PetAndPerson {
    private final Pet pet;
    private final Person person;

    /**
     * Constructs a {@code PetAndPerson}.
     *
     * @param pet A valid Pet object.
     * @param person A valid Person object.
     */
    public PetAndPerson(Pet pet, Person person) {
        this.pet = pet;
        this.person = person;
    }

    public Pet getPet() {
        return pet;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PetAndPerson)) {
            return false;
        }

        PetAndPerson otherPetAndPerson = (PetAndPerson) other;
        return pet.equals(otherPetAndPerson.getPet())
                && person.equals(otherPetAndPerson.getPerson());
    }

    @Override
    public int hashCode() {
        return 31 * pet.hashCode() + person.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + pet + ", " + person + "]";
    }
}
