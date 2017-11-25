/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import logika.IHra;
import logika.Prostor;
import main.Main;
import utils.Observer;

/**
 * Třída ListVychody realizuje výpis možných východů z aktuálního prostoru formou tlačítek. 
 * Rozšiřuje HBox a implementuje rozhraní Observer.
 * Sleduje a reaguje na změnu aktuálního prostoru(přehod mezi prostory).
 * 
 * 
 * @author Monika Dokoupilová 
 * @version 1.0.0
 */
public class ListVychody extends HBox implements Observer{
    private IHra hra;
    private Button vychod;
    private TextArea text;
    
    public ListVychody(IHra hra, TextArea text){
        this.hra=hra;
        hra.getHerniPlan().registerObserver(this);
        hra.getHerniPlan().getAktualniProstor().registerObserver(this);
        hra.getHerniPlan().getBatoh().registerObserver(this);
        
        this.text=text;
        init();
      
    }
     /**
     * Metoda inicializující HBox, do kterého jsou pozděj vkládány tlačítka s názvy možných východů.
     */
    private void init(){
            HBox vychodyList = new HBox();
            vychodyList.setAlignment(Pos.BOTTOM_CENTER);
            vychodyList.setPadding(new Insets(10));
            vychodyList.setSpacing(8);
           
            update();
            
            
    }
    /**
     * Metoda zajišťující přehrání zvuku při přechodu do sousedního prostoru (kliknutí na tlačítko prostoru).
     * Metoda je volána v rámci EventHandleru, který se provede po kliknutí na tlačítko prostoru.
     */
    private void playSound(){
       
        String sound = Main.class.getResource("/zdroje/presunSound.mp3").toString();
        Media presunSound = new Media(sound);
        MediaPlayer mediaPlayer = new MediaPlayer(presunSound);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();  
    }
    /**
     * Metoda Update provádí refresh listu tlačítek (východy). Je volána kdykoliv dojde k přechodu do 
     * jiného prostoru a také při zavolání nové hry.
     * Nejprve se vyčistí původní seznam východů, následně se zjístí možné východy, pak se pro každý 
     * východ zjístí jejích název, který se stane textem tlačítka.Tlačítko je následně přidáno do HBoxu.
     * Pokud dojde ke kliknutí na tlačítko zavolá se EventHandler, který zpracuje příkaz jdi název_prostoru
     * a centerText metody main rozšíří o slovní podobu příkazu a odpověď, kterou hra na zpracovaní příkazu
     * vrátila. Následně je zavolána místní metoda playSound(), která přehraje příslušný zvuk.
     */
    @Override
    public void update() {
        this.getChildren().clear(); 
        Collection<Prostor> seznamVychody = hra.getHerniPlan().getAktualniProstor().getVychody(); 
            
        for (Prostor seznamVychodyItem : seznamVychody) { 
            vychod = new Button(); 
            vychod.setText(seznamVychodyItem.getNazev()); 
            this.getChildren().add(vychod); 
                
            vychod.setOnAction(new EventHandler<ActionEvent>() {
                
                @Override 
                public void handle(ActionEvent e) {
                      
                    String zadanyPrikaz = "jdi"+" "+seznamVychodyItem.getNazev();
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
        hra.getHerniPlan().deleteObserver(this); // pred prirazenim nove instance observeru k pozorovani se musi observer z puvodni instance odregistrovat !! *** totez pro ostatni
        this.hra = hra; // puvodni globalni promenna hra teto tridy - jeji obsah se nahradi novou hrou vytvorenou(predanou) zde
        hra.getHerniPlan().registerObserver(this);
        update();
    }
    
}
