package com.mycompany.graafitesti;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Susanna Veijalainen
 */
public class Car {

    private final Rectangle car;
    private Point2D direction;
    private Point2D location;
    public double velocity;
    private boolean running;
    public final double acceleration;
    private final int topspeed;
    private final int turn;

    public Car(int x, int y) {
        this.car = new Rectangle(30, 50);
        this.car.setTranslateX(x);
        this.car.setTranslateY(y);
        this.location = new Point2D(x, y);
        this.direction = new Point2D(0, -1);
        this.velocity = 0;
        this.running = false;
        this.acceleration = 0.3; //used to scale acceleration and deceleration
        this.topspeed = 50; //used to limit speed
        this.turn = 2; //used to scale turning
    }

    public Rectangle getCar() {
        return car;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void turnLeft() {
        if (this.velocity > 0) {
            this.car.setRotate(this.car.getRotate() - turn);
        }
    }

    public void turnRight() {
        if (this.velocity > 0) {
            this.car.setRotate(this.car.getRotate() + turn);
        }
    }

    public void move() {
            //calculate vector for car direction
            //90 degrees added to take account difference in coordinate orientations
            this.direction = new Point2D(-Math.cos(Math.toRadians(this.car.getRotate() + 90)), -Math.sin(Math.toRadians(this.car.getRotate() + 90)));
            
            //vector normalized so that velocity is correct even in diagonal movement
            this.location = this.location.add(this.direction.normalize().multiply(this.velocity));
            this.car.setTranslateX(this.location.getX());
            this.car.setTranslateY(this.location.getY());

            //driving out of the screen
            if (this.car.getTranslateX() < 0) {
                this.car.setTranslateX((Math.ceil(Math.abs(this.car.getTranslateX()) / App.width) * App.width) + this.car.getTranslateX());
            }
            if (this.car.getTranslateX() > App.width) {
                this.car.setTranslateX(this.car.getTranslateX() % App.width);
            }
            if (this.car.getTranslateY() < 0) {
                this.car.setTranslateY((Math.ceil(Math.abs(this.car.getTranslateY()) / App.height) * App.height) + this.car.getTranslateY());
            }
            if (this.car.getTranslateY() > App.height) {
                this.car.setTranslateY(this.car.getTranslateY() % App.height);
            }
            
        //engine braking
            if (this.velocity>0){//
                decelerate(acceleration*0.1);
            } 
    }

    public void accelerate() {
        if (!(this.running == false)) {
            if (this.velocity <= this.topspeed) {
                this.velocity += this.acceleration;
            }
            if (this.velocity > this.topspeed) {
                this.velocity = this.topspeed;
            }
        }
    }

    public void decelerate(double deceleration) {
            if (!(this.velocity <= 0)) {
                this.velocity -= deceleration;
            }
            if (this.velocity < 0) {
                this.velocity = 0;
            }
    }

    public double getCarRotation() {
        double rot = this.car.getRotate();

        if (rot > 359) {
            rot = rot % 360;
        } else if (rot < 0) {
            rot = (Math.ceil(Math.abs(rot) / 360) * 360) + rot;
        }
        return rot;
    }
}
