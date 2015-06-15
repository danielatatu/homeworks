package heroes;

import abilities.*;
/**
 * Clasa ce implementeaza tipul abstract "erou".
 */
public abstract class Hero {
	public char race;
	int initialHP;
	int HPperLevel;
	int HP;
	int XP;
	int level;
	/**
	 * vector ce contine randul pe care se afla eroul (pos[0])
	 * si coloana pe care se afla acesta (pos[1])
	 */
	public int [] pos;
	/**
	 * numarul de runde in care eroul trebuie
	 * sa primeasca damage over time
	 */
	int DOTrounds;
	/**
	 * valoarea damage-ului over time
	 * pe care trebuie sa il primeasca eroul
	 */
	int DOT;
	/**
	 * arata daca eroul este paralizat sau nu
	 */
	boolean paralysed;
	/**
	 * numarul de runde in care eroul este paralizat
	 */
	int paralysisRounds;
	/**
	 * este true daca eroul a luptat in runda curenta
	 * si false in caz contrar
	 */
	public boolean hasFighted;
	/**
	 * arata daca eroul este mort sau nu
	 */
	public boolean dead;
	/**
	 * prima abilitate, specifica fiecarui tip de erou in parte
	 */
	Ability firstAb;
	/**
	 * a doua abilitate, specifica fiecarui tip de erou in parte
	 */
	Ability secondAb;

	/**
	 * @param r rasa
	 * @param hp HP-ul initial
	 * @param hplvl HP per nivel
	 * @param x randul pe care se afla
	 * @param y coloana pe care se afla
	 */
	Hero (char r, int hp, int hplvl, int x, int y) {
		race = r;
		initialHP = hp;
		HPperLevel = hplvl;
		HP = hp;
		XP = 0;
		level = 0;
		pos = new int[2];
		pos[0] = x;
		pos[1] = y;
		DOTrounds = 0;
		DOT = 0;
		paralysed = false;
		paralysisRounds = 0;
		hasFighted = false;
		dead = false;
	}

	public int getHP () { return HP; }
	/**
	 * @return viata teoretic maxima a eroului la nivelul lui
	 */
	public int getMaxHP () { return initialHP + level * HPperLevel; }
	public void setHP (int hp) { HP = hp; }
	public void setDOT (int dot, int rounds) {
		DOT = dot;
		DOTrounds = rounds;
	}
	/**
	 * Aceasta metoda este necesara pentru
	 * implementarea abilitatii {@link abilities.Deflect}.
	 * 
	 * @param land tipul terenului pe care are loc lupta
	 * @return damage-ul total (fara race modifiers) pe care
	 * il aplica asupra adversarului
	 */
	public int getDamage (char land) {
		return firstAb.getDamage(level, land) + secondAb.getDamage(level, land);
	}
	/**
	 * Seteaza incapacitatea eroului.
	 * 
	 * @param rounds numarul de runde in care
	 * eroul va ramane nemiscat
	 */
	public void paralyse (int rounds) {
		paralysed = true;
		paralysisRounds = rounds;
	}
	/**
	 * Aplica damage over time eroului daca este cazul. <br>
	 * Aceasta metoda se aplica la inceputul fiecarei runde.
	 */
	public void takeDOT () {
		if ( ! dead && DOTrounds > 0) {
			HP -= DOT;
			DOTrounds--;
			if (HP <= 0)
				dead = true;
		}
	}
	/**
	 * Muta eroul pe pozitia specificata in runda curenta
	 * si seteaza {@link #hasFighted} la false.
	 * 
	 * @param c comanda in functie de care se muta ('U', 'D', etc)
	 * @param limitX limita de randuri a hartii
	 * @param limitY limita de coloane a hartii
	 */
	public void move (char c, int limitX, int limitY) {
		hasFighted = false;
		if (paralysed == true) {
			paralysisRounds--;
			if (paralysisRounds == 0)
				paralysed = false;
			return;
		}
		if (c == 'U' && pos[0] > 0)
			pos[0]--;
		else if (c == 'D' && pos[0] < limitX)
			pos[0]++;
		else if (c == 'L' && pos[1] > 0)
			pos[1]--;
		else if (c == 'R' && pos[1] < limitY)
			pos[1]++;	
	}
	/**
	 * Lupta cu un knight pe terenul land.
	 */
	abstract void fightWith (Knight k, char land);
	/**
	 * Lupta cu un pyromancer pe terenul land.
	 */
	abstract void fightWith (Pyromancer p, char land);
	/**
	 * Lupta cu un rogue pe terenul land.
	 */
	abstract void fightWith (Rogue r, char land);
	/**
	 * Lupta cu un wizard pe terenul land.
	 */
	abstract void fightWith (Wizard w, char land);

	public abstract void acceptFightWith (Hero h, char land);
	
	/**
	 * Finalul luptei. <br>
	 * Se trag concluziile legate de moartea eroilor si cresterea experientei.
	 */
	void endFightWith (Hero h) {
		if ( HP <= 0)
			dead = true;
		if ( h.HP <= 0)
			h.dead = true;
		if ( !dead && h.dead ) {
			XP += Math.max (0, 200 - (level - h.level) * 40);
			levelUp();
		}
		if ( dead && !h.dead ) {
			h.XP += Math.max (0, 200 - (h.level - level) * 40);
			h.levelUp();
		}
	}
	
	void levelUp () {
		int l = level;
		while ( XP >= 250 + level * 50 )
			level++;
		if (level != l)
			HP = initialHP + HPperLevel * level;
	}
	
	public String toString() {
		if (dead == true)
			return race + " dead";
		else
			return race + " " + level + " " + XP + " " + HP
				   + " " + pos[0] + " " + pos[1];
	}
}
