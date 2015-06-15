package abilities;

import heroes.*;

/**
 * Abilitate specifica lui Wizard.
 */
public class Drain extends Ability {
	
	/**
	 * procent din viata adversarului; constant
	 */
	private final float percentage = 0.2f;
	/**
	 * procent pe nivel din viata adversarului; constant
	 */
	private final float percLvl = 0.05f;
	/**
	 * constanta folosita in calculul procentului
	 */
	private final float c = 0.3f;
	/**
	 * procentul calculat in functie de modificatori
	 */
	private float pc;
	/**
	 * retine valoarea minima necesara in formula
	 */
	private float min;

	public Drain () {
		super (0, 0, 0, 0, 1.2f, 0.9f, 0.8f, 1.05f, 1.1f);
	}
	
	/**
	 * Calculeaza procentul aplicat, fara modificatorul de rasa.
	 * 
	 * @param lvl level-ul eroului care aplica abilitatea
	 * @param land terenul de lupta
	 */
	void setPercentage (int lvl, char land) {
		pc = percentage + percLvl * lvl;
		if (land == 'D')
			pc *= landMod;
	}
	
	public void applyTo (Knight k, int lvl, char land) {

		setPercentage (lvl, land);
		pc *= knightMod;
		
		min = Math.min ( c * k.getMaxHP(), k.getHP() );
		damage = Math.round (pc * min);
		k.setHP (k.getHP() - damage);
	}

	public void applyTo (Pyromancer p, int lvl, char land) {

		setPercentage (lvl, land);
		pc *= pyroMod;
		
		min = Math.min ( c * p.getMaxHP(), p.getHP() );
		damage = Math.round (pc * min);
		p.setHP (p.getHP() - damage);
	}

	public void applyTo (Rogue r, int lvl, char land) {

		setPercentage (lvl, land);
		pc *= rogueMod;
		
		min = Math.min ( c * r.getMaxHP(), r.getHP() );
		damage = Math.round (pc * min);
		r.setHP (r.getHP() - damage);
	}

	public void applyTo (Wizard w, int lvl, char land) {

		setPercentage (lvl, land);
		pc *= wizardMod;
		
		min = Math.min ( c * w.getMaxHP(), w.getHP() );
		damage = Math.round (pc * min);
		w.setHP (w.getHP() - damage);
	}
}
