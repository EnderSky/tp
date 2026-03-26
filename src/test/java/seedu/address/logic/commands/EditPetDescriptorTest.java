package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_BREED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PET_NOTE_CUTE;
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
        descriptor.setSpecies(new Name(VALID_PET_SPECIES));
        descriptor.setBreed(new Name(VALID_PET_BREED));
        descriptor.setNote(new Name(VALID_PET_NOTE_CUTE));
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

        // different note -> returns false
        edited = new EditPetDescriptorBuilder(descriptor).withNote("note").build();
        assertFalse(descriptor.equals(edited));

        // different photoPath -> returns false
        edited = new EditPetDescriptorBuilder(descriptor).withPhotoPath("/images/clock.png").build();
        assertFalse(descriptor.equals(edited));
    }

    @Test
    public void toStringMethod() {
        EditPetDescriptor editPetDescriptor = new EditPetDescriptor();
        String expected = EditPetDescriptor.class.getCanonicalName() + "{name="
                + editPetDescriptor.getName().orElse(null) + ", species="
                + editPetDescriptor.getSpecies().orElse(null) + ", breed="
                + editPetDescriptor.getBreed().orElse(null) + ", note="
                + editPetDescriptor.getNote().orElse(null) + ", photoPath="
                + editPetDescriptor.getPhotoPath().orElse(null) + "}";
        assertEquals(expected, editPetDescriptor.toString());
    }
}
