package pro1.swingComponents;

import pro1.drawingModel.Drawable;

import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel
{
    Drawable image;

    public DrawingPanel(Drawable image) {
        this.image = image;
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        image.draw((Graphics2D) g);
    }



    public void setImage(Drawable image)
    {
        this.image = image;
        repaint();
    }
}
