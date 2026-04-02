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
import static seedu.address.logic.commands.CommandTestUtil.showlessonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_lesson;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_lesson;
import static seedu.address.testutil.Typicallessons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditlessonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.EditlessonDescriptorBuilder;
import seedu.address.testutil.lessonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Lesson editedLesson = new lessonBuilder().build();
        EditlessonDescriptor descriptor = new EditlessonDescriptorBuilder(editedLesson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_lesson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(model.getFilteredLessonList().get(0), editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastlesson = Index.fromOneBased(model.getFilteredLessonList().size());
        Lesson lastLesson = model.getFilteredLessonList().get(indexLastlesson.getZeroBased());

        lessonBuilder lessonInList = new lessonBuilder(lastLesson);
        Lesson editedLesson = lessonInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditlessonDescriptor descriptor = new EditlessonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastlesson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(lastLesson, editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_lesson, new EditlessonDescriptor());
        Lesson editedLesson = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showlessonAtIndex(model, INDEX_FIRST_lesson);

        Lesson lessonInFilteredList = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        Lesson editedLesson = new lessonBuilder(lessonInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_lesson,
                new EditlessonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(model.getFilteredLessonList().get(0), editedLesson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLessonUnfilteredList_failure() {
        Lesson firstLesson = model.getFilteredLessonList().get(INDEX_FIRST_lesson.getZeroBased());
        EditlessonDescriptor descriptor = new EditlessonDescriptorBuilder(firstLesson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_lesson, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_duplicateLessonFilteredList_failure() {
        showlessonAtIndex(model, INDEX_FIRST_lesson);

        // edit lesson in filtered list into a duplicate in address book
        Lesson lessonInList = model.getAddressBook().getLessonList().get(INDEX_SECOND_lesson.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_lesson,
                new EditlessonDescriptorBuilder(lessonInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_invalidlessonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        EditlessonDescriptor descriptor = new EditlessonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidlessonIndexFilteredList_failure() {
        showlessonAtIndex(model, INDEX_FIRST_lesson);
        Index outOfBoundIndex = INDEX_SECOND_lesson;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditlessonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_lesson, DESC_AMY);

        // same values -> returns true
        EditlessonDescriptor copyDescriptor = new EditlessonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_lesson, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand(ClearCommand.ClearState.PROMPT)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_lesson, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_lesson, DESC_BOB)));

        // different day -> returns false
        EditlessonDescriptor differentDay = new EditlessonDescriptorBuilder(DESC_AMY)
                .withDay(VALID_DAY_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_lesson, differentDay)));

        // different start time -> returns false
        EditlessonDescriptor differentStart = new EditlessonDescriptorBuilder(DESC_AMY)
                .withStartTime(VALID_START_TIME_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_lesson, differentStart)));

        // different end time -> returns false
        EditlessonDescriptor differentEnd = new EditlessonDescriptorBuilder(DESC_AMY)
                .withEndTime(VALID_END_TIME_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_lesson, differentEnd)));

        // different rate -> returns false
        EditlessonDescriptor differentRate = new EditlessonDescriptorBuilder(DESC_AMY)
                .withRate(VALID_RATE_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_lesson, differentRate)));


        // different isPaid -> returns false
        EditlessonDescriptor differentIsPaid = new EditlessonDescriptorBuilder(DESC_AMY)
                .withIsPaid(VALID_IS_PAID_BOB)
                .build();
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_lesson, differentRate)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditlessonDescriptor editlessonDescriptor = new EditlessonDescriptor();
        EditCommand editCommand = new EditCommand(index, editlessonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editlessonDescriptor="
                + editlessonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
