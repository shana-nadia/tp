package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showlessonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_lesson;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_lesson;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnmarkCommand}.
 */
public class UnmarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Set lesson as paid first so unmark has something to do
        Lesson lessonToUnmark = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        Lesson paidLesson = new LessonBuilder(lessonToUnmark).withPaid(true).build();
        model.setLesson(lessonToUnmark, paidLesson);

        UnmarkCommand unmarkCommand = new UnmarkCommand(List.of(INDEX_FIRST_lesson));

        Lesson unmarkedLesson = new LessonBuilder(paidLesson).withPaid(false).build();
        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_LESSON_SUCCESS,
                Messages.format(unmarkedLesson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setLesson(paidLesson, unmarkedLesson);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        UnmarkCommand unmarkCommand = new UnmarkCommand(List.of(outOfBoundIndex));

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Set lesson as paid first so unmark has something to do
        Lesson lessonToUnmark = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        Lesson paidLesson = new LessonBuilder(lessonToUnmark).withPaid(true).build();
        model.setLesson(lessonToUnmark, paidLesson);

        showlessonAtIndex(model, INDEX_FIRST_lesson);

        Lesson lessonInFilteredList = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        UnmarkCommand unmarkCommand = new UnmarkCommand(List.of(INDEX_FIRST_lesson));

        Lesson unmarkedLesson = new LessonBuilder(lessonInFilteredList).withPaid(false).build();
        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_LESSON_SUCCESS,
                Messages.format(unmarkedLesson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setLesson(lessonInFilteredList, unmarkedLesson);
        showlessonAtIndex(expectedModel, INDEX_FIRST_lesson);

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showlessonAtIndex(model, INDEX_FIRST_lesson);

        Index outOfBoundIndex = INDEX_SECOND_lesson;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        UnmarkCommand unmarkCommand = new UnmarkCommand(List.of(outOfBoundIndex));

        assertCommandFailure(unmarkCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyUnpaid_throwsCommandException() {
        // Typical lessons default to isPaid = false
        UnmarkCommand unmarkCommand = new UnmarkCommand(List.of(INDEX_FIRST_lesson));

        assertCommandFailure(unmarkCommand, model, UnmarkCommand.MESSAGE_ALREADY_UNPAID);
    }

    @Test
    public void execute_batchValidIndicesUnfilteredList_success() {
        Lesson firstLesson = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        Lesson secondLesson = model.getFilteredLessonList().get(INDEX_SECOND_lesson.getZeroBased());
        Lesson paidFirst = new LessonBuilder(firstLesson).withPaid(true).build();
        Lesson paidSecond = new LessonBuilder(secondLesson).withPaid(true).build();
        model.setLesson(firstLesson, paidFirst);
        model.setLesson(secondLesson, paidSecond);

        UnmarkCommand unmarkCommand = new UnmarkCommand(List.of(INDEX_FIRST_lesson, INDEX_SECOND_lesson));

        Lesson unmarkedFirst = new LessonBuilder(paidFirst).withPaid(false).build();
        Lesson unmarkedSecond = new LessonBuilder(paidSecond).withPaid(false).build();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setLesson(paidFirst, unmarkedFirst);
        expectedModel.setLesson(paidSecond, unmarkedSecond);

        String expectedMessage = String.format(UnmarkCommand.MESSAGE_UNMARK_LESSONS_SUCCESS,
                2, unmarkedFirst.getName() + ", " + unmarkedSecond.getName());

        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UnmarkCommand unmarkFirstCommand = new UnmarkCommand(List.of(INDEX_FIRST_lesson));
        UnmarkCommand unmarkSecondCommand = new UnmarkCommand(List.of(INDEX_SECOND_lesson));

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkCommand unmarkFirstCommandCopy = new UnmarkCommand(List.of(INDEX_FIRST_lesson));
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnmarkCommand unmarkCommand = new UnmarkCommand(List.of(targetIndex));
        String expected = UnmarkCommand.class.getCanonicalName() + "{targetIndices=[" + targetIndex + "]}";
        assertEquals(expected, unmarkCommand.toString());
    }
}
