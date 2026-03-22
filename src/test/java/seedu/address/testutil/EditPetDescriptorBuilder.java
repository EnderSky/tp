package seedu.address.testutil;

import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;

/**
 * A utility class to help with building EditPetDescriptor objects.
 */
public class EditPetDescriptorBuilder {

    private EditPetDescriptor descriptor;

    public EditPetDescriptorBuilder() {
        descriptor = new EditPetDescriptor();
    }

    public EditPetDescriptorBuilder(EditPetDescriptor descriptor) {
        this.descriptor = new EditPetDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPetDescriptor} with fields containing
     * {@code pet}'s details
     */
    public EditPetDescriptorBuilder(Pet pet) {
        descriptor = new EditPetDescriptor();
        descriptor.setName(pet.getName());
        descriptor.setSpecies(pet.getSpecies());
        descriptor.setBreed(pet.getBreed());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPetDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Species} of the {@code EditPetDescriptor} that we are
     * building.
     */
    public EditPetDescriptorBuilder withSpecies(String species) {
        descriptor.setSpecies(species);
        return this;
    }

    /**
     * Sets the {@code Breed} of the {@code EditPetDescriptor} that we are
     * building.
     */
    public EditPetDescriptorBuilder withBreed(String breed) {
        descriptor.setBreed(breed);
        return this;
    }

    public EditPetDescriptor build() {
        return descriptor;
    }
}
