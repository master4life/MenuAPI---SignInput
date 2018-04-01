package de.kiyan.SkyPrisonLibrary.Utils;

import de.kiyan.SkyPrisonLibrary.Main;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class sendToBungeeCord
{
    public void sendToBungeeCord( Player player, String sub )
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream( );
        DataOutputStream out = new DataOutputStream( b );
        try
        {
            out.writeUTF( "SkyPrisonCore" );
            out.writeUTF( sub );
        } catch( IOException e )
        {
            e.printStackTrace( );
        }

        player.sendPluginMessage( Main.getPlugin( Main.class ), "SkyPrisonCore", b.toByteArray( ) );
    }
}
