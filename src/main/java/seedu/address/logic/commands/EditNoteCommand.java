package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.GroomingNotes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * Edits the grooming notes of a pet identified by CLIENT_INDEX.PET_INDEX notation.
 */
public class EditNoteCommand extends Command {

    public static final String COMMAND_WORD = "edit note";
    public static final String COMMAND_WORD_ALIAS = "en";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the grooming notes of the pet identified by CLIENT_INDEX.PET_INDEX notation.\n"
            + "Parameters: CLIENT_INDEX.PET_INDEX " + PREFIX_NOTE + "NOTES\n"
            + "Example: " + COMMAND_WORD + " 1.2 " + PREFIX_NOTE + "Updated grooming instructions";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Updated note for pet %1$s (Owner: %2$s): %3$s";
    public static final String MESSAGE_INVALID_PET_INDEX = "The pet index provided is invalid for this client.";
    public static final String MESSAGE_NO_EXISTING_NOTE =
            "This pet does not have grooming notes. Use 'add note' to create notes first.";

    private final Index clientIndex;
    private final Index petIndex;
    private final GroomingNotes groomingNotes;

    /**
     * Creates an EditNoteCommand to edit the notes of the pet at the specified indices.
     */
    public EditNoteCommand(Index clientIndex, Index petIndex, GroomingNotes groomingNotes) {
        requireNonNull(clientIndex);
        requireNonNull(petIndex);
        requireNonNull(groomingNotes);
        this.clientIndex = clientIndex;
        this.petIndex = petIndex;
        this.groomingNotes = groomingNotes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person owner = lastShownList.get(clientIndex.getZeroBased());
        List<Pet> pets = new ArrayList<>(owner.getPets());

        if (petIndex.getZeroBased() >= pets.size()) {
            throw new CommandException(MESSAGE_INVALID_PET_INDEX);
        }

        Pet targetPet = pets.get(petIndex.getZeroBased());

        // Check if pet has existing notes to edit
        if (targetPet.getGroomingNotes().isEmpty()) {
            throw new CommandException(MESSAGE_NO_EXISTING_NOTE);
        }

        // Create updated pet with new grooming notes
        Pet updatedPet = new Pet(
                targetPet.getName(),
                targetPet.getSpecies().orElse(null),
                targetPet.getBreed().orElse(null),
                targetPet.getDateOfBirth().orElse(null),
                groomingNotes,
                targetPet.getPhotoPath().orElse(null)
        );

        // Replace the pet in the set while preserving order
        Set<Pet> updatedPets = new LinkedHashSet<>();
        for (Pet p : owner.getPets()) {
            if (p.equals(targetPet)) {
                updatedPets.add(updatedPet);
            } else {
                updatedPets.add(p);
            }
        }

        // Create updated person with the modified pet
        Person updatedOwner = new Person(owner, updatedPets);
        model.setPerson(owner, updatedOwner);

        return new CommandResult(String.format(MESSAGE_EDIT_NOTE_SUCCESS,
                targetPet.getName(), owner.getName().fullName, groomingNotes.value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditNoteCommand)) {
            return false;
        }

        EditNoteCommand otherCommand = (EditNoteCommand) other;
        return clientIndex.equals(otherCommand.clientIndex)
                && petIndex.equals(otherCommand.petIndex)
                && groomingNotes.equals(otherCommand.groomingNotes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientIndex", clientIndex)
                .add("petIndex", petIndex)
                .add("groomingNotes", groomingNotes)
                .toString();
    }
}
