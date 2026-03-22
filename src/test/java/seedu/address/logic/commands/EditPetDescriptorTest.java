package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_BREED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_SPECIES;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.model.person.Name;
import seedu.address.testutil.EditPetDescriptorBuilder;

public class EditPetDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPetDescriptor descriptor = new EditPetDescriptor();
        descriptor.setName(new Name(VALID_PET_NAME));
        descriptor.setSpecies(VALID_PET_SPECIES);
        descriptor.setBreed(VALID_PET_BREED);
        EditPetDescriptor descriptorWithSameValues = new EditPetDescriptor(descriptor);
        assertTrue(descriptor.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different name -> returns false
        EditPetDescriptor edited = new EditPetDescriptorBuilder(descriptor).withName("diff").build();
        assertFalse(descriptor.equals(edited));

        // different species -> returns false
        edited = new EditPetDescriptorBuilder(descriptor).withSpecies("spec").build();
        assertFalse(descriptor.equals(edited));

        // different breed -> returns false
        edited = new EditPetDescriptorBuilder(descriptor).withBreed("breed").build();
        assertFalse(descriptor.equals(edited));
    }

    @Test
    public void toStringMethod() {
        EditPetDescriptor editPetDescriptor = new EditPetDescriptor();
        String expected = EditPetDescriptor.class.getCanonicalName() + "{name="
                + editPetDescriptor.getName().orElse(null) + ", species="
                + editPetDescriptor.getSpecies().orElse(null) + ", breed="
                + editPetDescriptor.getBreed().orElse(null) + "}";
        assertEquals(expected, editPetDescriptor.toString());
    }
}
