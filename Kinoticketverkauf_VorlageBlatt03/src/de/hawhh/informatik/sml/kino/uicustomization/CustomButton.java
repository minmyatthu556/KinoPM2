package de.hawhh.informatik.sml.kino.uicustomization;

import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class CustomButton
{
    static Button _button;

    public CustomButton(Button button)
    {
        _button = button;
    }

    public static void setStyle(Button button, boolean yes)
    {
        if (yes)
        {
            button.setStyle("-fx-background-color: #57A6A1; -fx-text-fill: #ffffff; -fx-border-radius: 10; -fx-background-radius: 15;");

            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #57A6A1; -fx-text-fill: #ffffff; -fx-border-radius: 5; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.3), 10, 0, 0, 0);"));

            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #57A6A1; -fx-text-fill: #ffffff; -fx-border-radius: 5; -fx-background-radius: 15;"));

            button.setPadding(new Insets(10, 20, 10, 20));
        }
        else
        {
            button.setStyle("-fx-background-color: #9E4784; -fx-text-fill: #ffffff; -fx-border-radius: 10; -fx-background-radius: 15;");

            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #9E4784; -fx-text-fill: #ffffff; -fx-border-radius: 5; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.3), 10, 0, 0, 0);"));

            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #9E4784; -fx-text-fill: #ffffff; -fx-border-radius: 5; -fx-background-radius: 15;"));

            button.setPadding(new Insets(10, 20, 10, 20));
        }
    }

    public static void setStyle(Button button)
    {
        button.setStyle("-fx-background-color: #F6B17A; -fx-text-fill: #121212; -fx-border-radius: 10; -fx-background-radius: 15;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #F6B17A; -fx-text-fill: #121212; -fx-border-radius: 5; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.3), 10, 0, 0, 0);"));

        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #F6B17A; -fx-text-fill: #121212; -fx-border-radius: 5; -fx-background-radius: 15;"));

        button.setPadding(new Insets(10, 20, 10, 20));
    }
}
