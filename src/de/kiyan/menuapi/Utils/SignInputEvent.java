package de.kiyan.menuapi.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class SignInputEvent extends Event
{
    private static final HandlerList handlers = new HandlerList( );
    private String[] input;
    private Player player;

    public static HandlerList getHandlerList( )
    {
        return handlers;
    }

    public HandlerList getHandlers( )
    {
        return handlers;
    }

    public SignInputEvent( String[] lines, Player player )
    {
        this.input = lines;
        this.player = player;
    }

    public Player getPlayer( )
    {
        return this.player;
    }

    public String getLine( int num )
    {
        return this.input[ 0 ];
    }

    public String[] getLines( )
    {
        return this.input;
    }
}
