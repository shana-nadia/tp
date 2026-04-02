package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Lesson> filteredLessons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredLessons = new FilteredList<>(this.addressBook.getLessonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return addressBook.hasLesson(lesson);
    }

    @Override
    public void deleteLesson(Lesson target) {
        addressBook.removelesson(target);
    }

    @Override
    public void addLesson(Lesson lesson) {
        addressBook.addLesson(lesson);
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
    }

    @Override
    public void addTagsToLesson(Lesson target, Set<Tag> tagsToAdd) {
        addressBook.addTagsToLesson(target, tagsToAdd);
    }

    @Override
    public void deleteTagsFromLesson(Lesson target, Set<Tag> tagsToDelete) {
        addressBook.deleteTagsFromLesson(target, tagsToDelete);
    }

    @Override
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);

        addressBook.setLesson(target, editedLesson);
    }

    //=========== Filtered lesson List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code lesson} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return filteredLessons;
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        filteredLessons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
            && userPrefs.equals(otherModelManager.userPrefs)
            && filteredLessons.equals(otherModelManager.filteredLessons);
    }
}
