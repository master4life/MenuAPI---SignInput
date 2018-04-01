package de.kiyan.SkyPrisonLibrary.Commands;

import de.kiyan.SkyPrisonLibrary.Main;
import de.kiyan.menuapi.MenuAPI.InventoryClickType;
import de.kiyan.menuapi.MenuAPI.Menu;
import de.kiyan.menuapi.MenuAPI.MenuAPI;
import de.kiyan.menuapi.MenuAPI.MenuItem;
import de.kiyan.menuapi.SignAPI.SignMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CMDMenu implements CommandExecutor, Listener
{
    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args )
    {
        // Excluding non-players.
        if( !( sender instanceof Player ) )
        {
            sender.sendMessage( "§cYou must be a player" );

            return false;
        }

        // Being sure no other commands is being used.
        if( !label.equalsIgnoreCase( "test" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;
        Menu menu = new MenuAPI().createMenu( "Test", 3 );

        menu.addMenuItem( new MenuItem( "test", new ItemStack( Material.ANVIL ) ) {
            @Override
            public void onClick( Player player, InventoryClickType clickType )
            {
                if( clickType.isLeftClick() )
                {
                    new SignMenu( Main.getInstance ).open( player, new String[]{ "§oType", "", "", ""}, (p, text ) -> {
                        p.sendMessage( "You just typed: " + Arrays.toString( text ).replace( ",", "" ).replace( "[", "" ).replace( "]", "" ).trim( ) );

                        menu.updateMenu();
                    } );
                }
            }
        }, 0 );

        menu.openMenu( player );

        return false;
    }

}
