import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class KalkylatorEvents implements KeyEventDispatcher {

    // Events som gör att användaren kan mata in just de tecken vi tillåter via tangentbordet.

    private JLabel display;
    private JLabel historik;
    private JLabel brakform;
    KalkylatorMetoder objekt;

    KalkylatorEvents(JLabel display, JLabel historik, JLabel brakform) {
        this.display = display;
        this.historik = historik;
        this.brakform = brakform;
        this.objekt = new KalkylatorMetoder();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_PRESSED) {
            if(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '-', '/', '.').contains(e.getKeyChar())) {
                display.setText(display.getText() + e.getKeyChar());
            }

            // Om användaren matar in en stjärna så skrivs ett x ut istället på displayen.
            // Bara för att det är snyggare anser jag.
            else if(e.getKeyChar() == '*')
                display.setText(display.getText() + "x");

            // Om användaren trycker på enter så tilldelas historik JLabeln värdet av den sträng som  användaren har matat in.
            // Inmatat-variabeln i objektet sätts till strängen som visas i displayen och x ersätts med *.
            // Det sker ett metodanrop till geMigSvaret och displayen får värdet av den sträng som returneras.
            // brakform JLabeln tilldelas värdet av brakString(bråkformen av svaret) och objektet återställs.
            else if(e.getKeyCode() == 10){
                historik.setText("(" + display.getText() + ")");
                objekt.setInmatat(display.getText().replace("x", "*"));
                display.setText(objekt.geMigSvaret());
                brakform.setText(objekt.getBrakString());
                objekt.reset();
            }

            // Om användaren trycker på delete så tilldelas display och brakform en tom sträng och objektet återställs.
            else if(e.getKeyCode() == 127){
                display.setText("");
                brakform.setText("");
                objekt.reset();
            }

            // Om användaren trycker på backspace och strängen inte är tom så plockas det sista teckned i strängen bort.
            // Om strängen redan är tom så resettas objektet.
            else if(e.getKeyCode() == 8){

                if(!display.getText().equals(""))
                    display.setText(display.getText().substring(0, display.getText().length()-1));
                else{
                    objekt.reset();
                }
            }

            // Om ett kommateckan matas in så konverteras det till en punkt.
            else if(e.getKeyChar() == ',')
                display.setText(display.getText() + ".");
        }
        return false;
    }
}

