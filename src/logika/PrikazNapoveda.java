package logika;

/**
 *  Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 *@author     Jarmila Pavlickova, Luboš Pavlíček, Monika Dokoupilová
 *@version    1.0.0
 *  
 */
class PrikazNapoveda implements IPrikaz {
    private static final String NAZEV = "nápověda";
    private SeznamPrikazu platnePrikazy;
    
     /**
    *  Konstruktor třídy
    *  
    *  @param platnePrikazy seznam příkazů,
    *                       které je možné ve hře použít,
    *                       aby je nápověda mohla zobrazit uživateli. 
    */    
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }
    
    /**
     *  Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     *  vcelku primitivní zpráva a seznam dostupných příkazů.
     *  
     *  @return napoveda ke hre
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return "Tvým úkolem je zprovoznit Quidovi internet a odevzdat úkol\n"
        + "\n"
        + "Můžeš zadat tyto příkazy:\n"
        + platnePrikazy.vratNazvyPrikazu() + "\n";
    }
    
     /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
      public String getNazev() {
        return NAZEV;
     }

}
