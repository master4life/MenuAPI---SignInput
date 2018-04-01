package de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.element;

import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.ChatMenuAPI;
import de.kiyan.SkyPrisonLibrary.ChatMenuAPI.menu.IElementContainer;
import de.kiyan.SkyPrisonLibrary.Utils.Text;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * A button that opens a link when clicked.
 */
public class LinkButtonElement extends Element
{
    @Nonnull
    protected String text;
    @Nonnull
    protected String link;

    /**
     * Constructs a new {@code LinkButtonElement}
     *
     * @param x    the x coordinate
     * @param y    the y coordinate
     * @param text the text to display
     * @param link the link
     * @throws IllegalArgumentException if text contains newlines
     */
    public LinkButtonElement( int x, int y, @Nonnull String text, @Nonnull String link )
    {
        super( x, y );
        if( text.contains( "\n" ) )
            throw new IllegalArgumentException( "Button text cannot contain newline" );
        this.text = text;
        this.link = link;
    }

    /**
     * @return the text that displays for this button
     */
    @Nonnull
    public String getText( )
    {
        return text;
    }

    /**
     * @param text the new text to display
     */
    public void setText( @Nonnull String text )
    {
        if( text.contains( "\n" ) )
            throw new IllegalArgumentException( "Button text cannot contain newline" );
        this.text = text;
    }

    /**
     * @return the link
     */
    @Nonnull
    public String getLink( )
    {
        return link;
    }

    /**
     * @param link the new link
     */
    public void setLink( @Nonnull String link )
    {
        this.link = link;
    }

    public int getWidth( )
    {
        return ChatMenuAPI.getWidth( text );
    }

    public int getHeight( )
    {
        return 1;
    }

    public List<Text> render(IElementContainer context)
    {
        BaseComponent[] components = TextComponent.fromLegacyText( text );
        ClickEvent click = new ClickEvent( ClickEvent.Action.OPEN_URL, link );
        for( BaseComponent component : components )
            component.setClickEvent( click );

        return Collections.singletonList( new Text( components ) );
    }

    public void edit( @Nonnull IElementContainer container, @Nonnull String[] args )
    {
    }
}
