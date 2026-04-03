package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalLessons.AMY;
import static seedu.address.testutil.TypicalLessons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.lesson.Address;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Email;
import seedu.address.model.lesson.Name;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Phone;
import seedu.address.model.lesson.Rate;
import seedu.address.model.lesson.Time;
import seedu.address.testutil.LessonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Lesson expectedLesson = new LessonBuilder(BOB)
                .withDay(VALID_DAY_BOB)
                .withStartTime(VALID_START_TIME_BOB)
                .withEndTime(VALID_END_TIME_BOB)
                .withRate(VALID_RATE_BOB)
                .withTags()
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB
                + RATE_DESC_BOB, new AddCommand(expectedLesson));


        // multiple tags - all accepted
        Lesson expectedLessonMultipleTags = new LessonBuilder(BOB)
                .withDay(VALID_DAY_BOB)
                .withStartTime(VALID_START_TIME_BOB)
                .withEndTime(VALID_END_TIME_BOB)
                .withRate(VALID_RATE_BOB)
                .withTags()
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB
                        + RATE_DESC_BOB,
                new AddCommand(expectedLessonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedLessonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB
                + RATE_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple days of the week
        assertParseFailure(parser, DAY_DESC_AMY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // multiple start times
        assertParseFailure(parser, START_TIME_DESC_AMY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START));

        // multiple end times
        assertParseFailure(parser, END_TIME_DESC_AMY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END));

        // multiple tuition rates
        assertParseFailure(parser, RATE_DESC_AMY + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedLessonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + DAY_DESC_AMY + START_TIME_DESC_AMY + END_TIME_DESC_AMY + RATE_DESC_AMY
                        + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE,
                        PREFIX_DAY, PREFIX_START, PREFIX_END, PREFIX_RATE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid day of the week
        assertParseFailure(parser, INVALID_DAY_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // invalid start time
        assertParseFailure(parser, INVALID_START_TIME_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START));

        // invalid end time
        assertParseFailure(parser, INVALID_END_TIME_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END));

        // invalid tuition rate
        assertParseFailure(parser, INVALID_RATE_DESC + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedLessonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedLessonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedLessonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedLessonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid day of the week
        assertParseFailure(parser, validExpectedLessonString + INVALID_DAY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // invalid start time
        assertParseFailure(parser, validExpectedLessonString + INVALID_START_TIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START));

        // invalid end time
        assertParseFailure(parser, validExpectedLessonString + INVALID_END_TIME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END));

        // invalid tuition rate
        assertParseFailure(parser, validExpectedLessonString + INVALID_RATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Lesson expectedLesson = new LessonBuilder(AMY)
                .withDay(VALID_DAY_AMY)
                .withStartTime(VALID_START_TIME_AMY)
                .withEndTime(VALID_END_TIME_AMY)
                .withRate(VALID_RATE_AMY)
                .withTags()
                .build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + DAY_DESC_AMY + START_TIME_DESC_AMY + END_TIME_DESC_AMY + RATE_DESC_AMY,
                new AddCommand(expectedLesson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                expectedMessage);

        // missing day of the week prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + VALID_DAY_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DAY_DESC_BOB + VALID_START_TIME_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DAY_DESC_BOB + START_TIME_DESC_BOB + VALID_END_TIME_BOB + RATE_DESC_BOB,
                expectedMessage);

        // missing tuition rate prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + VALID_RATE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                        + VALID_DAY_BOB + VALID_START_TIME_BOB + VALID_END_TIME_BOB + VALID_RATE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                Address.MESSAGE_CONSTRAINTS);

        //invalid day of the week
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_DAY_DESC + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                Day.MESSAGE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DAY_DESC_BOB + INVALID_START_TIME_DESC + END_TIME_DESC_BOB + RATE_DESC_BOB,
                Time.MESSAGE_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DAY_DESC_BOB + START_TIME_DESC_BOB + INVALID_END_TIME_DESC + RATE_DESC_BOB,
                Time.MESSAGE_CONSTRAINTS);

        //invalid tuition rate
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + INVALID_RATE_DESC,
                Rate.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DAY_DESC_BOB + START_TIME_DESC_BOB + END_TIME_DESC_BOB + RATE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
