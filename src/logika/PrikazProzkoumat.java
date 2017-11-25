package logika;


/**
* Třída PrikazProzkoumat implementuje pro hru příkaz prozkoumat. Tento příkaz hráč používá k získání podrobnějšího popisu věci či postavy.
 * 
 * @author    Monika Dokoupilová
 * @version   1.0.0
 */
public class PrikazProzkoumat implements IPrikaz
{

   private static final String NAZEV = "prozkoumat";  // název příkazu
   private HerniPlan plan; // načtění herního plánu

    /**
     *  Konstruktor třídy 
     *  
     *  @param plan představuje odkaz na herní plán jehož metody jsou v této třídě využívány.
     */
    public PrikazProzkoumat(HerniPlan plan)
    {
       this.plan = plan;
    }

    /**
     *Metoda si nejprve načte herní batoh. Pak se zjišťuje zda byly zadány parametry pokud ne hráči se vypíše text: "Co mam prozkoumat? Jak se to jmenuje?".
     *Pokud ano, název zkoumané věci/postavy se nachází na 0. pozici pole parametrů. Z batohu se získá zkoumaná věc a z herního plánu se načte zkoumaná 
     *postava. Jestliže se v batohu věc nenachází dojde k pokusu odebrat věc z aktuálního prostoru. Pokud se zkoumaná věc/postava nenachází v prostoru, 
     *pak hráči vrátí text: "Tohle neni jmeno veci kterou bych mel v batohu nebo byla v prostoru a ani zadne postavy tady v okoli!". Pokud se zkoumaná 
     *věc v prostoru nachází vloží se věc, která byla z prostoru odebrána zpět do prostoru a vrátí se její popis. V jiném případě se nejedná
     *o věc nýbrž postavu a vrátí se popis postavy.
     *
     * @param parametry jmeno postavy nebo název věci kterou chce hráč prozkoumat
     * @return příslušný výpis dle situace
     */
    
    @Override
    public String provedPrikaz(String... parametry) {
        Batoh batoh = plan.getBatoh(); //nacteni herniho batohu
        if (parametry.length == 0 ){
            // pokud nebyly zadány parametry
            return "Co mám prozkoumat? Jak se to jmenuje? " + "\n";
        }
        // muze to byt vec nebo postava
        String nazevZkoumaneVeci = parametry[0];
        Vec zkoumanaVec =  batoh.getVec(nazevZkoumaneVeci); // je věc v batohu?
        Postava zkoumanaPostava = plan.getAktualniProstor().najdiPostavu(nazevZkoumaneVeci); // je postava v prostoru?
        
        if(zkoumanaVec == null){ // věc není v batohu
            zkoumanaVec =  plan.getAktualniProstor().odeberVec(nazevZkoumaneVeci); // věc se načte z aktuálního prostoru
            zkoumanaPostava = plan.getAktualniProstor().najdiPostavu(nazevZkoumaneVeci);
            if((zkoumanaVec == null) && (zkoumanaPostava == null )){ // ani postava ani věc nejsou v aktuálním prostoru
              if(zkoumanaVec == null){
                  
                  return "Tohle není jméno věci, kterou bych měl v batohu nebo byla v prostoru, a ani žádné postavy tady v okolí!" +"\n";  
                }
              
            }
            else{
                if(zkoumanaVec != null){ // věc je v prostoru
                    plan.getAktualniProstor().vlozVec(zkoumanaVec); // vrácení věci zpět do prostoru neboť sme jí v předchozím kroku odebrali
                    return zkoumanaVec.getPopis() +"\n";  // vrácení popisu věci
                }
                
            }
           
        }
        else{
            return zkoumanaVec.getPopis() +"\n";  // vrácení popisu věci
        }
        
       return zkoumanaPostava.getPopis() +"\n"; // vrácení popisu postavy
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
