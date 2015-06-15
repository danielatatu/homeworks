package abilities;

import heroes.*;

/**
 * Abilitate specifica lui Pyromancer.
 */
public class Fireblast extends Ability {

	public Fireblast () {
		super (350, 50, 0, 0, 1.2f, 0.9f, 0.8f, 1.05f, 1.25f);
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
	
	public void applyTo (Knight k, int lvl, char land) {
		getDamage(lvl, land);
		damage = Math.round (damage * knightMod);
		k.setHP ( k.getHP() - damage ); 
	}
	
	public void applyTo (Pyromancer p, int lvl, char land) {
		getDamage(lvl, land);
		damage = Math.round (damage * pyroMod);
		p.setHP ( p.getHP() - damage ); 
	}
	
	public void applyTo (Rogue r, int lvl, char land) {
		getDamage(lvl, land);
		damage = Math.round (damage * rogueMod);
		r.setHP ( r.getHP() - damage ); 
	}
	
	public void applyTo (Wizard w, int lvl, char land) {
		getDamage(lvl, land);
		damage = Math.round (damage * wizardMod);
		w.setHP ( w.getHP() - damage ); 
	}
}
