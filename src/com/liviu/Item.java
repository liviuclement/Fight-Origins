package com.liviu;

class Item {
    enum Slot {
        HEAD,
        HANDS,
        CHEST,
        LEGS,
        FEET
    }

    private Slot slot;
    private int bonusHP;
    private int bonusMeleeDamage;
    private int bonusRangeDamage;
    private int bonusMana;

    Item(Slot slot, int bonusHP, int bonusMeleeDamage, int bonusRangeDamage, int bonusMana) {
        this.slot = slot;
        this.bonusHP = bonusHP;
        this.bonusMeleeDamage = bonusMeleeDamage;
        this.bonusRangeDamage = bonusRangeDamage;
        this.bonusMana = bonusMana;
    }
}