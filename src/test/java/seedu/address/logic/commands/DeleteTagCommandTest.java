package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_lesson;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_lesson;
import static seedu.address.testutil.Typicallessons.getTypicalAddressBook;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteTagCommand.
 */
public class DeleteTagCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Lesson lessonToUpdate = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        Set<Tag> tagsToDelete = Set.of(new Tag("friends"));
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(INDEX_FIRST_lesson), tagsToDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_SUCCESS, Messages.format(lessonToUpdate));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTagsFromLesson(lessonToUpdate, tagsToDelete);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNotFound_throwsCommandException() {
        Set<Tag> tagsToDelete = Set.of(new Tag("classmate"));
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(INDEX_FIRST_lesson), tagsToDelete);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        Set<Tag> tagsToDelete = Set.of(new Tag("friends"));
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(outOfBoundIndex), tagsToDelete);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Set<Tag> firstTags = Set.of(new Tag("friends"));
        Set<Tag> secondTags = Set.of(new Tag("teammate"));
        DeleteTagCommand deleteFirstCommand = new DeleteTagCommand(List.of(INDEX_FIRST_lesson), firstTags);
        DeleteTagCommand deleteSecondCommand = new DeleteTagCommand(List.of(INDEX_SECOND_lesson), secondTags);
        DeleteTagCommand deleteFirstCommandDifferentTags =
                new DeleteTagCommand(List.of(INDEX_FIRST_lesson), secondTags);

        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        DeleteTagCommand deleteFirstCommandCopy = new DeleteTagCommand(List.of(INDEX_FIRST_lesson), firstTags);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteFirstCommand.equals(null));
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandDifferentTags));
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void hashCodeMethod() {
        Set<Tag> tagsToDelete = Set.of(new Tag("friends"));
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(INDEX_FIRST_lesson), tagsToDelete);
        DeleteTagCommand deleteTagCommandCopy = new DeleteTagCommand(List.of(INDEX_FIRST_lesson), tagsToDelete);

        assertEquals(deleteTagCommand.hashCode(), deleteTagCommandCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tagsToDelete = Set.of(new Tag("friends"));
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(List.of(INDEX_FIRST_lesson), tagsToDelete);
        String expected = DeleteTagCommand.class.getCanonicalName()
                + "{targetIndices=" + List.of(INDEX_FIRST_lesson) + ", tagsToDelete=" + tagsToDelete + "}";
        assertEquals(expected, deleteTagCommand.toString());
    }
}
