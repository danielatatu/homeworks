package abilities;

import heroes.*;

/**
 * Abilitate specifica lui Wizard.
 */
public class Deflect extends Ability {
	
	/**
	 * procent din damage-ul primit de la adversar; constant
	 */
	private final float percentage = 0.35f;
	/**
	 * procent pe nivel din damage-ul primit; constant
	 */
	private final float percLvl = 0.02f;
	/**
	 * limita maxima a procentului aplicat; constanta
	 */
	private final float limit = 0.7f;
	/**
	 * procentul calculat in functie de modificatori
	 */
	private float pc;
	
	public Deflect () {
		super (0, 0, 0, 0, 1.4f, 1.3f, 1.2f, 0f, 1.1f);
	}

	/**
	 * Calculeaza procentul aplicat, fara modificatorul de rasa.
	 * 
	 * @param lvl level-ul eroului care aplica abilitatea
	 * @param land terenul de lupta
	 */
	void setPercentage (int lvl, char land) {
		pc = percentage + percLvl * lvl;
		if (pc > limit)
			pc = limit;
		if (land == 'D')
			pc *= landMod;
	}

	public void applyTo (Knight k, int lvl, char land) {

		setPercentage (lvl, land);
		pc *= knightMod;
		
		damage = k.getDamage(land);
		damage = Math.round (pc * damage);
		k.setHP (k.getHP() - damage);
	}

	public void applyTo (Pyromancer p, int lvl, char land) {

		setPercentage (lvl, land);
		pc *= pyroMod;
		
		damage = p.getDamage(land);
		damage = Math.round (pc * damage);
		p.setHP (p.getHP() - damage);
	}

	public void applyTo (Rogue r, int lvl, char land) {

		setPercentage (lvl, land);
		pc *= rogueMod;
		
		damage = r.getDamage(land);
		damage = Math.round (pc * damage);
		r.setHP (r.getHP() - damage);
	}

	public void applyTo (Wizard w, int lvl, char land) { }
}
