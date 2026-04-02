package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.lesson.Lesson;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_LESSON_DISPLAYED_INDEX = "The lesson index provided is invalid";
    public static final String MESSAGE_LESSONS_LISTED_OVERVIEW = "%1$d lessons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code lesson} for display to the user.
     */
    public static String format(Lesson lesson) {
        final StringBuilder builder = new StringBuilder();
        builder.append(lesson.getName())
                .append("; Phone: ")
                .append(lesson.getPhone())
                .append("; Email: ")
                .append(lesson.getEmail())
                .append("; Address: ")
                .append(lesson.getAddress())
                .append("; Tags: ");
        lesson.getTags().forEach(builder::append);
        return builder.toString();
    }

}
