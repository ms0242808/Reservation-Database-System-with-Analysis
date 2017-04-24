/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RDBMSA;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author jerry
 */
public class AnimationGen {
    
    public void FadeAnimationOn(Node node, int duration, double from, double to, EventHandler<ActionEvent> eventHandler) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), node);
        fadeTransition.setOnFinished(eventHandler);
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
}
