package abilities;

import heroes.*;

/**
 * Abilitate specifica lui Rogue.
 */
public class Backstab extends Ability {
	
	/**
	 * contorizeaza de cate ori s-a aplicat Backstab;
	 * necesar pentru aplicarea critical hit
	 */
	private int criticalHit;
	/**
	 * modificatorul loviturii critical; constant
	 */
	private final float hitMod = 1.5f;

	public Backstab () {
		super (200, 20, 0, 0, 0.9f, 1.25f, 1.2f, 1.25f, 1.15f);
		criticalHit = 0;
	}

	/**
	 * Calculeaza damage-ul, fara modificatorul de rasa.
	 */
	public int getDamage (int lvl, char land) {
		damage = super.getDamage(lvl, land);
		if (land == 'W') {
			damage = Math.round (damage * landMod);
			if (criticalHit % 3 == 0) {
				damage = Math.round (damage * hitMod);
			}
		}
		return damage;
	}
	
	public void applyTo (Knight k, int lvl, char land) {

		getDamage(lvl, land);
		damage = Math.round (damage * knightMod);
		k.setHP (k.getHP() - damage);
		criticalHit++;
	}

	public void applyTo (Pyromancer p, int lvl, char land) {

		getDamage(lvl, land);
		damage = Math.round (damage * pyroMod);
		p.setHP (p.getHP() - damage);
		criticalHit++;
	}

	public void applyTo (Rogue r, int lvl, char land) {

		getDamage(lvl, land);
		damage = Math.round (damage * rogueMod);
		r.setHP (r.getHP() - damage);
		criticalHit++;
	}

	public void applyTo (Wizard w, int lvl, char land) {

		getDamage(lvl, land);
		damage = Math.round (damage * wizardMod);
		w.setHP (w.getHP() - damage);
		criticalHit++;
	}
}
