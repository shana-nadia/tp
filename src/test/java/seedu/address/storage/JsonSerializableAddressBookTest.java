package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalLessons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_LESSONS_FILE =
            TEST_DATA_FOLDER.resolve("typicalLessonsAddressBook.json");
    private static final Path INVALID_LESSON_EMAIL_FILE =
            TEST_DATA_FOLDER.resolve("invalidLessonEmailAddressBook.json");
    private static final Path INVALID_LESSON_DAY_FILE =
            TEST_DATA_FOLDER.resolve("invalidLessonDayAddressBook.json");
    private static final Path INVALID_LESSON_TIME_FILE =
            TEST_DATA_FOLDER.resolve("invalidLessonTimeAddressBook.json");
    private static final Path INVALID_LESSON_RATE_FILE =
            TEST_DATA_FOLDER.resolve("invalidLessonRateAddressBook.json");
    private static final Path DUPLICATE_LESSON_FILE =
            TEST_DATA_FOLDER.resolve("duplicateLessonAddressBook.json");
    private static final Path NOT_DUPLICATE_LESSON_FILE =
            TEST_DATA_FOLDER.resolve("notDuplicateLessonAddressBook.json");

    @Test
    public void toModelType_typicalLessonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_LESSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalLessonsAddressBook = TypicalLessons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalLessonsAddressBook);
    }

    @Test
    public void toModelType_invalidLessonEmailFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_LESSON_EMAIL_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidLessonDayFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_LESSON_DAY_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidLessonTimeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_LESSON_TIME_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
    @Test
    public void toModelType_invalidLessonRateFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_LESSON_RATE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateLessons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_LESSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_LESSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateLessons_allowed() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(NOT_DUPLICATE_LESSON_FILE,
                JsonSerializableAddressBook.class).get();

        assertDoesNotThrow(dataFromFile::toModelType);
    }

}
