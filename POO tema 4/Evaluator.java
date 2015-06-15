import elements.*;
import elements.Number;

/**
 * Class which "visits" the elements included in the syntax tree
 * and gets the numeric result for each one.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Evaluator implements Visitor {

	@Override
	public void visit(Element o) {}

	@Override
	public void visit(Number n) {
		n.setValue(n.getVal());
	}

	/**
	 * "visits" the right child of a unary operator
	 */
	private void visitNeighbour (Operator o) {
		if ( o.getLeft() != null )
			throw new EvaluatorException();
		if ( o.getRight() != null )
			o.getRight().accept(this);;
	}

	/**
	 * "visits" the childs of a binary operator
	 */
	private void visitNeighbours (Operator o) {
		if ( o.getLeft() != null )
			o.getLeft().accept(this);
		if ( o.getRight() != null )
			o.getRight().accept(this);
	}
	
	@Override
	public void visit(Plus o) {
		visitNeighbours(o);
		o.setValue(o.getLeft().getValue() + o.getRight().getValue());
		o.setLeft(null);
		o.setRight(null);
	}

	@Override
	public void visit(Minus o) {
		visitNeighbours(o);
		o.setValue(o.getLeft().getValue() - o.getRight().getValue());
		o.setLeft(null);
		o.setRight(null);
	}

	@Override
	public void visit(Multiply o) {
		visitNeighbours(o);
		o.setValue(o.getLeft().getValue() * o.getRight().getValue());
		o.setLeft(null);
		o.setRight(null);
	}

	@Override
	public void visit(Divide o) {
		visitNeighbours(o);
		if (o.getRight().getValue() == 0)
			// division by zero
			throw new EvaluatorException();
		o.setValue(o.getLeft().getValue() / o.getRight().getValue());
		o.setLeft(null);
		o.setRight(null);
	}

	@Override
	public void visit(Power o) {
		visitNeighbours(o);
		o.setValue( Math.pow (o.getLeft().getValue(), o.getRight().getValue()) );
		o.setLeft(null);
		o.setRight(null);
	}

	@Override
	public void visit(Logarithm o) {
		visitNeighbour(o);
		if (o.getRight().getValue() <= 0)
			// argument of a logarithm <= 0
			throw new EvaluatorException();
		o.setValue( Math.log10 (o.getRight().getValue()) );
		o.setRight(null);
	}

	@Override
	public void visit(Sqrt o) {
		visitNeighbour(o);
		if (o.getRight().getValue() < 0)
			// argument of sqrt < 0
			throw new EvaluatorException();
		o.setValue( Math.sqrt (o.getRight().getValue()) );
		o.setRight(null);
	}

	@Override
	public void visit(Sinus o) {
		visitNeighbour(o);
		o.setValue( Math.sin (o.getRight().getValue()) );
		o.setRight(null);
	}

	@Override
	public void visit(Cosinus o) {
		visitNeighbour(o);
		o.setValue( Math.cos (o.getRight().getValue()) );
		o.setRight(null);
	}

	@Override
	public void visit(UnaryMinus o) {
		visitNeighbour(o);
		o.setValue( - o.getRight().getValue() );
		o.setRight(null);
	}
	
	@Override
	public void visit(LeftBracket b) {}

	@Override
	public void visit(RightBracket b) {}
}
