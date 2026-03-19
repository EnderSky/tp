package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Breed;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.GroomingNotes;
import seedu.address.model.person.Name;
import seedu.address.model.person.Pet;
import seedu.address.model.person.PhotoPath;
import seedu.address.model.person.Species;

/**
 * Jackson-friendly version of {@link Pet}.
 */
class JsonAdaptedPet {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pet's %s field is missing!";

    private final String petName;
    private final String species;
    private final String breed;
    private final String dateOfBirth;
    private final String groomingNotes;
    private final String photoPath;

    /**
     * Constructs a {@code JsonAdaptedPet} with the given pet details.
     */
    @JsonCreator
    public JsonAdaptedPet(@JsonProperty("petName") String petName,
                          @JsonProperty("species") String species,
                          @JsonProperty("breed") String breed,
                          @JsonProperty("dateOfBirth") String dateOfBirth,
                          @JsonProperty("groomingNotes") String groomingNotes,
                          @JsonProperty("photoPath") String photoPath) {
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.groomingNotes = groomingNotes;
        this.photoPath = photoPath;
    }

    /**
     * Converts a given {@code Pet} into this class for Jackson use.
     */
    public JsonAdaptedPet(Pet source) {
        petName = source.getName().fullName;
        species = source.getSpecies().map(s -> s.value).orElse(null);
        breed = source.getBreed().map(b -> b.value).orElse(null);
        dateOfBirth = source.getDateOfBirth().map(d -> d.toStorageString()).orElse(null);
        groomingNotes = source.getGroomingNotes().map(g -> g.value).orElse(null);
        photoPath = source.getPhotoPath().map(p -> p.value).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted pet object into the model's {@code Pet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pet.
     */
    public Pet toModelType() throws IllegalValueException {
        // Validate and parse pet name (required field)
        if (petName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(petName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(petName);

        // Validate and parse species (optional field)
        final Species modelSpecies;
        if (species != null) {
            if (!Species.isValidSpecies(species)) {
                throw new IllegalValueException(Species.MESSAGE_CONSTRAINTS);
            }
            modelSpecies = new Species(species);
        } else {
            modelSpecies = null;
        }

        // Validate and parse breed (optional field)
        final Breed modelBreed;
        if (breed != null) {
            if (!Breed.isValidBreed(breed)) {
                throw new IllegalValueException(Breed.MESSAGE_CONSTRAINTS);
            }
            modelBreed = new Breed(breed);
        } else {
            modelBreed = null;
        }

        // Validate and parse date of birth (optional field)
        final DateOfBirth modelDateOfBirth;
        if (dateOfBirth != null) {
            if (!DateOfBirth.isValidDateOfBirth(dateOfBirth)) {
                throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS);
            }
            modelDateOfBirth = new DateOfBirth(dateOfBirth);
        } else {
            modelDateOfBirth = null;
        }

        // Validate and parse grooming notes (optional field)
        final GroomingNotes modelGroomingNotes;
        if (groomingNotes != null) {
            if (!GroomingNotes.isValidGroomingNotes(groomingNotes)) {
                throw new IllegalValueException(GroomingNotes.MESSAGE_CONSTRAINTS);
            }
            modelGroomingNotes = new GroomingNotes(groomingNotes);
        } else {
            modelGroomingNotes = null;
        }

        // Parse photo path (optional field, uses fromStorage to skip file existence check)
        final PhotoPath modelPhotoPath;
        if (photoPath != null) {
            modelPhotoPath = PhotoPath.fromStorage(photoPath);
        } else {
            modelPhotoPath = null;
        }

        return new Pet(modelName, modelSpecies, modelBreed, modelDateOfBirth, modelGroomingNotes, modelPhotoPath);
    }
}
