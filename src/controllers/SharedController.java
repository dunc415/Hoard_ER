package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SharedController {
    
/**
     * This is the popup that you see if information is not inputted correctly and
     * also gives a confirmation message
     * @param message
     * @param stage
     */
    public void popupActivation(String message, Stage stage) {
        Timeline timeline = new Timeline();

        double popupWidth = 250;
        double popupHeight = 50;

        Rectangle2D rectangle2D = findPopupPosition(Screen.getPrimary().getVisualBounds(), popupWidth, popupHeight, stage);


        Stage messageStage = new Stage();
        messageStage.setAlwaysOnTop(true);
        messageStage.setX(rectangle2D.getWidth());
        messageStage.setY(rectangle2D.getHeight());
        messageStage.initStyle(StageStyle.UNDECORATED);

        Label lblMessage = new Label(message);
        lblMessage.setWrapText(true);
        lblMessage.setTextAlignment(TextAlignment.CENTER);

        HBox popup = new HBox(lblMessage);
        popup.setAlignment(Pos.CENTER);
        popup.setSpacing(10);
        popup.setVisible(false);

        Scene messageScene = new Scene(popup, 250, 50);
        messageScene.getStylesheets().add("styles/AddAlbumStyle.css");
        messageStage.setScene(messageScene);
        messageStage.show();

        KeyValue transparent = new KeyValue(messageStage.opacityProperty(), 0.0);
        KeyValue opaque = new KeyValue(messageStage.opacityProperty(), 1.0);

        popup.setVisible(true);
        KeyFrame startFadeIn = new KeyFrame(Duration.ZERO, transparent);
        KeyFrame endFadeIn = new KeyFrame(Duration.millis(500), opaque);
        KeyFrame startFadeOut = new KeyFrame(Duration.millis(5000), opaque);
        KeyFrame endFadeOut = new KeyFrame(Duration.millis(5500), transparent);

        timeline.getKeyFrames().addAll(startFadeIn, endFadeIn, startFadeOut, endFadeOut);

        timeline.setCycleCount(1);
        timeline.play();

        timeline.setOnFinished(ActionEvent -> {
            messageStage.close();
        });
    }

    /**
     * Find the correct position for the error/added message.
     * @param rectangle2D
     * @param popupWidth
     * @param popupHeight
     * @param stage
     * @return newRectangle2D - Best way to return two doubles
     */
    public Rectangle2D findPopupPosition(Rectangle2D rectangle2D, double popupWidth, double popupHeight, Stage stage) {
        double mainStageWidth = stage.getWidth();
        double mainStageHeight = stage.getHeight();
        double mainStageStartingX = stage.getX();
        double mainStageStartingY = stage.getY();

        double mainStageEndingX = mainStageStartingX + mainStageWidth;
        double mainStageEndingY = mainStageStartingY + mainStageHeight;

        double positionOfPopupX = mainStageEndingX - popupWidth;
        double positionOfPopupY = mainStageEndingY - popupHeight;

        Rectangle2D newRectangle2D = new Rectangle2D(positionOfPopupX, positionOfPopupY, positionOfPopupX, positionOfPopupY);

        return newRectangle2D;
    }

    /**
     * Creating the rows and columns for the GridPane
     * @param grid
     * @param rows
     * @param cols
     */
	public void createRowsColumnsForGridPane(GridPane grid, int rows, int cols) {
		for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(row);
        }
        for(int i = 0; i < cols; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(col);
        }
	}

}
