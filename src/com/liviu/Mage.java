package com.liviu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Iterator.*;
import java.util.Random;

public class Mage extends Hero {

    private Skills magicattack = new Skills("magic attack", 0, 0, 1, 100, 1, 100);
    private Skills shield = new Skills("shield", 0, 0, 1, 60, 1, 100);
    private Skills heal = new Skills("heal", 0, 0, 1, 120, 5, 50);
    private Skills Megamagicattack = new Skills("mega magic attack", 0, 0, 1, 170, 10, 400);

    public ArrayList<Skills> list = new ArrayList<>();

    //mage
    Item weakHelmet = new Item(Item.Slot.HEAD, 150, 0, 0, 0);
    Item strongHelmet = new Item(Item.Slot.HEAD, 450, 0, 0, 0);
    Item magicBaguette = new Item(Item.Slot.HANDS, 0, 0, 0, 500);
    Item weakChestArmor = new Item(Item.Slot.CHEST, 100, 0, 0, 0);
    Item strongChestArmor = new Item(Item.Slot.CHEST, 300, 0, 0, 0);
    Item weakLegsArmor = new Item(Item.Slot.LEGS, 80, 0, 0, 0);
    Item strongLegsArmor = new Item(Item.Slot.LEGS, 200, 0, 0, 0);
    Item silverShoes = new Item(Item.Slot.FEET, 30, 0, 0, 0);
    Item goldShoes = new Item(Item.Slot.FEET, 75, 0, 0, 0);


    public Mage(int pos, int level, int XP) {

        name = "Mage";
        super.isAlive = true;
        super.level = 1;
        super.xpToNextLevel = 800 + (level - 1) * 1200;
        super.XP = XP;
        super.XPToGive = 800;
        super.toGiveOrNotToGive = true;
        Random r = new Random();
        super.statPoints = new Stats(500, 300, r.nextInt(11), 30, 10);
        this.pos = pos;
        list.add(magicattack);
        list.add(shield);
        list.add(heal);
        list.add(Megamagicattack);

        for (int i = 1; i < level; i++) {
            super.level++;
            statPoints.HP += 100;
            statPoints.dodgeChance += 7;
            statPoints.hitChance += 7;
            super.XPToGive += 800;
            if (super.level % 3 == 0) {
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).spellPower += 100;
                    //skillPoints.meleDamage += 100;
                }
            }
        }
    }

    public Mage(int pos, int level, int HP, int XP) {

        name = "mage";
        super.isAlive = true;
        super.level = 1;
        super.xpToNextLevel = 800;
        super.XP = XP;
        super.XPToGive = 800;
        super.toGiveOrNotToGive = true;
        Random r = new Random();
        super.statPoints = new Stats(500, 300, r.nextInt(11), 30, 10);
        this.pos = pos;
        list.add(magicattack);
        list.add(shield);
        list.add(heal);
        list.add(Megamagicattack);
        statPoints.HP = HP;
        for (int i = 1; i < level; i++) {
            super.level++;
            statPoints.dodgeChance += 7;
            statPoints.hitChance += 7;
            super.XPToGive += 800;
            if (super.level % 3 == 0) {
                for (int j = 0; j < list.size(); j++) {
                    list.get(j).spellPower += 100;
                }
            }
        }
    }

    @Override
    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    @Override
    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }


    @Override
    public void levelUp() {
        if (this.isAlive) {
            if (XP >= xpToNextLevel) {

                super.level++;
                statPoints.HP = 500 + (level - 1) * 100;
                statPoints.dodgeChance += 5;
                statPoints.hitChance += 5;
                this.XP -= xpToNextLevel;
                xpToNextLevel += 1200;

                if (level % 3 == 0) {
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).spellPower += 100;

                    }
                }
                System.out.println("Mage (" + this.pos + ") leveled up.");
            }
        }
    }

    public void attack(String s, Hero monster) {
        Iterator<Skills> itr = list.iterator();
        while (itr.hasNext()) {
            Skills element = itr.next();
            if (element.name.equals(s)) {
                if (element.name.equals("shield") || element.name.equals("heal")) {
                    monster.statPoints.HP += element.spellPower;
                    System.out.println("Mage (" + this.pos + ") protected " + monster.pos);
                    return;
                }

                if (monster.statPoints.HP - element.spellPower > 0)
                    monster.statPoints.HP -= element.spellPower;
                else {
                    monster.statPoints.HP = 0;
                }

                System.out.println("Mage (" + this.pos + ") attacked " + monster.pos);
                return;
            }

        }
        System.out.println("The attack doesn't belong to the Mage");
    }

    @Override
    public void afisare(boolean bool) {
        if (bool)
            System.out.println("The mage attacks ");
        for (int i = 0; i < list.size(); i++) {

            if (i == 0 && list.get(i).levelRequirement <= level)
                System.out.println("Attacks available: " + list.get(i).name + " (" + list.get(i).spellPower + ")");
            else if (i != 0 && list.get(i).levelRequirement <= level)
                System.out.println("                   " + list.get(i).name + " (" + list.get(i).spellPower + ")");
        }
    }

    public String getRandomAttack(Player player, Enemy enemy) {
        Random random = new Random();

        int rand = random.nextInt(list.size() - 1);
        while (list.get(rand).levelRequirement > this.level) {
            rand = random.nextInt(list.size() - 1);
        }
        return list.get(rand).name;
    }

    public String seePlayerStats() {
        return "Mage " + " Pos " + pos + " HP " + statPoints.HP + " level " + level + " XP " + XP + " XPToNextLevel " + xpToNextLevel;
    }

    public String seeEnemyStats() {
        return "Mage " + " Pos " + pos + " HP " + statPoints.HP + " level " + level;
    }

    public ArrayList<Skills> getList() {
        return list;
    }
}
