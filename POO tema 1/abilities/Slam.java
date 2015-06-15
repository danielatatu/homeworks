package abilities;

import heroes.*;

/**
 * Abilitate specifica lui Knight.
 */
public class Slam extends Ability {
	
	/**
	 * nr de runde in care adversarul ramane nemiscat; constant
	 */
	private final int rounds = 1;

	public Slam () {
		super (100, 40, 0, 0, 1.2f, 0.9f, 0.8f, 1.05f, 1.15f);
	}
	
	/**
	 * Calculeaza damage-ul, fara modificatorul de rasa.
	 */
	public int getDamage (int lvl, char land) {
		damage = super.getDamage(lvl, land);
		if (land == 'L')
			damage = Math.round (damage * landMod);
		return damage;
	}
	
	public void applyTo (Knight k, int lvl, char land) {

		getDamage (lvl, land);
		damage = Math.round (damage * knightMod);
		k.setHP (k.getHP() - damage);
		
		k.paralyse(rounds);
	}

	public void applyTo (Pyromancer p, int lvl, char land) {

		getDamage (lvl, land);
		damage = Math.round (damage * pyroMod);
		p.setHP (p.getHP() - damage); 
		
		p.paralyse(rounds);
	}

	public void applyTo (Rogue r, int lvl, char land) {

		getDamage (lvl, land);
		damage = Math.round (damage * rogueMod);
		r.setHP (r.getHP() - damage);
		
		r.paralyse(rounds);
	}

	public void applyTo (Wizard w, int lvl, char land) {

		getDamage (lvl, land);
		damage = Math.round (damage * wizardMod);
		w.setHP (w.getHP() - damage);
		
		w.paralyse(rounds);
	}
}
