package logika;


/**
 * Třída PrikazPromluv implementuje pro hru příkaz promluv. Tento příkaz hráč používá ke komunikaci s 
 * postavami hry.
 * 
 * @author    Monika Dokoupilová
 * @version   1.0.0
 */
public class PrikazPromluv implements IPrikaz
{
   
   private static final String NAZEV = "promluv"; // název příkazu
   private HerniPlan plan; // načtení herního plánu

    /**
     ***************************************************************************
     *  Konstruktor třídy 
     *  @param plan představuje odkaz na herní plán jehož metody jsou v této třídě využívány.
     */
    public PrikazPromluv(HerniPlan plan)
    {
        this.plan = plan;
    }

    /**
     * Pokud nejsou zadány žádné parametry (jméno postavy), pak je hráči vrácen text: "S kym mam promluvit??".
     * Jestliže parametry byly zadány, pak jméno postavy se nachází na 0. pozici pole paramterů. do proměnné
     * hledanaPostava se načte postava se kterou chce hráč hovořit z aktuálního prostoru pomocí metody najdiPostavu.
     * Pokud je hledanaPostava rovna null (v aktuálním prostoru se nenachází), pak je hráči vrácen text: "Nikdo takovy tu neni !".
     * V opačném případě se zavolá na hledanou postavu metoda getMluv(), která hráči vypíše řeč postavy.
     * 
     * @param parametry jmeno postavy se kterou chce hráč mluvit
     * @return příslušný výpis dle situace
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // 
            return "S kým mám promluvit??";
        }
        
        String jmenoPostavy = parametry[0];
        Postava hledanaPostava =  plan.getAktualniProstor().najdiPostavu(jmenoPostavy);
        
        if (hledanaPostava == null) {
            return "Nikdo takový tu není !";
        }
        else{
            return "Povídá: " + hledanaPostava.getMluv();
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
