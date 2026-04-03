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
 * Deletes one or more lessons identified using their displayed indices from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson(s) identified by the index number(s) used in the displayed lesson list.\n"
            + "Parameters: INDEX [INDEX]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";
    public static final String MESSAGE_DELETE_LESSONS_SUCCESS = "Deleted %1$d lessons: %2$s";

    private final List<Index> targetIndices;

    /**
     * Creates a DeleteCommand to delete lessons at {@code targetIndices}.
     */
    public DeleteCommand(List<Index> targetIndices) {
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

        List<Lesson> lessonsToDelete = new ArrayList<>();
        for (Index index : targetIndices) {
            lessonsToDelete.add(lastShownList.get(index.getZeroBased()));
        }

        for (Lesson lesson : lessonsToDelete) {
            model.deleteLesson(lesson);
        }

        if (lessonsToDelete.size() == 1) {
            return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS,
                    Messages.format(lessonsToDelete.get(0))));
        }
        String names = lessonsToDelete.stream()
                .map(p -> p.getName().toString()).collect(Collectors.joining(", "));
        return new CommandResult(String.format(MESSAGE_DELETE_LESSONS_SUCCESS, lessonsToDelete.size(), names));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
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
