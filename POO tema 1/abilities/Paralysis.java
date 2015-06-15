package abilities;

import heroes.*;

/**
 * Abilitate specifica lui Rogue.
 */
public class Paralysis extends Ability {

	/**
	 * nr de runde in care adversarul ramane nemiscat
	 * daca lupta are loc pe teren neutru; constant
	 */
	private final int rounds = 3;
	/**
	 * nr de runde in care adversarul ramane nemiscat
	 * daca lupta are loc pe teren Woods; constant
	 */
	private final int roundsW = 6;

	public Paralysis () {
		super (40, 10, 40, 10, 0.8f, 1.2f, 0.9f, 1.25f, 1.15f);
	}

	/**
	 * Calculeaza damage-ul, fara modificatorul de rasa.
	 */
	public int getDamage (int lvl, char land) {
		damage = super.getDamage(lvl, land);
		if (land == 'W')
			damage = Math.round (damage * landMod);
		return damage;
	}
	/**
	 * Aplica damage-ul over time si seteaza incapacitatea adversarului
	 * pentru urmatoarele runde.
	 */
	public void overtime (Hero h, char land) {
		if (land == 'W') {
			h.setDOT (damage, roundsW);
			h.paralyse (roundsW);
		}
		else {
			h.setDOT (damage, rounds);
			h.paralyse (rounds);
		}
	}

	public void applyTo (Knight k, int lvl, char land) {

		getDamage(lvl, land);
		damage = Math.round (damage * knightMod);
		k.setHP (k.getHP() - damage);
		
		overtime (k, land);
	}

	public void applyTo (Pyromancer p, int lvl, char land) {

		getDamage(lvl, land);
		damage = Math.round (damage * pyroMod);
		p.setHP (p.getHP() - damage);
		
		overtime (p, land);
	}

	public void applyTo (Rogue r, int lvl, char land) {

		getDamage(lvl, land);
		damage = Math.round (damage * rogueMod);
		r.setHP (r.getHP() - damage);
		
		overtime (r, land);
	}

	public void applyTo (Wizard w, int lvl, char land) {

		getDamage(lvl, land);
		damage = Math.round (damage * wizardMod);
		w.setHP (w.getHP() - damage);
		
		overtime (w, land);
	}
}
