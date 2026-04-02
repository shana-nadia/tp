package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Typicallessons.ALICE;
import static seedu.address.testutil.Typicallessons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.lessonBuilder;

public class UniqueLessonListTest {

    private final UniqueLessonList UniqueLessonList = new UniqueLessonList();

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.contains(null));
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        assertFalse(UniqueLessonList.contains(ALICE));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        UniqueLessonList.add(ALICE);
        assertTrue(UniqueLessonList.contains(ALICE));
    }

    @Test
    public void contains_lessonWithSameIdentityFieldsInList_returnsTrue() {
        UniqueLessonList.add(ALICE);
        Lesson editedAlice = new lessonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(UniqueLessonList.contains(editedAlice));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.add(null));
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        UniqueLessonList.add(ALICE);
        assertThrows(DuplicateLessonException.class, () -> UniqueLessonList.add(ALICE));
    }

    @Test
    public void setLesson_nullTargetLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.setLesson(null, ALICE));
    }

    @Test
    public void setLesson_nullEditedLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.setLesson(ALICE, null));
    }

    @Test
    public void setLesson_targetlessonNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> UniqueLessonList.setLesson(ALICE, ALICE));
    }

    @Test
    public void setLesson_editedlessonisSameLesson_success() {
        UniqueLessonList.add(ALICE);
        UniqueLessonList.setLesson(ALICE, ALICE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(ALICE);
        assertEquals(expectedUniqueLessonList, UniqueLessonList);
    }

    @Test
    public void setLesson_editedlessonHasSameIdentity_success() {
        UniqueLessonList.add(ALICE);
        Lesson editedAlice = new lessonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        UniqueLessonList.setLesson(ALICE, editedAlice);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(editedAlice);
        assertEquals(expectedUniqueLessonList, UniqueLessonList);
    }

    @Test
    public void setLesson_editedlessonHasDifferentIdentity_success() {
        UniqueLessonList.add(ALICE);
        UniqueLessonList.setLesson(ALICE, BOB);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(BOB);
        assertEquals(expectedUniqueLessonList, UniqueLessonList);
    }

    @Test
    public void setLesson_editedlessonHasNonUniqueIdentity_throwsDuplicateLessonException() {
        UniqueLessonList.add(ALICE);
        UniqueLessonList.add(BOB);
        assertThrows(DuplicateLessonException.class, () -> UniqueLessonList.setLesson(ALICE, BOB));
    }

    @Test
    public void addTagsToLesson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.addTagsToLesson(null,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void addTagsToLesson_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.addTagsToLesson(ALICE, null));
    }

    @Test
    public void addTagsToLesson_targetNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> UniqueLessonList.addTagsToLesson(ALICE,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void addTagsToLesson_targetInList_success() {
        UniqueLessonList.add(ALICE);
        UniqueLessonList.addTagsToLesson(ALICE, Set.of(new Tag("classmate")));

        Lesson expectedLesson = new lessonBuilder(ALICE).withTags("friends", "classmate").build();
        assertTrue(UniqueLessonList.contains(expectedLesson));
    }

    @Test
    public void deleteTagsFromLesson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.deleteTagsFromLesson(null,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void deleteTagsFromLesson_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.deleteTagsFromLesson(ALICE, null));
    }

    @Test
    public void deleteTagsFromLesson_targetNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> UniqueLessonList.deleteTagsFromLesson(ALICE,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void deleteTagsFromLesson_targetInList_success() {
        UniqueLessonList.add(ALICE);
        UniqueLessonList.deleteTagsFromLesson(ALICE, Set.of(new Tag("friends")));

        Lesson expectedLesson = new lessonBuilder(ALICE).withTags().build();
        assertTrue(UniqueLessonList.contains(expectedLesson));
    }

    @Test
    public void remove_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.remove(null));
    }

    @Test
    public void remove_lessonDoesNotExist_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> UniqueLessonList.remove(ALICE));
    }

    @Test
    public void remove_existingLesson_removeslesson() {
        UniqueLessonList.add(ALICE);
        UniqueLessonList.remove(ALICE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        assertEquals(expectedUniqueLessonList, UniqueLessonList);
    }

    @Test
    public void setLessons_nullUniqueLessonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.setLessons((UniqueLessonList) null));
    }

    @Test
    public void setLessons_UniqueLessonList_replacesOwnListWithProvidedUniqueLessonList() {
        UniqueLessonList.add(ALICE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(BOB);
        UniqueLessonList.setLessons(expectedUniqueLessonList);
        assertEquals(expectedUniqueLessonList, UniqueLessonList);
    }

    @Test
    public void setLessons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UniqueLessonList.setLessons((List<Lesson>) null));
    }

    @Test
    public void setLessons_list_replacesOwnListWithProvidedList() {
        UniqueLessonList.add(ALICE);
        List<Lesson> LessonList = Collections.singletonList(BOB);
        UniqueLessonList.setLessons(LessonList);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(BOB);
        assertEquals(expectedUniqueLessonList, UniqueLessonList);
    }

    @Test
    public void setLessons_listWithDuplicatelessons_throwsDuplicateLessonException() {
        List<Lesson> listWithDuplicateLessons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateLessonException.class, () -> UniqueLessonList.setLessons(listWithDuplicateLessons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> UniqueLessonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(UniqueLessonList.asUnmodifiableObservableList().toString(), UniqueLessonList.toString());
    }
}
