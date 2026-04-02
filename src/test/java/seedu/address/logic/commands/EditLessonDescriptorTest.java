package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditLessonDescriptor;
import seedu.address.testutil.EditLessonDescriptorBuilder;

public class EditLessonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditLessonDescriptor descriptorWithSameValues = new EditLessonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditLessonDescriptor editedAmy = new EditLessonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        String expected = EditLessonDescriptor.class.getCanonicalName() + "{name="
                + editLessonDescriptor.getName().orElse(null) + ", phone="
                + editLessonDescriptor.getPhone().orElse(null) + ", email="
                + editLessonDescriptor.getEmail().orElse(null) + ", address="
                + editLessonDescriptor.getAddress().orElse(null) + ", day="
                + editLessonDescriptor.getDay().orElse(null) + ", startTime="
                + editLessonDescriptor.getStartTime().orElse(null) + ", endTime="
                + editLessonDescriptor.getEndTime().orElse(null) + ", rate="
                + editLessonDescriptor.getRate().orElse(null) + ", isPaid="
                + editLessonDescriptor.getIsPaid().orElse(null) + ", tags="
                + editLessonDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editLessonDescriptor.toString());
    }
}
