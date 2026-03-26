package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.PhotoPath;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditPetCommand extends Command {

    public static final String COMMAND_WORD = "editPet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the pet "
            + "with the given POSITION. "
            + "Existing values will be overwritten by the new values.\n"
            + "User inputs: POSITION "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SPECIES + "SPECIES] "
            + "[" + PREFIX_BREED + "BREED] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_PHOTO + "PHOTO PATH] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Meowy"
            + PREFIX_SPECIES + "cat"
            + PREFIX_BREED + "persian"
            + PREFIX_NOTE + "Good kitty"
            + PREFIX_PHOTO + "C:\\Users\\DummyUser\\Photos\\meowy.png";

    public static final String MESSAGE_EDIT_PET_SUCCESS = "Edited Pet: %1$s";
    public static final String MESSAGE_INDEX_TOO_SMALL = "The POSITION provided should be 1 or more";
    public static final String MESSAGE_INDEX_TOO_LARGE = "The POSITION provided is too large";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists.";

    private final Index index;
    private final EditPetDescriptor editPetDescriptor;

    /**
     * Creates an EditPetCommand to edit a {@code Pet}
     */
    public EditPetCommand(Index index, EditPetDescriptor editPetDescriptor) {
        requireNonNull(index);
        requireNonNull(editPetDescriptor);

        this.index = index;
        this.editPetDescriptor = new EditPetDescriptor(editPetDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();

        Person owner = null;
        Pet petToEdit = null;
        int petCounter = 0;
        for (Person person : personList) {
            for (Pet pet : person.getPets()) {
                petCounter++;
                if (petCounter == index.getOneBased()) {
                    owner = person;
                    petToEdit = pet;
                }
            }
        }
        if (petToEdit == null) {
            throw new CommandException(MESSAGE_INDEX_TOO_LARGE);
        }
        Pet editedPet = createEditedPet(petToEdit, editPetDescriptor);

        if (owner.removePet(petToEdit).getPets().contains(editedPet)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }

        model.setPerson(owner, owner.removePet(petToEdit).addPet(editedPet));
        return new CommandResult(String.format(MESSAGE_EDIT_PET_SUCCESS, Messages.format(editedPet)));
    }

    /**
     * Creates and returns a {@code Pet} with the details of {@code petToEdit}
     * edited with {@code editPetDescriptor}.
     */
    private static Pet createEditedPet(Pet petToEdit, EditPetDescriptor editPetDescriptor) {

        Name updatedName = editPetDescriptor.getName().orElse(petToEdit.getName());
        Name updatedSpecies = editPetDescriptor.getSpecies().orElse(petToEdit.getSpecies());
        Name updatedBreed = editPetDescriptor.getBreed().orElse(petToEdit.getBreed());
        Name updatedNote = editPetDescriptor.getNote().orElse(petToEdit.getNote());
        PhotoPath updatedPhotoPath = editPetDescriptor.getPhotoPath().orElse(petToEdit.getPhotoPath());
        return new Pet(updatedName, updatedSpecies, updatedBreed, updatedNote, updatedPhotoPath);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPetCommand)) {
            return false;
        }

        EditPetCommand otherEditPetCommand = (EditPetCommand) other;
        return index.equals(otherEditPetCommand.index)
                && editPetDescriptor.equals(otherEditPetCommand.editPetDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPetDescriptor", editPetDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the pet with. Each non-empty field value will
     * replace the
     * corresponding field value of the pet.
     */
    public static class EditPetDescriptor {
        private Name name;
        private Name species;
        private Name breed;
        private Name note;
        private PhotoPath photoPath;

        public EditPetDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPetDescriptor(EditPetDescriptor toCopy) {
            setName(toCopy.name);
            setSpecies(toCopy.species);
            setBreed(toCopy.breed);
            setNote(toCopy.note);
            setPhotoPath(toCopy.photoPath);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, species, breed, note, photoPath);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setSpecies(Name species) {
            this.species = species;
        }

        public Optional<Name> getSpecies() {
            return Optional.ofNullable(species);
        }

        public void setBreed(Name breed) {
            this.breed = breed;
        }

        public Optional<Name> getBreed() {
            return Optional.ofNullable(breed);
        }

        public void setNote(Name note) {
            this.note = note;
        }

        public Optional<Name> getNote() {
            return Optional.ofNullable(note);
        }

        public void setPhotoPath(PhotoPath photoPath) {
            this.photoPath = photoPath;
        }

        public Optional<PhotoPath> getPhotoPath() {
            return Optional.ofNullable(photoPath);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPetDescriptor)) {
                return false;
            }

            EditPetDescriptor otherEditPetDescriptor = (EditPetDescriptor) other;
            return Objects.equals(name, otherEditPetDescriptor.name)
                    && Objects.equals(species, otherEditPetDescriptor.species)
                    && Objects.equals(breed, otherEditPetDescriptor.breed)
                    && Objects.equals(note, otherEditPetDescriptor.note)
                    && Objects.equals(photoPath, otherEditPetDescriptor.photoPath);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("species", species)
                    .add("breed", breed)
                    .add("note", note)
                    .add("photoPath", photoPath)
                    .toString();
        }
    }
}
