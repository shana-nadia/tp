package seedu.address.model.lesson;


import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code lesson}'s {@code Tags} matches any of the keywords given.
 */
public class TagsContainKeywordsPredicate implements Predicate<Lesson> {
    private final List<String> keywords;

    public TagsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Lesson lesson) {
        for (String keyword : keywords) {
            if (lesson.getTags().stream()
                    .noneMatch(tag -> tag.tagName.equalsIgnoreCase(keyword))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsContainKeywordsPredicate)) {
            return false;
        }

        TagsContainKeywordsPredicate otherTagsContainKeywordsPredicate = (TagsContainKeywordsPredicate) other;
        return keywords.equals(otherTagsContainKeywordsPredicate.keywords);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
