package de.kiyan.menuapi.Commands;

import de.kiyan.menuapi.Main;
import de.kiyan.menuapi.MenuAPI.InventoryClickType;
import de.kiyan.menuapi.MenuAPI.Menu;
import de.kiyan.menuapi.MenuAPI.MenuAPI;
import de.kiyan.menuapi.MenuAPI.MenuItem;
import de.kiyan.menuapi.SignAPI.SignMenu;
import de.kiyan.menuapi.TitlesAPI.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CMDTitles implements CommandExecutor
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
        if( !label.equalsIgnoreCase( "test5" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;
        Menu menu = new MenuAPI( ).createMenu( "§1T§2i§3t§4l§5e§6s§b API Test", 1 );

        menu.addMenuItem( new MenuItem( "§cAppearingTitle", new ItemStack( Material.SIGN ) )
        {
            @Override
            public void onClick( Player player, InventoryClickType clickType )
            {
                AppearingTitle Apptitle = new AppearingTitle( TitleSlot.TITLE_SUBTITLE, ChatColor.GOLD + "Magic Titles", ChatColor.RED + "It's AWESOME!", 30 );
                Apptitle.send( player );

                menu.closeMenu( player );
            }
        }, 0 );

        menu.addMenuItem( new MenuItem( "§aBlinkTitle", new ItemStack( Material.SIGN ) )
        {
            @Override
            public void onClick( Player player, InventoryClickType clickType )
            {
                new SignMenu( Main.getInstance ).open( player, new String[]{"&o&l<text>", "", "", ""}, ( p, text) ->
                {
                    BlinkTitle blinkTitle = new BlinkTitle( TitleSlot.TITLE_SUBTITLE, ChatColor.GOLD + Arrays.toString( text ).replace( ",", "" ).replace( "[", "" ).replace( "]", "" ).trim( ), "test", 20, false );
                    blinkTitle.send( player );

                    menu.closeMenu( player );
                });
            }
        }, 1 );

        menu.addMenuItem( new MenuItem( "§2MagicTitle", new ItemStack( Material.SIGN ), 20 )
        {
            @Override
            public void onClick( Player player, InventoryClickType clickType )
            {
                MagicTitle magicTitle = new MagicTitle( TitleSlot.TITLE_SUBTITLE, ChatColor.GOLD + "Magic Titles", 20, 30 );
                magicTitle.send( player );

                menu.closeMenu( player );
            }
        }, 2 );

        menu.addMenuItem( new MenuItem( "§2NormalTitle", new ItemStack( Material.SIGN ) )
        {
            @Override
            public void onClick( Player player, InventoryClickType clickType )
            {
                NormalTitle normalTitle = new NormalTitle( TitleSlot.TITLE_SUBTITLE, ChatColor.GOLD + "Magic Titles", "Test" );
                normalTitle.send( player );

                menu.closeMenu( player );
            }
        }, 3 );

        menu.openMenu( player );

        return false;
    }

}
