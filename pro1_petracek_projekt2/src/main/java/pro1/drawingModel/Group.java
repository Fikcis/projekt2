package pro1.drawingModel;

import java.awt.*;

public class Group implements Drawable
{
    private Drawable[] items;

    public Group(Drawable[] items) {
        this.items = items;
    }

    @Override
    public void draw(Graphics2D g) {
        for(Drawable item : items)
        {
            item.draw(g);
        }
    }
}
