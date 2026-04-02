package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;

/**
 * Adds tags to one or more existing lessons in the address book.
 */
public class AddTagCommand extends TagCommand {
    public static final String SUBCOMMAND_WORD = "add";
    public static final String COMMAND_PHRASE = TagCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Adds tag(s) to lesson(s) in the address book. "
            + "Parameters: "
            + "INDEX [INDEX]... (must be positive integers) "
            + PREFIX_TAG + "TAG (must be a non-empty string)\n"
            + "Example: " + COMMAND_PHRASE + " "
            + "1 2 "
            + PREFIX_TAG + "Primary1 "
            + PREFIX_TAG + "Mathematics";

    public static final String MESSAGE_SUCCESS = "Tag(s) added to lesson: %1$s";
    public static final String MESSAGE_BATCH_SUCCESS = "Tag(s) added to %1$d lessons: %2$s";
    public static final String MESSAGE_TAG_ALREADY_EXISTS =
            "One or more specified tags already exist for this lesson.";

    /**
     * Creates an AddTagCommand to add tags to lessons at {@code targetIndices}.
     */
    public AddTagCommand(List<Index> targetIndices, Set<Tag> tagsToAdd) {
        super(targetIndices, tagsToAdd);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lessonsToTag = getTargetLessons(model);

        for (Lesson lesson : lessonsToTag) {
            if (lesson.getTags().stream().anyMatch(tag -> getTags().contains(tag))) {
                throw new CommandException(MESSAGE_TAG_ALREADY_EXISTS);
            }
        }

        for (Lesson lesson : lessonsToTag) {
            model.addTagsToLesson(lesson, getTags());
        }

        if (lessonsToTag.size() == 1) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(lessonsToTag.get(0))));
        }
        String names = lessonsToTag.stream()
                .map(p -> p.getName().toString()).collect(Collectors.joining(", "));
        return new CommandResult(String.format(MESSAGE_BATCH_SUCCESS, lessonsToTag.size(), names));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddTagCommand)) {
            return false;
        }
        AddTagCommand otherTagCommand = (AddTagCommand) other;
        return getTargetIndices().equals(otherTagCommand.getTargetIndices())
            && getTags().equals(otherTagCommand.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTargetIndices(), getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndices", getTargetIndices())
            .add("tagsToAdd", getTags())
            .toString();
    }
}
