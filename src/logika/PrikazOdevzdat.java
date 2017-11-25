package logika;


/**
* Třída PrikazOdevzdat implementuje pro hru příkaz odevzdat_ukol. Tento příkaz hráč použíje k odevzdání úkolu. Jakmile hráč úkol odevdá
* vítězí a hra končí.
 * 
 * @author    Monika Dokoupilová
 * @version   1.0.0
 */
public class PrikazOdevzdat implements IPrikaz
{

    private static final String NAZEV = "odevzdat_úkol"; // název příkazu
    private HerniPlan plan; // načtení herního plánu
    private Hra hra; // načtení hry pro umožnění přísupu k metodě setKonecHry()

    /**
      *  Konstruktor třídy 
     *  @param plan představuje odkaz na herní plán jehož metody jsou v této třídě využívány.
     *  @param hra představuje odkaz na právě spuštěnou herní instanci
     */
    public PrikazOdevzdat(HerniPlan plan, Hra hra)
    {

       this.plan = plan;
       this.hra = hra;
    }

    /**
     *Metoda si nejprve načte aktuální prostor. Pokud  zadané parametry jsou větší než 0 (k příkazu byly přidány redundantní znaky),
     *pak se hráči vrátí text: "Takhle to nefunguje!". Pokud žádné parametry přidány nebyly zjístí se zda se hráč nachází v prostoru
     *domeček což je vítězný prostor (zda se název aktuálního prostoru shoduje s domeček), pokud ano nastavý se konec hry na hodnotu true
     *a hráči se zobrazí příslušný výpis uvedený ve třídě hra. Pokud ne  hráči se vypíše text: "Nemohu odevzdat ukol! nefunguje mi preci 
     *internet!"
     *Aby mohl hráč úspěšně odevzdat úkol je třeba aby se hodnota proměnné kterou vrací metoda getVyhra rovnala true ( metoda se nachází 
     *ve třídě herní plán. Hodnata se natavuje metodou set která se volá v metodě třídy výměna po úspěšné výměně SFP) čil, aby měl hráč
     *již splněn úkol výměna SFP, protože jinak mu nebude fungovat internet.
     *
     * @param parametry - dodatecne parametry
     * @return příslušný výpis v závislosti na nastalé události
     */
    
    @Override
    public String provedPrikaz(String... parametry) {
      Prostor aktualni = plan.getAktualniProstor(); //načtení aktuálního prostoru
        
       if (parametry.length > 0) {
            // pokud jsou pridany parametry
            return "Takhle to nefunguje!" + "\n";
        }
       else{
            if(aktualni.getNazev().equals("domeček")){ // jestliže se aktualni = domeček
                if(plan.getVyhra() == true){
                
                hra.setKonecHry(true); // hodnota konec hry se nastaví na true = hra se ukončí a vypíše se příslušný výpis

               }
               else{
                   return "Nemohu odevzdat ůkol! Nefunguje mi přeci internet!"+ "\n"; // pokud hrač neni v domečku ale má splněn úkol výměna
                }
            }
            return "Musím být v domečku! Tady nemám počítač!" + "\n";
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
