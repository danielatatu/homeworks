/**
 * @author Daniela Florentina Tatu, 325 CA
 * @version 11 Nov 2014
 */
package main;

import game.*;

/**
 * Clasa Main este folosita pentru preluarea 
 * si organizarea datelor de intrare
 */
public class Main {

	/**
	 * @param args <br>
	 * <ul>
	 * <li> args[0] fisierul din care se citesc datele de intrare </li>
	 * <li> args[1] fisierul in care se scriu datele de iesire </li>
	 * </ul>
	 */
	public static void main(String[] args) {
		
		FileIO fin = new FileIO (args[0], true);
		
		/**
		 * @see Map
		 */
		Map map = new Map();
		map.setMapInfo(fin);

		/**
		 * @see Game
		 */
		Game game = new Game();
		game.setGameInfo(fin);

		fin.close();
		
		game.play(map);
		
		FileIO fout = new FileIO (args[1], false);
		
		game.end(fout);
		
		fout.close();
	}
}
