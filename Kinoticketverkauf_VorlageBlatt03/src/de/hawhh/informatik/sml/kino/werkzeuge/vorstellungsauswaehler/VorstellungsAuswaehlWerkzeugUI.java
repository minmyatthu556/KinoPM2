package de.hawhh.informatik.sml.kino.werkzeuge.vorstellungsauswaehler;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * Die UI des {@link VorstellungsAuswaehlWerkzeug}.
 *
 * @author SE2-Team (Uni HH), PM2-Team
 * @version SoSe 2024
 */
class VorstellungsAuswaehlWerkzeugUI {
    // Die Widgets, aus denen das UI sich zusammensetzt
    private BorderPane _hauptPanel;
    private ListView<VorstellungsFormatierer> _vorstellungAuswahlList;

    /**
     * Initialisiert die UI.
     */
    public VorstellungsAuswaehlWerkzeugUI() {
        _hauptPanel = new BorderPane();
        _hauptPanel.setStyle("-fx-padding: 10; -fx-text-fill: #f5f3f0; -fx-background-color: #343434; -fx-border-color: #343434");

        Label vorstellungsLabel = new Label("Vorstellung:");
        vorstellungsLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #f5f3f0; -fx-padding: 10 0 10 0;");
        _hauptPanel.setTop(vorstellungsLabel);

        _vorstellungAuswahlList = new ListView<VorstellungsFormatierer>();


        PseudoClass hover = PseudoClass.getPseudoClass("hover");

        _vorstellungAuswahlList.setCellFactory(new Callback<ListView<VorstellungsFormatierer>, ListCell<VorstellungsFormatierer>>() {
            @Override
            public ListCell<VorstellungsFormatierer> call(ListView<VorstellungsFormatierer> param) {
                return new ListCell<VorstellungsFormatierer>() {
                    @Override
                    protected void updateItem(VorstellungsFormatierer item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            //setGraphic(null);
                        } else {
                            setText(item.toString());
                        }
                        if (getIndex() % 2 == 0) {
                            setStyle("-fx-background-color: #343434; -fx-text-fill: #f5f3f0;"); // Dark background for even rows
                        } else {
                            setStyle("-fx-background-color: #565656; -fx-text-fill: #F5F3F0;"); // Slightly lighter background for odd rows
                        }
                        // Add hover effect
                        hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                            if (isNowHovered) {
                                setStyle("-fx-background-color: #676767; -fx-text-fill: #F5F3F0;"); // Even lighter background for hovered rows
                            } else {
                                if (empty || item == null) {
                                    setStyle("-fx-background-color: #565656; -fx-text-fill: #F5F3F0;");
                                } else {
                                    if (getIndex() % 2 == 0) {
                                        setStyle("-fx-background-color: #343434; -fx-text-fill: #d8d9d7;"); // Dark background for even rows
                                    } else {
                                        setStyle("-fx-background-color: #565656; -fx-text-fill: #F5F3F0;"); // Slightly lighter background for odd rows
                                    }
                                }

                            }
                        });
                    }
                };
            }
        });

        _hauptPanel.setCenter(_vorstellungAuswahlList);
    }

    /**
     * Liefert den UI-Container, in dem die Widgets angeordnet sind.
     */
    public Pane getUIPane() {
        return _hauptPanel;
    }

    /**
     * Liefert das Listen-Widget, in dem die Vorstellungen angezeigt werden.
     */
    public ListView<VorstellungsFormatierer> getVorstellungAuswahlList() {
        return _vorstellungAuswahlList;
    }
}
