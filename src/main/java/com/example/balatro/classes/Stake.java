package com.example.balatro.classes;

public class Stake
{
    private int id;
    private String stakeImageStickerUrl;
    private String stakeImageChipUrl;
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

    public String getStakeImageStickerUrl() {
        return stakeImageStickerUrl;
    }

    public void setStakeImageStickerUrl(String stakeImageStickerUrl) {
        this.stakeImageStickerUrl = stakeImageStickerUrl;
    }

    public String getStakeImageChipUrl() {
        return stakeImageChipUrl;
    }

    public void setStakeImageChipUrl(String stakeImageChipUrl) {
        this.stakeImageChipUrl = stakeImageChipUrl;
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
