package pro1.drawingModel;

import java.awt.*;

public class Rectangle implements Drawable {
    private int x;
    private int y;
    private int width;
    private int height;
    private String color;
    private int graphPercentage = -1;

    public Rectangle(int x, int y, int width, int height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Rectangle(int x, int y, int width, int height, String color,int graphPercentage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.graphPercentage = graphPercentage;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.decode(color));
        g.fillRect(x, y, width, height);

        //pokud hodnota graphPercentage není mimo interval 0 až 100, tak napsat k trouhelníku hodnotu
        if(graphPercentage > -1 && graphPercentage < 101) {
            Font defaultFont = g.getFont();

            g.setFont(new Font("Arial", Font.PLAIN, 46));
            g.drawString(Integer.toString(graphPercentage) + "%", x, y);

            g.setFont(defaultFont);
        }
    }
}
