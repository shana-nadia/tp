package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditlessonDescriptor;
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
 * A utility class to help with building EditlessonDescriptor objects.
 */
public class EditlessonDescriptorBuilder {

    private EditlessonDescriptor descriptor;

    public EditlessonDescriptorBuilder() {
        descriptor = new EditlessonDescriptor();
    }

    public EditlessonDescriptorBuilder(EditlessonDescriptor descriptor) {
        this.descriptor = new EditlessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditlessonDescriptor} with fields containing {@code lesson}'s details
     */
    public EditlessonDescriptorBuilder(Lesson lesson) {
        descriptor = new EditlessonDescriptor();
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
     * Sets the {@code Name} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Day} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withDay(String day) {
        descriptor.setDay(new Day(day));
        return this;
    }

    /**
     * Sets the {@code Start Time} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new Time(startTime));
        return this;
    }

    /**
     * Sets the {@code End Time} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new Time(endTime));
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withRate(String rate) {
        descriptor.setRate(new Rate(rate));
        return this;
    }

    /**
     * Sets the {@code isPaid} of the {@code EditlessonDescriptor} that we are building.
     */
    public EditlessonDescriptorBuilder withIsPaid(boolean isPaid) {
        descriptor.setIsPaid(isPaid);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditlessonDescriptor}
     * that we are building.
     */
    public EditlessonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditlessonDescriptor build() {
        return descriptor;
    }
}
