package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.ALICE;
import static seedu.address.testutil.TypicalLessons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.LessonBuilder;

public class UniqueLessonListTest {

    private final UniqueLessonList uniqueLessonList = new UniqueLessonList();

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.contains(null));
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        assertFalse(uniqueLessonList.contains(ALICE));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        uniqueLessonList.add(ALICE);
        assertTrue(uniqueLessonList.contains(ALICE));
    }

    @Test
    public void contains_lessonWithSameIdentityFieldsInList_returnsTrue() {
        uniqueLessonList.add(ALICE);
        Lesson editedAlice = new LessonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueLessonList.contains(editedAlice));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.add(null));
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        uniqueLessonList.add(ALICE);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.add(ALICE));
    }

    @Test
    public void setLesson_nullTargetLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLesson(null, ALICE));
    }

    @Test
    public void setLesson_nullEditedLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLesson(ALICE, null));
    }

    @Test
    public void setLesson_targetLessonNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.setLesson(ALICE, ALICE));
    }

    @Test
    public void setLesson_editedLessonIsSameLesson_success() {
        uniqueLessonList.add(ALICE);
        uniqueLessonList.setLesson(ALICE, ALICE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(ALICE);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLesson_editedLessonHasSameIdentity_success() {
        uniqueLessonList.add(ALICE);
        Lesson editedAlice = new LessonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueLessonList.setLesson(ALICE, editedAlice);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(editedAlice);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLesson_editedLessonHasDifferentIdentity_success() {
        uniqueLessonList.add(ALICE);
        uniqueLessonList.setLesson(ALICE, BOB);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(BOB);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLesson_editedLessonHasNonUniqueIdentity_throwsDuplicateLessonException() {
        uniqueLessonList.add(ALICE);
        uniqueLessonList.add(BOB);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.setLesson(ALICE, BOB));
    }

    @Test
    public void addTagsToLesson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.addTagsToLesson(null,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void addTagsToLesson_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.addTagsToLesson(ALICE, null));
    }

    @Test
    public void addTagsToLesson_targetNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.addTagsToLesson(ALICE,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void addTagsToLesson_targetInList_success() {
        uniqueLessonList.add(ALICE);
        uniqueLessonList.addTagsToLesson(ALICE, Set.of(new Tag("classmate")));

        Lesson expectedLesson = new LessonBuilder(ALICE).withTags("friends", "classmate").build();
        assertTrue(uniqueLessonList.contains(expectedLesson));
    }

    @Test
    public void deleteTagsFromLesson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.deleteTagsFromLesson(null,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void deleteTagsFromLesson_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.deleteTagsFromLesson(ALICE, null));
    }

    @Test
    public void deleteTagsFromLesson_targetNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.deleteTagsFromLesson(ALICE,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void deleteTagsFromLesson_targetInList_success() {
        uniqueLessonList.add(ALICE);
        uniqueLessonList.deleteTagsFromLesson(ALICE, Set.of(new Tag("friends")));

        Lesson expectedLesson = new LessonBuilder(ALICE).withTags().build();
        assertTrue(uniqueLessonList.contains(expectedLesson));
    }

    @Test
    public void remove_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.remove(null));
    }

    @Test
    public void remove_lessonDoesNotExist_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.remove(ALICE));
    }

    @Test
    public void remove_existingLesson_removesLesson() {
        uniqueLessonList.add(ALICE);
        uniqueLessonList.remove(ALICE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLessons_nullUniqueLessonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons((UniqueLessonList) null));
    }

    @Test
    public void setLessons_uniqueLessonList_replacesOwnListWithProvidedUniqueLessonList() {
        uniqueLessonList.add(ALICE);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(BOB);
        uniqueLessonList.setLessons(expectedUniqueLessonList);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLessons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons((List<Lesson>) null));
    }

    @Test
    public void setLessons_list_replacesOwnListWithProvidedList() {
        uniqueLessonList.add(ALICE);
        List<Lesson> lessonList = Collections.singletonList(BOB);
        uniqueLessonList.setLessons(lessonList);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        expectedUniqueLessonList.add(BOB);
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void setLessons_listWithDuplicateLessons_throwsDuplicateLessonException() {
        List<Lesson> listWithDuplicateLessons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.setLessons(listWithDuplicateLessons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueLessonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueLessonList.asUnmodifiableObservableList().toString(), uniqueLessonList.toString());
    }
}
