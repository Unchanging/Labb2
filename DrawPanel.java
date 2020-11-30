import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represent the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    private Map<GeneralVehicle, CarGraphics> carGraphicsMap;

    // Initializes the panel and reads the images
    public DrawPanel(List<GeneralVehicle> carsList, int x, int y) {

        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        carGraphicsMap = initializeCarGraphicsMap(carsList);
    }
    public Dimension getImageDimension(GeneralVehicle car) {
        return carGraphicsMap.get(car).getImageDimension();
    }

    // This method is called each time the panel updates/refreshes/repaints itself

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(GeneralVehicle car : carGraphicsMap.keySet()) {
            carGraphicsMap.get(car).drawYourself(g, car.getPosition());
        }
    }

    private Map<GeneralVehicle, CarGraphics> initializeCarGraphicsMap(List<GeneralVehicle> cars) {
        Map<GeneralVehicle, CarGraphics> res = new TreeMap<>(new Comparator<GeneralVehicle>() {
            @Override
            public int compare(GeneralVehicle o1, GeneralVehicle o2) {
                return o1.hashCode() - o2.hashCode();
            }
        });

        for (GeneralVehicle car : cars) {
            res.put(car,new CarGraphics(car));
        }
        return res;
    }

    /**
     * A class which represents the graphical characteristics of a car.
     */
    private class CarGraphics {
        private BufferedImage carImage;

        public CarGraphics(GeneralVehicle car) {
            try {
                carImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/" + car.getModelName() +".jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void drawYourself(Graphics g, Point carPosition) {
            g.drawImage(carImage, carPosition.x, carPosition.y, null);
        }

        public Dimension getImageDimension() {
            return new Dimension(carImage.getWidth(), carImage.getHeight());
        }
    }
}

