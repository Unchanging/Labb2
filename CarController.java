import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private int delay = 50;
    // The timer is started with an listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    private CarView frame;
    // A list of cars, modify if needed
    private List<GeneralVehicle> cars;

    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        // Start the timer
        cc.timer.start();
    }

    public CarController() {

//        GeneralVehicle testCar1 = new Saab95();
//        GeneralVehicle testCar2 = new Volvo240();
//
//        testCar2.setPosition(new Point(200, 200));
//        testCar1.setPosition(new Point(0,  100));
//
//        double temp = getDirectionAwayFrom(testCar1, testCar2);
//        System.out.println(temp);
//
//        cars = new ArrayList<>();
//        turnerHelper(testCar1, temp);
//        cars.add(testCar1);

        userSelectSimulationSpeed();
        cars = userSelectVehicles();


    }
    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (GeneralVehicle car : cars) {
                turnRandomly(car);
                detectVehicleCollision(car);
                car.move();
                turnAtEdgeCollision(car);
                // repaint() calls the paintComponent method of the panel
                frame.repaint();
            }
        }
    }

    private void userSelectSimulationSpeed() {
        Integer[] speedOptions = {20, 50, 100};
        int chosenSpeedOption = JOptionPane.showOptionDialog(null, "Choose the simulation speed", "Speed Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, speedOptions, speedOptions[0]);
        timer.setDelay(speedOptions[chosenSpeedOption]);
    }

    private List<GeneralVehicle> userSelectVehicles() {
        List<GeneralVehicle> resListOfCars = new ArrayList<>();

        Class<? extends GeneralVehicle>[] vehicleOptions = new Class[3];
        vehicleOptions[0] = Volvo240.class;
        vehicleOptions[1] = Saab95.class;
        vehicleOptions[2] = Scania.class;

        Class[] paramTypesSub = {};
        Object[] paramValuesSub = {};

        int chosenVehicle = JOptionPane.showOptionDialog(null, "Choose a vehicle", "Vehicle Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, vehicleOptions, vehicleOptions[0]);
        int y = 0;

        while (chosenVehicle != -1) {
            try {
                GeneralVehicle vehicle = vehicleOptions[chosenVehicle].getConstructor(paramTypesSub).newInstance(paramValuesSub);
                vehicle.setPosition(new Point(0, y));
                resListOfCars.add(vehicle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            chosenVehicle = JOptionPane.showOptionDialog(null, "Choose a vehicle", "Vehicle Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, vehicleOptions, vehicleOptions[0]);
            y += 100;
        }
        return resListOfCars;
    }

    private void detectVehicleCollision(GeneralVehicle thisCar){

        Dimension thisCarDimension = frame.getImageDimension(thisCar);
        Rectangle2D thisCarRect = new Rectangle(thisCar.getPosition(), thisCarDimension);

        for (GeneralVehicle otherCar : cars.<GeneralVehicle>stream().filter(c -> !c.equals(thisCar)).collect(Collectors.toUnmodifiableList())) {
            Dimension otherCarDimension = frame.getImageDimension(otherCar);
            Rectangle2D otherCarRect = new Rectangle(otherCar.getPosition(), otherCarDimension);

            if (thisCarRect.intersects(otherCarRect)) {
                turnerHelper(thisCar, getDirectionAwayFrom(thisCar, otherCar));
                turnerHelper(otherCar, getDirectionAwayFrom(otherCar, thisCar));
            }
        }
    }

    private double getDirectionAwayFrom(GeneralVehicle firstCar, GeneralVehicle secondCar) {
        double deltaX = firstCar.getPosition().getX() - secondCar.getPosition().getX(); // calculated the difference in x between the cars
        double deltaY = firstCar.getPosition().getY() - secondCar.getPosition().getY(); // calculated the difference in x between the cars
        double res =  Math.atan2(deltaY, deltaX); //function which returns the angle corresponding to the change in x and y
        if (res < 0)
            res += 2 * Math.PI;
        return res;
    }

    private void turnAtEdgeCollision(GeneralVehicle car) {
        int X = (int) (frame.getDrawPanelDimension().getWidth() - frame.getImageDimension(car).getWidth());
        int Y = (int) (frame.getDrawPanelDimension().getHeight() - frame.getImageDimension(car).getHeight());
        if (car.getPosition().getX() < 0)
            turnerHelper(car, 0);
        else if (car.getPosition().getX() > X )
            turnerHelper(car, Math.PI);
        else if (car.getPosition().getY() < 0)
            turnerHelper(car, Math.PI*0.5);
        else if (car.getPosition().getY() > Y)
            turnerHelper(car, Math.PI*1.5);
    }

    private void turnRandomly(GeneralVehicle car) {
        if (Math.random() > 0.999)
            car.turnRight();
        else if (Math.random() > 0.999)
            car.turnLeft();
    }

    private void turnerHelper(GeneralVehicle car, double directionRadians) {
        while (Math.abs(car.getHeading() - directionRadians) > 0.25)
            car.turnRight();
    }

    public List<GeneralVehicle> getCars() {
        return cars;
    }

    // Calls the gas method for each car once
    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (GeneralVehicle car : cars) {
            car.gas(gas);
        }
    }

    public void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (GeneralVehicle car : cars) {
            car.brake(brake);
        }
    }

    public void turboOn() {
        for (GeneralVehicle saab : cars.<GeneralVehicle>stream().filter(c -> c instanceof Saab95).collect(Collectors.toUnmodifiableList())) {
            ((Saab95) saab).setTurboOn();
        }
    }

    public void turboOff() {
        for (GeneralVehicle saab : cars.<GeneralVehicle>stream().filter(c -> c instanceof Saab95).collect(Collectors.toUnmodifiableList())) {
            ((Saab95) saab).setTurboOff();
        }
    }

    public void startAllCars(){
        for(GeneralVehicle car : cars) {
            car.startEngine();
        }
    }

    public void stopAllCars(){
        for(GeneralVehicle car : cars) {
            car.stopEngine();
        }
    }

    public void liftAllScaniaBeds() {
        for (GeneralVehicle scania : cars.<GeneralVehicle>stream().filter(c -> c instanceof Scania).collect(Collectors.toUnmodifiableList())) {
            ((Scania) scania).raisePlatform(15);
        }
    }

    public void lowerAllScaniaBeds() {
        for (GeneralVehicle scania : cars.<GeneralVehicle>stream().filter(c -> c instanceof Scania).collect(Collectors.toUnmodifiableList())) {
            ((Scania) scania).lowerPlatform(15);
        }
    }
}
