package game;

import main.*;
import heroes.*;

/**
 * Clasa Game este folosita pentru a monitoriza
 * actiunile eroilor pe parcursul unui joc.
 */
public class Game {
	/**
	 * numarul de eroi din joc
	 */
	public int P;
	/**
	 * vector ce memoreaza toti eroii implicati in joc
	 */
	public Hero [] heroes;
	/**
	 * numarul de runde ale jocului
	 */
	public int R;
	/**
	 * vector ce contine toate rundele jocului
	 * @see game.Round
	 */
	public Round [] rounds;

	/**
	 * Aceasta metoda preia informatiile despre joc (eroi, runde) 
	 * si le atribuie membrilor clasei corespunzatori.
	 * 
	 * @param f Fisierul din care se citesc datele de intrare.
	 */
	public void setGameInfo (FileIO f) {
		P = Integer.parseInt(f.readLine());
		heroes = new Hero [P];
		String [] s;
		for (int i = 0; i < P; i++) {
			s = f.readLine().split(" ");
			if ( s[0].equals("W") )
				heroes[i] = new Wizard (Integer.parseInt(s[1]),Integer.parseInt(s[2]));
			else if ( s[0].equals("R") )
				heroes[i] = new Rogue (Integer.parseInt(s[1]),Integer.parseInt(s[2]));
			else if ( s[0].equals("K") )
				heroes[i] = new Knight (Integer.parseInt(s[1]),Integer.parseInt(s[2]));
			else if ( s[0].equals("P") )
				heroes[i] = new Pyromancer (Integer.parseInt(s[1]),Integer.parseInt(s[2]));
		}
		R = Integer.parseInt(f.readLine());
		rounds = new Round [R];
		for (int i = 0; i < R; i++)
			rounds[i] = new Round(f.readLine());
	}
	/**
	 * Aceasta metoda realizeaza executarea tuturor actiunilor din cadrul unui joc.
	 * <br>
	 * Pentru fiecare runda cuprinde 2 pasi:
	 * <ul>
	 * <li> initializarea unei noi runde </li>
	 * <li> luptele eroilor </li>
	 * </ul>
	 * 
	 * @param map Harta jocului.
	 *
	 * @see game.Map
	 * @see game.Game#newRound(int, Map) newRound
	 * @see game.Game#heroesFights(Map) heroesFights
	 */
	public void play (Map map) {
		for (int k = 0; k < R; k++) {
			newRound (k, map);
			heroesFights(map);
		}
	}
	/**
	 * Initializeaza o noua runda, aplicand eroilor 
	 * dot (damage over time) daca este cazul si mutandu-i
	 * in functie de specificatiile rundei curente.
	 * 
	 * @param k Numarul rundei curente.
	 * @param map Harta jocului.
	 */
	public void newRound (int k, Map map) {
		for (int i = 0; i < P; i++) {
			heroes[i].takeDOT();
			if (heroes[i].dead == false)
				heroes[i].move(rounds[k].moves[i], map.N-1, map.M-1);
		}
	}
	/**
	 * Verifica ce eroi se afla pe aceeasi pozitie pe harta
	 * si, daca indeplinesc conditiile necesare
	 * (nu sunt morti), porneste lupta intre ei.
	 */
	public void heroesFights (Map map) {
		for (int i = 0; i < P; i++) {
			if (heroes[i].dead == false && heroes[i].hasFighted == false)
				for (int j = i+1; j < P; j++)
					if (heroes[i].pos[0] == heroes[j].pos[0] && heroes[i].pos[1] == heroes[j].pos[1])
						if (heroes[j].dead == false) {
							heroes[j].acceptFightWith (heroes[i], map.land[heroes[i].pos[0]][heroes[i].pos[1]]);
							heroes[i].hasFighted = true;
							heroes[j].hasFighted = true;
							break;
						}
		}
	}
	/**
	 * Aceasta metoda marcheaza finalul unui joc
	 * si afiseaza informatiile despre eroi.
	 * @param f Fisierul in care sunt scrise datele de iesire.
	 */
	public void end (FileIO f) {
		for (int i = 0; i < P; i++)
				f.writeLine(heroes[i].toString());
		System.out.println();
	}
}
