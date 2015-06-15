package game;
import main.*;

/**
 * Clasa Map este folosita pentru a monitoriza harta jocului.
 */
public class Map {
	/**
	 * dimensiune a hartii
	 */
	public int N;
	/**
	 * dimensiune a hartii
	 */
	public int M;
	/**
	 * matrice ce retine tipurile de terenuri de pe harta
	 */
	public char [][] land;

	/**
	 * Aceasta metoda preia informatiile despre harta
	 * (dimensiuni, tipuri de terenuri)
	 * si le atribuie membrilor clasei corespunzatori.
	 * 
	 * @param f Fisierul din care se citesc datele de intrare.
	 */
	public void setMapInfo (FileIO f) {
		String s = f.readLine();
		N = Integer.parseInt(s.split(" ")[0]);
		M = Integer.parseInt(s.split(" ")[1]);
		land = new char [N][M];
		for (int i = 0; i < N; i++) {
			s = f.readLine();
			for (int j = 0; j < M; j++)
				land[i][j] = s.charAt(j);
		}
	}
}
