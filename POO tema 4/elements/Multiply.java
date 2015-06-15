package elements;

/**
 * Class representing multiplication operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Multiply extends Operator {

	public Multiply() {
		super(OpType.BINARY, 1);
	}
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
