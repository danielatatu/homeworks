package elements;

/**
 * Class representing a mathematical operator such as plus, multiply, etc.
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Operator extends Element {

	/**
	 * type of the operator (unary, binary, bracket)
	 */
	private OpType type;
	/**
	 * priority of the operator: <br>
	 * -> 0 for +, - <br>
	 * -> 1 for *, /, unary - <br>
	 * -> 2 for ^ <br>
	 * -> 3 for log, sqrt, sin, cos <br>
	 * -> -1 for (, )
	 */
	private int priority;

	public Operator(OpType t, int p) {
		type = t;
		priority = p;
	}
	
	public OpType getType() {
		return type;
	}
	
	public int getPriority() {
		return priority;
	}

	/**
	 * Enumeration of the types of operators:
	 * unary, binary, bracket.
	 */
	public enum OpType {
		UNARY,
		BINARY,
		BRACKET
	}
}
