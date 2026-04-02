package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Typicallessons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.lessonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newLesson_success() {
        Lesson validLesson = new lessonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addLesson(validLesson);

        assertCommandSuccess(new AddCommand(validLesson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validLesson)),
                expectedModel);
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson lessonInList = model.getAddressBook().getLessonList().get(0);
        assertCommandFailure(new AddCommand(lessonInList), model,
                AddCommand.MESSAGE_DUPLICATE_LESSON);
    }

}
