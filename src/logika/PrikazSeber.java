/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/*******************************************************************************
 * Třída PrikazSeber implementuje pro hru příkaz seber. Tento příkaz hráč používá ke sbírání věcí ve hře.
 * 
 * @author    Monika Dokoupilová
 * @version   1.0.0
 */
public class PrikazSeber implements IPrikaz
{
    private static final String NAZEV = "seber"; //neměnná konstanta NAZEV udává název příkazu
    private HerniPlan plan; // načtení herního plánu, umožní přístup k jeho metodám
    
    /***************************************************************************
     *  Konstruktor třídy 
     *  @param plan představuje odkaz na herní plán jehož metody jsou v této třídě využívány.
     */
    public PrikazSeber(HerniPlan plan)
    {
      this.plan = plan;
     
    }
    
    /***************************************************************************
     * Metoda provádí příkaz.
     * V případě, že k příkazu nejsou zadány žádné parametry vrací text: "Co mám sebrat? Jak se to jmenuje?".
     * Pokud jsou zadány parametry, pak název sbírané věci se nachází v poli parametrů na 0. pozici.
     * Z aktuálního prostoru se odebere přes herní plán věc danného názvu a načte se herní batoh do proměnné
     * batoh.
     * Pokd odebrání věci z aktuálního prostoru nebylo úspěšné (věc se v prostoru nenachází a byla vrácena 
     * hodnota null) je hráči vrácen text: "Ta tu není!". Jestliže odebrání věci z prostoru proběhlo úspěšně
     * zjišťuje se, zda je věc přenositelná a zda není batoh již plný. Pokud je věc přenositelná a batoh není
     * plný věc se vloží do batohu. Pokud je věc přenositelná ale v batohu již není místo je to oznámeno hráči 
     * a věc se vloží zpět do aktuálního prostoru. V případě, že věc není přenositelná je to taktéž oznámeno
     * hráči a věc je vložena zpět do aktuálního prostoru.
     * 
     * @param parametry nazev věci, kterou chce hráč sebrat
     * @return příslušný výpis dle situace
     */
     @Override
    public String provedPrikaz(String... parametry) {
       
        if (parametry.length == 0) {
            // pokud nejsou zadány parametry
            return "Co mám sebrat? Jak se to jmenuje?" + "\n";
        }

        String nazevSbiraneVeci = parametry[0];
        // zkoušíme přejít do sousedního prostoru
        Vec sbiranaVec =  plan.getAktualniProstor().odeberVec(nazevSbiraneVeci);
        Batoh batoh = plan.getBatoh(); //nacteni herniho batohu
        if (sbiranaVec == null) {
            return "Ta tu není!"+ "\n";
        }
        else {   
            if((sbiranaVec.jePrenositelna() == true) && (batoh.jePlno() == false)){
                batoh.vlozDoBatohu(sbiranaVec); //vlozeni veci do batohu pokud neni plny a vypis obsahu  
                return "Věc byla vložena do batohu" + "\n" + batoh.vypisVeci() + "\n";
               
            }
            if((sbiranaVec.jePrenositelna() == true) && (batoh.jePlno() == true)){ // batoh je plný
               plan.getAktualniProstor().vlozVec(sbiranaVec); // vlozeni věci zpět do aktuálního prostoru
               return "V batohu již není místo!... musíš něco odebrat!" + "\n" + batoh.vypisVeci() + "\n";
            }
            else{
                plan.getAktualniProstor().vlozVec(sbiranaVec); //Pokud věc není sebratelná vloží se zpět do prostoru
                return "Tuto věc nelze sebrat" + "\n";
            }
        }
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
