package com.mycompany.graafitesti;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    public static int width = 1000;
    public static int height = 600;

    @Override
    public void start(Stage stage) throws Exception {
        //create screen and a car
        Pane screen = new Pane();

        Text velocity = new Text(10, 20, "Velocity: ");
        Text direction = new Text(10, 40, "Direction: ");
        screen.getChildren().add(velocity);
        screen.getChildren().add(direction);

        screen.setPrefSize(width, height);
        Car car = new Car(width / 2, height / 2);
        screen.getChildren().add(car.getCar());

        Scene scene = new Scene(screen);
        stage.setTitle("This is a Car program");
        stage.setScene(scene);
        stage.show();

        //handling keyboard inputs
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {

            @Override
            public void handle(long currentTime) {

                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    car.turnLeft();
                }
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    car.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    car.accelerate();
                }

                if (pressedKeys.getOrDefault(KeyCode.DOWN, false)) {
                    car.decelerate();
                }

                car.move();
                velocity.setText("Velocity: " + car.getVelocity() + "km/h");
                //velocity.setText("Velocity: " + speedometer.getAndSet(car.getVelocity())+ "km/h");
                //direction.setText("Direction: " + car.getAngle(0,-1) + "°");
                DecimalFormat formatter = new DecimalFormat("#0.00");
                direction.setText("Direction: " + formatter.format(car.getCarRotation()) + "°");
                //System.out.println("angle: " + car.getAngle(0,-1) );
                //System.out.println("getRot: " + car.getCarRotation());
            }
        }.start();

    }

    public static void main(String[] args) {
        launch();
    }

}
