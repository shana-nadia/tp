package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditLessonDescriptor;
import seedu.address.model.lesson.Address;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Email;
import seedu.address.model.lesson.Name;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Phone;
import seedu.address.model.lesson.Rate;
import seedu.address.model.lesson.Time;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditLessonDescriptor objects.
 */
public class EditLessonDescriptorBuilder {

    private EditLessonDescriptor descriptor;

    public EditLessonDescriptorBuilder() {
        descriptor = new EditLessonDescriptor();
    }

    public EditLessonDescriptorBuilder(EditLessonDescriptor descriptor) {
        this.descriptor = new EditLessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLessonDescriptor} with fields containing {@code lesson}'s details
     */
    public EditLessonDescriptorBuilder(Lesson lesson) {
        descriptor = new EditLessonDescriptor();
        descriptor.setName(lesson.getName());
        descriptor.setPhone(lesson.getPhone());
        descriptor.setEmail(lesson.getEmail());
        descriptor.setAddress(lesson.getAddress());
        descriptor.setDay(lesson.getDay());
        descriptor.setStartTime(lesson.getStartTime());
        descriptor.setEndTime(lesson.getEndTime());
        descriptor.setRate(lesson.getRate());
        descriptor.setTags(lesson.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Day} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withDay(String day) {
        descriptor.setDay(new Day(day));
        return this;
    }

    /**
     * Sets the {@code Start Time} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new Time(startTime));
        return this;
    }

    /**
     * Sets the {@code End Time} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new Time(endTime));
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withRate(String rate) {
        descriptor.setRate(new Rate(rate));
        return this;
    }

    /**
     * Sets the {@code isPaid} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withIsPaid(boolean isPaid) {
        descriptor.setIsPaid(isPaid);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditLessonDescriptor}
     * that we are building.
     */
    public EditLessonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditLessonDescriptor build() {
        return descriptor;
    }
}
