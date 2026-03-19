package seedu.address.model.person;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person} has pets matching the given filter criteria.
 * Can filter by species and/or tags. Multiple criteria use AND logic.
 */
public class PetFilterPredicate implements Predicate<Person> {
    private final Optional<String> speciesFilter;
    private final Set<Tag> tagFilters;

    /**
     * Constructs a PetFilterPredicate with optional species and tag filters.
     *
     * @param speciesFilter Optional species to filter by (case-insensitive)
     * @param tagFilters Set of tags to filter by (all must match)
     */
    public PetFilterPredicate(Optional<String> speciesFilter, Set<Tag> tagFilters) {
        this.speciesFilter = speciesFilter;
        this.tagFilters = tagFilters;
    }

    @Override
    public boolean test(Person person) {
        // A person matches if they have at least one pet that matches all criteria
        return person.getPets().stream().anyMatch(pet -> {
            // Check species filter
            if (speciesFilter.isPresent()) {
                boolean speciesMatches = pet.getSpecies()
                        .map(s -> s.value.equalsIgnoreCase(speciesFilter.get()))
                        .orElse(false);
                if (!speciesMatches) {
                    return false;
                }
            }

            // Check tag filters - all tags must be present on the pet
            // Note: Pet tags are Phase 3 feature, for now we skip tag matching on pets
            // This can be extended when pet tags are implemented

            return true;
        });
    }

    /**
     * Returns true if this predicate has any active filters.
     */
    public boolean hasFilters() {
        return speciesFilter.isPresent() || !tagFilters.isEmpty();
    }

    /**
     * Combines this predicate with another, creating a new predicate with merged criteria.
     */
    public PetFilterPredicate combineWith(PetFilterPredicate other) {
        // Take the new species filter if present, otherwise keep existing
        Optional<String> newSpecies = other.speciesFilter.isPresent()
                ? other.speciesFilter : this.speciesFilter;

        // Combine tag filters
        Set<Tag> newTags = new java.util.HashSet<>(this.tagFilters);
        newTags.addAll(other.tagFilters);

        return new PetFilterPredicate(newSpecies, newTags);
    }

    public Optional<String> getSpeciesFilter() {
        return speciesFilter;
    }

    public Set<Tag> getTagFilters() {
        return tagFilters;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PetFilterPredicate)) {
            return false;
        }

        PetFilterPredicate otherPredicate = (PetFilterPredicate) other;
        return speciesFilter.equals(otherPredicate.speciesFilter)
                && tagFilters.equals(otherPredicate.tagFilters);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("species", speciesFilter.orElse("any"))
                .add("tags", tagFilters)
                .toString();
    }

    /**
     * Returns a human-readable description of the active filters.
     */
    public String getFilterDescription() {
        StringBuilder sb = new StringBuilder();
        speciesFilter.ifPresent(s -> sb.append("species=").append(s));
        if (!tagFilters.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("tags=").append(tagFilters);
        }
        return sb.length() > 0 ? sb.toString() : "none";
    }
}
