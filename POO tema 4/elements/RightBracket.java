package elements;

/**
 * Class representing a right bracket.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class RightBracket extends Operator {

	public RightBracket() {
		super(OpType.BRACKET, -1);
	}
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
