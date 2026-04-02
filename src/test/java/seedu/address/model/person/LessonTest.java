package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Typicallessons.ALICE;
import static seedu.address.testutil.Typicallessons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.lessonBuilder;

public class LessonTest {

    @Test
    public void hashCode_includesAllFields() {
        Lesson aliceCopy = new lessonBuilder(ALICE).build();
        Lesson aliceSame = new lessonBuilder(ALICE).build();
        assertEquals(aliceCopy.hashCode(), aliceSame.hashCode());
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Lesson lesson = new lessonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> lesson.getTags().remove(0));
    }

    @Test
    public void isSameLesson() {
        // same object -> returns true
        assertTrue(ALICE.isSameLesson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameLesson(null));

        // same name and phone number, all other attributes different -> returns true
        Lesson editedAlice = new lessonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withDay(VALID_DAY_BOB).withStartTime(VALID_START_TIME_BOB)
                .withEndTime(VALID_END_TIME_BOB).withRate(VALID_RATE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameLesson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new lessonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameLesson(editedAlice));

        // different phone, all other attributes same -> returns false
        editedAlice = new lessonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSameLesson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Lesson editedBob = new lessonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameLesson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new lessonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameLesson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Lesson aliceCopy = new lessonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different lesson -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Lesson editedAlice = new lessonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new lessonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new lessonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new lessonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different day -> returns false
        editedAlice = new lessonBuilder(ALICE).withDay(VALID_DAY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different start time -> returns false
        editedAlice = new lessonBuilder(ALICE).withStartTime(VALID_START_TIME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different end time -> returns false
        editedAlice = new lessonBuilder(ALICE).withEndTime(VALID_END_TIME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different rate -> returns false
        editedAlice = new lessonBuilder(ALICE).withRate(VALID_RATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new lessonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Lesson.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail()
                + ", address=" + ALICE.getAddress()
                + ", day=" + ALICE.getDay()
                + ", startTime=" + ALICE.getStartTime()
                + ", endTime=" + ALICE.getEndTime()
                + ", rate=" + ALICE.getRate()
                + ", isPaid=" + ALICE.isPaid()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
