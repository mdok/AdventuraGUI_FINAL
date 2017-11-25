package logika;

import java.util.ArrayList;
import java.util.List;
import utils.Observer;
import utils.Subject;


/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 * 
 *  Nově třída HerniPlan implementuje rozhraní Subject a je předmětem pozorování pro observery ListVeciProstor,
 *  ListBatoh, ListVychody a Mapa. Notifikovat všechny pozorovatele je potřeba v případě přechodu
 *  mezi prostory herního plánu (zavolat metodu update() u všech observerů).
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Monika Dokoupilová
 *@version    2.0.0
 */
public class HerniPlan implements Subject{
    
    private Prostor aktualniProstor;
    private Prostor viteznyProstor;
    private Prostor zamceny; // ve hre se bude vyskytovat pouze 1 zamceny prostor .. lze jej definovat rovnou
    private Batoh batoh = new Batoh(); // vytvoreni herniho batohu ktery budou ostatni tridy nacitat pres get
    private boolean vyhra;
    
    private List<Observer> listObserveru = new ArrayList<Observer>();
    
     /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     *  
     *  
     */
    public HerniPlan() {
        zalozProstoryHry();
        this.vyhra = false; // nastavení proměnné která určuje výhru  na hodnotu false - na začátku hry nemůže být výhra
    }
    
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů. Vytváří a vkládá věci do prostorů. Vytváří a vkládá postavy do prostorů.
     *  Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor domecek = new Prostor("domeček","domeček, ve kterém bydlí Quido." + "\n" + "Je to sice skromný příbytek, ale útulný", 160,60);
        Prostor rybiTrh = new Prostor("rybí_trh", "Rybí trh vedle Quidova domečku.. Tady jsou ty nejlepší ryby! Ňam!"  + "\n" + "Čerstvá a voňavá rybiška na každičkém rohu!", 50,60);
        Prostor ustredna = new Prostor("ústředna","Ústředna. Je tu switch kam jsou připojeni všichni mistní zákazníci."  + "\n" + "Vše tu vypadá v pořádku.. všechny porty svítí.. žádný uvolněný kabel..",280,60);
        Prostor sidloISP = new Prostor("sídlo_ISP","Sídlo ISP. Tady sídlí místní poskytovatel telekomunikačních služeb."  + "\n" + "Jediný kdo tu je je mladý technik v monterkách...",280,150);
        Prostor hlubokyLes = new Prostor("hluboký_les","Hluboký les... Sakryš tolika stromů na jednom místě sem jakživ neviděl!...",150,150);
        Prostor parkoviste = new Prostor ("parkoviště","Parkoviště. Nachází se hned vedle datacentra. Je tu vrátný v letech. Má slušivou čapku",50,150);
        Prostor datacentrum = new Prostor ("datacentrum","Datacentrum. Zde se nachází všechny routery okolních ISP.",50,250);
        Prostor sidloKocicaka = new Prostor ("sídlo_Kočičáka","Sídlo velkého Kočičáka  největšího dodavatele modulů SFP+ široko daleko."  + "\n" + "Je tu Kočičák sám. Vypadá jako pravý gantleman",260,250);
        Prostor park = new Prostor ("park","Park. Sem chodí kočičí rodinky se svými koťátky, aby se provětrala. Teď je u ale prázdno",440,250);
        Prostor utulek = new Prostor ("útulek","Útulek pro ztracená koťátka. Posílají sem všechna ztracená koťátka."  + "\n" + "Na nástěnce je pár otisků tlapek nových nalezenců a jejich jména",440,100);
                
        // přiřazují se průchody mezi prostory (sousedící prostory)
        domecek.setVychod(rybiTrh);
        domecek.setVychod(ustredna);
        
        rybiTrh.setVychod(domecek);
        
        ustredna.setVychod(domecek);
        ustredna.setVychod(sidloISP);
        
        sidloISP.setVychod(ustredna);
        sidloISP.setVychod(hlubokyLes);
        
        hlubokyLes.setVychod(parkoviste);
        hlubokyLes.setVychod(sidloISP);
        
        parkoviste.setVychod(hlubokyLes);
        parkoviste.setVychod(datacentrum);
        
        datacentrum.setVychod(parkoviste);
        datacentrum.setVychod(sidloKocicaka);
        
