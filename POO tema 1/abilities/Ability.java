package abilities;

import heroes.*;

/**
 * Implementeaza abilitati specifice unuia dintre eroi.
 */
public abstract class Ability {
	/**
	 * valoarea damage-ului de baza
	 */
	final int baseDmg;
	/**
	 * valoarea damage-ului de baza pe nivel
	 */
	final int baseDmgLvl;
	/**
	 * valoarea damage-ului over time
	 */
	final int DoT;
	/**
	 * valoarea damage-ului over time pe nivel
	 */
	final int DoTlvl;

	// modificatori
	final float knightMod;
	final float pyroMod;
	final float rogueMod;
	final float wizardMod;
	final float landMod;
	/**
	 * damage-ul de baza calculat in functie de modificatori
	 */
	int damage;
	/**
	 * damage-ul over time calculat in functie de modificatori
	 */
	int dot;
	
	Ability (int bd, int bdl, int dot, int dotl,
			float km, float pm, float rm, float wm, float lm) {
		baseDmg = bd;
		baseDmgLvl = bdl;
		DoT = dot;
		DoTlvl = dotl;
		knightMod = km;
		pyroMod = pm;
		rogueMod = rm;
		wizardMod = wm;
		landMod = lm;
	}
	
	/**
	 * Calculeaza damage-ul de baza, fara modificatori.
	 * 
	 * @param lvl level-ul eroului ce aplica abilitatea
	 * @param land terenul de lupta
	 */
	public int getDamage (int lvl, char land) {
		int damage = baseDmg + baseDmgLvl * lvl;
		return damage;
	}

	public abstract void applyTo (Knight k, int lvl, char land);
	public abstract void applyTo (Pyromancer p, int lvl, char land);
	public abstract void applyTo (Rogue r, int lvl, char land);
	public abstract void applyTo (Wizard w, int lvl, char land);
}
