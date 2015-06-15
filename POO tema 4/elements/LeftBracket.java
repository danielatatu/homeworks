package elements;

/**
 * Class representing a left bracket.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class LeftBracket extends Operator {

	public LeftBracket() {
		super(OpType.BRACKET, -1);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
