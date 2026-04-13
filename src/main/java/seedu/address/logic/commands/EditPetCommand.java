package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Breed;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.PhotoPath;
import seedu.address.model.person.Species;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditPetCommand extends Command {

    public static final String COMMAND_WORD = "editpet";
    public static final String ALIAS = "ep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the pet "
            + "with the given POSITION. "
            + "Existing values will be overwritten by the new values.\n"
            + "User inputs: POSITION "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SPECIES + "SPECIES] "
            + "[" + PREFIX_BREED + "BREED] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_PHOTO + "PICTURE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Meowy "
            + PREFIX_SPECIES + "cat "
            + PREFIX_BREED + "persian "
            + PREFIX_NOTE + "Good kitty "
            + PREFIX_PHOTO + "doggy.png";

    public static final String MESSAGE_EDIT_PET_SUCCESS = "Edited Pet: %1$s";
    public static final String MESSAGE_NO_INDEX_PASSED = "No POSITION was detected.";
    public static final String MESSAGE_INDEX_TOO_LARGE = "The POSITION provided is too large.";
    public static final String MESSAGE_MANY_WORDS = "There are unrecognised words behind the POSITION.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PET = "A pet with this name and owner already exists.";

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

        Person owner;
        Pet petToEdit;
        try {
            owner = model.getPet(index).getKey();
            petToEdit = model.getPet(index).getValue();
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INDEX_TOO_LARGE);
        }

        Pet editedPet = createEditedPet(petToEdit, editPetDescriptor);

        if (owner.removePet(petToEdit).getPets().contains(editedPet)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }

        model.setPerson(owner, owner.setPet(petToEdit, editedPet));
        return new CommandResult(String.format(MESSAGE_EDIT_PET_SUCCESS, Messages.format(editedPet)));
    }

    /**
     * Creates and returns a {@code Pet} with the details of {@code petToEdit}
     * edited with {@code editPetDescriptor}.
     */
    private static Pet createEditedPet(Pet petToEdit, EditPetDescriptor editPetDescriptor) {

        Name updatedName = editPetDescriptor.getName().orElse(petToEdit.getName());
        Species updatedSpecies = editPetDescriptor.getSpecies().orElse(petToEdit.getSpecies());
        Breed updatedBreed = editPetDescriptor.getBreed().orElse(petToEdit.getBreed());
        Note updatedNote = editPetDescriptor.getNote().orElse(petToEdit.getNote());
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
        private Species species;
        private Breed breed;
        private Note note;
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

        public void setSpecies(Species species) {
            this.species = species;
        }

        public Optional<Species> getSpecies() {
            return Optional.ofNullable(species);
        }

        public void setBreed(Breed breed) {
            this.breed = breed;
        }

        public Optional<Breed> getBreed() {
            return Optional.ofNullable(breed);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
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
