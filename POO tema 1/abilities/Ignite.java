package abilities;

import heroes.*;

/**
 * Abilitate specifica lui Pyromancer.
 */
public class Ignite extends Ability {

	public Ignite () {
		super (150, 20, 50, 30, 1.2f, 0.9f, 0.8f, 1.05f, 1.25f);
	}
	
	/**
	 * Calculeaza damage-ul, fara modificatorul de rasa.
	 */
	public int getDamage (int lvl, char land) {
		damage = super.getDamage(lvl, land);
		if (land == 'V')
			damage = Math.round (damage * landMod);
		return damage;
	}
	
	/**
	 * Calculeaza damage-ul over time, fara modificatorul de rasa.
	 */
	public void setDot (int lvl, char land) {
		dot = DoT + DoTlvl * lvl;
		if (land == 'V')
			dot = Math.round (dot * landMod);
	}
	
	public void applyTo (Knight k, int lvl, char land) {

		getDamage (lvl, land);
		damage = Math.round (damage * knightMod);
		k.setHP (k.getHP() - damage);

		setDot (lvl, land);
		dot = Math.round (dot * knightMod);
		k.setDOT (dot, 2);
	}

	public void applyTo (Pyromancer p, int lvl, char land) {
		
		getDamage (lvl, land);
		damage = Math.round (damage * pyroMod);
		p.setHP (p.getHP() - damage);

		setDot (lvl, land);
		dot = Math.round (dot * pyroMod);
		p.setDOT (dot, 2);
	}

	public void applyTo (Rogue r, int lvl, char land) {

		getDamage (lvl, land);
		damage = Math.round (damage * rogueMod);
		r.setHP (r.getHP() - damage);

		setDot (lvl, land);
		dot = Math.round (dot * rogueMod);
		r.setDOT (dot, 2);
	}

	public void applyTo (Wizard w, int lvl, char land) {

		getDamage (lvl, land);
		damage = Math.round (damage * wizardMod);
		w.setHP (w.getHP() - damage);

		setDot (lvl, land);
		dot = Math.round (dot * wizardMod);
		w.setDOT (dot, 2);
	}
}