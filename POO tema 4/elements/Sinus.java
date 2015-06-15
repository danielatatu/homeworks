package elements;

/**
 * Class representing sine operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Sinus extends Operator {

	public Sinus() {
		super(OpType.UNARY, 3);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