        sidloKocicaka.setVychod(datacentrum);
        sidloKocicaka.setVychod(park);
        
        park.setVychod(sidloKocicaka);
        park.setVychod(utulek);
        
        utulek.setVychod(park);
        
       
       //DOMECEK !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       //veci
        Vec pocitac = new Vec ("počítač", false,"Obstojná mašinka... Zatím slouží dobře" );
        Vec penize = new Vec ("peníze", true,"Slušný obnos na studenta.. Za to bych mohl na rybím trhu koupit pořádnou rybu");
        Vec postel = new Vec ("postel", false,"Stará rozvrzaná postel");
        Vec miska = new Vec ("miska", true,"Prázdná miska na jídlo");
        Vec konzerva = new Vec ("konzerva", true,"Konzerva s kočičím žrádlem");
        
        //vlozeni veci do domecku
        domecek.vlozVec(pocitac);
        domecek.vlozVec(penize);
        domecek.vlozVec(postel);
        domecek.vlozVec(miska);
        domecek.vlozVec(konzerva);


       //RYBI TRH !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       //veci
        Vec losos = new Vec ("losos", true,"Velký šťavnatý čerstvý losos té nejvyšší kvality");
        Vec makrela = new Vec ("makrela", true,"Voňavá makrela");
        Vec pstruh = new Vec ("pstruh", true, "Urostlý pstruh");
        Vec pult = new Vec ("pult", false,"Pult s rybami všech moří");
        Vec kad = new Vec ("káď", false,"Káď plná ryb");
       
       // postavy
        Postava prodejce = new Postava("Prodejce","Čerstvé ryby!! Kupte rybu!! Chtěl by jsi rybu? Zaplať! Zadarmo ani kuře nehrabe!","Mohutný prodejce ryb.. chybí mu noha a očividně i vychování, ale má lososa");
       // nastaveni reci pro vymenu a veci
        prodejce.nastavVymenu(losos, penize, "Tímhle jako budeš platit? Ani náhodou!", "No výborně.. jeden čerství losos pro váženého zákazníka!","Čerstvé ryby!! Kupte rybu!! Čerstvé ryby!");
         
        
       //vlozeni veci na rib trh
        rybiTrh.vlozVec(makrela);
        rybiTrh.vlozVec(pstruh);
        rybiTrh.vlozVec(pult);
        rybiTrh.vlozVec(kad);

        //vlozeni postav na rybi trh
        rybiTrh.vlozPostavu(prodejce);
        
        //USTREDNA !!!!!!!!!!!!!!!!!!!!!!!!!!!
        //veci
        Vec prvekSwitch = new Vec ("switch", false,"Páni úplně nová cisco 3850G!" + "\n" +"Zkontroloval jsem všechny kabely zda nejsou uvolněné. Vše se tu zdá být v pořádku");
        Vec listecek = new Vec ("lísteček", true," Stojí tu: v případě problémů navštivte svého ISP");
        Vec klubicko = new Vec ("klubíčko", true, "Na hraní nemám teď bohužel čas... ale mohu si ho schovat na později");
        Vec malyRack = new Vec ("malý_rack", false, "Rack v němž je umístěn switch");
        
        //vlozeni veci do ustredny
        ustredna.vlozVec(prvekSwitch);
        ustredna.vlozVec(listecek);
        ustredna.vlozVec(klubicko);
        ustredna.vlozVec(malyRack);
        
        //SIDLO ISP !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //veci
        Vec kripovaciKleste = new Vec ("krimpovací_kleště", true,"Krimpovací kleště pro osazování konektorů na UTP kabely");
        Vec konektor = new Vec ("konektor", true,"RJ45 konektor");
        Vec stul = new Vec ("stůl", false,"Nový stůl v šedém provedení");
        Vec monterky = new Vec ("monterky", false,"Vzhledné modré monterky");
        Vec povoleni = new Vec ("povolení", true,"Povolenka na vstup do datacentra k racku ISP");
        
