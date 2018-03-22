package de.kiyan.menuapi.Utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SignChangeDetector extends PacketAdapter
{
    String packageName = Bukkit.getServer( ).getClass( ).getPackage( ).getName( );
    int vID = Integer.parseInt( this.packageName.split( "_" )[ 1 ] );

    public SignChangeDetector( Plugin pl, ListenerPriority priority )
    {
        super( pl, priority, new PacketType[]{ PacketType.Play.Client.UPDATE_SIGN } );
    }

    public void onPacketReceiving( PacketEvent event )
    {
        Player who = event.getPlayer( );
        if( ( ( BlockPosition ) event.getPacket( ).getBlockPositionModifier( ).read( 0 ) ).equals( new BlockPosition( 0, 928, 0 ) ) )
        {
            String[] signLine;
            if( this.vID < 9 )
            {
                WrappedChatComponent[] text = ( WrappedChatComponent[] ) event.getPacket( ).getChatComponentArrays( ).read( 0 );

                String[] lines = { text[ 0 ].toString( ).split( "\"" )[ 1 ], text[ 1 ].toString( ).split( "\"" )[ 1 ], text[ 2 ].toString( ).split( "\"" )[ 1 ], text[ 3 ].toString( ).split( "\"" )[ 1 ] };

                signLine = lines;
            } else
            {
                signLine = ( String[] ) event.getPacket( ).getStringArrays( ).read( 0 );
            }
            Bukkit.getPluginManager( ).callEvent( new SignInputEvent( signLine, who ) );
        }
    }
}
