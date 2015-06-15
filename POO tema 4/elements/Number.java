package elements;

/**
 * Class representing a numerical element.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Number extends Element {

	/**
	 * value of the number
	 */
	private double val;

	public Number(double v) {
		val = v;
	}
	
	public double getVal () {
		return val;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
