package de.kiyan.SkyPrisonLibrary.Examples;

import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.ChatMenu;
import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.element.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDChat implements CommandExecutor
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
        if( !label.equalsIgnoreCase( "test2" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;

        // Tutorial how to use ChatMenuAPI on https://github.com/timtomtim7/ChatMenuAPI#setup

        ChatMenu menu = new ChatMenu( ).pauseChat( );

        menu.add( new TextElement( "Kick User:", 30, 10 ) );

        InputElement inElemName = new InputElement( 90, 10, 50, "§o<name>" );
        InputElement inElemReason = new InputElement( 150, 10, 150, "§o<Type a reason>" );
        menu.add( inElemName );
        menu.add( inElemReason );

        menu.add( new HorizontalRuleElement( 1, 12, 310 ) );

        menu.add( new NumberSliderElement( 30, 13, 20, 0  ) );

        menu.add( new LinkButtonElement( 10, 15, "§b§n§oURL", "http://www.google.de" ));

        menu.add( new TextElement( "§bTeleport Up?", 230, 15 ) );
        BooleanElement inElemBoolean= new BooleanElement( 300, 15, false ).colors( ChatColor.GREEN, ChatColor.RED );
        menu.add( inElemBoolean );

        inElemBoolean.value.setChangeCallback( ( p ) -> {
            if( p.getCurrent() == true )
                player.teleport( player.getLocation().add( 0, 2, 0 ) );
        } );

        menu.add( new ButtonElement( 5, 19, "§c[Close]", ( p ) -> {
            menu.close( p );
            return false;
        } ) );


        menu.add( new ButtonElement( 360, 19, "§2[Accept]", ( p ) -> {
            Player target = Bukkit.getPlayer( inElemName.getValue( ) );
            if( target != null )
            {
                if( inElemReason.getValue() != "§o<Type a reason>" )
                {
                    target.kickPlayer( inElemReason.getValue() );

                    menu.close( p );
                } else
                    inElemReason.setValue( "§mDont forget a reason!" );

            } else
                inElemName.setValue( "§m§oWRONG!" );
        } ) );

        menu.openFor( player );
        return false;
    }
}
