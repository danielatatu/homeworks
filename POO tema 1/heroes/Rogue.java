package heroes;

import abilities.*;

/**
 * Erou ce are abilitatile Backstab si Paralysis.
 */
public class Rogue extends Hero {

	public Rogue (int x, int y) {
		super ('R', 600, 40, x, y);
		firstAb = new Backstab();
		secondAb = new Paralysis();
	}

	public void fightWith (Knight k, char land) {

		firstAb.applyTo (k, level, land);
		secondAb.applyTo (k, level, land);

		k.firstAb.applyTo (this, k.level, land);
		k.secondAb.applyTo (this, k.level, land);
		
		endFightWith (k);
	}

	public void fightWith (Pyromancer p, char land) {

		firstAb.applyTo (p, level, land);
		secondAb.applyTo (p, level, land);

		p.firstAb.applyTo (this, p.level, land);
		p.secondAb.applyTo (this, p.level, land);
		
		endFightWith (p);
	}

	public void fightWith (Rogue r, char land) {

		firstAb.applyTo (r, level, land);
		secondAb.applyTo (r, level, land);

		r.firstAb.applyTo (this, r.level, land);
		r.secondAb.applyTo (this, r.level, land);
		
		endFightWith (r);
	}

	public void fightWith (Wizard w, char land) {
		
		w.firstAb.applyTo (this, w.level, land);
		w.secondAb.applyTo (this, w.level, land);

		firstAb.applyTo (w, level, land);
		secondAb.applyTo (w, level, land);
		
		endFightWith (w);
	}

	public void acceptFightWith (Hero h, char land) {
		h.fightWith (this, land);
	}
}
