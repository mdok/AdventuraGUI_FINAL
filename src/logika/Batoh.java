package logika;
import java.util.*;
import utils.Observer;
import utils.Subject;



/**
 * Třída batoh popisuje herní batoh a jeho metody. Intance této třídy je vytvořena ve třídě herní plán,
 * která také obsahuje metodu getBatoh(), jež slouží pro sdílení jednoho stejného batohu ve všech třídách
 * hry a umožňuje jim tak s batohem manipulovat pomocí metod definovaných v této třídě.
 * Nově třída Batoh implementuje rozhraní Subject a je předmětem pozorování pro observery ListVeciProstor a
 * ListBatoh. Notifikovat všechny pozorovatele je potřeba v případě odebrání věci z batohu a při přidání věci
 * do batohu (zavolat metodu update() u všech observerů).
 * 
 * @author Monika Dokoupilová 
 * @version 2.0.0
 */
public class Batoh implements Subject
{
     private Set<Vec> veciVBatohu ;
     private int limit = 3; // limit batohu jsou 3 věci
    
     private List<Observer> listObserveru = new ArrayList<Observer>();


    /**
     * Batohu je definován limit věcí, které může obsahovat a je realizován jako HashSet.
     */
    public Batoh()
    {
        veciVBatohu = new HashSet<>();// zalozeni seznamu veci
        this.limit = 3; //limit batohu

    }

    /**
     * Metoda vkládá věc do batohu a ověřuje pomocí metody jePlno() zda batoh již není plný.
     * Návratová hodnota je typu boolean. V případě úspěšného vložení vrací true. V opačném případě false.
     * Při vložení věci do batohu se notifikují observeři.
     * @param vec - věc vkládaná do batohu
     * @return vrací výsledek vložení true/false
     */
    public boolean vlozDoBatohu(Vec vec)
    {
       if(this.jePlno() == false){
        veciVBatohu.add(vec);
        notifyAlllObservers();
        return true;
       }
       else{
           return false;
        }
    }
    
     /**
     * Metoda odebírá věc daného názvu z batohu. Postupně prochází věci v batohu pomocí cyklu foreach,
     * zjišťuje jejich název a porovnává jej s názvem věci, kterou se hráč snaží odebrat z batohu.
     * V případě shody se věc odebere z batohu a metoda vrátí hodnotu true. V opačném případě vrací false.
     * Při odebrání věci z batohu se notifikují observeři.
     * @param nazev - název odebírané věci
     * @return vrací výsledek odebrání
     */
    public boolean odeberZBatohu(String nazev){ 
      Vec odebirana = null;

     for(Vec vec: veciVBatohu){
       if(nazev.equals(vec.getNazev())){
          odebirana = vec;  
          veciVBatohu.remove(odebirana);
          notifyAlllObservers(); 
          return true; 
       }
     }
    
     notifyAlllObservers(); 
     return veciVBatohu.remove(odebirana); 
      
    }
  
    /**
     * Metoda vypisuje názvy věcí v batohu opět pomocí cyklu foreach.
     * 
     * @return vrací výpis věcí v batohu
     */
    public String vypisVeci(){
       //vypis veci v batohu
       String vypis = "Věci v batohu:"+"\n";
        for (Vec vec : veciVBatohu) {
            vypis += " " + vec.getNazev()+"\n";
        }
        return vypis;
       
    }
   
    /**
     * Metoda testuje zda je batoh plný. 
     * 
     * @return vrací true pokud je batoh plný a false pokud ne
     */
    public boolean jePlno(){ //testovani kapacity batohu
       if (veciVBatohu.size() < limit){
           return false;
        }
       return true;
    }
    
    /**
     * Metoda vrací proměnou limit, která reprezentuje limit batohu = kolik věcí lze maximálně do batohu vložit
     * 
     * @return vrací hodnotu proměnné limit 
     */
    public int getLimit(){ //testovani kapacity batohu
       return this.limit;
    }
    
    /**
     * Metoda vrací věc z batohu na základě zadaného názvu věci a jeho porovnávání s názvy věcí v batohu 
     * realizovaného pomocí cyklu foreach.
     * 
     * @param nazev - název hledané věci v batohu
     * @return vrací instanci třídy Vec, konkrétně hledanou věc
     */ 
    public Vec getVec(String nazev){  //vraceni veci  z batohu podle nazvu
        Vec hledana = null;
      for(Vec vec: veciVBatohu){
       if(nazev.equals(vec.getNazev())){
          hledana = vec;               
       }  
      }
      return hledana;
    }
    
    
    /**
     * Metoda vrací set věcí v batohu.
     * 
     * @return vrací set věcí v batohu.
     */ 
    public Set<Vec> getVeciVBatohu(){ //testovani kapacity batohu
       return this.veciVBatohu;
    }
    /**
     * Metoda registruje observera k pozorování událostí týkajícíh se batohu.
     * @param observer - parametrem je observer (instance třídy, která pozoruje).
     */ 
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }
    /**
     * Metoda ruší registraci observera k pozorování událostí týkajícíh se batohu.
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
