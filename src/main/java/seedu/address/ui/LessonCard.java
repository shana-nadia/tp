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
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label lessonLabel;
    @FXML
    private Label rateLabel;
    @FXML
    private Label paymentStatusLabel;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code lessonCode} with the given {@code lesson} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        entryHeader.setText("ENTRY " + displayedIndex);
        idLabel.setText(String.valueOf(displayedIndex));
        nameLabel.setText(lesson.getName().fullName);
        phoneLabel.setText(lesson.getPhone().value);
        emailLabel.setText(lesson.getEmail().value);
        addressLabel.setText(lesson.getAddress().value);

        if (lesson.getDay() != null && lesson.getStartTime() != null && lesson.getEndTime() != null) {
            this.lessonLabel.setText("Lesson: " + lesson.getDay().value + " "
                    + lesson.getStartTime().value + "-" + lesson.getEndTime().value);
        } else {
            this.lessonLabel.setText("");
        }

        if (lesson.getRate() != null) {
            rateLabel.setText("$" + lesson.getRate().value);
        } else {
            rateLabel.setText("");
        }

        if (lesson.isPaid()) {
            paymentStatusLabel.setText("Paid");
            paymentStatusLabel.getStyleClass().add("payment-paid");
        } else {
            paymentStatusLabel.setText("Unpaid");
            paymentStatusLabel.getStyleClass().add("payment-unpaid");
        }

        lesson.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
