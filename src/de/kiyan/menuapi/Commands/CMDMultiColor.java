package de.kiyan.menuapi.Commands;

import de.kiyan.menuapi.ChatMenuAPI.menu.ChatMenu;
import de.kiyan.menuapi.ChatMenuAPI.menu.IElementContainer;
import de.kiyan.menuapi.ChatMenuAPI.menu.element.ButtonElement;
import de.kiyan.menuapi.ChatMenuAPI.menu.element.Element;
import de.kiyan.menuapi.ChatMenuAPI.menu.element.InputElement;
import de.kiyan.menuapi.ChatMenuAPI.menu.element.TextElement;
import de.kiyan.menuapi.Utils.State;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CMDMultiColor implements CommandExecutor
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
        if( !label.equalsIgnoreCase( "test3" ) )
        {
            return false;
        }

        Player player = ( Player ) sender;

        // Tutorial how to use ChatMenuAPI on https://github.com/timtomtim7/ChatMenuAPI#setup

        ChatMenu menu = new ChatMenu( ).pauseChat( );

        menu.add( new TextElement( "Your name:", 11, 11 ) );

        InputElement inElemName = new InputElement( 70, 11, 100, player.getDisplayName( ).replace( "§", "&" ).replace( "&r", "" ) );
        menu.add( inElemName );

        TextElement preview = new TextElement( "Preview: " + inElemName.getValue( ).replace( "&", "§" ), 11, 15 );
        menu.add( preview );

        menu.add( new TextElement( "Colorcode: §0&0 §1&b §2&2 §3&3 §4&4 §5&5 §6&6 §7&7 §8&8 §9&9 §a&a §b&b §c&c §d&d §e&e §f&f", 11, 13 ) );
        inElemName.value.setChangeCallback( ( s ) ->
        {
            System.out.println( "inElemName changed! " + s.getPrevious( ) + " -> " + s.getCurrent( ) );

            preview.setText( "Preview: " + s.getCurrent( ).replace( "&", "§" ) );
        } );


        menu.add( new ButtonElement( 5, 18, "§c[Close]", ( p ) -> {
            menu.close( p );
            return false;
        } ) );

        menu.openFor( player );
        return false;
    }
}
