package seedu.address.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Registry containing all command information for the help system.
 * Provides methods for retrieving commands by category and searching.
 */
public class CommandRegistry {

    private static final List<CommandInfo> ALL_COMMANDS;

    static {
        List<CommandInfo> commands = new ArrayList<>();

        // ===================== CLIENT COMMANDS =====================
        commands.add(new CommandInfo(
                "add client", "ac",
                "Add a new client to the address book",
                "add client n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]...",
                "add client n/John Doe p/98765432 e/john@email.com a/123 Pet Street t/VIP",
                CommandCategory.CLIENT,
                "Tags are optional. You can add multiple tags."
        ));

        commands.add(new CommandInfo(
                "edit client", "ec",
                "Edit an existing client's information",
                "edit client INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...",
                "edit client {CLIENT_INDEX} p/91234567 e/newemail@example.com",
                CommandCategory.CLIENT,
                "At least one field must be provided. Editing tags replaces all existing tags."
        ));

        commands.add(new CommandInfo(
                "delete client", "dc",
                "Delete a client and all their pets",
                "delete client INDEX",
                "delete client {CLIENT_INDEX}",
                CommandCategory.CLIENT,
                "Warning: This also deletes all pets belonging to this client."
        ));

        commands.add(new CommandInfo(
                "clear clients", "cc",
                "Remove all clients and their pets from the address book",
                "clear clients",
                "clear clients",
                CommandCategory.CLIENT,
                "Warning: This action cannot be undone!"
        ));

        // ===================== PET COMMANDS =====================
        commands.add(new CommandInfo(
                "add pet", "ap",
                "Add a pet to an existing client",
                "add pet INDEX n/NAME [s/SPECIES] [b/BREED] [dob/DATE_OF_BIRTH]",
                "add pet {CLIENT_INDEX} n/Buddy s/Dog b/Golden Retriever dob/2020-05-15",
                CommandCategory.PET,
                "Species, breed, and date of birth are optional. Date format: YYYY-MM-DD"
        ));

        commands.add(new CommandInfo(
                "edit pet", "ep",
                "Edit an existing pet's information",
                "edit pet CLIENT_INDEX.PET_INDEX [n/NAME] [s/SPECIES] [b/BREED] [dob/DATE]",
                "edit pet {PET_INDEX} n/Max s/Cat",
                CommandCategory.PET,
                "Uses CLIENT.PET notation (e.g., 1.2 = 2nd pet of 1st client)"
        ));

        commands.add(new CommandInfo(
                "delete pet", "dp",
                "Delete a pet from a client",
                "delete pet CLIENT_INDEX.PET_INDEX",
                "delete pet {PET_INDEX}",
                CommandCategory.PET,
                "Uses CLIENT.PET notation (e.g., 1.2 = 2nd pet of 1st client)"
        ));

        commands.add(new CommandInfo(
                "clear pets", "cp",
                "Remove all pets while keeping clients",
                "clear pets",
                "clear pets",
                CommandCategory.PET,
                "Removes all pets from all clients. Clients are preserved."
        ));

        // ===================== NOTES & PHOTOS COMMANDS =====================
        commands.add(new CommandInfo(
                "add note", "an",
                "Add grooming notes to a pet",
                "add note CLIENT_INDEX.PET_INDEX note/NOTE_TEXT",
                "add note {PET_INDEX} note/Prefers gentle brushing, sensitive skin",
                CommandCategory.NOTES_PHOTOS,
                "Use notes to record grooming preferences, allergies, or special instructions."
        ));

        commands.add(new CommandInfo(
                "edit note", "en",
                "Edit existing grooming notes",
                "edit note CLIENT_INDEX.PET_INDEX note/NEW_NOTE_TEXT",
                "edit note {PET_INDEX} note/Updated grooming preferences",
                CommandCategory.NOTES_PHOTOS,
                "Replaces the entire note content with new text."
        ));

        commands.add(new CommandInfo(
                "delete note", "dn",
                "Delete grooming notes from a pet",
                "delete note CLIENT_INDEX.PET_INDEX",
                "delete note {PET_INDEX}",
                CommandCategory.NOTES_PHOTOS,
                "Removes all notes from the specified pet."
        ));

        commands.add(new CommandInfo(
                "add photo", "aph",
                "Add a photo to a pet using file chooser",
                "add photo CLIENT_INDEX.PET_INDEX",
                "add photo {PET_INDEX}",
                CommandCategory.NOTES_PHOTOS,
                "Opens a file chooser dialog. Supports JPG, PNG, and GIF formats."
        ));

        commands.add(new CommandInfo(
                "delete photo", "dph",
                "Delete a photo from a pet",
                "delete photo CLIENT_INDEX.PET_INDEX",
                "delete photo {PET_INDEX}",
                CommandCategory.NOTES_PHOTOS,
                "Removes the photo attachment from the specified pet."
        ));

        // ===================== GENERAL COMMANDS =====================
        commands.add(new CommandInfo(
                "find", "f",
                "Search clients and pets by any attribute",
                "find KEYWORD [MORE_KEYWORDS]...",
                "find alice dog golden 91234567",
                CommandCategory.GENERAL,
                "Searches client name, phone, email, address, tags, pet name, species, breed. "
                        + "Case-insensitive partial matching. Use 'list' to clear search results."
        ));

        commands.add(new CommandInfo(
                "list", "l",
                "List all clients and pets",
                "list",
                "list",
                CommandCategory.GENERAL,
                "Shows all clients and their pets. Clears any active search."
        ));

        commands.add(new CommandInfo(
                "view", "v",
                "View client or pet details",
                "view INDEX or view CLIENT_INDEX.PET_INDEX",
                "view {CLIENT_INDEX} or view {PET_INDEX}",
                CommandCategory.GENERAL,
                "Single index views client details. CLIENT.PET notation views pet details."
        ));

        commands.add(new CommandInfo(
                "help", "h",
                "Show this help window",
                "help",
                "help",
                CommandCategory.GENERAL,
                "Opens the command reference with all available commands."
        ));

        commands.add(new CommandInfo(
                "undo", "u",
                "Undo the last data-modifying command",
                "undo",
                "undo",
                CommandCategory.GENERAL,
                "Reverts the most recent change. Can be used multiple times to undo further."
        ));

        commands.add(new CommandInfo(
                "redo", "r",
                "Redo the last undone command",
                "redo",
                "redo",
                CommandCategory.GENERAL,
                "Re-applies a previously undone change. Only available after using undo."
        ));

        commands.add(new CommandInfo(
                "exit", null,
                "Exit the application",
                "exit",
                "exit",
                CommandCategory.GENERAL,
                "Closes the application. Data is automatically saved."
        ));

        ALL_COMMANDS = Collections.unmodifiableList(commands);
    }

