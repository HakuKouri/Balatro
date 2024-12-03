package com.example.balatro.classes;

public class Joker extends Card
{
    private String rarity;
    private String unlockRequirement;
    private String actTiming;

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
    public String getUnlockRequirement()
    {
        return unlockRequirement;
    }
    public void setUnlockRequirement(String unlockRequirement)
    {
        this.unlockRequirement = unlockRequirement;
    }
    public String getActTiming()
    {
        return actTiming;
    }
    public void setActTiming(String actTiming)
    {
        this.actTiming = actTiming;
    }
    public static String SqlString(Joker joker) {
        //          (id,                jokerImage,                             jokerName,                  jokerEffect,                        cost,                       rarity,                     unlockRequirement,                   jokerType,                       act)
        return joker.getId() + ", '" + joker.getCardFrontCoverUrl() + "', '" + joker.getName() + "', '" + joker.getDescription()  + "', " + joker.getCost() + ", '" + joker.getRarity() + "', '" + joker.getUnlockRequirement() + "', '" + joker.getType()  + "', '" + joker.getActTiming() + "'";
    }
}
