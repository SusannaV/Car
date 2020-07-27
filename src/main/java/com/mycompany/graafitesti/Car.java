package com.mycompany.graafitesti;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Susanna Veijalainen
 */
public class Car {

    private Rectangle car;
    private Point2D movement;
    private int velocity;
    private boolean running;

    public Car(int x, int y) {
        this.car = new Rectangle(50, 30);
        this.car.setTranslateX(x);
        this.car.setTranslateY(y);
        this.movement = new Point2D(0, 0);
        this.velocity = 0;
        this.running = false;
    }

    public Rectangle getCar() {
        return car;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void turnLeft() {
        if (!(this.velocity <= 0)) {
            this.car.setRotate(this.car.getRotate() - (this.velocity * 0.1));
            //System.out.println("turnleft: " + this.car.getRotate());
        }

    }

    public void turnRight() {
        if (!(this.velocity <= 0)) {
            this.car.setRotate(this.car.getRotate() + (this.velocity * 0.1));
            //System.out.println("turnRight: " + this.car.getRotate());
        }
        //this.car.setRotate(this.car.getRotate() + 2);
    }

    public void move() {
        if (!(this.running = false)) {

            double newXPoint = this.car.getTranslateX() + this.movement.getX();
            this.car.setTranslateX(newXPoint);

            double newYPoint = this.car.getTranslateY() + this.movement.getY();
            this.car.setTranslateY(newYPoint);

            int speed = (int) (movement.magnitude() * 10);
            this.velocity = speed;

            //driving out of the screen
            if (this.car.getTranslateX() < 0) {
                this.car.setTranslateX(this.car.getTranslateX() + App.width);
            }
            if (this.car.getTranslateX() > App.width) {
                this.car.setTranslateX(this.car.getTranslateX() % App.width);
            }

            if (this.car.getTranslateY() < 0) {
                this.car.setTranslateY(this.car.getTranslateY() + App.height);
            }
            if (this.car.getTranslateY() > App.height) {
                this.car.setTranslateY(this.car.getTranslateY() % App.height);
            }
            //System.out.println("Car-luokan getRotate" + this.car.getRotate());
        }
    }

    public void accelerate() {
        if (!(this.running = false)) {
            double changeInX = Math.cos(Math.toRadians(this.car.getRotate()));
            double changeInY = Math.sin(Math.toRadians(this.car.getRotate()));
            changeInX *= 0.05;
            changeInY *= 0.05;
            this.movement = this.movement.add(changeInX, changeInY);
        }
    }

    public void decelerate() {
        if (!(this.running = false)) {
            if (!(this.velocity <= 0)) {

                double changeInX = Math.cos(Math.toRadians(this.car.getRotate()));
                double changeInY = Math.sin(Math.toRadians(this.car.getRotate()));

                changeInX *= -0.05;
                changeInY *= -0.05;
                this.movement = this.movement.add(changeInX, changeInY);
            }
            /*else {
            this.movement.add(0,0);
        }*/
            //tästä ei näyttänyt olevan oikeastaan mitään hyötyä
            //System.out.println("breaking velo: " + this.velocity);
        }
    }

    public int getAngle(int x, int y) {
        return (int) this.movement.angle(x, y);
    }

    public double getCarRotation() {
        double rot = this.car.getRotate();

        if (rot > 359) {
            rot = rot % 360;
        } else if (rot < 0) {
            rot = (Math.ceil(Math.abs(rot)/360)*360)+rot;
        }

        return rot;
    }
}
