package abilities;

import heroes.*;

/**
 * Abilitate specifica lui Knight.
 */
public class Execute extends Ability {
	
	/**
	 * limita de HP pe care adversatul trebuie
	 * sa il aiba pentru a nu muri imediat
	 */
	private float HPlimit;
	/**
	 * procent pentru calculare HPlimit
	 */
	private final float percentage = 0.2f;
	/**
	 * procent pe nivel pentru calculare HPlimit
	 */
	private final float percLvl = 0.01f;
	/**
	 * limita maxima procent
	 */
	private final float limit = 0.4f;

	public Execute () {
		super (200, 30, 0, 0, 1.0f, 1.1f, 1.15f, 0.8f, 1.15f);
		HPlimit = 0.2f;
	}

	/**
	 * Calculeaza damage-ul aplicat in cazul in care adversarul
	 * are HP peste limita precizata (nu moare imediat).
	 */
	public int getDamage (int lvl, char land) {
		damage = super.getDamage(lvl, land);
		if (land == 'L')
			damage = Math.round (damage * landMod);
		return damage;
	}
	/**
	 * Calculul limitei HP, pe care adversatul trebuie
	 * sa il aiba pentru a nu muri imediat
	 */
	float getHPlimit (Hero h, int lvl) {
		HPlimit = percentage + percLvl * lvl;
		if (HPlimit > limit)
			HPlimit = limit;
		HPlimit *= h.getMaxHP();
		return HPlimit;
	}
	
	public void applyTo (Knight k, int lvl, char land) {

		if ( k.getHP() < getHPlimit (k, lvl) )
			k.setHP(0);
		else {
			getDamage (lvl, land);
			damage = Math.round (damage * knightMod);
			k.setHP (k.getHP() - damage);
		}
	}

	public void applyTo (Pyromancer p, int lvl, char land) {

		if ( p.getHP() < getHPlimit (p, lvl) )
			p.setHP(0);
		else {
			getDamage (lvl, land);
			damage = Math.round (damage * pyroMod);
			p.setHP (p.getHP() - damage);
		}
	}

	public void applyTo (Rogue r, int lvl, char land) {
	
		if ( r.getHP() < getHPlimit (r, lvl) )
			r.setHP(0);
		else {
			getDamage (lvl, land);
			damage = Math.round (damage * rogueMod);
			r.setHP (r.getHP() - damage);
		}
	}

	public void applyTo (Wizard w, int lvl, char land) {

		if ( w.getHP() < getHPlimit (w, lvl) )
			w.setHP(0);
		else {
			getDamage (lvl, land);
			damage = Math.round (damage * wizardMod);
			w.setHP (w.getHP() - damage);
		}
	}
}
