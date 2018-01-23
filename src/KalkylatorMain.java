import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KalkylatorMain {

    private JButton ett, tva, tre, fyra, fem, sex, sju, atta, nio, noll, deci, plus, minus, multi, divi, likaMed, rensa;
    private JLabel display;
    private JPanel bakgrund;
    private JButton backa;
    private JLabel historik;
    private JLabel brakform;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Kalkylator");
        frame.setContentPane(new KalkylatorMain().bakgrund);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Action listeners för vad som hände om användaren klickar på en viss knapp på den grafiska kalkylatorn.
    public KalkylatorMain() {

        KalkylatorMetoder objekt = new KalkylatorMetoder();

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KalkylatorEvents(display, historik, brakform));

        ett.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "1");
            }
        });
        tva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "2");
            }
        });
        tre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "3");
            }
        });
        fyra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "4");
            }
        });
        fem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "5");
            }
        });
        sex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "6");
            }
        });
        sju.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "7");
            }
        });
        atta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "8");
            }
        });
        nio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "9");
            }
        });
        noll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "0");
            }
        });
        deci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + ".");
            }
        });

        // Om användaren trycker på likhetstecknet så tilldelas historik JLabeln värdet av den sträng som  användaren har matat in.
        // Inmatat-variabeln i objektet sätts till strängen som visas i displayen och x ersätts med *.
        // Det sker ett metodanrop till geMigSvaret och displayen får värdet av den sträng som returneras.
        // brakform JLabeln tilldelas värdet av brakString(bråkformen av svaret) och objektet återställs.
        likaMed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!display.getText().equals("")){
                    historik.setText("(" + display.getText() + ")");
                    objekt.setInmatat(display.getText().replace("x", "*"));
                    display.setText(objekt.geMigSvaret());
                    brakform.setText(objekt.getBrakString());
                    objekt.reset();
                }
            }
        });
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "+");
            }
        });
        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "-");
            }
        });
        multi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "x");
            }
        });
        divi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText(display.getText() + "/");
            }
        });

        // Om användaren trycker på C på den grafiska kalkylatorn så tilldelas display och brakform en tom
        // sträng och objektet återställs.
        rensa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText("");
                brakform.setText("");
                objekt.reset();
            }

        });

        // Om användaren trycker på backknappen på den grafiska kalkylatorn och strängen inte är tom så
        // plockas det sista teckned i strängen bort. Om strängen redan är tom så resettas objektet.
        backa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!display.getText().equals(""))
                    display.setText(display.getText().substring(0, display.getText().length()-1));
                else{
                    objekt.reset();
                }
            }
        });
    }
}
