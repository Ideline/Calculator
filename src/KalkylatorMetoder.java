import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KalkylatorMetoder {

    private String svarString = "";
    private String brakString = "";
    private String inmatat = "";
    private List<String> taljare = new ArrayList<>();
    private List<String> namnare = new ArrayList<>();
    private List<String> operatorer = new ArrayList<>();
    private boolean fel = false;
    private boolean loop = true;

    public void setSvarString(String svarString) {
        this.svarString = svarString;
    }

    public String getSvarString() {
        return svarString;
    }

    public void setBrakString(String brakString) {
        this.brakString = brakString;
    }

    public String getBrakString() {
        return brakString;
    }

    public void setInmatat(String inmatat) {
        this.inmatat = inmatat;
    }

    public String getInmatat() {
        return inmatat;
    }

    public void setTaljare(List<String> taljare) {
        this.taljare = taljare;
    }

    public List<String> getTaljare() {
        return taljare;
    }

    public void setNamnare(List<String> namnare) {
        this.namnare = namnare;
    }

    public List<String> getNamnare() {
        return namnare;
    }

    public void setOperatorer(List<String> operatorer) {
        this.operatorer = operatorer;
    }

    public List<String> getOperatorer() {
        return operatorer;
    }

    public void setFel(boolean fel) {
        this.fel = fel;
    }

    public boolean isFel() {
        return fel;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isLoop() {
        return loop;
    }

    // Defaultkonstruktor
    public KalkylatorMetoder() {
    }

    // Konstruktor för inmatning av sträng
    public KalkylatorMetoder(String inmatat) {
        this.inmatat = inmatat;
    }

    // Metod som kör igång hela processen
    public String geMigSvaret() {

        while (loop) {

            // Kontrollerar om användaren försökt dividera med noll
            boolean fel = divNoll();
            if (fel) {
                return "Du får ej dela med 0!";
            }

            // Om användaren inte dividerar med noll så påbörjas uträkningen
            if (!fel) {

                // Konverterar alla "tal" användaren matat in till bråktal
                // så att all uträkning sker i bråkform, och ett exakt svar
                // kan returneras i förenklad bråkform. Alla täljare matas in
                // i en lista och alla nämnare i en annan lista.
                deciTillBrak();

                // Hittar alla operatorer och matar in dem i en  egen  lista.
                hittaOperatorer();

                // Hanterar först all division.
                divisionPrio();

                // Räknar ut minsta gemensamma nämnare först för alla bråk med
                // tiopotenser som nämnare, och sedan för de bråk som inte har
                // tiopotenser som nämnare. Sist räknar den ut mgn för samtliga
                // bråktal.
                mgn();

                // Hanterar all multiplikation.
                multiPrio();

                // Hanterar de "tal" som  är negativa.
                subPrio();

                // Mellanlagrar svaret
                svarString = utrakning();

                // Hoppar ur loopen så att svaret kan returneras.
                loop = false;
            }
        }
        // Returnerar svaret till användaren via displayen.
        return svarString;
    }

    // Metod för att mata in tal
    public String inmatning() {
        Scanner scan = new Scanner(System.in);

        // Läser in en sträng från tangentbordet eller genom att användaren
        // klickar med musen (eller touchscreen), på skärmen.
        inmatat = scan.next();

        return inmatat;
    }

    // Letar efter ett divisionstecken följt av en nolla
    public boolean divNoll() {

        boolean fel = inmatat.contains("/0");
        return fel;
    }

    // Metod för att omvandla decimaltal till bråktal
    public void deciTillBrak() {

        int forraIndex = 0; // markör för var första siffran i "talet" är
        String delAvDeci = "";
        String delAvDeci2 = "";
        String brakTalTaljare = "";
        String brakTalNamnare = "";
        int decLangd = 0;
        int divator = 1;

        for (int i = 0; i < inmatat.length(); i++) {

            String del = inmatat.substring(i, i + 1);
            // Letar efter en operator
            if (del.equals("+") || del.equals("-") || del.equals("*") || del.equals("/")) {

                // Tilldelar brakTaljare värdet fram till operatorn (ej operator)
                brakTalTaljare = inmatat.substring(forraIndex, i);

                // Tittar om värdet är ett "decimaltal"
                if (brakTalTaljare.contains(".")) {

                    for (int j = forraIndex; j < inmatat.length(); j++) {

                        // Om det finns ett decimaltecken så letas det upp var
                        if (inmatat.substring(j, j + 1).equals(".")) {

                            // Tilldelar delAvDeci värdet före punkten och flyttar fram forraIndex till efter punkten
                            delAvDeci = inmatat.substring(forraIndex, j);
                            forraIndex = j + 1;

                            for (int k = j; k < inmatat.length(); k++) {

                                // Letar upp nästa tecken
                                if (inmatat.substring(k, k + 1).equals("+") || inmatat.substring(k, k + 1).equals("-")
                                        || inmatat.substring(k, k + 1).equals("*") || inmatat.substring(k, k + 1).equals("/")) {

                                    // Räknar ut hur många decimaler "talet" har
                                    decLangd = inmatat.substring(forraIndex, k).length(); // kanske k+1?
                                    divator = 1;

                                    // Skapar en divator baserat på antalet decimaler
                                    for (int l = 0; l < decLangd; l++) {

                                        divator *= 10;
                                    }

                                    delAvDeci2 = inmatat.substring(forraIndex, k); // Tilldelar delAvDeci2 värdet av "decimalerna"
                                    brakTalTaljare = delAvDeci + delAvDeci2; // Slår ihop delarna till en täljare
                                    taljare.add(brakTalTaljare);// Lägger till strängen i listan för täljare
                                    namnare.add("" + divator); // Lägger till strängen i listan för nämnare
                                    k = inmatat.length(); // Avbryter loopen
                                    j = inmatat.length();// Avbryter loopen
                                    forraIndex = i + 1; // Sätter forraIndex till "siffran" efter operatorn vi först hittade
                                }
                                // Om decimaltalet är sist i strängen
                                else if (k == inmatat.length() - 1) {

                                    decLangd = inmatat.substring(forraIndex).length();
                                    divator = 1;
                                    for (int l = 0; l < decLangd; l++) {

                                        divator *= 10;
                                    }

                                    delAvDeci2 += inmatat.substring(forraIndex);
                                    brakTalTaljare = delAvDeci + delAvDeci2;
                                    taljare.add(brakTalTaljare);
                                    namnare.add("" + divator);
                                }
                            }
                        }
                    }
                }
                // Om det inte finns ett decimaltecken så tilldelas brakTalTaljare värdet av alla siffror före operatorn
                // och nämnaren sätts till ett då det handlar om ett heltal
                else {

                    brakTalNamnare = "1";
                    taljare.add(brakTalTaljare);
                    namnare.add(brakTalNamnare);
                    forraIndex = i + 1; // forraIndex sätts till första siffran efter operatorn
                }
            }
            // Om siffrorna är de sista i strängen inmatat
            else if (i == inmatat.length() - 1) {

                brakTalTaljare = inmatat.substring(forraIndex);

                // Är det ett decimaltal?
                if (brakTalTaljare.contains(".")) {

                    for (int j = forraIndex; j < inmatat.length(); j++) {

                        if (inmatat.substring(j, j + 1).equals(".")) {

                            delAvDeci = inmatat.substring(forraIndex, j);
                            forraIndex = j + 1;

                            delAvDeci2 = inmatat.substring(forraIndex);

                            decLangd = inmatat.substring(forraIndex).length();
                            divator = 1;

                            for (int l = 0; l < decLangd; l++) {

                                divator *= 10;
                            }

                            brakTalTaljare = delAvDeci + delAvDeci2;
                            taljare.add(brakTalTaljare);
                            namnare.add("" + divator);
                            j = inmatat.length();
                        }

                    }
                }
                // Om inte decimaltal
                else {
                    brakTalNamnare = "1";
                    taljare.add(brakTalTaljare);
                    namnare.add(brakTalNamnare);
                }
            }
        }
    }

    // Metod för att föra över operatorerna till en egen lista
    public void hittaOperatorer() {

        String op = "";

        // loopar igenom  hela listan
        for (int i = 0; i < inmatat.length(); i++) {

            // Tittar på ett tecken åt gången i strängen
            String del = inmatat.substring(i, i + 1);

            // Om tecknet är en av följande operatorer så matas det in i listan för operatorer.
            if (del.equals("+") || del.equals("-") || del.equals("*") || del.equals("/")) {

                op = del;
                operatorer.add(del);
            }
        }
    }

    // Metod för att hantera prioritet av division
    public void divisionPrio() {

        // Loopar igenom hela operatorlistan
        for (int i = 0; i < operatorer.size(); i++) {

            // Om operatorn på ett visst index är ett divisionstecken så multipliceras täljaren med samma index
            // med nämnaren med följande index, och täljaren med följande index multipliceras med nämnaren
            // med samma index som operatorn. I enlighet med räkneregler för bråktal och division.
            // Täljaren och nämnaren med samma index som operatorn tilldelas värdet av produkterna och
            // täljaren samt nämnaren med följande index tas bort.
            // i subtraheras med 1 då listorna nu blivit kortare.
            if (operatorer.get(i).equals("/")) {
                taljare.set(i, "" + Long.parseLong(taljare.get(i)) * Long.parseLong(namnare.get(i + 1)));
                namnare.set(i, "" + Long.parseLong(taljare.get(i + 1)) * Long.parseLong(namnare.get(i)));
                taljare.remove(i + 1);
                namnare.remove(i + 1);
                operatorer.remove(i);
                i--;
            }
        }
    }

    // Metod för att hitta minsta gemensamma nämnare
    public void mgn() {

        long iMGN = 1;
        long iMGN2 = 1;
        long namnareLong = 1;
        long faktor = 1;
        boolean tiopotens = true;

        boolean ejTioPotens = true;
        long sgd = iMGN;
        long b = 0;
        long c;

        List<String> tmpEjTioPotens = new ArrayList<String>();

        // Tittar på alla strängar i listan
        for (int i = 0; i < namnare.size(); i++) {

            // Återställer variablerna efter varje loop
            int j = 0;
            tiopotens = true;

            // Tittar på första delen av strängen och avgör om den är "1"
            if (namnare.get(i).substring(j, j + 1).equals("1")) {

                // Loopar igenom resten av strängen
                for (j = 1; j < namnare.get(i).length(); j++) {

                    // Om strängen innehåller något annat än första ettan eller nolla, så sätts
                    // tiopotens till false då vi börjar att behandla "bråktalen" med tiopotenser som nämnare
                    if (!namnare.get(i).substring(j, j + 1).equals("0")) {

                        tiopotens = false;
                        j = namnare.get(i).length();
                    } else {

                        tiopotens = true;
                    }
                }

                // Om strängen uppfyller kraven så är den med i beräkningen av iMGN
                if (tiopotens) {

                    if (Long.parseLong(namnare.get(i)) > iMGN) {

                        iMGN = Long.parseLong(namnare.get(i));
                    }
                }
            }
        }

        // Tittar på  alla strängar i listan
        for (int i = 0; i < namnare.size(); i++) {

            int j = 0;
            // Behandlar bara de som uppfyllde tidigare krav
            if (namnare.get(i).substring(j, j + 1).equals("1")) {

                for (j = 1; j < namnare.get(i).length(); j++) {

                    if (!namnare.get(i).substring(j, j + 1).equals("0")) {

                        tiopotens = false;
                        j = namnare.get(i).length();
                    } else {

                        tiopotens = true;
                    }
                }
                // Om de är tiopotenser så tar man reda på vilken faktor som både täljaren
                // och nämnaren ska multipliceras med.
                if (tiopotens) {

                    namnareLong = Long.parseLong(namnare.get(i));
                    faktor = iMGN / namnareLong;
                    taljare.set(i, "" + Long.parseLong(taljare.get(i)) * faktor);
                    namnare.set(i, "" + Long.parseLong(namnare.get(i)) * faktor);
                }
            }
        }

        // Vi har omvandlat alla nämnare som är tiopotenser till MGN, och behöver nu hantera övriga nämnare
        // så att de blir lika. Därefter behöver vi gå tillbaka till början av listan och fixa tiopotenserna.

        for (int i = 0; i < namnare.size(); i++) {

            // Återställer variablerna efter varje loop
            int j = 0;
            ejTioPotens = true;

            // Tittar på första delen av strängen och avgör om  den är "1"
            if (namnare.get(i).substring(j, j + 1).equals("1")) {

                // Loopar igenom resten av strängen
                if (namnare.get(i).length() > 1) {
                    for (j = 1; j < namnare.get(i).length(); j++) {

                        // Om substrängen innehåller något annat än först en etta och sen en nolla
                        // så sätts ejTioPotens till true, då denna ska tas med i beräkningen.
                        if (!namnare.get(i).substring(j, j + 1).equals("0")) {

                            ejTioPotens = true;
                            j = namnare.get(i).length();
                        } else {

                            ejTioPotens = false;
                        }
                    }
                } else {
                    ejTioPotens = false;
                }
            }
            // Om första tecknet i substringen inte är en etta så sätts direkt ejTioPotens till true
            else if (!namnare.get(i).substring(j, j + 1).equals("1")) {

                ejTioPotens = true;
            }

            // Om strängen uppfyller kraven så är den med i beräkningen av iMGN
            if (ejTioPotens) {

                tmpEjTioPotens.add(namnare.get(i));

                b = Long.parseLong(namnare.get(i));
                while (b != 0) {
                    c = sgd % b;
                    sgd = b;
                    b = c;
                }

                b = Long.parseLong(namnare.get(i)); // Återställer b

                // Bestäm nu MGN för alla "tal" fram t.o.m detta elementet i listan enligt följande:
                iMGN = iMGN * b / sgd;

                for (int k = 0; k < i + 1; k++) {

                    namnareLong = Long.parseLong(namnare.get(k));
                    faktor = iMGN / namnareLong;
                    taljare.set(k, "" + Long.parseLong(taljare.get(k)) * faktor);
                    namnare.set(k, "" + Long.parseLong(namnare.get(k)) * faktor);
                }
            }
        }

        //Räkna ut MGN för alla icke 10 potenser
        if (tmpEjTioPotens.size() > 1) {
            for (int i = 0; i < tmpEjTioPotens.size(); i++) {
                sgd = Long.parseLong(tmpEjTioPotens.get(i + 1));
                b = Long.parseLong(tmpEjTioPotens.get(i));
                while (b != 0) {
                    c = sgd % b;
                    sgd = b;
                    b = c;
                }

                iMGN2 = iMGN2 * Long.parseLong(tmpEjTioPotens.get(i)) / sgd;

                tmpEjTioPotens.remove(0);
                tmpEjTioPotens.remove(0);

                tmpEjTioPotens.add("" + iMGN2);

                i = 0;
            }
        }

        //Jamför och lagra den största MGN för tiopotenser eller icke tiopotenser
        sgd = iMGN;
        b = iMGN2;
        while (b != 0) {
            c = sgd % b;
            sgd = b;
            b = c;
        }

        //Den slutliga nämnaren
        iMGN = iMGN * iMGN2 / sgd;

        for (int i = 0; i < namnare.size(); i++) {
            namnareLong = Long.parseLong(namnare.get(i));
            faktor = iMGN / namnareLong;
            taljare.set(i, "" + Long.parseLong(taljare.get(i)) * faktor);
            namnare.set(i, "" + Long.parseLong(namnare.get(i)) * faktor);
        }
    }

    // Metod för att hantera multiplikation
    public void multiPrio() {

        for (int i = 0; i < operatorer.size(); i++) {

            // Använder datatypen long då det kan uppstå väldigt stora tal pga att allt räknas i bråkform
            // och mgn därför kan bli väldigt stor. Jag stötte på problem med att talen då blev negativa
            // eftersom integern blev överfylld.
            // Om tecknet i operatorlistan är ett multiplikationstecken så multipliceras täljaren med samma
            // index som operatorn med täljaren med nästkommande index, och produkten divideras med nämnaren
            // som har samma index som operatorn. (Enligt räknereglerna för multiplikation och bråktal)
            // täljaren och nämnaren med följande index tas bort och i subtraheras med 1 då listorna blivit
            // kortare.
            if (operatorer.get(i).equals("*")) {
                taljare.set(i, "" + Long.parseLong(taljare.get(i)) * Long.parseLong(taljare.get(i + 1))
                        / Long.parseLong(namnare.get(i)));
                taljare.remove(i + 1);
                namnare.remove(i + 1);
                operatorer.remove(i);
                i--;
            }
        }
    }

    // Metod för att hantera substraktion
    public void subPrio() {

        // Loopar igenom operatorlistan. Om det finns ett subtraktionstecken så multipliceras täljaren
        // med nästkommande index, med -1 så att "bråktalet" blir negativt.
        for (int i = 0; i < operatorer.size(); i++) {

            if (operatorer.get(i).equals("-")) {

                taljare.set(i + 1, "" + Long.parseLong(taljare.get(i + 1)) * -1);
            }
        }
    }

    // Metod för att räkna ut svaret
    public String utrakning() {

        long svarTaljare = 0;
        float svarFloat = 0;
        float b = 0;
        String svarNamnare = "";

        // Loopar igenom hela listan och konverterar alla strängar i täljarlistan till heltal.
        // adderar sen alla heltal med varandra och tilldelar svarTaljare detta värde.
        for (int i = 0; i < taljare.size(); i++) {

            svarTaljare += Long.parseLong(taljare.get(i));
        }

        // Tilldelar svarNamnare värdet av den gemensamma nämnaren.
        svarNamnare = namnare.get(0);

        // Skickar in variablerna i metoden för att förenkla bråktalet.
        brakString = forenkla(svarTaljare, svarNamnare);

        // Räknar ut svaret av bråktalet i decimalform
        svarFloat = svarTaljare / Float.parseFloat(svarNamnare);

        // Avrundar decimaltalet till ett heltal
        b = Math.round(svarFloat);

        // (Enbart för att det är roligt att tramsa sig) Om svaret är 1337 så skrivs
        // följande meddelande ut på displayen. Annars returneras det riktiga svaret.
        if (b == 1337)
            return "AK ÄR BÄST!!!";
        else
            return "" + svarFloat;
    }

    // Metod som använder Euklides algoritm för att räkna ut SGD och förkorta talet.
    public String forenkla(long svarTaljare, String svarNamnare) {

        long sgd = svarTaljare;
        long b = Long.parseLong(svarNamnare);
        long c;
        String svarString = "";

        while (b != 0) {
            c = sgd % b;
            sgd = b;
            b = c;
        }
        svarTaljare = svarTaljare / sgd;
        svarNamnare = "" + (Long.parseLong(svarNamnare) / sgd);
        svarString = "" + svarTaljare + "/" + svarNamnare;

        return svarString;
    }

    // Metod som resettar listorna, inmatningen och svaren.
    // (egentligen resettas inte listorna utan nya listor skapas)
    // loop sätts till true, så att användaren kan göra nya uträkningar.
    public void reset() {
        this.svarString = "";
        this.brakString = "";
        this.inmatat = "";
        taljare = new ArrayList<>();
        namnare = new ArrayList<>();
        operatorer = new ArrayList<>();
        fel = false;
        loop = true;
    }
}
