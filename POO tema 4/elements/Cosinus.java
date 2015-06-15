package elements;

/**
 * Class representing cosine operator.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Cosinus extends Operator {

	public Cosinus() {
		super(OpType.UNARY, 3);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
