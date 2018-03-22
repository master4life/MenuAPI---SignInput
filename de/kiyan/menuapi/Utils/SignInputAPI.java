package de.kiyan.menuapi.Utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

public class SignInputAPI
{
    public void openEditor( Player player )
    {
        PacketContainer signUse = new PacketContainer( PacketType.Play.Server.OPEN_SIGN_EDITOR );
        signUse.getBlockPositionModifier( ).write( 0, new BlockPosition( 0, 928, 0 ) );
        try
        {
            ProtocolLibrary.getProtocolManager( ).sendServerPacket( player, signUse );
        } catch( InvocationTargetException e )
        {
            e.printStackTrace( );
        }
    }
}