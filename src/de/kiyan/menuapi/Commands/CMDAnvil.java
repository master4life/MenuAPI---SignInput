package de.kiyan.menuapi.Commands;

import de.kiyan.menuapi.AnvilAPI.AnvilGUI;
import de.kiyan.menuapi.Main;
import de.kiyan.menuapi.MenuAPI.InventoryClickType;
import de.kiyan.menuapi.MenuAPI.Menu;
import de.kiyan.menuapi.MenuAPI.MenuAPI;
import de.kiyan.menuapi.MenuAPI.MenuItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMDAnvil implements CommandExecutor
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
        if( !label.equalsIgnoreCase( "test4" ) )
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
                    new AnvilGUI( Main.getInstance, player, "Whats the the meaning of life?", (player2, reply ) -> {

                        if( !reply.isEmpty())
                        {
                            menu.removeMenuItem( 0 );
                            menu.addMenuItem( new MenuItem( reply, new ItemStack( Material.ANVIL ) ) {
                                @Override
                                public void onClick( Player player, InventoryClickType clickType )
                                {

                                }
                            }, 0 );

                            menu.openMenu( player2 );
                        }

                        return "§cPlease type a valid name.";
                    } );

                }
            }
        }, 0 );

        menu.openMenu( player );

        return false;
    }
}
