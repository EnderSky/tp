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
 * Adds grooming notes to a pet identified by CLIENT_INDEX.PET_INDEX notation.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "add note";
    public static final String COMMAND_WORD_ALIAS = "an";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds grooming notes to the pet identified by CLIENT_INDEX.PET_INDEX notation.\n"
            + "Parameters: CLIENT_INDEX.PET_INDEX " + PREFIX_NOTE + "NOTES\n"
            + "Example: " + COMMAND_WORD + " 1.2 " + PREFIX_NOTE + "Sensitive ears, use gentle shampoo";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to pet %1$s (Owner: %2$s): %3$s";
    public static final String MESSAGE_INVALID_PET_INDEX = "The pet index provided is invalid for this client.";
    public static final String MESSAGE_PET_ALREADY_HAS_NOTE =
            "This pet already has grooming notes. Use 'edit note' to modify existing notes.";

    private final Index clientIndex;
    private final Index petIndex;
    private final GroomingNotes groomingNotes;

    /**
     * Creates an AddNoteCommand to add notes to the pet at the specified indices.
     */
    public AddNoteCommand(Index clientIndex, Index petIndex, GroomingNotes groomingNotes) {
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

        // Check if pet already has notes
        if (targetPet.getGroomingNotes().isPresent()) {
            throw new CommandException(MESSAGE_PET_ALREADY_HAS_NOTE);
        }

        // Create updated pet with grooming notes
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

        return new CommandResult(String.format(MESSAGE_ADD_NOTE_SUCCESS,
                targetPet.getName(), owner.getName().fullName, groomingNotes.value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddNoteCommand)) {
            return false;
        }

        AddNoteCommand otherCommand = (AddNoteCommand) other;
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
