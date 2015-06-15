package elements;

/**
 * Class representing an element included in math expressions.
 * This class implements the Visitable interface and defines the accept operation. 
 * 
 * @author Daniela Tatu, 325 CA
 */
public class Element implements Visitable {

	/**
	 * left child of the element in the syntax tree
	 */
	private Element leftElem;
	/**
	 * right child of the element in the syntax tree
	 */
	private Element rightElem;
	/**
	 * numerical value of the element
	 */
	private double value;

	Element() {
		leftElem = null;
		rightElem = null;
	}

	public Element getLeft() {
		return leftElem;
	}
	
	public void setLeft(Element left) {
		leftElem = left;
	}
	
	public Element getRight() {
		return rightElem;
	}
	
	public void setRight(Element right) {
		rightElem = right;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double v) {
		value = v;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}