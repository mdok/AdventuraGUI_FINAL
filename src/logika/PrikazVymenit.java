package logika;


/**
  * Třída PrikazSeber implementuje pro hru příkaz vymen. Tento příkaz hráč použije k výměne SFP modulu v datacentru. 
 * 
 * @author    Monika Dokoupilová
 * @version   1.0.0
 */
public class PrikazVymenit implements IPrikaz
{

   private static final String NAZEV = "vyměň"; // název příkazu
   private HerniPlan plan; // načtení herního plánu

  /**
     *  Konstruktor třídy 
     *  @param plan představuje odkaz na herní plán jehož metody jsou v této třídě využívány.
     */
    public PrikazVymenit(HerniPlan plan)
    {
        // initialise instance variables
        this.plan = plan;
    }

    /**
     * Metoda nejprve zjišťuje zda byly zadány parametry. Pokud ano název vyměňované věci se nachází na 0. pozici poli parametrů. Načte se herní batoh
     * ,aktuální prostor a z batohu se načte vyměňovaná věc. Jestliže se v batohu věc nenachází hráči se vrátí text: "Takova vec v batohu neni!". Pokud 
     * se věc v batohu nachází a aktuální prostor se shoduje s datacentrem ( výměna musí být provedena zde u hlavního routeru ISP), pak se hráči vrátí 
     * text: "Tak a je to ! Hura do domecku odevdat ukol!" a v herním plánu se boolean hodnota  Vyhra nastaví na true (to pak umožní provedení příkazu
     * odevzdat_ukol v prostoru domeček). V opačném případě se věc vloží zpět do herního batohu a hráči se vypíše text: "Tady to menit nemuzu!".
     * 
     * @param parametry - je název vyměňované věci
     * @return vrací příslušný výpis v závislosti na události
     */
    @Override
    public String provedPrikaz(String... parametry) {
       if (parametry.length == 0) {
            // nebyly zadány parametry
            return "Co mám vyměnit? Jak se to jmenuje?" + "\n";
       }

       String nazevVymenovaneVeci = parametry[0];
       Batoh batoh = plan.getBatoh(); //nacteni herniho batohu
       Prostor aktualni = plan.getAktualniProstor();
       Vec vymenovana = batoh.getVec(nazevVymenovaneVeci);
        
    
       if(batoh.odeberZBatohu(nazevVymenovaneVeci) != true){
            return "Taková věc v batohu není!" + "\n" + batoh.vypisVeci() + "\n";  // vypsani veci v pripade nenalezeni zadane veci
       }
       else{
        if(aktualni.getNazev().equals("datacentrum") && (nazevVymenovaneVeci.equals("SFP")) ){
            plan.setVyhra(true);
            return "Tak a je to ! Hurá do domečku odevzdat ůkol!" + "\n" + batoh.vypisVeci() + "\n";
        }
        if(nazevVymenovaneVeci.equals("SFP") == false){
            batoh.vlozDoBatohu(vymenovana);
            return "To přeci není ono!" + "\n";
        }
         batoh.vlozDoBatohu(vymenovana);
         return "Tady to měnit přeci nemůžu!" + "\n";

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