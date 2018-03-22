package de.kiyan.menuapi;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import de.kiyan.menuapi.Commands.CMDMenu;
import de.kiyan.menuapi.Utils.MenuGUI;
import de.kiyan.menuapi.Utils.SignChangeDetector;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    // Main class is needed.
    public static Main getInstance;

    @Override
    public void onEnable( )
    {
        // Saving main instance to 'getInstance'
        getInstance = this;

        // Registering CommandExecutor on different class.
        this.getCommand( "test" ).setExecutor( new CMDMenu( ) );

        // Register Listener for MenuAPI.
        Bukkit.getPluginManager( ).registerEvents( this, this );
        Bukkit.getPluginManager( ).registerEvents( new CMDMenu(), this );

        ProtocolManager manager = ProtocolLibrary.getProtocolManager( );

        // Registering Packet Listener for ProtocolLib.
        if( !manager.getPacketListeners( ).contains( new SignChangeDetector( this, ListenerPriority.NORMAL ) ) )
            manager.addPacketListener( new SignChangeDetector( this, ListenerPriority.NORMAL ) );

    }

    /*
    * Those two listeners are required to fetch menu behavior.
    *
    * Not necessary to be on main class, can also relocated to another class ( but has to be registered ! )
    *
    * !! Do not touch this code !! Only copy & paste it for relocation.
    *
     */
    @EventHandler
    public void onInventoryClose( InventoryCloseEvent e )
    {
       MenuGUI menu = MenuGUI.checkForMenuClose( this, e ); // Assuming that 'this' is a JavaPlugin object, your main class instance
    }

    @EventHandler
    public void onInventoryClick( InventoryClickEvent e )
    {
        MenuGUI menu = MenuGUI.checkForMenuClick( this, e, true ); // Assuming that 'this' is a JavaPlugin object, your main class instance
        if( menu != null )
        {
            e.setCancelled( true );
        }
    }
}
