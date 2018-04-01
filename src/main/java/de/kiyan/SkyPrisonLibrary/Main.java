package de.kiyan.SkyPrisonLibrary;

import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.ChatMenuAPI;
import de.kiyan.SkyPrisonLibrary.MenuAPI.MenuAPI;
import de.kiyan.SkyPrisonLibrary.TitlesAPI.Titles;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    // Main class is needed.
    public static Main getInstance;

    @Override
    public void onEnable( )
    {
        // Saving main instance to 'getInstance'
        getInstance = this;
        String v = Bukkit.getServer( ).getClass( ).getPackage( ).getName( ).split( "\\." )[ 3 ];
        Titles.oldReflection = v.equals( "v1_8_R1" );
        Titles.newReflection = v.contains( "v1_12" );
        Titles.plugin = this;

        // Initiate ChatMenuAPI
        ChatMenuAPI.init( this );

        // Registering CommandExecutor on different class.

//        this.getCommand( "test" ).setExecutor( new CMDMenu( ) );
//        this.getCommand( "test2" ).setExecutor( new CMDChat( ) );
//        this.getCommand( "test3" ).setExecutor( new CMDMultiColor( ) );
//        this.getCommand( "test4" ).setExecutor( new CMDAnvil( ) );
//        this.getCommand( "test5" ).setExecutor( new CMDTitles( ) );

        // Register Listener for MenuAPI.

        Bukkit.getPluginManager( ).registerEvents( new MenuAPI( ), this );
//        Bukkit.getPluginManager( ).registerEvents( new CMDMenu( ), this );

    }

    public void onDisable( )
    {
        // Unload ChatMenuAPI
        ChatMenuAPI.disable( );
    }
}
