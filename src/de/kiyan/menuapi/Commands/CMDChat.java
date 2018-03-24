package de.kiyan.menuapi.Commands;

import de.kiyan.menuapi.ChatMenuAPI.menu.ChatMenu;
import de.kiyan.menuapi.ChatMenuAPI.menu.element.*;
import de.kiyan.menuapi.Utils.State;
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

        menu.add( new TextElement( "Kick User:", 30, 13 ) );

        InputElement inElemName = new InputElement( 90, 13, 50, "§o<name>" );
        InputElement inElemReason = new InputElement( 150, 13, 150, "§o<Type a reason>" );
        menu.add( inElemName );
        menu.add( inElemReason );

        menu.add( new NumberSliderElement( 30, 15, 50, 0  ) );

        menu.add( new ButtonElement( 5, 18, "§c[Close]", ( p ) -> {
            menu.close( p );
            return false;
        } ) );

        menu.add( new ButtonElement( 360, 18, "§2[Accept]", ( p ) -> {
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
