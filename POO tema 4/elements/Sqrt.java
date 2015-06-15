package elements;

/**
 * Class representing square root operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Sqrt extends Operator {

	public Sqrt() {
		super(OpType.UNARY, 3);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}