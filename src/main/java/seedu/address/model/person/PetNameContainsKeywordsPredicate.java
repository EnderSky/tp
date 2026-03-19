package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Pet} names match any of the keywords given.
 * A person matches if any of their pets have a name containing any of the keywords.
 */
public class PetNameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PetNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getPets().stream()
                .anyMatch(pet -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(pet.getName().fullName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PetNameContainsKeywordsPredicate)) {
            return false;
        }

        PetNameContainsKeywordsPredicate otherPredicate = (PetNameContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
