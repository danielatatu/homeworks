package elements;

/**
 * Interface used to declare the visit operations for all the types of visitable classes.
 * 
 * @author Daniela Tatu, 325 CA
 */
public interface Visitor {

	public void visit(Element o);
	
	public void visit(Number n);

    public void visit(Plus o);
    public void visit(Minus o);

    public void visit(Multiply o);
    public void visit(Divide o);

    public void visit(Power o);

    public void visit(Logarithm o);
    public void visit(Sqrt o);
    public void visit(Sinus o);
    public void visit(Cosinus o);
    public void visit(UnaryMinus o);

    public void visit(LeftBracket b);
    public void visit(RightBracket b);

}
