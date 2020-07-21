package com.mycompany.graafitesti;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Susanna Veijalainen
 */
public class Car {

    private Rectangle car;
    private Point2D movement;
    //en osaa käyttää tätä velocityä mihinkään ;_;
    private int velocity;
    private AtomicInteger speedometer;

    public Car(int x, int y, AtomicInteger speedometer) {
        this.car = new Rectangle(50, 30);
        this.car.setTranslateX(x);
        this.car.setTranslateY(y);
        this.movement = new Point2D(0, 0);
        this.velocity = 0;
        this.speedometer = speedometer;
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
    
    

    public void turnLeft() {
        this.car.setRotate(this.car.getRotate() - 2);
    }

    public void turnRight() {
        this.car.setRotate(this.car.getRotate() + 2);
    }

    public void move() {
        //magic happens here
        
        double newXPoint = this.car.getTranslateX()+this.movement.getX();
        //double vauhtiapumuuttuja = (newXPoint - this.car.getTranslateX());
        //if (!(vauhtiapumuuttuja>=0)){
        //    vauhtiapumuuttuja*=(-1);
        //}
        //System.out.println("vauhti jälkeen: " + vauhtiapumuuttuja);

        this.car.setTranslateX(newXPoint);
        
        double newYPoint = this.car.getTranslateY() + this.movement.getY();
        System.out.println("getTranslateX" + this.car.getTranslateY());
        System.out.println("movement.getX" + this.movement.getX());
        this.car.setTranslateY(newYPoint);
        //System.out.println("newX: " + newXPoint + "newY: " +newYPoint);
        
        if (!(this.movement.getX()== 0 &&  this.movement.getY()== 0)){
            countVelocity(newXPoint, newYPoint);
        }


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
    }

    public void accelerate() {
        //Kommenteissa se, mitä tapahtuu, jos kiihdytän suoraan eteenpäin
        double changeInX = Math.cos(Math.toRadians(this.car.getRotate()));
        double changeInY = Math.sin(Math.toRadians(this.car.getRotate()));
        //changeInX palauttaa nyt koko ajan 1.0
        changeInX *= 0.05;
        //tässä kohtaa se palauttaa 0.05
        changeInY *= 0.05;
        this.movement = this.movement.add(changeInX, changeInY);
        System.out.println(this.movement);
        
        
        //this.movement palauttaa olion, jolla on X ja Y.
        //x on jotain about välillä 0.05-15.0 (joka on jo aika hurja vauhti) 
        //y=0.0 (koska en ole kääntänyt autoa)
        //x on siis se, kuinka paljon auton nykyiseen x koordinaattiin lisätään arvoa koko ajan
        //tavallaan nopeus voisi olla x+y, paitsi että kumpikin voi olla negatiivisiä, 
        //jos auton keulan kääntää vasemmalle.
    }

    public void decelerate() {
        double changeInX = Math.cos(Math.toRadians(this.car.getRotate()));
        double changeInY = Math.sin(Math.toRadians(this.car.getRotate()));

        changeInX *= -0.05;
        changeInY *= -0.05;
        this.movement = this.movement.add(changeInX, changeInY);
    }
    
    public void countVelocity(double x, double y){
        System.out.println("countVelocityn alku x ja y: " + x + " ja " + y);
        double speed = Math.sqrt(Math.pow(x, 2)*Math.pow(y, 2));
        //speed *=10;
        int intSpeed = (int) speed;
        int newSpeed = intSpeed -this.getVelocity();
        this.setVelocity(newSpeed);
        
        //this.speedometer.incrementAndGet();
    }
}
