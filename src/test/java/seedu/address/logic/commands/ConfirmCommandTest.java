package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Typicallessons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ConfirmCommandTest {

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager();

        ConfirmCommand confirmClear = new ConfirmCommand("clear");
        assertCommandSuccess(confirmClear, model, ConfirmCommand.MESSAGE_SUCCESS_CLEAR, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        ConfirmCommand confirmClear = new ConfirmCommand("clear");
        assertCommandSuccess(confirmClear, model, ConfirmCommand.MESSAGE_SUCCESS_CLEAR, expectedModel);
    }

    @Test
    public void execute_unknownArgs_returnsInvalidMessage() {
        Model model = new ModelManager();
        ConfirmCommand confirmUnknown = new ConfirmCommand("delete");

        assertCommandSuccess(confirmUnknown, model, ConfirmCommand.MESSAGE_INVALID, model);
    }

    @Test
    public void execute_uppercaseInput_convertsToLowercase() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        ConfirmCommand command = new ConfirmCommand("CLEAR");

        assertCommandSuccess(command, model,
                ConfirmCommand.MESSAGE_SUCCESS_CLEAR, expectedModel);
    }

    @Test
    public void equals() {
        ConfirmCommand clearCommand = new ConfirmCommand("clear");
        ConfirmCommand clearCommandCopy = new ConfirmCommand("clear");
        ConfirmCommand differentCommand = new ConfirmCommand("delete");

        // same object -> true
        assertTrue(clearCommand.equals(clearCommand));

        // same values -> true
        assertTrue(clearCommand.equals(clearCommandCopy));

        // different type -> false
        assertFalse(clearCommand.equals(1));

        // null -> false
        assertFalse(clearCommand.equals(null));

        // different confirmType -> false
        assertFalse(clearCommand.equals(differentCommand));
    }

    @Test
    public void hashCode_sameValue_sameHashCode() {
        ConfirmCommand c1 = new ConfirmCommand("clear");
        ConfirmCommand c2 = new ConfirmCommand("clear");

        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
