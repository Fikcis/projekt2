package pro1.swingComponents;

import pro1.drawingModel.Drawable;
import pro1.drawingModel.Group;
import pro1.drawingModel.Line;
import pro1.drawingModel.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Random;

public class CoinTossingFrame extends JFrame {
    //setup
    private DrawingPanel drawingPanel;
    private Random random = new Random();
    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public CoinTossingFrame()
    {
        //setup pro frame
        setTitle("coin tossing frame");
        setVisible(true);
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        //vytvořit instanci drawingPanel
        drawingPanel = new DrawingPanel(new Rectangle(0,0,0,0, "#FFFFFF"));
        add(drawingPanel);

        //vytvořit levý panel
        JPanel leftPanel = new JPanel(); //prázdný šedý panel
        leftPanel.setPreferredSize(new Dimension(200, 0));
        add(leftPanel, BorderLayout.WEST);

        //vytvořit obsah levého panelu
        JFormattedTextField coinTossesTextField = new JFormattedTextField(numberFormat);
        coinTossesTextField.setValue(10);
        coinTossesTextField.setPreferredSize(new Dimension(180,40));
        coinTossesTextField.setFont(new Font("Arial", Font.PLAIN, 36));
        leftPanel.add(coinTossesTextField, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(140, 30));
        startButton.setFont(new Font("Arial", Font.PLAIN, 26));
        leftPanel.add(startButton);

        /*
        kontrola, že obsah je vygenerovaný správně (bez této části vykreslí
            obsah panelu správně jenom občas, při provedení kódu)
        */
        leftPanel.revalidate(); //zkontroluje co za komponenty (tlačítko, textové pole, ...) existují v panelu
        leftPanel.repaint(); //vykreslit panel znovu

        //přidat akci (lambda) k tlačítku "startButton"
        startButton.addActionListener((ActionEvent e) -> {
            //setup pro házení mince
            double numOfRedLanding = 0; //"numOfBlueLanding" není, jelikož lze dopočítat a nebylo by tolik používáno
            double percentageOfRedLanding = 0;
            double percentageOfBlueLanding = 0;
            double numberOfCoinTosses = Double.parseDouble(coinTossesTextField.getValue().toString());
            /*
            kontrola vstupu od uživatele ( 1) číslo není desetiné, 2) číslo není záporné nebo 0,
            3) číslo není větší než limit
             */
            if(Math.floor(numberOfCoinTosses) != numberOfCoinTosses ||
                    numberOfCoinTosses < 1 ||
                    numberOfCoinTosses > 999)
                return;

            //setup pro vykreslení trouhelníků (hlavně pro přehled a jednoduší přepsání) (hodnoty v pixelech)
            int xStartingPointOfTheGraph = 200;
            int yStartingPointOfTheGraph = 750;
            double rectangleMaxHeight = 400.0;
            int rectangleWidth = 100;
            int gapBetweenRectangles = 100;

            //setup pro čáry (jen pro přehled a jednoduší přepsání) (hodnoty v pixelech)
            int lineThickness = 2;
            int gapBetweenLinesAndRectangles = 2;
            int shiftedStartingPoint = 20;

            //provést hody mincí
            for(int i = 0; i < numberOfCoinTosses; i++)
            {
                if(random.nextInt(2) == 1)
                    numOfRedLanding++;
            }

            //převést počet dopadů na procenta
            percentageOfRedLanding = numOfRedLanding / (numberOfCoinTosses / 100);
            percentageOfBlueLanding = 100.0 - percentageOfRedLanding;

            /*
            kontrola, jestli v procentech jsou desetiná čísla, jestli ano, tak vyřešit které ze dvou čísel je za
             desetinou čárkou větší a větší číslo pak zvětšit o 1 a nakonec zaokrouhlit.
             (například: 75.7 a 24.3 -> (zjistíme jestli jsou čísla desetiná) 75.0(zaokrouhleno) != 75.7 ->
              (porovnáme) 0.7 > 0.3 -> (zvětšíme větší číslo) (75.7 + 1) a 24.3 -> (zaokrouhlíme) 76 a 24)
             */
            if(Math.floor(percentageOfRedLanding) != percentageOfRedLanding) {
                if (percentageOfRedLanding - Math.floor(percentageOfRedLanding) >
                        percentageOfBlueLanding - Math.floor(percentageOfBlueLanding))
                    percentageOfRedLanding++;
                else
                    percentageOfBlueLanding++;

                percentageOfRedLanding = Math.floor(percentageOfRedLanding);
                percentageOfBlueLanding = Math.floor(percentageOfBlueLanding);
            }

            //vytvořit skupinu pro graf, do které jsou hned vložený jednotlivé objekty grafu
            Group graph = new Group( new Drawable[]{
                    //nakreslit čáry
                        //vertikální čára
                    new Line(
                            xStartingPointOfTheGraph -  gapBetweenLinesAndRectangles,
                            yStartingPointOfTheGraph + shiftedStartingPoint,
                            xStartingPointOfTheGraph - gapBetweenLinesAndRectangles,
                            yStartingPointOfTheGraph - (int)(rectangleMaxHeight), /*kde končí čára*/
                            lineThickness,
                            "#000000"),
                        //horizontální čára
                    new Line(
                            xStartingPointOfTheGraph - shiftedStartingPoint,
                            yStartingPointOfTheGraph + gapBetweenLinesAndRectangles,
                            xStartingPointOfTheGraph + 2 * rectangleWidth + gapBetweenRectangles, /*kde končí čára*/
                            yStartingPointOfTheGraph + gapBetweenLinesAndRectangles,
                            lineThickness,
                            "#000000"),
                    //nakrelit obdelníky
                        //červený obdelník
                    new Rectangle(
                            xStartingPointOfTheGraph,
                            yStartingPointOfTheGraph - ((int)(percentageOfRedLanding * (rectangleMaxHeight / 100))),
                            rectangleWidth,
                            (int)(percentageOfRedLanding * (rectangleMaxHeight / 100)),
                            "#FF0000",
                            (int)(percentageOfRedLanding)),
                        //modrý obdelník
                    new Rectangle(
                            xStartingPointOfTheGraph + rectangleWidth + gapBetweenRectangles,
                            yStartingPointOfTheGraph - ((int)(percentageOfBlueLanding * (rectangleMaxHeight / 100))),
                            rectangleWidth,
                            (int)(percentageOfBlueLanding * (rectangleMaxHeight / 100)),
                            "#0000FF",
                            (int)(percentageOfBlueLanding))
            });

            //nakteslit graf
            drawingPanel.setImage(graph);
        });
    }

}
