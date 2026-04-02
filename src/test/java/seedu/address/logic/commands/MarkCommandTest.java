package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showlessonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_lesson;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_lesson;
import static seedu.address.testutil.Typicallessons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.lessonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code MarkCommand}.
 */
public class MarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Lesson lessonToMark = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        MarkCommand markCommand = new MarkCommand(List.of(INDEX_FIRST_lesson));

        Lesson markedLesson = new lessonBuilder(lessonToMark).withPaid(true).build();
        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_LESSON_SUCCESS,
                Messages.format(markedLesson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setLesson(lessonToMark, markedLesson);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        MarkCommand markCommand = new MarkCommand(List.of(outOfBoundIndex));

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showlessonAtIndex(model, INDEX_FIRST_lesson);

        Lesson lessonToMark = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        MarkCommand markCommand = new MarkCommand(List.of(INDEX_FIRST_lesson));

        Lesson markedLesson = new lessonBuilder(lessonToMark).withPaid(true).build();
        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_LESSON_SUCCESS,
                Messages.format(markedLesson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setLesson(lessonToMark, markedLesson);
        showlessonAtIndex(expectedModel, INDEX_FIRST_lesson);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showlessonAtIndex(model, INDEX_FIRST_lesson);

        Index outOfBoundIndex = INDEX_SECOND_lesson;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        MarkCommand markCommand = new MarkCommand(List.of(outOfBoundIndex));

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyPaid_throwsCommandException() {
        Lesson lessonToMark = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        Lesson alreadyPaidLesson = new lessonBuilder(lessonToMark).withPaid(true).build();
        model.setLesson(lessonToMark, alreadyPaidLesson);

        MarkCommand markCommand = new MarkCommand(List.of(INDEX_FIRST_lesson));

        assertCommandFailure(markCommand, model, MarkCommand.MESSAGE_ALREADY_PAID);
    }

    @Test
    public void execute_batchValidIndicesUnfilteredList_success() {
        Lesson firstLesson = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        Lesson secondLesson = model.getFilteredLessonList().get(INDEX_SECOND_lesson.getZeroBased());
        MarkCommand markCommand = new MarkCommand(List.of(INDEX_FIRST_lesson, INDEX_SECOND_lesson));

        Lesson markedFirst = new lessonBuilder(firstLesson).withPaid(true).build();
        Lesson markedSecond = new lessonBuilder(secondLesson).withPaid(true).build();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setLesson(firstLesson, markedFirst);
        expectedModel.setLesson(secondLesson, markedSecond);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_LESSONS_SUCCESS,
                2, markedFirst.getName() + ", " + markedSecond.getName());

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MarkCommand markFirstCommand = new MarkCommand(List.of(INDEX_FIRST_lesson));
        MarkCommand markSecondCommand = new MarkCommand(List.of(INDEX_SECOND_lesson));

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkCommand markFirstCommandCopy = new MarkCommand(List.of(INDEX_FIRST_lesson));
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkCommand markCommand = new MarkCommand(List.of(targetIndex));
        String expected = MarkCommand.class.getCanonicalName() + "{targetIndices=[" + targetIndex + "]}";
        assertEquals(expected, markCommand.toString());
    }
}
