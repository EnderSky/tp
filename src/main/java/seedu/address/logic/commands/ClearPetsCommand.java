package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Pet;

/**
 * Clears all pets from all clients in the address book.
 */
public class ClearPetsCommand extends Command {

    public static final String COMMAND_WORD = "clear pets";
    public static final String COMMAND_WORD_ALIAS = "cp";

    public static final String MESSAGE_SUCCESS = "All pets have been removed from clients!";
    public static final String MESSAGE_NO_PETS = "There are no pets to clear.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Person> personList = model.getAddressBook().getPersonList();
        boolean anyPetsRemoved = false;

        for (Person person : personList) {
            if (!person.getPets().isEmpty()) {
                anyPetsRemoved = true;
                // Create person with empty pets
                Set<Pet> emptyPets = new LinkedHashSet<>();
                Person updatedPerson = new Person(person, emptyPets);
                model.setPerson(person, updatedPerson);
            }
        }

        if (!anyPetsRemoved) {
            return new CommandResult(MESSAGE_NO_PETS);
        }

        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
