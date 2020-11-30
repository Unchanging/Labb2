public class Main {
	public static void main(String[] args) {

		Car volvo = new Volvo240();
		Saab95 saab = new Saab95();

		Saab95 saab1 = new Saab95();
		Saab95 saab2 = new Saab95();
		Saab95 saab3 = new Saab95();
		Saab95 saab4 = new Saab95();

		Scania scania = new Scania();

		Ferry<Saab95> ferry = new Ferry<>(10);

		ferry.addUnit(saab);


	}
}
