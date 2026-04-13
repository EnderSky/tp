package seedu.address.storage;

import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_IMAGE_PATH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Breed;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Pet;
import seedu.address.model.person.PhotoPath;
import seedu.address.model.person.Species;

/**
 * Jackson-friendly version of {@link Pet}.
 */
public class JsonAdaptedPet {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pet's %s field is missing!";

    private final String petName;
    private final String species;
    private final String breed;
    private final String note;
    private final String photoPath;

    /**
     * Constructs a {@code JsonAdaptedPet} with the given {@code petName}.
     */
    @JsonCreator
    public JsonAdaptedPet(@JsonProperty("petName") String petName,
            @JsonProperty("species") String species,
            @JsonProperty("breed") String breed,
            @JsonProperty("note") String note,
            @JsonProperty("photoPath") String photoPath) {
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.note = note;
        this.photoPath = photoPath;
    }

    /**
     * Converts a given {@code Pet} into this class for Jackson use.
     */
    public JsonAdaptedPet(Pet source) {
        petName = source.getName().fullName;
        species = source.getSpecies().speciesName;
        breed = source.getBreed().breedName;
        note = source.getNote().fullNote;
        photoPath = source.getPhotoPath().toString();

    }

    /**
     * Converts this Jackson-friendly adapted pet object into the model's
     * {@code Pet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted pet.
     */
    public Pet toModelType() throws IllegalValueException {
        if (petName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(petName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelPetName = new Name(petName);

        if (species == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Species"));
        }
        if (!Species.isValidSpecies(species)) {
            throw new IllegalValueException(Species.MESSAGE_CONSTRAINTS);
        }
        final Species modelSpecies = new Species(species);

        if (breed == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Breed"));
        }
        if (!Breed.isValidBreed(breed)) {
            throw new IllegalValueException(Breed.MESSAGE_CONSTRAINTS);
        }
        final Breed modelBreed = new Breed(breed);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Note"));
        }
        if (!Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }
        final Note modelNote = new Note(note);

        String editedPath = photoPath;
        if (!PhotoPath.isValidPhotoPath(photoPath)) {
            editedPath = PLACEHOLDER_IMAGE_PATH;
        }
        final PhotoPath modelPhotoPath = new PhotoPath(editedPath);

        return new Pet(modelPetName, modelSpecies, modelBreed, modelNote, modelPhotoPath);
    }
}
