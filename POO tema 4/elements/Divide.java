package elements;

/**
 * Class representing division operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Divide extends Operator {

	public Divide() {
		super(OpType.BINARY, 1);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
