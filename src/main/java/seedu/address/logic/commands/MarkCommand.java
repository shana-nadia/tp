package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Marks one or more students' payment status as paid.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the payment status of the student(s) identified by index number(s)"
            + " in the displayed student list as paid.\n"
            + "Parameters: INDEX [INDEX]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_MARK_LESSON_SUCCESS = "Marked student as paid: %1$s";
    public static final String MESSAGE_MARK_LESSONS_SUCCESS = "Marked %1$d students as paid: %2$s";
    public static final String MESSAGE_ALREADY_PAID = "This student has already been marked as paid.";

    private final List<Index> targetIndices;

    /**
     * Creates a MarkCommand to mark the lessons at {@code targetIndices} as paid.
     */
    public MarkCommand(List<Index> targetIndices) {
        requireNonNull(targetIndices);
        this.targetIndices = new ArrayList<>(targetIndices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lastShownList = model.getFilteredLessonList();

        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
            }
        }

        List<Lesson> lessonsToMark = new ArrayList<>();
        for (Index index : targetIndices) {
            Lesson lesson = lastShownList.get(index.getZeroBased());
            if (lesson.isPaid()) {
                throw new CommandException(MESSAGE_ALREADY_PAID);
            }
            lessonsToMark.add(lesson);
        }

        List<Lesson> markedLessons = new ArrayList<>();
        for (Lesson lessonToMark : lessonsToMark) {
            Lesson markedLesson = new Lesson(
                    lessonToMark.getName(), lessonToMark.getPhone(), lessonToMark.getEmail(),
                    lessonToMark.getAddress(), lessonToMark.getDay(), lessonToMark.getStartTime(),
                    lessonToMark.getEndTime(), lessonToMark.getRate(), true, lessonToMark.getTags());
            model.setLesson(lessonToMark, markedLesson);
            markedLessons.add(markedLesson);
        }

        if (markedLessons.size() == 1) {
            return new CommandResult(String.format(MESSAGE_MARK_LESSON_SUCCESS,
                    Messages.format(markedLessons.get(0))));
        }
        String names = markedLessons.stream()
                .map(p -> p.getName().toString()).collect(Collectors.joining(", "));
        return new CommandResult(String.format(MESSAGE_MARK_LESSONS_SUCCESS, markedLessons.size(), names));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MarkCommand)) {
            return false;
        }
        MarkCommand otherMarkCommand = (MarkCommand) other;
        return targetIndices.equals(otherMarkCommand.targetIndices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
