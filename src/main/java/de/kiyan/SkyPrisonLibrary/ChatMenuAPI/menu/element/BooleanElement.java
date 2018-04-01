package de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.element;

import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.ChatMenuAPI;
import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.IElementContainer;
import de.kiyan.menuapi.Utils.State;
import de.kiyan.menuapi.Utils.Text;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A boolean element. Basically a checkbox (without the box).
 */
public class BooleanElement extends Element
{
    @Nonnull
    public final State< Boolean > value;

    @Nonnull
    protected ChatColor trueColor = ChatColor.GREEN;
    @Nonnull
    protected ChatColor falseColor = ChatColor.RED;

    protected boolean showText = false;

    /**
     * Constructs a {@code BooleanElement}
     *
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param value the starting value
     */
    public BooleanElement( int x, int y, boolean value )
    {
        super( x, y );
        this.value = new State<>( value );
    }

    /**
     * Shows the "true/false" text after the symbol.
     *
     * @return this
     */
    @Nonnull
    public BooleanElement showText( )
    {
        setShowText( true );
        return this;
    }

    /**
     * @param showText whether or not to show the "true/false" text
     */
    public void setShowText( boolean showText )
    {
        this.showText = showText;
    }

    /**
     * @param trueColor  The color the symbol should be if the value is {@code true}
     * @param falseColor The color the symbol should be if the value is {@code false}
     * @return this
     */
    @Nonnull
    public BooleanElement colors( @Nonnull ChatColor trueColor, @Nonnull ChatColor falseColor )
    {
        setTrueColor( trueColor );
        setFalseColor( falseColor );
        return this;
    }

    /**
     * @return the color the text will be if the value is {@code false}
     */
    @Nonnull
    public ChatColor getFalseColor( )
    {
        return falseColor;
    }

    /**
     * @param falseColor the color the symbol should be if the value is {@code false}
     */
    public void setFalseColor( @Nullable ChatColor falseColor )
    {
        this.falseColor = falseColor == null ? ChatColor.RED : falseColor;
    }

    /**
     * @return the color the text will be if the value is {@code true}
     */
    @Nonnull
    public ChatColor getTrueColor( )
    {
        return trueColor;
    }

    /**
     * @param trueColor The color the symbol should be if the value is {@code true}
     */
    public void setTrueColor( @Nullable ChatColor trueColor )
    {
        this.trueColor = trueColor == null ? ChatColor.GREEN : trueColor;
    }

    public int getWidth( )
    {
        return 8 + ( showText ? ChatMenuAPI.getWidth( " " + value.getCurrent( ) ) : 0 );
    }

    public int getHeight( )
    {
        return 1;
    }

    @Nonnull
    public List< Text > render( @Nonnull IElementContainer context )
    {
        String baseCommand = context.getCommand( this );

        List< BaseComponent > components = new ArrayList<>( );
        boolean current = value.getOptionalCurrent( ).orElse( false );
        TextComponent c = new TextComponent( current ? "\u2714" : "\u2718" );
        c.setColor( current ? trueColor : falseColor );
        c.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, baseCommand + !current ) );
        components.add( c );

        if( showText )
            components.add( new TextComponent( " " + current ) );

        return Collections.singletonList( new Text( components ) );
    }

    public void edit( @Nonnull IElementContainer container, @Nonnull String[] args )
    {
        value.setCurrent( Boolean.parseBoolean( args[ 0 ] ) );
    }

    /**
     * @return the current value
     */
    public boolean getValue( )
    {
        return value.getOptionalCurrent( ).orElse( false );
    }

    /**
     * @param value the new value
     */
    public void setValue( boolean value )
    {
        this.value.setCurrent( value );
    }

    @Nonnull
    public List< State< ? > > getStates( )
    {
        return Collections.singletonList( value );
    }
}
