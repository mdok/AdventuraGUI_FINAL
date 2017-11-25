package logika;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.*;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import utils.Observer;
import utils.Subject;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *  
 * Nově třída Prostor implementuje rozhraní Subject a je předmětem pozorování pro observery ListVeciProstor a
 * ListBatoh. Notifikovat všechny pozorovatele je potřeba v případě sebrání věci z prostoru a při přidání věci
 * do prostoru (zavolat metodu update() u všech observerů).
 * 
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Monika Dokoupilová
 * @version   1.0.0
 */
public class Prostor implements Subject {
    private String nazev; // název prostoru
    private String popis; // popis prostoru
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private Map<String,Vec> veciVProstoru; // seznam veci v prostoru
    private Set<Postava> postavyVProstoru; // seznam postav v prostoru
    private boolean zamcen; // promena pro zamikani prostoru 
    
    private double posX; // pridani 
    private double posY;
    
    private List<Observer> listObserveru = new ArrayList<Observer>();

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     * @param posX v rpostoru
     * @param posY v prostoru
     *  
     */
    public Prostor(String nazev, String popis, double posX, double posY) {
        this.nazev = nazev;
        this.popis = popis;
        this.posX = posX;
        this.posY = posY;
        vychody = new HashSet<>();
        
       
        veciVProstoru = new HashMap<>();
        postavyVProstoru = new HashSet<>();
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }  

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v mistnosti/prostoru " + popis + ".\n\n"
                + popisVychodu()  + "\n"
                + popisVeci() + "\n"
                + popisPostav() + "\n\n";
                
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "východy:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
            if (sousedni.jeZamceno()) { // jestli ze je prostor zamcen prida se k vypisu zamknuto k danemu prostoru
                 vracenyText += "(zamknuto)";
             }
        }
        return vracenyText;
    }
    
    /**
     * Metoda vrací seznam věcí v prostoru. Pomocí cyklu forach jsou procházeny věci z prostoru a je zíksáván jejich název
     * 
     * @return vrací výpis věcí nacházejících se v prostoru
     */
    private String popisVeci() {
        String vracenyText = "věci:";
        for (String nazev : veciVProstoru.keySet()) {
            vracenyText += " " + nazev;
        }
        return vracenyText;
    }
    
    /**
     * Metoda vrací seznam postav v prostoru. Opět realizováno cyklem foreach
     * 
     * @return vrací výpis postav v prostoru
     */
    private String popisPostav(){
       String vracenyText = "Postavy: ";
        for (Postava postava : postavyVProstoru) {
            vracenyText += " " + postava.getJmeno();
        }
        return vracenyText;  
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }
    
    /**
     * Metoda vkládá věco do prostoru. Vstupním parametrem je věc. 
     * Při vložení věci do prostoru se notifikují observeři.
     * @param neco reprezentuje věc vkládanou do prostoru
     * @return vrací výsledek vložení true/false
     */
    
    public boolean vlozVec (Vec neco){
        veciVProstoru.put(neco.getNazev(), neco);
        notifyAlllObservers(); //nove
        return true;
    }
    
    /**
     * Metoda zjišťuje zda se v prostoru nachází věc daného názvu.
     * 
     * @param nazev název hledané věci
     * @return true v případě, že věc v prostoru je a false v případě, že není
     */
    public boolean jeVecVProstoru (String nazev){
        return veciVProstoru.containsKey(nazev);
    }
    
    /**
     * Mteoda odebírá věc daného názvu z prostoru.
     * Při odebrání věci z prostoru se notifikují observeři.
     * @param nazev název odebírané věci
     * @return vrací odebíranou věc
     */
    public Vec odeberVec (String nazev){
           Vec odebirana = null;
           odebirana = veciVProstoru.get(nazev);
           veciVProstoru.remove(nazev);
           notifyAlllObservers();
           return odebirana;
       
    }
    
    /**
     * Metoda vkládá postavu do prostoru.
     * 
     * @param postava instance třídy postava - postava vkládaná do prostoru
     */
    public void vlozPostavu(Postava postava) {
         postavyVProstoru.add(postava);
        
    }
    
    /**
     *Metoda zjíšťuje zda se postava daného jména nachází v prostoru. Realizováno pomocí postupného procházení seznamu postav v prostoru prostřednictvím
     *cyklu foreach
     *
     *@param jmeno hledané postavy
     *@return vrací instanci třídy postava -  hledaná postava
     */
    public Postava najdiPostavu(String jmeno){
        Postava hledana = null;
        for (Postava postava : postavyVProstoru) {
            if(postava.getJmeno().equals(jmeno)){
                hledana = postava;
            }
        }
        return hledana;
        
    }
    
    /**
     * Metoda nastavuje hodnotu proměnné zamcen, která určuje zda je daný prostor zamčený
     * 
     * @param zamcen zamčen = true, odemčen = false
     */
    public void zamknout(boolean zamcen){ // metoda pro nastaveni promene zamcen {zamceno == true, odemceno == false}
        this.zamcen = zamcen;
        
    }
    
    /**
     * Metoda vrací hodnotu proměnné zamcen. Tedy zjišťuje zda je prostor zamčen nebo odemčen
     * 
     * @return vrací hodnotu proměnné zamcen 
     */
    public boolean jeZamceno(){ // pro zjisteni hodnoty promene zamcen ... je zamceno? nebo odemceno?
       return zamcen;
    }
    
    /**
     * Metoda vrací seznam věcí v prostoru. 
     * 
     * @return vrací popis věcí nacházejících se v prostoru
     */
    public String getPopisVeci(){
       return this.popisVeci();
    }
    
    /**
     *Metoda vrací seznam postav v prostoru.
     *
     *@return vrací popis postav nacházejících se v prostoru
     */
    public String gePopisPostav(){
        return this.popisPostav();
    }

    
    //*nove
    
    public Vec getVec(String nazev){  //vraceni veci  z batohu podle nazvu
        Vec vec = veciVProstoru.get(nazev);
        return vec;
    }
    
    public Map<String,Vec> getVeciVProstoru(){ //testovani kapacity batohu
       return this.veciVProstoru;
    }
    
    /**
     * Metoda registruje observera k pozorování událostí týkajícíh se věcí v prostoru.
     * @param observer - parametrem je observer (instance třídy, která pozoruje).
     */ 
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }
    /**
     * Metoda ruší registraci observera k pozorování událostí týkajícíh se věcí v prostoru.
     * @param observer - parametrem je observer (instance třídy, která pozoruje).
     */ 
    @Override
    public void deleteObserver(Observer observer) {
        listObserveru.remove(observer);
    }
    /**
     * Metoda notifikuje všechny observery = volá na ně metodu update().
     */ 
    @Override
    public void notifyAlllObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }

    }
}
