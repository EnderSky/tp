package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Pet;

/**
 * A utility class containing a list of {@code Pet} objects to be used in tests.
 */
public class TypicalPets {

    public static final Pet SNOOPY = new PetBuilder().withName("Snoopy")
            .withSpecies("Dog").withBreed("Beagle").withDateOfBirth("2020-01-01").build();
    public static final Pet DOGGY = new PetBuilder().withName("Doggy")
            .withSpecies("Dog").build();
    public static final Pet FLUFFY = new PetBuilder().withName("Fluffy")
            .withSpecies("Cat").withBreed("Persian").withDateOfBirth("2021-06-15").build();
    public static final Pet GOLDIE = new PetBuilder().withName("Goldie")
            .withSpecies("Fish").build();
    public static final Pet TWEETY = new PetBuilder().withName("Tweety")
            .withSpecies("Bird").withBreed("Canary").build();

    private TypicalPets() {} // prevents instantiation

    public static List<Pet> getTypicalPets() {
        return new ArrayList<>(Arrays.asList(SNOOPY, DOGGY, FLUFFY, GOLDIE, TWEETY));
    }
}
