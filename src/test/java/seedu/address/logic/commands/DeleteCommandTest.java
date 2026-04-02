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

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Lesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_lesson));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.format(lessonToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showlessonAtIndex(model, INDEX_FIRST_lesson);

        Lesson lessonToDelete = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_lesson));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.format(lessonToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(lessonToDelete);
        showNolesson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showlessonAtIndex(model, INDEX_FIRST_lesson);

        Index outOfBoundIndex = INDEX_SECOND_lesson;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_batchValidIndicesUnfilteredList_success() {
        Lesson firstLesson = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        Lesson secondLesson = model.getFilteredLessonList().get(INDEX_SECOND_lesson.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_lesson, INDEX_SECOND_lesson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(firstLesson);
        expectedModel.deleteLesson(secondLesson);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LESSONS_SUCCESS,
                2, firstLesson.getName() + ", " + secondLesson.getName());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(List.of(INDEX_FIRST_lesson));
        DeleteCommand deleteSecondCommand = new DeleteCommand(List.of(INDEX_SECOND_lesson));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(List.of(INDEX_FIRST_lesson));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different lesson -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(targetIndex));
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndices=[" + targetIndex + "]}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNolesson(Model model) {
        model.updateFilteredLessonList(p -> false);

        assertTrue(model.getFilteredLessonList().isEmpty());
    }
}
