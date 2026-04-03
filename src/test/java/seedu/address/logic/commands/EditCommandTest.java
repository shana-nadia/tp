package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IS_PAID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLessonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditLessonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Lesson editedLesson = new LessonBuilder().build();
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LESSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(model.getFilteredLessonList().get(0), editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLesson = Index.fromOneBased(model.getFilteredLessonList().size());
        Lesson lastLesson = model.getFilteredLessonList().get(indexLastLesson.getZeroBased());

        LessonBuilder lessonInList = new LessonBuilder(lastLesson);
        Lesson editedLesson = lessonInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastLesson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(lastLesson, editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LESSON, new EditLessonDescriptor());
        Lesson editedLesson = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLessonAtIndex(model, INDEX_FIRST_LESSON);

        Lesson lessonInFilteredList = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        Lesson editedLesson = new LessonBuilder(lessonInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LESSON,
                new EditLessonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(model.getFilteredLessonList().get(0), editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLessonUnfilteredList_failure() {
        Lesson firstLesson = model.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(firstLesson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_LESSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_duplicateLessonFilteredList_failure() {
        showLessonAtIndex(model, INDEX_FIRST_LESSON);

        // edit lesson in filtered list into a duplicate in address book
        Lesson lessonInList = model.getAddressBook().getLessonList().get(INDEX_SECOND_LESSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LESSON,
                new EditLessonDescriptorBuilder(lessonInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_invalidLessonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidLessonIndexFilteredList_failure() {
        showLessonAtIndex(model, INDEX_FIRST_LESSON);
        Index outOfBoundIndex = INDEX_SECOND_LESSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditLessonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_LESSON, DESC_AMY);

        // same values -> returns true
        EditLessonDescriptor copyDescriptor = new EditLessonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_LESSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand(ClearCommand.ClearState.PROMPT)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_LESSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LESSON, DESC_BOB)));

        // different day -> returns false
        EditLessonDescriptor differentDay = new EditLessonDescriptorBuilder(DESC_AMY)
                .withDay(VALID_DAY_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LESSON, differentDay)));

        // different start time -> returns false
        EditLessonDescriptor differentStart = new EditLessonDescriptorBuilder(DESC_AMY)
                .withStartTime(VALID_START_TIME_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LESSON, differentStart)));

        // different end time -> returns false
        EditLessonDescriptor differentEnd = new EditLessonDescriptorBuilder(DESC_AMY)
                .withEndTime(VALID_END_TIME_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LESSON, differentEnd)));

        // different rate -> returns false
        EditLessonDescriptor differentRate = new EditLessonDescriptorBuilder(DESC_AMY)
                .withRate(VALID_RATE_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LESSON, differentRate)));


        // different isPaid -> returns false
        EditLessonDescriptor differentIsPaid = new EditLessonDescriptorBuilder(DESC_AMY)
                .withIsPaid(VALID_IS_PAID_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LESSON, differentRate)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        EditCommand editCommand = new EditCommand(index, editLessonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editLessonDescriptor="
                + editLessonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
