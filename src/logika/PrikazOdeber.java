package logika;


/**
 * Třída PrikazOdeber implementuje pro hru příkaz odeber. Tento příkaz hráč používá k odebírání věcí z batohu a vložení jich do 
 * aktuálního prostoru hry.
 * 
 * @author Monika Dokoupilová
 * @version  1.0.0
 */
public class PrikazOdeber implements IPrikaz
{
    private static final String NAZEV = "odeber"; // nazev příkazu
    private HerniPlan plan; //nacteni herniho planu
    
    /**
     * Konstruktor třídy 
     * 
     *  @param plan představuje odkaz na herní plán jehož metody jsou v této třídě využívány.
     */
    public PrikazOdeber(HerniPlan plan)
    {
 
         this.plan = plan;
    }

    /**
     * Prikaz odeber umoznuje odebirat veci z batohu a vlozit je zpet do prostoru ve kterem se zrovna nachazime (neni treba 
     * resit zda je vec sebratelna kdyz jsme ji jednou uz preci sebrali). Pokud nejsou zadány parametry (název odebírané věci) pak
     * je hráči vrácen text: "Co mám odebrat? Jak se to jmenuje?". Pokud jsou zadány parametry, pak je název odebírané věci v poli
     * parametrů na 0. pozici. Načte se herní batoh  a aktuální prostor. Z batohu se odebere odebíraná věc. Pokud se v batohu nachází
     * věc se zadaným názvem (vrácena hodnota true), pak se věc vloží do aktuálního prostoru. V opačném případě je hráči vrácen 
     * text: "Takova vec v batohu neni!".
     * 
     * @param parametry - nazev věci odebírané z batohu
     * @return příslušný výpis dle situace
     */
     @Override
    public String provedPrikaz(String... parametry) {
         if (parametry.length == 0) {
            // pokud nejsou zadány parametry
            return "Co mám odebrat? Jak se to jmenuje?" + "\n";
        }
        
        String nazevOdebiraneVeci = parametry[0]; // v prvnim poli pole parametru je nazev veci
        Batoh batoh = plan.getBatoh(); //nacteni batohu
        Prostor aktualni = plan.getAktualniProstor(); // nacteni aktualniho prostoru pro pozdejsi vkladani odebrane veci
        Vec odebirana = batoh.getVec(nazevOdebiraneVeci); //nacteni odebirane veci dokud je jeste ve hre
        
        if(batoh.odeberZBatohu(nazevOdebiraneVeci) == true){
             // pokud je odebíraná věc v batohu
             aktualni.vlozVec(odebirana); // vlozeni odebrane veci zpet do hry do aktualniho prostoru
             return  "Věc byla odebrána z batohu. " + "\n" + batoh.vypisVeci() + "\n"  + aktualni.getPopisVeci() + "\n"; 
             //vypis veci v batohu po odebrani + vypis vsech veci vcetne novych v prostoru
            }
        else {
            return "Taková věc v batohu není!" + "\n" + batoh.vypisVeci() + "\n"; // vypsani veci v pripade nenalezeni zadane veci
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
