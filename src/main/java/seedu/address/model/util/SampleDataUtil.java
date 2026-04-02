package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Lesson[] getSamplelessons() {
        return new Lesson[] {
            new Lesson(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Day("Monday"), new Time("10:00"), new Time("12:00"),
                    new Rate("20"), true, getTagSet("friends")),

            new Lesson(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Day("Tuesday"), new Time("14:00"), new Time("16:00"),
                    new Rate("25"), false, getTagSet("colleagues", "friends")),

            new Lesson(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Day("Wednesday"), new Time("09:00"), new Time("11:00"),
                    new Rate("30"), true, getTagSet("neighbours")),

            new Lesson(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Day("Thursday"), new Time("18:00"), new Time("20:00"),
                    new Rate("35"), false, getTagSet("family")),

            new Lesson(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Day("Friday"), new Time("13:00"), new Time("15:00"),
                    new Rate("22"), true, getTagSet("classmates")),

            new Lesson(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Day("Saturday"), new Time("11:00"), new Time("13:00"),
                    new Rate("28"), false, getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Lesson sampleLesson : getSamplelessons()) {
            sampleAb.addLesson(sampleLesson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
