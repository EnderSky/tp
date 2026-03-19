package seedu.address.ui;

import java.util.Objects;

/**
 * Represents information about a command for display in the help system.
 * Contains all details needed to render a command card including usage, examples, and category.
 */
public class CommandInfo {
    private final String commandWord;
    private final String alias;
    private final String description;
    private final String usage;
    private final String exampleTemplate;
    private final CommandCategory category;
    private final String notes;

    /**
     * Creates a CommandInfo with all fields.
     *
     * @param commandWord The full command word (e.g., "add client")
     * @param alias The shorthand alias (e.g., "ac")
     * @param description Brief description of what the command does
     * @param usage The command syntax with parameters
     * @param exampleTemplate Example with placeholders for real data (e.g., "{CLIENT_INDEX}")
     * @param category The category this command belongs to
     * @param notes Additional notes or tips (can be null)
     */
    public CommandInfo(String commandWord, String alias, String description,
                       String usage, String exampleTemplate, CommandCategory category, String notes) {
        this.commandWord = Objects.requireNonNull(commandWord);
        this.alias = alias; // Can be null if no alias
        this.description = Objects.requireNonNull(description);
        this.usage = Objects.requireNonNull(usage);
        this.exampleTemplate = exampleTemplate; // Can be null
        this.category = Objects.requireNonNull(category);
        this.notes = notes; // Can be null
    }

    /**
     * Creates a CommandInfo without notes.
     */
    public CommandInfo(String commandWord, String alias, String description,
                       String usage, String exampleTemplate, CommandCategory category) {
        this(commandWord, alias, description, usage, exampleTemplate, category, null);
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getAlias() {
        return alias;
    }

    public boolean hasAlias() {
        return alias != null && !alias.isEmpty();
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String getExampleTemplate() {
        return exampleTemplate;
    }

    public boolean hasExample() {
        return exampleTemplate != null && !exampleTemplate.isEmpty();
    }

    public CommandCategory getCategory() {
        return category;
    }

    public String getNotes() {
        return notes;
    }

    public boolean hasNotes() {
        return notes != null && !notes.isEmpty();
    }

    /**
     * Returns a formatted display string showing command word and alias.
     * Example: "add client (ac)"
     */
    public String getCommandDisplay() {
        if (hasAlias()) {
            return commandWord + " (" + alias + ")";
        }
        return commandWord;
    }

    /**
     * Checks if this command matches the given search query.
     * Searches across command word, alias, description, and usage.
     *
     * @param query The search query (case-insensitive)
     * @return true if the command matches the query
     */
    public boolean matchesSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return true;
        }
        String lowerQuery = query.toLowerCase().trim();
        return commandWord.toLowerCase().contains(lowerQuery)
                || (alias != null && alias.toLowerCase().contains(lowerQuery))
                || description.toLowerCase().contains(lowerQuery)
                || usage.toLowerCase().contains(lowerQuery);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CommandInfo)) {
            return false;
        }
        CommandInfo o = (CommandInfo) other;
        return commandWord.equals(o.commandWord)
                && Objects.equals(alias, o.alias)
                && description.equals(o.description)
                && usage.equals(o.usage)
                && Objects.equals(exampleTemplate, o.exampleTemplate)
                && category.equals(o.category)
                && Objects.equals(notes, o.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandWord, alias, description, usage, exampleTemplate, category, notes);
    }

    @Override
    public String toString() {
        return String.format("CommandInfo{%s, category=%s}", commandWord, category);
    }
}
