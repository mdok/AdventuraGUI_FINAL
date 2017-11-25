package logika;


/**
 * Třída PrikazBatoh implementuje pro hru příkaz batoh. Tento příkaz hráč používá k vysání obsahu herního batohu.
 * 
 * @author    Monika Dokoupilová
 * @version   1.0.0
 */
public class PrikazBatoh implements IPrikaz
{
    private static final String NAZEV = "batoh";
    private HerniPlan plan;

    /***************************************************************************
     *  Konstruktor třídy 
     *  
     *  @param plan představuje odkaz na herní plán jehož metody jsou v této třídě využívány.
     */
    public PrikazBatoh(HerniPlan plan)
    {
       this.plan = plan;
    }

    /**
     * Metoda si načtě herní batoh a následně zavolá metodu pro výpis obsahu batohu implementovanou ve třídě
     * batoh
     * 
     * @param parametry, pokud hráč omylem parametr zadá i přesto se obsah vypíše (jde o neškodný překlep)
     * @return výpis věcí v batohu
     */
    @Override
    public String provedPrikaz(String... parametry) {
        Batoh batoh = plan.getBatoh();
        int limit = plan.getBatoh().getLimit();
        return  batoh.vypisVeci() + "\n" + "Limit: " + limit + " věci";
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
