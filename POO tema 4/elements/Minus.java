package elements;

/**
 * Class representing subtraction operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Minus extends Operator {

	public Minus() {
		super(OpType.BINARY, 0);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
