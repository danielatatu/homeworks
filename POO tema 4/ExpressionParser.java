import elements.*;
import elements.Number;

/**
 * Class which evaluates a mathematical expression and calculates its result.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class ExpressionParser {

	/**
	 * 
	 * Evaluates a mathematical expression and calculates its result.
	 * 
	 * @param expression math expression evaluated
	 * @return numeric result of the expression
	 * @throws SyntacticException
	 * @throws EvaluatorException
	 */
	public float eval(String expression) throws SyntacticException, EvaluatorException {

		// parse math expression and get the root of the syntax tree
		Element result = parse(expression);
		// evaluate the syntax tree using Evaluator visitor and get the numeric result.
		result.accept(new Evaluator());

		return (float)result.getValue();
	}

	/**
	 * Parses a mathematical expression and creates the syntax tree associated with it.
	 * 
	 * @param expression math expression that has to be parsed
	 * @return root of the syntax tree associated with the expression
	 * @throws SyntacticException
	 */
	public Element parse (String expression) throws SyntacticException {

		String[] exp = expression.split(" ");
		Element e;
		// visitor which creates the syntax tree
		TreeCreator treeCreator = new TreeCreator();

		for (int i = 0; i < exp.length; i++) {

			if (exp[i].equals("")) // in case 2 elements are separated by multiple spaces
				continue;
			else if (exp[i].equals("+"))
				e = new Plus();
			else if (exp[i].equals("-"))
					if (i == 0 || exp[i-1].equals("("))
						e = new UnaryMinus();
					else
						e = new Minus();
			else if (exp[i].equals("*"))
					e = new Multiply();
			else if (exp[i].equals("/"))
				e = new Divide();
			else if (exp[i].equals("^"))
				e = new Power();
			else if (exp[i].equals("log"))
				e = new Logarithm();
			else if (exp[i].equals("sqrt"))
				e = new Sqrt();
			else if (exp[i].equals("sin"))
				e = new Sinus();
			else if (exp[i].equals("cos"))
				e = new Cosinus();
			else if (exp[i].equals("("))
				e = new LeftBracket();
			else if (exp[i].equals(")"))
				e = new RightBracket();
			else
				try {
					e = new Number(Float.parseFloat(exp[i]));
				}
				catch (NumberFormatException exc) {
					// invalid operator
					throw new SyntacticException();
				}
			// "visit" each element included in the expression
			// in order to create the syntax tree
			e.accept(treeCreator);
		}
		treeCreator.finishCreation();
		return treeCreator.resultStack.pop();
	}
}