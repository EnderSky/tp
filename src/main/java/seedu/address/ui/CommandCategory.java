package seedu.address.ui;

/**
 * Represents the categories for organizing commands in the help system.
 * Each category corresponds to a tab in the help window.
 */
public enum CommandCategory {
    CLIENT("Clients", "📋", "Commands for managing client information"),
    PET("Pets", "🐾", "Commands for managing pets"),
    NOTES_PHOTOS("Notes & Photos", "📝", "Commands for notes and photo attachments"),
    GENERAL("General", "ℹ", "General application commands");

    private final String displayName;
    private final String icon;
    private final String description;

    CommandCategory(String displayName, String icon, String description) {
        this.displayName = displayName;
        this.icon = icon;
        this.description = description;
    }

    /**
     * Returns the display name for this category.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the icon emoji for this category.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Returns the description for this category.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the tab title combining icon and display name.
     */
    public String getTabTitle() {
        return icon + " " + displayName;
    }
}
