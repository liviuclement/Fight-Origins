package com.liviu;

public class Skills {

    String name;
    int meleDamage;
    int rangeDamage;
    private int numberOfTargets;
    private int manaCost;
    int levelRequirement;
    int spellPower;

    Skills(String name, int meleDamage, int rangeDamage, int numberOfTargets, int manaCost, int levelRequirement, int spellPower) {
        this.rangeDamage = rangeDamage;
        this.meleDamage = meleDamage;
        this.name = name;
        this.numberOfTargets = numberOfTargets;
        this.manaCost = manaCost;
        this.levelRequirement = levelRequirement;
        this.spellPower = spellPower;
    }

    public String getName() {
        return name;
    }
}
