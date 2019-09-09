package com.liviu;

public class Stats {

    int dodgeChance;
    int hitChance;
    int HP;     //(hitpoints)
    int mana;           //(wisdom)
    int initiative;

    public Stats( int HP, int mana, int initiative, int dodgeChance, int hitChance) {

        this.hitChance = hitChance;
        this.dodgeChance = dodgeChance;
        this.HP = HP; //hitpoints
        this.mana = mana;
        this.initiative = initiative;
    }

    public Stats (){}

}
