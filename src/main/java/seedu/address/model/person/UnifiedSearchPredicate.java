package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} or any of their {@code Pet}s match any of the keywords given.
 * Searches across all attributes including:
 * - Client: name, phone, email, address, tags
 * - Pet: name, species, breed
 * Matching is case-insensitive and uses partial "contains" matching.
 */
public class UnifiedSearchPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public UnifiedSearchPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword -> matchesPerson(person, keyword));
    }

    /**
     * Returns true if the person or any of their pets match the keyword.
     */
    private boolean matchesPerson(Person person, String keyword) {
        String lowerKeyword = keyword.toLowerCase();

        // Check client name
        if (containsIgnoreCase(person.getName().fullName, lowerKeyword)) {
            return true;
        }

        // Check client phone
        if (containsIgnoreCase(person.getPhone().value, lowerKeyword)) {
            return true;
        }

        // Check client email
        if (containsIgnoreCase(person.getEmail().value, lowerKeyword)) {
            return true;
        }

        // Check client address
        if (containsIgnoreCase(person.getAddress().value, lowerKeyword)) {
            return true;
        }

        // Check client tags
        if (person.getTags().stream()
                .anyMatch(tag -> containsIgnoreCase(tag.tagName, lowerKeyword))) {
            return true;
        }

        // Check pets
        return person.getPets().stream().anyMatch(pet -> matchesPet(pet, lowerKeyword));
    }

    /**
     * Returns true if the pet matches the keyword.
     */
    private boolean matchesPet(Pet pet, String lowerKeyword) {
        // Check pet name
        if (containsIgnoreCase(pet.getName().fullName, lowerKeyword)) {
            return true;
        }

        // Check pet species
        if (pet.getSpecies().isPresent()
                && containsIgnoreCase(pet.getSpecies().get().value, lowerKeyword)) {
            return true;
        }

        // Check pet breed
        if (pet.getBreed().isPresent()
                && containsIgnoreCase(pet.getBreed().get().value, lowerKeyword)) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if the text contains the keyword (case-insensitive).
     */
    private boolean containsIgnoreCase(String text, String lowerKeyword) {
        return text != null && text.toLowerCase().contains(lowerKeyword);
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnifiedSearchPredicate)) {
            return false;
        }

        UnifiedSearchPredicate otherPredicate = (UnifiedSearchPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
