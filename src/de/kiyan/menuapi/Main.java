package de.kiyan.menuapi;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import de.kiyan.menuapi.ChatMenuAPI.menu.ChatMenuAPI;
import de.kiyan.menuapi.Commands.CMDAnvil;
import de.kiyan.menuapi.Commands.CMDChat;
import de.kiyan.menuapi.Commands.CMDMenu;
import de.kiyan.menuapi.Commands.CMDMultiColor;
import de.kiyan.menuapi.MenuAPI.MenuAPI;
import de.kiyan.menuapi.Utils.SignChangeDetector;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
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

        // Initiate ChatMenuAPI
        ChatMenuAPI.init(this);

        // Registering CommandExecutor on different class.

        this.getCommand( "test" ).setExecutor( new CMDMenu( ) );
        this.getCommand( "test2" ).setExecutor( new CMDChat( ) );
        this.getCommand( "test3" ).setExecutor( new CMDMultiColor( ) );
        this.getCommand( "test4" ).setExecutor( new CMDAnvil( ) );

        // Register Listener for MenuAPI.

        Bukkit.getPluginManager( ).registerEvents( this, this );
        Bukkit.getPluginManager( ).registerEvents( new MenuAPI(), this );
        Bukkit.getPluginManager( ).registerEvents( new CMDMenu( ), this );

        ProtocolManager manager = ProtocolLibrary.getProtocolManager( );

        // Registering Packet Listener for ProtocolLib.
        if( !manager.getPacketListeners( ).contains( new SignChangeDetector( this, ListenerPriority.NORMAL ) ) )
            manager.addPacketListener( new SignChangeDetector( this, ListenerPriority.NORMAL ) );

    }

    public void onDisable()
    {
        // Unload ChatMenuAPI
        ChatMenuAPI.disable();
    }
}
