package logika;


/**
 *Třída PrikazDej implementuje pro hru příkaz dej. Tento příkaz hráč používá k předávání věcí z batohu postavám hry.
 * 
 * @author    Monika Dokoupilová
 * @version   1.00.000
 */
public class PrikazDej implements IPrikaz
{
    private static final String NAZEV = "dej"; // název příkazu
    private HerniPlan plan; // načtění herního plánu

    /***************************************************************************
     *  Konstruktor třídy 
     *  
     *  @param plan představuje odkaz na herní plán jehož metody jsou v této třídě využívány.
     */
    public PrikazDej(HerniPlan plan)
    {
      this.plan = plan;
    }

    /**
     *V metodě se nejprve načte herní batoh, se kterým se bude dále pracovat. Pak se zjišťuje zda byly zadány parametry. Pokud nebyl
     *zadán ani jeden parametr vrátí se hráči text: "Co mám dát a komu? Jak se ta věc a postava jmenují?". Jestliže byly zadány méně
     *než 2 parametry vratí se hráči text: "Komu to mam dat? Jak se jmenuje?". V případě, že byly zadány všechny parametry, nazev dávané
     *věci se nachází na 0. pozici pole parametrů a název postavy na 1. pozici. Do proměnné postava se načte výsledek hledání postavy
     *s daným názvem v aktuálním prostoru a do proměnné davanaVec výsledek vybrání věci s daným názvem z herního batohu.
     *Jestli ze davanaVec == null (není v batohu) je hráči vrácen text: "Takovou ves u sebe nemam!". Jestliže postava == null (není v 
     *aktuálním prostoru) vrátí se hráči text: "Nikdo takovy tu neni!". Pokud je věc v batohu a postava v prostoru a zárověň již výměna
     *proběhla (postava má věc, kterou chtěla a dala věc, kterou měla) vrítí se hráči řeč postavy po výměně.
     *Pokud výměna zatím neproběhla zavolá se na postavu metoda vymena a zjístí se, zda byla od postavy získána věc (= úspšná výměna).
     *Jestliže ano vloží se do batohu obdržená věc a odebere se věc dávaná. Pokud je výměna neúspěšná ( například hráč dává věc, kterou
     *postava nechce) vrátí se hráči řeč postavy pro výměnu (postava mu vlastně řekne, že tuto věc nechce).
     *V případě, že se název věci, kterou hráč od postavy obdrží shoduje s "pristupova_karta", pak se odemkne zamčený prostor datacentrum
     *a oznámí se to hráči.
     *
     * @param parametry - parametry prikazu
     * @return příslušný výpis dle situace
     */
     @Override
    public String provedPrikaz(String... parametry) {
       Batoh batoh = plan.getBatoh(); //nacteni herniho batohu
        if ((parametry.length == 0 ) || (parametry.length < 2)) {
            // test na zadane parametry
            if(parametry.length == 0 ){
                 return "Co mám dát a komu? Jak se ta věc a postava jmenují?" + "\n" + batoh.vypisVeci() + "\n";
            }
            if((parametry.length < 2) && (parametry.length == 1)){
                 return "Komu to mám dát? Jak se jmenuje?" + "\n" + plan.getAktualniProstor().gePopisPostav() + "\n";
            }
         
        }
        
        String nazevDavaneVeci = parametry[0]; // nacteni parametru potrebnych pro metodu vymen postava, vec
        String nazevPostavy = parametry[1];
        Postava postava = plan.getAktualniProstor().najdiPostavu(nazevPostavy); // je postava v prostoru?
        Vec davanaVec =  batoh.getVec(nazevDavaneVeci); //mam vec v batohu??
        
        if ((davanaVec == null) || (postava == null )) {
           if(davanaVec == null){
              return "Takovou věc u sebe nemám!"+ "\n" + batoh.vypisVeci() + "\n"; // vec neni v batohu
           }
           if(postava == null){
               return "Nikdo takový tu není!" + "\n"; // postava neni v prostoru
           }
        }
        if(postava.getProbehlaVymena() == true){
            return postava.getMluv(); // jestlize jiz probehla vymena vrati se proslov 
        }
        else { 
            Vec obdrzena = postava.vymena(davanaVec); // vymena veci 
           
            if(obdrzena != null){ // uspesna vymena
                batoh.odeberZBatohu(davanaVec.getNazev()); // odebrat davanou z batohu 
                batoh.vlozDoBatohu(obdrzena); // vlozit obdrzenou do batohu
                if(obdrzena.getNazev().equals("přístupová_karta")){ // jestli je nazev obdrzene veci pristupova_karta
                    Prostor zamceny = plan.getZamceny(); //pak se ziska zamceny prostor ze tridy HerniPlan
                    zamceny.zamknout(false); // a nastavi se zamceno na false == odemceno
                    return postava.getRecVymena() + "\n" + "Právě byl zpřístupněn prostor datacentrum" +"\n" +  batoh.vypisVeci();
                }
            }
           
        }  
         return postava.getRecVymena() + "\n" + batoh.vypisVeci() + "\n"; // vraceni reci po vymene bud uspesne nebo 
                                                                            // neuspesne a vypsani batohu
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
