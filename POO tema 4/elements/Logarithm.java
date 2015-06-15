package elements;

/**
 * Class representing logarithm operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Logarithm extends Operator {

	public Logarithm() {
		super(OpType.UNARY, 3);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