        // postavy
        Postava technik = new Postava("Technik","Tak tobě tedy nejde Internet jo? Já mám zas velký hlad a tak rád bych si dal lososa."  + "\n" + "Když mi ho doneseš poradím ti co dál.","Mladý, ale zkušený síťový technik");
       // nastaveni reci pro vymenu a veci
        technik.nastavVymenu(povoleni, losos, "Tohle že má být losos? Tak takhle teda ne kamaráde! Nejsem slepej!", "Ňam losos! to jsem si pošmáknul... Slíbil jsem ti radu viď?"  + "\n" + "Problém bude někde v datacentru u hlavního routeru."  + "\n" + "Aby ses tam dostal potřebuješ  mít povolení. Ná.. už sem ti ho napsal.","Tak co? Už to funguje?");
         
        //vlozeni veci do sidla ISP
        sidloISP.vlozVec(kripovaciKleste);
        sidloISP.vlozVec(konektor);
        sidloISP.vlozVec(stul);
        sidloISP.vlozVec(monterky);
        
        //vlozeni postav do sidla ISP
        sidloISP.vlozPostavu(technik);
        

        //HLUBOKY LES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //veci
        Vec mochomurka = new Vec ("mochomurka_zelená", true,"Zrádná houba je to!");
        Vec telefon = new Vec ("telefon", true,"Telefon NOKIA stará nerozbitelná kvalita");
        Vec lahev = new Vec ("láhev", true,"prazdná láhev od piva");
        Vec parez = new Vec ("pařez", false,"mohutný pařez");
        Vec pristupova_karta = new Vec ("přístupová_karta", true,"Díky tomuhle můžu do datacentra k racku ISP");
        
        
        //vlozeni veci do hlubokeho lesa
        hlubokyLes.vlozVec(mochomurka);
        hlubokyLes.vlozVec(telefon);
        hlubokyLes.vlozVec(lahev);
        hlubokyLes.vlozVec(parez);
        
        //PARKOVISTE
        // postavy
        Postava vratny = new Postava("Vrátný","Achjo... kde sakra je! Nemůžu najít svůj telefon." + "\n" + "Ty by jsi mi ale mohl pomoci...Když mi ho pomůžeš najít umožním ti projít vrátnicí do datacentra.","Starý vrátný...");
       // nastaveni reci pro vymenu a veci
        vratny.nastavVymenu(pristupova_karta, telefon, "Tohle přeci není můj telefon! Musíš ještě hledat!", "Dekuji! Konečně zas na drátě!... ná tady máš přístupovou kartu." + "\n" + " S tou se dostaneš přes vrátnici přímo do datacentra.","Tak jak mladej? Jak to jde?");
        
        //vloeni postav na parkoviste
        parkoviste.vlozPostavu(vratny);
        
 
        //DATACENTRUM !!!!!!!!!!!!!!!!!!!!!!!
        //veci
        Vec rack = new Vec ("rack", false,"Tenhle rack je mnohem větší než ten co sem ho viděl u ISP");
        Vec prvekRouter = new Vec ("router", false,"Router ISP.. zdá se, že je vadné SFP... nebliká");
        Vec spunty = new Vec ("špunty_do_uší", true," V sále je pěkný kravál nedivím se že tu jsou");
        
        //vlozeni veci do datacentra
        datacentrum.vlozVec(rack);
        datacentrum.vlozVec(prvekRouter);
        datacentrum.vlozVec(spunty);
        
        //uzamknuti mistnosti
        datacentrum.zamknout(true);
        
        //  UTULEK !!!!!!!!!!!!!!!!!!!
        //veci v utulku 
        Vec police = new Vec ("police", false,"Zaprášená police se spisy");
        Vec nastenka = new Vec ("nástěnka", false,"Je tu seznam nalezených koťátek");
        Vec otisk = new Vec ("otisk_tlapky_Kočičákova_koťátka", true,"Pod poličkou jsou otisky... vezmu si ten správný");
       
        utulek.vlozVec(police);
        utulek.vlozVec(nastenka);
        utulek.vlozVec(otisk);
        
        
        //SIDLO KOCICAKA !!!!!!!!!!!!!!!!!!!!!
        //veci
        Vec portret = new Vec ("portrét_velkého_Kočičáka_a_jeho_koťat", false,"Pěkný portrét ...olejomalba");
        Vec kreslo = new Vec ("křeslo", false," Takové bych taky chtěl vypadá pohodlně");
        Vec velkyStul = new Vec ("velký_bohatě_zdobený_dřevěný_stůl", false,"Stůl jako pro krále");
        Vec SFP = new Vec ("SFP", true,"Úplně nový modul SFP+ ten bude určitě fungovat!");
        
