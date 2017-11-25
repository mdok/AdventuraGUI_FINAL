/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/*******************************************************************************
 *  Třída Věc - třída představující věci ve hře a jejich vlastnosti (přenositelná/nepřenositelná)
 * 
 *  
 * @author    Monika Dokoupilová
 * @version   1.0.0
 */
public class Vec
{
    private String nazev; // název věci
    private String popis; // popis věci
    private boolean prenositelnost;
    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     * Každá věc má název typu String, popis typu String a přenositelnost (ano/ne) typu boolean
     * @param nazev - nazev veci
     * @param prenositelnost - urceni prenositelnosti veci
     * @param popis - popis veci
     */
    public Vec(String nazev , boolean prenositelnost, String popis ) 
    {
        this.nazev = nazev;
        this.prenositelnost = prenositelnost;
        this.popis = popis;
    }
    
    /**
     * Metoda vrací název věci
     *  
     *@return vrací název věci
     */
    public String getNazev(){
       return nazev;
    }
    
    /**
     * Metoda vrací popis věci
     *  
     *@return vrací popis věci
     */
    public String getPopis(){
       return popis;
    }
    
    /**
     * Metoda nastavuje popis věci 
     * 
     * @param popis - popis věci
     */
    public void setPopis(String popis){
       this.popis = popis;
    }
    
    /**
     * Metoda určuje zda je věc přenositelná či nikoliv
     * 
     *@return vrací hodnotu proměnné přenositelnost (true = přenositelná, false = nepřenositelná)
     */
    public boolean jePrenositelna(){
        return prenositelnost;
        
    }

    /**
     * Dvě věci jsou shodné jestliže se shoduje jejich název
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaná věc stejný název, jinak false
     */  
    @Override
    public boolean equals(Object o) {
        if (o instanceof Vec) {
            Vec druha = (Vec) o;
            return nazev.equals(druha.nazev);
        }
        return false;
    }
    
    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     * 
     * @return čísledný identifikátor instance
     */
    @Override
    public int hashCode() {
        return nazev.hashCode();
    }

}
