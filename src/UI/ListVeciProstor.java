/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import logika.IHra;
import logika.Vec;
import main.Main;
import utils.Observer;

/**
 * Třída ListProstor realizuje výpis věcí v prostoru formou obrázků. Rozšiřuje VBox a implementuje rozhraní Observer.
 * Sleduje a reaguje na změnu stavu batohu(pridání a odebrání věci) a aktuálního prostoru (odebrání a přidání věci).
 * 
 * 
 * @author Monika Dokoupilová 
 * @version 1.0.0
 */
public class ListVeciProstor extends VBox implements Observer{
    private IHra hra;
    private TextArea text;
    
    public ListVeciProstor(IHra hra, TextArea text){
        this.hra=hra;
        hra.getHerniPlan().getAktualniProstor().registerObserver(this);
        hra.getHerniPlan().registerObserver(this);
        hra.getHerniPlan().getBatoh().registerObserver(this);
        
        this.text=text;
        init();
      
    }
    /**
     * Metoda inicializující VBox, do kterého jsou pozděj vkládány obrázky předmětů v prostoru 
     * (včetně těch vyhozených z batohu).
     */
    private void init(){
        VBox veciProstorList = new VBox();
        veciProstorList.setAlignment(Pos.CENTER);
        veciProstorList.setPadding(new Insets(10));
        veciProstorList.setSpacing(8);
            
        update();
            
            
    }
    /**
     * Metoda zajišťující přehrání zvuku při sebrání předmětu (kliknutí na obrázek předmětu).
     * Rozlišuje zvuk při kliknutí na obrázek sebratelného i nesebratelného předmětu.
     * Metoda je volána v rámci EventHandleru, který se provede po kliknutí na obrázek.
     * @param prenositelnost - datový typ boolean (true/false)
     */
    private void playSound(Boolean prenositelnost, Boolean jePlno){
       String soundString="";

       if (prenositelnost == false || jePlno == true){ 
           soundString= "seberNelzeSound.mp3";  
           
        }
       else{
            soundString="seberSound.mp3";
       }
                
        String sound = Main.class.getResource("/zdroje/"+soundString).toString();
        Media soundPlay = new Media(sound);
        MediaPlayer mediaPlayer = new MediaPlayer(soundPlay);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();  
    }
    /**
     * Metoda Update provádí refresh listu obrázků (itemy v prostoru). Je volána kdykoliv dojde k odebrání
     * nebo sebrání věci (kdykoliv se aktualizuje stav věcí v prostoru) a také při zavolání nové hry.
     * Nejprve se vyčistí původní seznam věcí, následně se zjístí stav věcí v prostoru, pak se pro každou věc 
     * v prostoru zjístí její název podle kterého se ve složce zdroje najde stejně pojmenovaný obrázek.
     * Obrázek je násleně přidán do VBoxu.
     * Pokud dojde ke kliknutí na obrázek zavolá se EventHandler, který zpracuje příkaz seber název_věci
     * a centerText metody main rozšíří o slovní podobu příkazu a odpověď, kterou hra na zpracovaní příkazu
     * vrátila. Následně je zavolána místní metoda playSound() s parametrem určujícím přenositelnost věci,
     * která přehraje příslušný zvuk (sebrání přenositelné věci nebo pokus sebrat věc nepřenositelnou).
     */
    @Override
    public void update() {
        this.getChildren().clear(); 
        Map<String,Vec> seznamVeciProstor = hra.getHerniPlan().getAktualniProstor().getVeciVProstoru(); 
            
        for (String seznamVeciProstorItem : seznamVeciProstor.keySet()) { 
                 
            String obrazekNazev = seznamVeciProstorItem;
            ImageView obrazek = new ImageView( new Image(Main.class.getResourceAsStream("/zdroje/"+obrazekNazev+".png"),50,50,false,false));
            Tooltip.install(obrazek, new Tooltip(obrazekNazev));
            this.getChildren().addAll(obrazek); 
                 
            obrazek.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                
                @Override 
                public void handle(MouseEvent event) {
                    Boolean prenositelnost = seznamVeciProstor.get(obrazekNazev).jePrenositelna(); // musi byt zjisteno pred zpracováním příkazu (pak uz zjistit nelze nebot vec uz neni v prostoru ale v batohu)
                    Boolean jePlno = hra.getHerniPlan().getBatoh().jePlno(); // -//-

                    String zadanyPrikaz = "seber"+" "+obrazekNazev;
                    String odpoved = hra.zpracujPrikaz(zadanyPrikaz); 
                           
                    text.appendText("\n"+zadanyPrikaz);
                    text.appendText("\n"+odpoved);
                           
                    hra.zpracujPrikaz(zadanyPrikaz);
                           
                    playSound(prenositelnost,jePlno);
                           
                }
            });
               
        }   
    }
     /**
     * Metoda zajišťující přeregistrování observerů (změna ukazatelů z původních míst v paměti na nová, přiřazená
     * nové instanci hry)
     */
    @Override
    public void novaHra(IHra hra) {
        hra.getHerniPlan().deleteObserver(this); // pred prirazenim nove instance observeru k pozorovani se musi observer z puvodni instance odregistrovat !! *** totez pro ostatni
        hra.getHerniPlan().getBatoh().deleteObserver(this);
        this.hra = hra; // puvodni globalni promenna hra teto tridy - jeji obsah se nahradi novou hrou vytvorenou(predanou) zde
        hra.getHerniPlan().registerObserver(this);
        hra.getHerniPlan().getBatoh().registerObserver(this);
        update();
    }
    
}
