package elements;

/**
 * Interface which declares the accept operation that enables
 * an object to be "visited" by the visitor object.
 *
 * @author Daniela Tatu, 325CA
 */
public interface Visitable {
	public void accept(Visitor v);
}