    /**
     * Returns all registered commands.
     */
    public static List<CommandInfo> getAllCommands() {
        return ALL_COMMANDS;
    }

    /**
     * Returns all commands in the specified category.
     */
    public static List<CommandInfo> getCommandsByCategory(CommandCategory category) {
        return ALL_COMMANDS.stream()
                .filter(cmd -> cmd.getCategory() == category)
                .collect(Collectors.toList());
    }

    /**
     * Searches for commands matching the given query.
     * Searches across command word, alias, description, and usage.
     *
     * @param query The search query (case-insensitive)
     * @return List of matching commands
     */
    public static List<CommandInfo> searchCommands(String query) {
        if (query == null || query.trim().isEmpty()) {
            return ALL_COMMANDS;
        }
        return ALL_COMMANDS.stream()
                .filter(cmd -> cmd.matchesSearch(query))
                .collect(Collectors.toList());
    }

    /**
     * Returns the total number of registered commands.
     */
    public static int getCommandCount() {
        return ALL_COMMANDS.size();
    }

    /**
     * Returns information about available prefixes for command parameters.
     */
    public static String getPrefixInfo() {
        return """
                n/ - Name
                p/ - Phone number
                e/ - Email address
                a/ - Address
                t/ - Tag (can use multiple)
                s/ - Species
                b/ - Breed
                dob/ - Date of birth (YYYY-MM-DD)
                note/ - Grooming notes""";
    }

    /**
     * Returns information about the CLIENT.PET index notation.
     */
    public static String getIndexNotationInfo() {
        return """
                Pet commands use CLIENT.PET notation:
                • First number = client index in the list
                • Second number = pet index within that client
                • Example: 1.2 = 2nd pet of 1st client
                • Example: 3.1 = 1st pet of 3rd client""";
    }
}
