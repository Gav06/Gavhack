package net.minecraft.entity.boss;

public final class BossStatus
{
    public static float healthScale;
    public static int statusBarTime;
    public static String bossName;
    public static boolean hasColorModifier;

    public static void setBossStatus(IBossDisplayData par0IBossDisplayData, boolean par1)
    {
        healthScale = par0IBossDisplayData.getHealth() / par0IBossDisplayData.getMaxHealth();
        statusBarTime = 100;
        bossName = par0IBossDisplayData.func_145748_c_().getFormattedText();
        hasColorModifier = par1;
    }
}
