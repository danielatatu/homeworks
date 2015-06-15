package elements;

/**
 * Class representing unary minus operator (negates an expression).
 * 
 * @author Daniela Tatu, 325 CA
 */
public class UnaryMinus extends Operator {

	public UnaryMinus() {
		super(OpType.UNARY, 1);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
