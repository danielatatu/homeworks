package game;

/**
 * Clasa Round este folosita pentru a monitoriza miscarile
 * pe care fiecare erou trebuie sa le faca intr-o runda.
 */
public class Round {

	char [] moves;
	/**
	 * Memoreaza in vectorul {@link #moves moves} miscarile
	 * pe care trebuie sa le execute eroii in runda curenta.
	 * 
	 * @param m String format din literele corespunzatoare
	 * miscarilor fiecarui erou.
	 */
	Round (String m) {
		moves = new char[m.length()];
		for (int i = 0; i < m.length(); i++)
			moves[i] = m.charAt(i);
	}
}
