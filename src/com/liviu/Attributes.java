package com.liviu;

public class Attributes {
    private int XP, level,xpToNextLevel, statPoints, skillPoints, statPointsPerLevel, lastXpToNextLevel;;
    private double skillPointsPerLevel;
    public Attributes(){};
    Attributes(int XP, int level, int xpToNextLevel, int statPoints, int skillPoints, int statPointsPerLevel, double skillPointsPerLevel) {
        this.XP = XP;
        this.level = level;
        this.xpToNextLevel = xpToNextLevel;
        this.statPoints = statPoints;
        this.skillPoints = skillPoints;
        this.statPointsPerLevel = statPointsPerLevel;
        this.skillPointsPerLevel = skillPointsPerLevel;
        this.lastXpToNextLevel = xpToNextLevel;
    }

    public int getXP() {
        return XP;
    }

    public int getLevel() {
        return level;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public int getStatPoints() {
        return statPoints;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getStatPointsPerLevel() {
        return statPointsPerLevel;
    }

    public double getSkillPointsPerLevel() {
        return skillPointsPerLevel;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    public void setStatPoints(int statPoints) {
        this.statPoints = statPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void setStatPointsPerLevel(int statPointsPerLevel) {
        this.statPointsPerLevel = statPointsPerLevel;
    }

    public void setSkillPointsPerLevel(double skillPointsPerLevel) {
        this.skillPointsPerLevel = skillPointsPerLevel;
    }

    public int calculateXpToNextLevel()
    {
        int result = lastXpToNextLevel*(level/4 + 1);
        lastXpToNextLevel = xpToNextLevel;
        return result;
    }
}
