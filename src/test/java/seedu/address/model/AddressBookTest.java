package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.ALICE;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.LessonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getLessonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateLessons_throwsDuplicateLessonException() {
        // Two lessons with the same identity fields
        Lesson editedAlice = new LessonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Lesson> newLessons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newLessons);

        assertThrows(DuplicateLessonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasLesson_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasLesson(null));
    }

    @Test
    public void hasLesson_lessonNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasLesson(ALICE));
    }

    @Test
    public void hasLesson_lessonInAddressBook_returnsTrue() {
        addressBook.addLesson(ALICE);
        assertTrue(addressBook.hasLesson(ALICE));
    }

    @Test
    public void hasLesson_lessonWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addLesson(ALICE);
        Lesson editedAlice = new LessonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasLesson(editedAlice));
    }

    @Test
    public void addTagsToLesson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.addTagsToLesson(null, Set.of(new Tag("friends"))));
    }

    @Test
    public void addTagsToLesson_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.addTagsToLesson(ALICE, null));
    }

    @Test
    public void addTagsToLesson_targetInAddressBook_success() {
        addressBook.addLesson(ALICE);
        addressBook.addTagsToLesson(ALICE, Set.of(new Tag("classmate")));

        Lesson expectedLesson = new LessonBuilder(ALICE).withTags("friends", "classmate").build();
        assertTrue(addressBook.hasLesson(expectedLesson));
    }

    @Test
    public void deleteTagsFromLesson_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.deleteTagsFromLesson(null,
            Set.of(new Tag("friends"))));
    }

    @Test
    public void deleteTagsFromLesson_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.deleteTagsFromLesson(ALICE, null));
    }

    @Test
    public void deleteTagsFromLesson_targetInAddressBook_success() {
        addressBook.addLesson(ALICE);
        addressBook.deleteTagsFromLesson(ALICE, Set.of(new Tag("friends")));

        Lesson expectedLesson = new LessonBuilder(ALICE).withTags().build();
        assertTrue(addressBook.hasLesson(expectedLesson));
    }

    @Test
    public void getLessonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getLessonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{lessons=" + addressBook.getLessonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose lessons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Lesson> lessons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Lesson> lessons) {
            this.lessons.setAll(lessons);
        }

        @Override
        public ObservableList<Lesson> getLessonList() {
            return lessons;
        }
    }

}
