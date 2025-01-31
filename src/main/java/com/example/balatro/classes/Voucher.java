package com.example.balatro.classes;

public class Voucher extends Card
{
    private int voucherId;
    private String voucherBaseImageUrl;
    private String voucherUpgradeImageUrl;
    private String voucherBaseName;
    private String voucherUpgradeName;
    private String voucherBaseEffect;
    private String voucherUpgradeEffect;
    private String voucherUpgradeUnlockCondition;
    private String voucherNotes;

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherBaseImageUrl()
    {
        return voucherBaseImageUrl;
    }

    public void setVoucherBaseImageUrl(String voucherBaseImageUrl)
    {
        this.voucherBaseImageUrl = voucherBaseImageUrl;
    }

    public String getVoucherUpgradeImageUrl()
    {
        return voucherUpgradeImageUrl;
    }

    public void setVoucherUpgradeImageUrl(String voucherUpgradeImageUrl) {
        this.voucherUpgradeImageUrl = voucherUpgradeImageUrl;
    }

    public String getVoucherBaseName()
    {
        return voucherBaseName;
    }

    public void setVoucherBaseName(String voucherBaseName)
    {
        this.voucherBaseName = voucherBaseName;
    }

    public String getVoucherUpgradeName()
    {
        return voucherUpgradeName;
    }

    public void setVoucherUpgradeName(String voucherUpgradeName)
    {
        this.voucherUpgradeName = voucherUpgradeName;
    }

    public String getVoucherBaseEffect()
    {
        return voucherBaseEffect;
    }

    public void setVoucherBaseEffect(String voucherBaseEffect)
    {
        this.voucherBaseEffect = voucherBaseEffect;
    }

    public String getVoucherUpgradeEffect()
    {
        return voucherUpgradeEffect;
    }

    public void setVoucherUpgradeEffect(String voucherUpgradeEffect) {
        this.voucherUpgradeEffect = voucherUpgradeEffect;
    }

    public String getVoucherUpgradeUnlockCondition()
    {
        return voucherUpgradeUnlockCondition;
    }

    public void setVoucherUpgradeUnlockCondition(String voucherUpgradeUnlockCondition) {
        this.voucherUpgradeUnlockCondition = voucherUpgradeUnlockCondition;
    }

    public String getVoucherNotes()
    {
        return voucherNotes;
    }

    public void setVoucherNotes(String voucherNotes)
    {
        this.voucherNotes = voucherNotes;
    }
}
