package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditlessonDescriptor;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;

/**
 * A utility class for lesson.
 */
public class lessonUtil {

    /**
     * Returns an add command string for adding the {@code lesson}.
     */
    public static String getAddCommand(Lesson lesson) {
        return AddCommand.COMMAND_WORD + " " + getlessonDetails(lesson);
    }

    /**
     * Returns the part of command string for the given {@code lesson}'s details.
     */
    public static String getlessonDetails(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + lesson.getName().fullName + " ");
        sb.append(PREFIX_PHONE + lesson.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + lesson.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + lesson.getAddress().value + " ");
        sb.append(PREFIX_DAY + lesson.getDay().value + " ");
        sb.append(PREFIX_START + lesson.getStartTime().value + " ");
        sb.append(PREFIX_END + lesson.getEndTime().value + " ");
        sb.append(PREFIX_RATE + lesson.getRate().value + " ");
        lesson.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditlessonDescriptor}'s details.
     */
    public static String getEditlessonDescriptorDetails(EditlessonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name ->
                sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone ->
                sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email ->
                sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address ->
                sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getDay().ifPresent(day ->
                sb.append(PREFIX_DAY).append(day.value).append(" "));
        descriptor.getStartTime().ifPresent(startTime ->
                sb.append(PREFIX_START).append(startTime.value).append(" "));
        descriptor.getEndTime().ifPresent(endTime ->
                sb.append(PREFIX_END).append(endTime.value).append(" "));
        descriptor.getRate().ifPresent(rate ->
                sb.append(PREFIX_RATE).append(rate.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
