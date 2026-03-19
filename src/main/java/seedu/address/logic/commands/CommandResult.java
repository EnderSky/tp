package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The person to display in the detail panel (for view client command). */
    private final Person personToView;

    /** The pet to display in the detail panel (for view pet command). */
    private final Pet petToView;

    /** The owner of the pet to view (needed for view pet command). */
    private final Person petOwner;

    /** A file picker dialog should be shown to the user. */
    private final boolean needsFilePicker;

    /** The client index for file picker requests. */
    private final Index clientIndex;

    /** The pet index for file picker requests. */
    private final Index petIndex;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null, null, null, false, null, null);
    }

    /**
     * Constructs a {@code CommandResult} with all fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         Person personToView, Pet petToView, Person petOwner) {
        this(feedbackToUser, showHelp, exit, personToView, petToView, petOwner, false, null, null);
    }

    /**
     * Constructs a {@code CommandResult} with all fields including file picker support.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         Person personToView, Pet petToView, Person petOwner,
                         boolean needsFilePicker, Index clientIndex, Index petIndex) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.personToView = personToView;
        this.petToView = petToView;
        this.petOwner = petOwner;
        this.needsFilePicker = needsFilePicker;
        this.clientIndex = clientIndex;
        this.petIndex = petIndex;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Creates a {@code CommandResult} for viewing a client in the detail panel.
     */
    public static CommandResult withClientView(String feedbackToUser, Person client) {
        return new CommandResult(feedbackToUser, false, false, client, null, null);
    }

    /**
     * Creates a {@code CommandResult} for viewing a pet in the detail panel.
     */
    public static CommandResult withPetView(String feedbackToUser, Pet pet, Person owner) {
        return new CommandResult(feedbackToUser, false, false, null, pet, owner, false, null, null);
    }

    /**
     * Creates a {@code CommandResult} for requesting a file picker dialog.
     */
    public static CommandResult withFilePickerRequest(String feedbackToUser,
                                                      Index clientIndex, Index petIndex) {
        return new CommandResult(feedbackToUser, false, false, null, null, null,
                true, clientIndex, petIndex);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    /**
     * Returns true if this command result should update the detail panel with a client view.
     */
    public boolean hasClientToView() {
        return personToView != null;
    }

    /**
     * Returns true if this command result should update the detail panel with a pet view.
     */
    public boolean hasPetToView() {
        return petToView != null && petOwner != null;
    }

    /**
     * Returns the person to display in the detail panel, if any.
     */
    public Optional<Person> getPersonToView() {
        return Optional.ofNullable(personToView);
    }

    /**
     * Returns the pet to display in the detail panel, if any.
     */
    public Optional<Pet> getPetToView() {
        return Optional.ofNullable(petToView);
    }

    /**
     * Returns the owner of the pet to view, if any.
     */
    public Optional<Person> getPetOwner() {
        return Optional.ofNullable(petOwner);
    }

    /**
     * Returns true if this command result requests a file picker dialog.
     */
    public boolean needsFilePicker() {
        return needsFilePicker;
    }

    /**
     * Returns the client index for file picker requests.
     */
    public Index getClientIndex() {
        return clientIndex;
    }

    /**
     * Returns the pet index for file picker requests.
     */
    public Index getPetIndex() {
        return petIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && Objects.equals(personToView, otherCommandResult.personToView)
                && Objects.equals(petToView, otherCommandResult.petToView)
                && Objects.equals(petOwner, otherCommandResult.petOwner)
                && needsFilePicker == otherCommandResult.needsFilePicker
                && Objects.equals(clientIndex, otherCommandResult.clientIndex)
                && Objects.equals(petIndex, otherCommandResult.petIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, personToView, petToView, petOwner,
                needsFilePicker, clientIndex, petIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("personToView", personToView)
                .add("petToView", petToView)
                .add("petOwner", petOwner)
                .add("needsFilePicker", needsFilePicker)
                .add("clientIndex", clientIndex)
                .add("petIndex", petIndex)
                .toString();
    }

}
