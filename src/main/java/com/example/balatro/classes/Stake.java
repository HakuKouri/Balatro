package com.example.balatro.classes;

public class Stake
{
    private int id;
    private String stakeImageUrl;
    private String stakeName;
    private String stakeEffect;
    private String stakeUnlockCondition;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getStakeImageUrl()
    {
        return stakeImageUrl;
    }

    public void setStakeImageUrl(String stakeImageUrl)
    {
        this.stakeImageUrl = stakeImageUrl;
    }

    public String getStakeName()
    {
        return stakeName;
    }

    public void setStakeName(String stakeName)
    {
        this.stakeName = stakeName;
    }

    public String getStakeEffect()
    {
        return stakeEffect;
    }

    public void setStakeEffect(String stakeEffect)
    {
        this.stakeEffect = stakeEffect;
    }

    public String getStakeUnlockCondition()
    {
        return stakeUnlockCondition;
    }

    public void setStakeUnlockCondition(String stakeUnlockCondition)
    {
        this.stakeUnlockCondition = stakeUnlockCondition;
    }
}
