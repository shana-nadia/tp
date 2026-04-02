package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.lessonBuilder;

class TagsContainKeywordsPredicateTest {
    @Test
    void test_singleKeywordMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Collections.singletonList("friends"));
        Lesson lesson = new lessonBuilder().withTags("friends").build();
        assertTrue(predicate.test(lesson));
    }

    @Test
    void test_singleKeywordNoMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Collections.singletonList("colleagues"));
        Lesson lesson = new lessonBuilder().withTags("friends").build();
        assertFalse(predicate.test(lesson));
    }

    @Test
    void test_multipleKeywordsAllMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Arrays.asList("friends", "colleagues"));
        Lesson lesson = new lessonBuilder().withTags("friends", "colleagues").build();
        assertTrue(predicate.test(lesson));
    }

    @Test
    void test_multipleKeywordsPartialMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Arrays.asList("friends", "colleagues"));
        Lesson lesson = new lessonBuilder().withTags("friends").build();
        assertFalse(predicate.test(lesson));
    }
}
