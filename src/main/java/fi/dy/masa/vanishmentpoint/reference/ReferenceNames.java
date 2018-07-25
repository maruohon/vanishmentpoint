package fi.dy.masa.vanishmentpoint.reference;

public class ReferenceNames
{
    public static final String NAME_BLOCK_VANISHING_BLOCK_TIME = "vanishing_block_time";

    public static String getPrefixedName(String name)
    {
        return Reference.MOD_ID + "_" + name;
    }

    public static String getDotPrefixedName(String name)
    {
        return Reference.MOD_ID + "." + name;
    }
}
