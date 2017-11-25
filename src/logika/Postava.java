package logika;


/**
 * Třída postava - popisuje postavy hry a jejich společné vlastnosti (metody). Naprosou většinu těchto metod
 * využívá příkaz dej k ošetření všech situací, které mohou nastat.
 * S postavami si lze vyměňovat předměty.
 * 
 * @author Monika Dokoupilova 
 * @version 1.0.0
 */
public class Postava
{

    private String jmeno;
    private String popis; // popis postavy
    private Vec coChce; // věc co požaduje aby byla výměna úspěšná
    private Vec coMa; // věc co předá hráči v případě úspěšné výměny
    private String mluvPred; // řeč před výměnou
    private String mluvPo; // řeč po výměně
    private String recNechce; // řeč v případě, že věc nechce
    private String recChce; // řeč v případě, že věc chce
    private boolean probehlaVymena=false; // proměnná typu boolean, která určuje zda již proběhla výměna
    
    /**
     * Konstruktor pro objekty ktřídy postava. Každá postava má:
     *  - jméno typu String
     *  - proslov typu String, který se vypíše při provedení příkazu promluv jméno_postavy
     *  - popis typu String, který se vypíše při provedení příkazu prozkoumat jmeno_postavy
     * @param jmeno - jmeno postavy
     * @param mluvPred - to co rekne pred akci
     * @param popis - popis postavy
     */
    public Postava(String jmeno, String mluvPred, String popis)
    {
        this.jmeno = jmeno;
        this.mluvPred = mluvPred;
        this.popis = popis;
    }
    
    /**
     * Metoda, která nastavuje postavě parametry pro výměnu
     *  
     * @param coMa = to co má postava u sebe
     * @param coChce = to co pořaduje od hráče
     * @param recNechce = to co hráči sdělí pokud se hráč pokusí postavě dát věc, kterou nechce
     * @param recChce = to co se vypíše pokud je výměna úspěšná
     * @param mluvPo = to co se vypíše při použití příkazu promluv jmeno_postavy po úspěšné výměně
     */
    public void nastavVymenu(Vec coMa, Vec coChce, String recNechce, String recChce, String mluvPo){
      this.coMa = coMa;
      this.coChce = coChce;
      this.recNechce = recNechce;
      this.recChce = recChce;
      this.mluvPo = mluvPo;  
    }
    
    /**
     * Metoda, která realizuje výměnu. Vstupním parametrem této metody je věc, kterou hráč postavě nabízí.
     * Pokud se název nabízené věci shoduje s názvem požadované věci, pak se proměnná probehlaVymena, která
     * určuje zda proběhla výměna, nastaví na hodnotu true.
     * Mtedo vrací věc, kterou má postava u sebe.
     * Pokud se však názvy neshodují vrátí hodnotu null.
     *  
     * @param nabidka - nabízená věc
     * @return vrací buďto věc co postava má (coMa) nebo null v případě neúspěšné výměny
     */
    public Vec vymena(Vec nabidka){
        if (nabidka.getNazev().equals(coChce.getNazev())){
           probehlaVymena = true;
           return coMa; 
           
        }
        return null;
    }
    
    /**
     * Metoda Vrací jméno postavy.
     * 
     * @return vrací jméno postavy
     */
    public String getJmeno()
    {
        return this.jmeno;
    }
    
    /**
     * Vrací reč postavy pro úspěšnou výměnu v případě, že se proměnná probehlaVymena rovná true, čili 
     * v případě, že výměna byla úspěšná. V opačném případě vrací řeč pro neúspěšnou výměnu.
     * 
     * @return vrací reč postavy pro obě varianty výměny (úspěšná/neúspěšná)
     */
    String getRecVymena(){
       if (probehlaVymena == true) {
         return this.recChce;  
       }
       return this.recNechce;
    }
    
    /**
     * Metoda vrací popis postavy.
     * 
     * @return vrací popis postavy
     */
     public String getPopis()
    {
       return this.popis;
    }
    
    /**
     * Vrací řeč postavy po provedení příkazu promluv jméno_postavy v závislosti na tom, zda proběhla výměna.
     * 
     * @return vrací řeč postavy v závislosti na proměnné probehlaVymena
     */
    public String getMluv(){ // klasicka rec
      if (probehlaVymena == false) {
          return this.mluvPred;  
        }
      return this.mluvPo;
    }
    
    /**
     * Vrací hodnotu proměnné proběhla výměna.
     * 
     * @return vrací hodnotu proměnné probehlaVymena (true/false)
     */
    public boolean getProbehlaVymena(){ // pro zjisteni u prikazu dej zda vymena jiz neprobehla
      return this.probehlaVymena; 
    }
    
    /**
     * Dvě postavy jsou shodné jestliže mají stejné jméno.
     * 
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaná postava stejné jméno, jinak false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Postava) {
            Postava druha = (Postava) o;
            return jmeno.equals(druha.jmeno);
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
        return jmeno.hashCode();
    }
    
}
