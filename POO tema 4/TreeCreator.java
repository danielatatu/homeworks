import java.util.Stack;
import elements.*;
import elements.Number;
import elements.Operator.OpType;

/**
 * Class which creates the syntax tree, by "visiting"
 * the elements included in math expressions.
 * This class implements the Visitor interface and defines all the visit methods.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class TreeCreator implements Visitor {

	/**
	 * stack used to hold operands and intermediate results of operations
	 */
	Stack<Element> resultStack;
	/**
	 * stack used to hold operators
	 */
	Stack<Operator> operatorStack;

	public TreeCreator() {
		resultStack = new Stack<Element>();
		operatorStack = new Stack<Operator>();
	}

	@Override
	public void visit(Element o) {}

	/**
	 * "visiting" a numerical element implies adding it in the result stack
	 */
	@Override
	public void visit(Number n) {
		resultStack.push(n);
	}

	/**
	 * "solves" all the operators which have the same or higher priority
	 * than the one received as a parameter, then pushes it in the stack
	 * 
	 * @param o operator analyzed
	 */
	private void analyze (Operator o) {
		if ( operatorStack.empty() )
			operatorStack.push(o);
		else {
			while ( ! operatorStack.empty() && operatorStack.peek().getPriority() >= o.getPriority())
				resolve(operatorStack.pop());
			operatorStack.push(o);
		}
	}

	/**
	 * sets the child / childs (in the syntax tree) of the operator
	 * received as a parameter, then pushes it in the result stack
	 * 
	 * @param o operator "solved"
	 */
	private void resolve (Operator o) {
		try {
			if (o.getType() == OpType.BINARY) {
				if (resultStack.size() < 2)
					// not enough operands in the stack => invalid expression
					throw new SyntacticException();
				o.setRight(resultStack.pop());
				o.setLeft(resultStack.pop());
				resultStack.push(o);
			}
			else if (o.getType() == OpType.UNARY) {
				if (resultStack.empty())
					// not enough operands in the stack => invalid expression
					throw new SyntacticException();
				o.setRight(resultStack.pop());
				resultStack.push(o);
			}
		}
		catch (SyntacticException e) {}
	}

	@Override
	public void visit(Plus o) {
		analyze(o);
	}

	@Override
	public void visit(Minus o) {
		analyze(o);
	}

	@Override
	public void visit(Multiply o) {
		analyze(o);
	}

	@Override
	public void visit(Divide o) {
		analyze(o);
	}

	/**
	 * visiting a power operator implies "solving" all the operators
	 * which have higher priority, then pushing it in the stack
	 */
	@Override
	public void visit(Power o) {
		if ( operatorStack.empty() )
			operatorStack.push(o);
		else {
			while ( ! operatorStack.empty() && operatorStack.peek().getPriority() > o.getPriority())
				resolve(operatorStack.pop());
			operatorStack.push(o);
		}
	}

	@Override
	public void visit(Logarithm o) {
		analyze(o);
	}

	@Override
	public void visit(Sqrt o) {
		analyze(o);
	}

	@Override
	public void visit(Sinus o) {
		analyze(o);
	}

	@Override
	public void visit(Cosinus o) {
		analyze(o);
	}

	@Override
	public void visit(UnaryMinus o) {
		analyze(o);
	}

	/**
	 * "visiting" a left bracket implies adding it in the result stack
	 */
	@Override
	public void visit(LeftBracket b) {
		operatorStack.push(b);
	}

	/**
	 * "visiting" a right bracket implies "solving" all the operations
	 * since the left bracket was found
	 */
	@Override
	public void visit(RightBracket b) {
		try {
			if ( operatorStack.empty() )
				// no left bracket => invalid expression
				throw new SyntacticException();
			else {
				while ( ! operatorStack.empty() && operatorStack.peek().getType() != OpType.BRACKET )
					resolve(operatorStack.pop());

				if ( operatorStack.empty() )
					// no left bracket => invalid expression
					throw new SyntacticException();
				else
					// remove the left bracket from the stack
					operatorStack.pop();
			}
		}
		catch (SyntacticException e) {}
	}

	/**
	 * "solves" all the operations remained in the stack
	 * 
	 * @throws SyntacticException
	 */
	void finishCreation() throws SyntacticException {
		while ( ! operatorStack.empty() ) {
			if ( operatorStack.peek().getType() == OpType.BRACKET )
				// left bracket without right bracket => invalid expression
				throw new SyntacticException();
			resolve(operatorStack.pop());
		}
		if ( resultStack.size() != 1 )
			// unused operands => invalid expression
			throw new SyntacticException();
	}
}
