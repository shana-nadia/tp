package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Address;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Email;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Name;
import seedu.address.model.lesson.Phone;
import seedu.address.model.lesson.Rate;
import seedu.address.model.lesson.Time;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing lesson in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lesson identified "
            + "by the index number used in the displayed lesson list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited lesson: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the address book.";

    private final Index index;
    private final EditlessonDescriptor editlessonDescriptor;

    /**
     * @param index of the lesson in the filtered lesson list to edit
     * @param editlessonDescriptor details to edit the lesson with
     */
    public EditCommand(Index index, EditlessonDescriptor editlessonDescriptor) {
        requireNonNull(index);
        requireNonNull(editlessonDescriptor);

        this.index = index;
        this.editlessonDescriptor = new EditlessonDescriptor(editlessonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lastShownList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToEdit = lastShownList.get(index.getZeroBased());
        Lesson editedLesson = createEditedlesson(lessonToEdit, editlessonDescriptor);

        if (!lessonToEdit.isSameLesson(editedLesson) && model.hasLesson(editedLesson)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.setLesson(lessonToEdit, editedLesson);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, Messages.format(editedLesson)));
    }

    /**
     * Creates and returns a {@code lesson} with the details of {@code lessonToEdit}
     * edited with {@code editlessonDescriptor}.
     */
    private static Lesson createEditedlesson(Lesson lessonToEdit, EditlessonDescriptor editlessonDescriptor) {
        assert lessonToEdit != null;

        Name updatedName = editlessonDescriptor.getName().orElse(lessonToEdit.getName());
        Phone updatedPhone = editlessonDescriptor.getPhone().orElse(lessonToEdit.getPhone());
        Email updatedEmail = editlessonDescriptor.getEmail().orElse(lessonToEdit.getEmail());
        Address updatedAddress = editlessonDescriptor.getAddress().orElse(lessonToEdit.getAddress());
        Day updatedDay = editlessonDescriptor.getDay().orElse(lessonToEdit.getDay());
        Time updatedStartTime = editlessonDescriptor.getStartTime().orElse(lessonToEdit.getStartTime());
        Time updatedEndTime = editlessonDescriptor.getEndTime().orElse(lessonToEdit.getEndTime());
        Rate updatedRate = editlessonDescriptor.getRate().orElse(lessonToEdit.getRate());
        boolean updatedIsPaid = editlessonDescriptor.getIsPaid().orElse(lessonToEdit.isPaid());
        Set<Tag> updatedTags = editlessonDescriptor.getTags().orElse(lessonToEdit.getTags());

        return new Lesson(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedDay, updatedStartTime, updatedEndTime, updatedRate, updatedIsPaid, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editlessonDescriptor.equals(otherEditCommand.editlessonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editlessonDescriptor", editlessonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the lesson with. Each non-empty field value will replace the
     * corresponding field value of the lesson.
     */
    public static class EditlessonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Day day;
        private Time startTime;
        private Time endTime;
        private Rate rate;
        private Boolean isPaid;
        private Set<Tag> tags;

        public EditlessonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditlessonDescriptor(EditlessonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setDay(toCopy.day);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
            setRate(toCopy.rate);
            setIsPaid(toCopy.isPaid);
            setTags(toCopy.tags);
        }
        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, day, startTime, endTime, rate, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setDay(Day day) {
            this.day = day;
        }

        public Optional<Day> getDay() {
            return Optional.ofNullable(day);
        }

        public void setStartTime(Time startTime) {
            this.startTime = startTime;
        }

        public Optional<Time> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(Time endTime) {
            this.endTime = endTime;
        }

        public Optional<Time> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public void setRate(Rate rate) {
            this.rate = rate;
        }

        public Optional<Rate> getRate() {
            return Optional.ofNullable(rate);
        }

        public void setIsPaid(Boolean isPaid) {
            this.isPaid = isPaid;
        }

        public Optional<Boolean> getIsPaid() {
            return Optional.ofNullable(isPaid);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditlessonDescriptor)) {
                return false;
            }

            EditlessonDescriptor otherEditlessonDescriptor = (EditlessonDescriptor) other;
            return Objects.equals(name, otherEditlessonDescriptor.name)
                    && Objects.equals(phone, otherEditlessonDescriptor.phone)
                    && Objects.equals(email, otherEditlessonDescriptor.email)
                    && Objects.equals(address, otherEditlessonDescriptor.address)
                    && Objects.equals(day, otherEditlessonDescriptor.day)
                    && Objects.equals(startTime, otherEditlessonDescriptor.startTime)
                    && Objects.equals(endTime, otherEditlessonDescriptor.endTime)
                    && Objects.equals(rate, otherEditlessonDescriptor.rate)
                    && Objects.equals(isPaid, otherEditlessonDescriptor.isPaid)
                    && Objects.equals(tags, otherEditlessonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("day", day)
                    .add("startTime", startTime)
                    .add("endTime", endTime)
                    .add("rate", rate)
                    .add("isPaid", isPaid)
                    .add("tags", tags)
                    .toString();
        }
    }
}