        //vlozeni veci do sidla Kocicaka
        sidloKocicaka.vlozVec(portret);
        sidloKocicaka.vlozVec(kreslo);
        sidloKocicaka.vlozVec(velkyStul);
        
        //postavy 
        Postava kocicak = new Postava("Kočičák","Beeeeeheeee!!! Kde je moje koťátko .. Beee!!. Pomůžeš mi ho najít prosím?!" + "\n" + "Jestliže mi pomůžeš najít mé ztracené koťátko, které se ztratilo při hře v parku, dám ti naoplátku nový modul SFP+.","Sedí v křesle");
       // nastaveni reci pro vymenu a veci
        kocicak.nastavVymenu(SFP, otisk, "Tohle mi přeci nijak nepomůže!", " Aháá tak tam je uličník jeden! Teď si ho tam mohu vyzvednout děkuji! " + "\n" + " Ná.. tady máš to SFPčko","Opět jedna velká šťastná rodinka! Jen díky tobě! ");
        
        //vloeni postav na parkoviste
        sidloKocicaka.vlozPostavu(kocicak);
        
        //PARK !!!!!!!!!!!!!!!!!!!!
        Vec socha = new Vec ("socha", false,"Socha kočičí bohyně Whiskas");
        Vec fontana = new Vec ("fontána", false,"Pěkná fontána s čerstvou vodou");
       
        park.vlozVec(socha);
        park.vlozVec(fontana);
        
      
        aktualniProstor = domecek;  // hra začíná v domečku  
        viteznyProstor = domecek; // a taky tam končí po provedení úspěšného příkazu výměna
        zamceny = datacentrum; // jediny zamceny prostor ve hre je datacentrum
    }
    
    
    /**
     * Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
        
    }
    
    /**
     * Metoda vrací zamčený prostor ( v případě této adventury je jediným zamčeným prostorem prostor datacentrum)
     * 
     * @return    zamčený prostor
     */
    public Prostor getZamceny(){ //metoda vraci zamceny prostor datacentrum aby mohla byt pouzita metoda datacentrum.zamceno(false) ve tride dej
        return zamceny;
    }
    
    
    /**
     * Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     * Nově také notifikuje všechny observery kdykoliv dojde ke změně aktuálního prostoru
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
       jeVyhra();
       notifyAlllObservers();
    }
    
    
    /**
     * Metoda zjišťuje zda se jedná o výhru ( zda metoda getVyhra vrací hodnotu true = proběhal úspěšná výměna modulu SFP)
     * pokud ano, zjišťuje ještě zda se aktuální prostor rovná vítěznému (domeček)
     *
     *@return výsledek porovnání
     */
    public boolean jeVyhra(){
        if(this.getVyhra() == true){
        return aktualniProstor.equals(viteznyProstor.getNazev());
       }
       return false;
    }
  
    /**
     * Metoda získává herní batoh = jeden batoh pro celou hru jeho stav se getuje a nevytváří se nový v každé třídě. Všechnty třídy sdílí jeden batoh.
     * 
     *@return instance třídy batoh
     */
    public Batoh getBatoh(){
        return this.batoh;
        
    }

    /**
     * Metoda zjišťuje zda se jedná o výhru
     * 

     *@return hodnota proměnné výhra
     */
    public boolean getVyhra(){
       return this.vyhra; 
    }
    /**
     * Metoda zjišťuje vrací vítězný prostor 
     * 

     *@return hodnota proměnné výhra
     */
    public Prostor getVitezny(){
       return this.viteznyProstor; 
    }
    
    /**
     * Metoda nastavuje výhru (použito ve třídě PrikazVymen), při úspěšné výměně modulu SFP se vyhra nastavi na tru a je umožněno odevzdat úkol nejedná se tedy zatím
     * o skutečnou výhru ta se zjišťuje v metodě jeVyhra().
     * 
     * @param vyhra ano/ne true/false
     */
    public void setVyhra(boolean vyhra){
       this.vyhra = vyhra;
    }
    /**
     * Metoda registruje observera k pozorování událostí týkajícíh se herního plánu.
     * @param observer - parametrem je observer (instance třídy, která pozoruje).
     */ 
    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }
    /**
     * Metoda ruší registraci observera k pozorování událostí týkajícíh se herního plánu.
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
