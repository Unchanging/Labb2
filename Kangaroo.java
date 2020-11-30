import java.awt.*;
import java.util.Iterator;

public class Kangaroo<typeToTransport extends Transportable> implements Transporter<typeToTransport>, Transportable{
	@Override
	public void addUnit(typeToTransport unitToTransport) {

	}

	@Override
	public void updatePositions(Point newPosition) {

	}

	@Override
	public typeToTransport unloadLastUnit() {
		return null;
	}

	@Override
	public typeToTransport unloadFirstUnit() {
		return null;
	}

	@Override
	public Iterator<typeToTransport> getIterator() {
		return null;
	}

	@Override
	public int getNrOfUnits() {
		return 0;
	}

	@Override
	public void setPosition(Point newPosition) {

	}

	@Override
	public String getIdentifier() {
		return null;
	}
}
