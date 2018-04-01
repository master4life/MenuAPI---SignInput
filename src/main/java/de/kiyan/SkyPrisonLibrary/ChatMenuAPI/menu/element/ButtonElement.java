package de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.element;

import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.ChatMenu;
import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.ChatMenuAPI;
import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.IElementContainer;
import de.kiyan.menuapi.Utils.Text;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A button that runs a callback when clicked.
 */
public class ButtonElement extends Element
{
    public static ButtonElement createCloseButton( int x, int y, @Nonnull String text, @Nonnull ChatMenu menu )
    {
        return new ButtonElement( x, y, text, ( p ) -> {
            menu.close( p );
            return false;
        } );
    }

    @Nonnull
    protected String text;
    @Nullable
    protected Function< Player, Boolean > callback;

    /**
     * Construct a {@code ButtonElement} with no callback.
     *
     * @param x    the x coordinate
     * @param y    the y coordinate
     * @param text the text
     */
    public ButtonElement( int x, int y, @Nonnull String text )
    {
        this( x, y, text, ( Function< Player, Boolean > ) null );
    }

    /**
     * Constructs a {@code ButtonElement} with the provided callback. Will always resend the menu after the button is clicked.
     *
     * @param x        the x coordinate
     * @param y        the y coordinate
     * @param text     the text
     * @param callback the callback to be called when the button is clicked.
     */
    public ButtonElement( int x, int y, @Nonnull String text, @Nullable Consumer< Player > callback )
    {
        this( x, y, text, player -> {
            if( callback != null )
                callback.accept( player );
            return true;
        } );
    }

    /**
     * Constructs a {@code ButtonElement} with the provided callback.
     *
     * @param x        the x coordinate
     * @param y        the y coordinate
     * @param text     the text
     * @param callback the callback to be called when the button is clicked. Should return {@code true} to automatically resend the menu.
     */
    public ButtonElement( int x, int y, @Nonnull String text, @Nullable Function< Player, Boolean > callback )
    {
        super( x, y );
        if( text.contains( "\n" ) )
            throw new IllegalArgumentException( "Button text cannot contain newline" );
        this.text = text;
        this.callback = callback;
    }

    /**
     * @return the text this button displays
     */
    @Nonnull
    public String getText( )
    {
        return text;
    }

    /**
     * @param text the new text this button should display
     * @throws IllegalArgumentException if text contains a newline.
     */
    public void setText( @Nonnull String text )
    {
        if( text.contains( "\n" ) )
            throw new IllegalArgumentException( "Button text cannot contain newline" );
        this.text = text;
    }

    public int getWidth( )
    {
        return ChatMenuAPI.getWidth( text );
    }

    public int getHeight( )
    {
        return 1;
    }

    public boolean isEnabled( )
    {
        return true;
    }

    @Nonnull
    public List< Text > render( @Nonnull IElementContainer context )
    {
        String baseCommand = context.getCommand( this );

        BaseComponent[] components = TextComponent.fromLegacyText( text );
        ClickEvent click = new ClickEvent( ClickEvent.Action.RUN_COMMAND, baseCommand );
        for( BaseComponent component : components )
            component.setClickEvent( click );

        return Collections.singletonList( new Text( components ) );
    }

    public boolean onClick( @Nonnull IElementContainer container, @Nonnull Player player )
    {
        super.onClick( container, player );
        return callback == null ? false : callback.apply( player );
    }

    public void edit( @Nonnull IElementContainer container, @Nonnull String[] args )
    {
    }
}
