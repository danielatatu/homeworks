package elements;

/**
 * Class representing power operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Power extends Operator {

	public Power() {
		super(OpType.BINARY, 2);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
