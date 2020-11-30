import java.awt.*;
import java.awt.geom.Line2D;

public interface GeneralVehicle extends Movable, Transportable{

	/**A method which should determine how the vehicle accelerate.
	 * @return The factor which governs acceleration.
	 */
	double speedFactor();

	/**A method which should return the vehicle's enginePower
	 * @return The enginepower of the vehicle
	 */
	double getEnginePower();

	/**A method which should return the vehicle's modelName
	 * @return The model name of the vehicle
	 */
	String getModelName();

	/**A method which should return the vehicle's current speed
	 * @return The vehicle's current speed
	 */
	double getCurrentSpeed();

	/**A method which should return the vehicle's color.
	 * return The vehicle's color.
	 */
	Color getColor();

	/**A method which should return the vehicle's current position in 2D space.
	 * @return The vehicle's position.
	 */
	Point getPosition();

	/**A method which should return the vehicle's current heading in radians.
	 * @return The vehicle's current heading in radians.
	 */
	double getHeading();

	/**A method which should set the  of the vehicle.
	 * @param color The color to be set.
	 */
	void setColor(Color color);

	/**A method which should increase the speed of the vehicle based on the input.
	 * The input must be within [0, 1]
	 * @param amount The intensity of the increase in speed.
	 */
	void gas(double amount);

	/**A method which should decrease the speed of the vehicle based on the input.
	 * The input must be within [0, 1]
	 * @param amount The intensity of the decrease in speed.
	 */
	void brake(double amount);

	/** A method which should increase the speed of the vehicle based on the input.
	 * Calling this method when the vehicle is not ready to accelerate should throw an exception
	 * @param amount The increase in speed.
	 */
	void incrementSpeed(double amount);
	/**A method which should decrease the speed of the vehicle based on the input.
	 * @param amount The decrease in speed.
	 */
	void decrementSpeed(double amount);


	/**
	 * The method should start the vehicle by setting the current speed to a positive value
	 */
	void startEngine();

	/**
	 * The method should stop the vehicle by setting the current speed to zero.
	 */
	void stopEngine();

	Point getMovementProjection();

	/**An implementation of Transportable's getIdentifier() method which is suitable for vehicles by redirecting the call to getModelName()
	 * @return An identifier string most suitable for the classes which implements this interface.
	 */
	default String getIdentifier() {
		return getModelName();
	}
}
