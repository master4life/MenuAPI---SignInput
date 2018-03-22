package de.kiyan.menuapi.Commands;

import de.kiyan.menuapi.Main;
import de.kiyan.menuapi.Utils.*;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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

        // Being sure no other commands are being used.
        if( !label.equalsIgnoreCase( "test" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;

        /*
        * YesNoGUI is the confirmation menu. This can be used to double check the seriousness about the users choice.
        * @param title - Define your custom chest name
        * @param plugin - return your main instance to get it working.
        *
        * onFinish. Represent the user choice.
        *           Response.YES == When he clicked on 'Yes' and initiate the code below.
        *           and
        *           REsponse.NO == When he clicked on 'No'
        *
        * Optional: You can also override onClose( ) & onClick( ) to change behavior ( but not needed ).
        *
         */
        YesNoGUI menu2 = new YesNoGUI( "§2§lAre you sure?", Main.getInstance ) {
            @Override
            public void onFinish( Response response )
            {
                if( response == Response.YES )
                {
                    // Initiate your sign prompt.
                    SignInputAPI Sign = new SignInputAPI( );

                    Sign.openEditor( player );
                }

                if( response == Response.NO )
                {
                    player.sendMessage( "You clicked no!" );
                }
            }
        };

        /*
         * MenuGUI is the Core function.
         *
         * @params title - Define your custom chest name
         * @param size - Define your rows ( notice only 9er steps.. 9, 18, 27 )
         * @param plugin - return your main instance to get it working.
         *
         * You can add as much items as you like by simple adding an .addOption to end line
         * .addOption( new ItemStack( Material.DIAMOND ), 2 ).addOption( new ItemStack( Material.GOLD_INGOT ), 3 );
         * or using ItemBuilder makes it easier to customize items.
         *
         * .addOption( new ItemBuilder( Material.DIAMOND ).setName( "§cHerbert" ).setLore( "Fuck", "mah", "Butt" ).toItemStack( ), 2 );
         */
        MenuGUI menu = new MenuGUI( "§cMy Menu", 18, Main.getInstance )
        {
            @Override
            public void onClose( InventoryCloseEvent e ) { }

            @Override
            public void onClick( InventoryClickEvent e )
            {
                Player p = ( Player ) e.getWhoClicked( );

                ItemStack is = e.getCurrentItem( );

                if( is == null )
                    return;
                try
                {
                    if( is.getType() == Material.DIAMOND )
                    {
                        menu2.show( player );
                    }
                } catch( Exception ex )
                {
                    //This means that the item clicked wasnt one of the options we defined.
                }
            }
        }.addOption( new ItemBuilder( Material.DIAMOND ).setName( "§cHerbert" ).toItemStack( ), 2 );

        menu.show( player );

        return false;
    }

    @EventHandler
    public void SignInput( SignInputEvent event )
    {
        Player player = ( Player ) event.getPlayer( );
        String signLines = Arrays.toString( event.getLines( ) ) // Receive typed messages in to 'signLines' and replace all non-needed chars.
                .replace( ",", "" )
                .replace( "[", "" )
                .replace( "]", "" )
                .replace( " ", "" ).trim( );

        player.sendMessage( "Succesful! You just typed | §c" + signLines );
    }
}
