package elements;

/**
 * Class representing additive operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Plus extends Operator {

	public Plus() {
		super(OpType.BINARY, 0);
	}
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
