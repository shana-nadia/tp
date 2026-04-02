package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameLesson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueLessonList lessons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        lessons = new UniqueLessonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the lessons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the lesson list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons.setLessons(lessons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setLessons(newData.getLessonList());
    }

    //// lesson-level operations

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in the address book.
     */
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return lessons.contains(lesson);
    }

    /**
     * Adds a lesson to the address book.
     * The lesson must not already exist in the address book.
     */
    public void addLesson(Lesson p) {
        lessons.add(p);
    }

    /**
     * Replaces the given lesson {@code target} in the list with {@code editedlesson}.
     * {@code target} must exist in the address book.
     * The lesson identity of {@code editedlesson} must not be the same as another existing lesson in the address book.
     */
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireNonNull(editedLesson);

        lessons.setLesson(target, editedLesson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removelesson(Lesson key) {
        lessons.remove(key);
    }

    /**
     * Adds the given tags to the lesson.
     * {@code target} must exist in the address book.
     */
    public void addTagsToLesson(Lesson target, Set<Tag> tagsToAdd) {
        requireNonNull(target);
        requireNonNull(tagsToAdd);

        lessons.addTagsToLesson(target, tagsToAdd);
    }

    /**
     * Deletes the given tags from the lesson.
     * {@code target} must exist in the address book.
     */
    public void deleteTagsFromLesson(Lesson target, Set<Tag> tagsToDelete) {
        requireNonNull(target);
        requireNonNull(tagsToDelete);

        lessons.deleteTagsFromLesson(target, tagsToDelete);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("lessons", lessons)
                .toString();
    }

    @Override
    public ObservableList<Lesson> getLessonList() {
        return lessons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return lessons.equals(otherAddressBook.lessons);
    }

    @Override
    public int hashCode() {
        return lessons.hashCode();
    }
}
