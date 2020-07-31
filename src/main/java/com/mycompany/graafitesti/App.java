package com.mycompany.graafitesti;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {

    public static int width = 1000;
    public static int height = 600;

    @Override
    public void start(Stage stage) throws Exception {
        
        //creating screen, labels, buttons and the car
        Pane gamescreen = new Pane();
        
        Button powerOnButton = new Button( "Power on");
        Button powerOffButton = new Button("Power off");
        Text velocity = new Text(10, 20, "Velocity: ");
        Text direction = new Text(10, 40, "Direction: ");
        Text engine = new Text (200, 20, "The engine is off. Start the engine.");
        
        powerOnButton.setFocusTraversable(false);
        powerOffButton.setFocusTraversable(false);
        powerOnButton.setLayoutX(600);
        powerOnButton.setLayoutY(10);
        powerOffButton.setLayoutX(700);
        powerOffButton.setLayoutY(10);

        gamescreen.setPrefSize(width, height);
        Car car = new Car(width / 2, height / 2);
        gamescreen.getChildren().addAll(car.getCar(), powerOnButton, powerOffButton, velocity, direction, engine);
       
        //handling engine power button events
        powerOnButton.setOnAction((event) -> {
            car.setRunning(true);
            engine.setText("Engine running");
                    });
        powerOffButton.setOnAction((event) -> {
            car.setRunning(false);
            engine.setText("The engine is off. Start the engine.");
                    });

        //creating the scene
        Scene scene = new Scene(gamescreen);
        stage.setTitle("This is a Car program");
        stage.setScene(scene);
        stage.show();

        //handling pressed keys
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
                //listen to keyboard inputs
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
                    car.decelerate(car.acceleration);
                }

                //moving the car according to inputs above
                //and updating the texts at the top of the screen
                car.move();
                DecimalFormat formatter = new DecimalFormat("#0.00");
                velocity.setText("Velocity: " + formatter.format(Math.abs(car.getVelocity()*4)) + "km/h");
                direction.setText("Direction: " + formatter.format(car.getCarRotation()) + "°");
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }

}
