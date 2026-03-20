package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Breed;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;
import seedu.address.model.person.Species;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing pet of a person in the address book.
 */
public class EditPetCommand extends Command {

    public static final String COMMAND_WORD = "edit pet";
    public static final String COMMAND_WORD_ALIAS = "ep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the pet identified "
            + "by the client and pet index numbers used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: CLIENT_INDEX.PET_INDEX (must be positive integers) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SPECIES + "SPECIES] "
            + "[" + PREFIX_BREED + "BREED] "
            + "[" + PREFIX_DOB + "DATE_OF_BIRTH] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1.2 "
            + PREFIX_NAME + "Fluffy Jr. "
            + PREFIX_SPECIES + "Dog "
            + PREFIX_BREED + "Golden Retriever "
            + PREFIX_TAG + "Anxious";

    public static final String MESSAGE_EDIT_PET_SUCCESS = "Edited Pet: %1$s (Owner: %2$s)";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PET = "This pet already exists for the owner.";

    private final Index clientIndex;
    private final Index petIndex;
    private final EditPetDescriptor editPetDescriptor;

    /**
     * @param clientIndex      of the person in the filtered person list who owns the pet
     * @param petIndex         of the pet in the person's pet list to edit
     * @param editPetDescriptor details to edit the pet with
     */
    public EditPetCommand(Index clientIndex, Index petIndex, EditPetDescriptor editPetDescriptor) {
        requireNonNull(clientIndex);
        requireNonNull(petIndex);
        requireNonNull(editPetDescriptor);

        this.clientIndex = clientIndex;
        this.petIndex = petIndex;
        this.editPetDescriptor = new EditPetDescriptor(editPetDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personWithPet = lastShownList.get(clientIndex.getZeroBased());
        List<Pet> petList = new ArrayList<>(personWithPet.getPets());

        if (petIndex.getZeroBased() >= petList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_DISPLAYED_INDEX);
        }

        Pet petToEdit = petList.get(petIndex.getZeroBased());
        Pet editedPet = createEditedPet(petToEdit, editPetDescriptor);

        // Check for duplicate pets (same name for the same owner)
        if (!petToEdit.isSamePet(editedPet) && personWithPet.hasSamePet(editedPet)) {
            throw new CommandException(MESSAGE_DUPLICATE_PET);
        }

        // Create new pet set with the edited pet, preserving position
        List<Pet> petListCopy = new ArrayList<>(personWithPet.getPets());
        int editIndex = petListCopy.indexOf(petToEdit);
        petListCopy.set(editIndex, editedPet);
        Set<Pet> updatedPets = new LinkedHashSet<>(petListCopy);

        Person editedPerson = new Person(personWithPet, updatedPets);
        model.setPerson(personWithPet, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_EDIT_PET_SUCCESS,
                Messages.format(editedPet), editedPerson.getName().fullName),
                false, false, editedPerson, editedPet, editedPerson);
    }

    /**
     * Creates and returns a {@code Pet} with the details of {@code petToEdit}
     * edited with {@code editPetDescriptor}.
     */
    private static Pet createEditedPet(Pet petToEdit, EditPetDescriptor editPetDescriptor) {
        assert petToEdit != null;

        Name updatedName = editPetDescriptor.getName().orElse(petToEdit.getName());
        Species updatedSpecies = editPetDescriptor.getSpecies().orElse(petToEdit.getSpecies().orElse(null));
        Breed updatedBreed = editPetDescriptor.getBreed().orElse(petToEdit.getBreed().orElse(null));
        DateOfBirth updatedDateOfBirth = editPetDescriptor.getDateOfBirth()
                .orElse(petToEdit.getDateOfBirth().orElse(null));
        Set<Tag> updatedTags = editPetDescriptor.getTags().orElse(petToEdit.getTags());

        // Preserve existing photo path and grooming notes during editing
        return new Pet(updatedName, updatedSpecies, updatedBreed, updatedDateOfBirth,
                updatedTags, petToEdit.getGroomingNotes().orElse(null),
                petToEdit.getPhotoPath().orElse(null));
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
        return clientIndex.equals(otherEditPetCommand.clientIndex)
                && petIndex.equals(otherEditPetCommand.petIndex)
                && editPetDescriptor.equals(otherEditPetCommand.editPetDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("petIndex", petIndex)
                .add("editPetDescriptor", editPetDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the pet with. Each non-empty field value will replace the
     * corresponding field value of the pet.
     */
    public static class EditPetDescriptor {
        private Name name;
        private Species species;
        private Breed breed;
        private DateOfBirth dateOfBirth;
        private Set<Tag> tags;

        public EditPetDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPetDescriptor(EditPetDescriptor toCopy) {
            setName(toCopy.name);
            setSpecies(toCopy.species);
            setBreed(toCopy.breed);
            setDateOfBirth(toCopy.dateOfBirth);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, species, breed, dateOfBirth, tags);
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

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new LinkedHashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
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
                    && Objects.equals(dateOfBirth, otherEditPetDescriptor.dateOfBirth)
                    && Objects.equals(tags, otherEditPetDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("species", species)
                    .add("breed", breed)
                    .add("dateOfBirth", dateOfBirth)
                    .add("tags", tags)
                    .toString();
        }
    }
}
