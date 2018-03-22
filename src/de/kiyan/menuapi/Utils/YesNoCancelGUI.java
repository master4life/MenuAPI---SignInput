package de.kiyan.menuapi.Utils;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A simple abstract GUI to let user choose between three options - YES, NO or CANCEL.
 * Requires MenuGUI and ItemBuilder to use.
 * <i>If you don't want ItemBuilder, change all the itembuilder constructors into normal itemstack creating.</i>
 * <u>ItemBuilder link:</u> <a href="https://www.spigotmc.org/threads/.48397/">"https://www.spigotmc.org/threads/.48397/"</a>
 *
 * @author NonameSLdev
 */
public abstract class YesNoCancelGUI extends MenuGUI
{
    public static enum Response
    {
        YES, NO, CANCEL;
    }

    private ItemStack noItem = new ItemBuilder( Material.STAINED_GLASS_PANE ).setName( ChatColor.RED + "" + ChatColor.BOLD + "No" ).setDyeColor( DyeColor.RED ).toItemStack( ), cancelItem = new ItemBuilder( Material.STAINED_GLASS_PANE ).setName( ChatColor.WHITE + "" + ChatColor.BOLD + "Cancel" ).toItemStack( ), yesItem = new ItemBuilder( Material.STAINED_GLASS_PANE ).setName( ChatColor.GREEN + "" + ChatColor.BOLD + "Yes" ).setDyeColor( DyeColor.GREEN ).toItemStack( );

    public YesNoCancelGUI( String title, JavaPlugin main )
    {
        super( title, 9 * 3, main );
        init( );
    }

    public void setCancelItem( ItemStack is )
    {
        this.cancelItem = is;
    }

    public void setNoItem( ItemStack is )
    {
        this.noItem = is;
    }

    public void setYesItem( ItemStack is )
    {
        this.yesItem = is;
    }

    private void init( )
    {
        addOption( yesItem );
        addOption( yesItem, 9 );
        addOption( yesItem, 18 );

        addOption( cancelItem, 4 );
        addOption( cancelItem, 13 );
        addOption( cancelItem, 22 );

        addOption( noItem, 8 );
        addOption( noItem, 17 );
        addOption( noItem, 26 );
        int i = 0;
        while( getInventory( ).firstEmpty( ) != -1 )
        {
            addOption( new ItemBuilder( Material.STAINED_GLASS_PANE ).setDyeColor( DyeColor.GRAY ).setName( ChatColor.values( )[ i ] + "" ).toItemStack( ) );
            i++;
        }
    }

    @Override
    public void onClose( InventoryCloseEvent e ) {}

    @Override
    public void onClick( InventoryClickEvent e )
    {
        e.setCancelled( true );
        ItemStack is = e.getCurrentItem( );
        if( is == null || !is.hasItemMeta( ) || !is.getItemMeta( ).hasDisplayName( ) )
            return;
        String disp = is.getItemMeta( ).getDisplayName( );
        if( disp.equals( cancelItem.getItemMeta( ).getDisplayName( ) ) )
        {
            onFinish( Response.CANCEL );
        } else if( disp.equals( yesItem.getItemMeta( ).getDisplayName( ) ) )
        {
            onFinish( Response.YES );
        } else if( disp.equals( noItem.getItemMeta( ).getDisplayName( ) ) )
        {
            onFinish( Response.NO );
        }
    }

    public abstract void onFinish( Response response );
}
