/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.Set;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logika.IHra;
import logika.Vec;
import main.Main;
import utils.Observer;

/**
 * Třída ListBatoh realizuje výpis věcí v batohu formou obrázků. Rozšiřuje HBox a implementuje rozhraní Observer.
 * Sleduje a reaguje na změnu stavu batohu (přidání a odebrání věci) a aktuálního prostoru.
 * 
 * @author Monika Dokoupilová 
 * @version 1.0.0
 */
public class ListBatoh extends HBox implements Observer{
    private IHra hra;
    private TextArea text;

    
    public ListBatoh(IHra hra, TextArea text){
        this.hra=hra;
        hra.getHerniPlan().getBatoh().registerObserver(this);
        hra.getHerniPlan().registerObserver(this);
        hra.getHerniPlan().getAktualniProstor().registerObserver(this);

        this.text=text;
        init();
      
    }
    
    /**
     * Metoda inicializující HBox, do kterého jsou pozděj vkládány obrázky sebraných předmětů.
     */
    private void init(){
            HBox veciBatohList = new HBox();
            veciBatohList.setPadding(new Insets(10));
            veciBatohList.setSpacing(8);

            update();
            
            
    }
    /**
     * Metoda zajišťující přehrání zvuku při odebrání předmětu (kliknutí na obrázek předmětu).
     * Metoda je volána v rámci EventHandleru, který se provede po kliknutí na obrázek.
     */
    private void playSound(){
       
        String sound = Main.class.getResource("/zdroje/vyhodSound.mp3").toString();
        Media soundVyhod = new Media(sound);
        MediaPlayer mediaPlayer = new MediaPlayer(soundVyhod);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();  
    }
    
    /**
     * Metoda Update provádí refresh listu obrázků (itemy v batohu). Je volána kdykoliv dojde k odebrání
     * nebo sebrání věci (kdykoliv se aktualizuje batoh) a také při zavolání nové hry.
     * Nejprve se vyčistí původní seznam věcí, následně se zjístí stav věcí v batohu, pak se pro každou 
     * věc zjístí její název podle kterého se ve složce zdroje najde stejně pojmenovaný obrázek.Obrázek je 
     * násleně přidán do HBoxu.
     * Pokud dojde ke kliknutí na obrázek zavolá se EventHandler, který zpracuje příkaz odeber název_věci
     * a centerText metody main rozšíří o slovní podobu příkazu a odpověď, kterou hra na zpracovaní příkazu
     * vrátila. Následně je zavolána místní metoda playSound(), která přehraje příslušný zvuk.
     */
    @Override
    public void update() {
        this.getChildren().clear(); 
        Set<Vec> seznamBatoh = hra.getHerniPlan().getBatoh().getVeciVBatohu(); 
            
        for (Vec seznamBatohItem : seznamBatoh) { 
            String obrazekNazev =seznamBatohItem.getNazev();
            ImageView obrazek = new ImageView( new Image(Main.class.getResourceAsStream("/zdroje/"+obrazekNazev+".png"),50,50,false,false));
            Tooltip.install(obrazek, new Tooltip(obrazekNazev));
            this.getChildren().addAll(obrazek); 
                 
            obrazek.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                
                @Override 
                public void handle(MouseEvent event) {
                    String zadanyPrikaz = "odeber"+" "+obrazekNazev;
                    String odpoved = hra.zpracujPrikaz(zadanyPrikaz);
                    
                    text.appendText("\n"+zadanyPrikaz);
                    text.appendText("\n"+odpoved);
                           
                    hra.zpracujPrikaz(zadanyPrikaz);
                    
                    playSound();
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
        hra.getHerniPlan().deleteObserver(this); // pred prirazenim nove instance observeru k pozorovani se musi observer z puvodni instance odregistrovat 
        hra.getHerniPlan().getBatoh().deleteObserver(this);
        this.hra = hra; // puvodni globalni promenna hra teto tridy - jeji obsah se nahradi novou hrou vytvorenou(predanou) zde
        hra.getHerniPlan().registerObserver(this);
        hra.getHerniPlan().getBatoh().registerObserver(this);
        update();
    }
    
}

