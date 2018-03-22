package de.kiyan.menuapi.Utils;

import java.util.ArrayList;
import java.util.List;

import de.kiyan.menuapi.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A nice util to create menus.
 * <i>If you're planning on using this in your resource, give me some credit, and <b>leave this notice</b>!</i>
 * Liked it? Tell me! Didn't like it? Tell me! Got an idea to improve? Tell me! Contact me via spigot @ NonameSL.
 *
 * @author NonameSL
 * @version 1.4.1
 */
public abstract class MenuGUI implements Listener
{
    /**
     * Should be called on InventoryClickEvent, is required for the onClick method to work.
     *
     * @param e           The event called.
     * @param plugin      The plugin owning the menus.
     * @param cancelShift Wether or not to cancel shift clicks from bottom inventory.
     * @return The menu recognized, null if not a menugui that was clicked.
     */
    public static MenuGUI checkForMenuClick( JavaPlugin plugin, InventoryClickEvent e, boolean cancelShift )
    {
        if( e == null )
            return null;
        if( e.getClickedInventory( ) == null )
            return null;
        for( MenuGUI gui : guis )
        {
            if( gui.main.getDescription( ).getName( ).equals( plugin.getDescription( ).getName( ) ) )
            {
                if( gui.inv.getType( ) == e.getClickedInventory( ).getType( ) && gui.inv.getTitle( ).equals( e.getClickedInventory( ).getTitle( ) ) && gui.inv.getViewers( ).equals( e.getClickedInventory( ).getViewers( ) ) )
                {
                    gui.onClick( e );
                    return gui;
                }
                if( cancelShift && e.getClick( ).name( ).contains( "SHIFT" )/*Backwardss compability*/ && gui.inv.getType( ) == e.getView( ).getTopInventory( ).getType( ) && gui.inv.getTitle( ).equals( e.getView( ).getTopInventory( ).getTitle( ) ) && gui.inv.getViewers( ).equals( e.getView( ).getTopInventory( ).getViewers( ) ) )
                {
                    e.setCancelled( true );
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Should be called on InventoryCloseEvent, is required for the onClose method to work.
     *
     * @param e      The event called.
     *
     * @return The menu recognized, null if not a menugui that was closed.
     */
    public static MenuGUI checkForMenuClose( Main main, InventoryCloseEvent e )
    {
        if( e == null )
            return null;
        if( e.getInventory( ) == null )
            return null;//Just in case. Why not?
        for( MenuGUI gui : guis )
        {
            if( gui.main.getDescription( ).getName( ).equals( main.getDescription( ).getName( ) ) && gui.inv.getType( ) == e.getInventory( ).getType( ) && gui.inv.getTitle( ).equals( e.getInventory( ).getTitle( ) ) && gui.inv.getViewers( ).equals( e.getInventory( ).getViewers( ) ) )
            {
                gui.onClose( e );
                return gui;
            }
        }
        return null;
    }

    public static final ArrayList< MenuGUI > guis = new ArrayList<>( );
    private String name;
    private int size;
    public JavaPlugin main;
    private Inventory inv;

    /**
     * Create a new MenuGUI.
     *
     * @param name   The title of the menu.
     * @param size   The size of the menu (Valid options are 9, 18, 27, 36, 45, 54)
     */
    public MenuGUI( String name, int size, JavaPlugin main )
    {
        this.name = name;
        this.size = size;
        this.main = main;
        this.inv = Bukkit.createInventory( null, size, name );
        guis.add( this );
    }

    /**
     * Action performed on menu close.
     *
     * @param e The close event.
     */

    @EventHandler
    public abstract void onClose( InventoryCloseEvent e );

    /**
     * Action performed on menu click.
     *
     * @param e The click event.
     */
    @EventHandler
    public abstract void onClick( InventoryClickEvent e );

    /**
     * Get the inventory the MenuGUI represents.
     *
     * @return The inventory
     */
    public Inventory getInventory( )
    {
        return inv;
    }

    /**
     * Set the title and recreate the inventory.
     *
     * @param title The new title.
     */
    public void setTitle( String title )
    {
        this.name = title;
        recreateInventory( );
    }

    /**
     * Recreate the inventory.
     */
    public void recreateInventory( )
    {
        this.inv = Bukkit.createInventory( null, size, name );
    }

    /**
     * Chained builder to add an item to the gui to the next available spot.
     *
     * @param is The item to add
     */
    public MenuGUI addOption( ItemStack is )
    {
        addOption( is, -1 );
        return this;
    }

    /**
     * Chained builder to add an item to the gui at a fixed position.
     *
     * @param is       The item to add
     * @param position The position where to add it (0 - size)
     */
    public MenuGUI addOption( ItemStack is, int position )
    {
        if( Math.floor( position / 9 ) > 5 )
            return this;
        if( position < 0 )
        {
            inv.addItem( is );
        } else
            inv.setItem( position, is );
        return this;
    }

    /**
     * Show the menu to a player.
     *
     * @param player The player to show it to.
     */
    public void show( Player player )
    {
        player.openInventory( inv );
    }

    /**
     * Show the menu to some players.
     *
     * @param p The players to show it to
     */
    public void show( Player... p )
    {
        for( Player player : p )
            show( player );
    }

    /**
     * Get the menu size.
     *
     * @return The menu size.
     */
    public int getSize( )
    {
        return size;
    }

    /**
     * Evict all current viewers with no warning message.
     */
    public List< Player > ecivtViewers( )
    {
        return evictViewers( null );
    }

    /**
     * Evict all current viewers.
     *
     * @param msg The message to display to those who you kicked.
     */
    public List< Player > evictViewers( String msg )
    {
        List< Player > viewers = new ArrayList<>( );
        for( HumanEntity entity : inv.getViewers( ) )
        {
            entity.closeInventory( );
            if( msg != null && entity instanceof Player )
            {
                ( ( Player ) entity ).sendMessage( msg );
                viewers.add( ( ( Player ) entity ) );
            }
        }
        return viewers;
    }

    /**
     * The plugin owning the menu.
     *
     * @return The plugin.
     */
    public JavaPlugin getPlugin( )
    {
        return main;
    }
}
