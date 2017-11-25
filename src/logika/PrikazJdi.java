package logika;

/**
 *  Třída PrikazJdi implementuje pro hru příkaz jdi.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 *@author     Jarmila Pavlickova, Luboš Pavlíček, Monika Dokoupilová
 *@version    1.0.0
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */    
    public PrikazJdi(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *  
     * Pokud je sousední prostor zamčený oznámí se to hráči. Jediným zamčeným prostorem ve hře je 
     * datacentrum. Pokud je jméno prosotru, kam se hráč snaží jít, shodné s "datacentrum" a hráč u sebe
     * zárověň nemá povolení, které získá splněním předchozího úkolu nemůže do prostoru vejít ačkoli je
     * prostor již odemčen.
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];
        Batoh batoh = plan.getBatoh(); 
        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            // pokud prostor kam chce hráč jít není sousedním prostorem
            return "Tam se odsud jít nedá!";
        }
        else {
            if (sousedniProstor.jeZamceno()) {
                // pokud protor je uzamčen a nelze do něj vejít
                return "dveře do místnosti "+sousedniProstor.getNazev()
                     +" jsou zamčené";
            }
            if((sousedniProstor.getNazev().equals("datacentrum")) && (batoh.getVec("povolení") == null)){
                // pokud hráč nemá u sebe povolení vejít ačkoli je prostor odemčen nemůže vejít
                return "Nemám povolení.. Takhle do datacentra nemůžu vstoupit!";
            }
            plan.setAktualniProstor(sousedniProstor); // jinak se podaří vejít do protoru
            return sousedniProstor.dlouhyPopis();
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
