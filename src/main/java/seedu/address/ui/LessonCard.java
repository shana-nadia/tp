package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;

/**
 * An UI component that displays information of a {@code lesson}.
 */
public class LessonCard extends UiPart<Region> {

    private static final String FXML = "LessonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Lesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label entryHeader;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label lesson;
    @FXML
    private Label rate;
    @FXML
    private Label paymentStatus;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code lessonCode} with the given {@code lesson} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        entryHeader.setText("ENTRY " + displayedIndex);
        id.setText(String.valueOf(displayedIndex));
        name.setText(lesson.getName().fullName);
        phone.setText(lesson.getPhone().value);
        email.setText(lesson.getEmail().value);
        address.setText(lesson.getAddress().value);

        if (lesson.getDay() != null && lesson.getStartTime() != null && lesson.getEndTime() != null) {
            this.lesson.setText("Lesson: " + lesson.getDay().value + " "
                    + lesson.getStartTime().value + "-" + lesson.getEndTime().value);
        } else {
            this.lesson.setText("");
        }

        if (lesson.getRate() != null) {
            rate.setText("$" + lesson.getRate().value);
        } else {
            rate.setText("");
        }

        if (lesson.isPaid()) {
            paymentStatus.setText("Paid");
            paymentStatus.getStyleClass().add("payment-paid");
        } else {
            paymentStatus.setText("Unpaid");
            paymentStatus.getStyleClass().add("payment-unpaid");
        }

        lesson.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
