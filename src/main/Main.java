/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import UI.ListBatoh;
import UI.ListVeciProstor;
import UI.ListVychody;
import UI.Mapa;
import UI.MenuPole;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.Hra; 
import logika.IHra;
import uiText.TextoveRozhrani;

/**
 * Třída Main realizuje události, které nastanou po spuštění hry. 
 * Rozšiřuje třídu Application.
 * 
 * @author Monika Dokoupilová 
 * @version 1.0.0
 */
public class Main extends Application {
    
    private Mapa mapa;
    private MenuPole menu;
    private IHra hra;
    private TextArea centerText; // globalni promenne dostupne v cele tride - v netbeans jsou zelene / plati tedy pro celou main tridu
    private Stage primaryStage; // globalni promena v ramci tridy main - abychom se na ni mohli dostat pres instance tridy main v jinych tridach jako je menuPole (main.getPrimaryStage())
    
    private ListVychody listVychody;
    private ListBatoh listBatoh;
    private ListVeciProstor listVeciProstor;
    
    /**
     * Metoda vytváří hlavní jeviště hry a vkládá na něj objekty.
     */ 
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        hra= new Hra(); // spusti hru
        mapa = new Mapa(hra);
        menu = new MenuPole(this);
        centerText = new TextArea();
        
        listVychody= new ListVychody(hra,centerText);
        listBatoh= new ListBatoh(hra,centerText);
        listVeciProstor = new ListVeciProstor(hra,centerText);
        
        BorderPane borderPane = new BorderPane();
  
        centerText.setText(hra.vratUvitani());
        centerText.setEditable(false);
        borderPane.setCenter(centerText);
       
        Label zadejPrikazLabel = new Label("Zadej příkaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial",FontWeight.BOLD, 16));

        TextField zadejPrikazTextField = new TextField("Sem zadej příkaz");
        zadejPrikazTextField.setMinWidth(200);
        zadejPrikazTextField.setOnAction(new EventHandler<ActionEvent>() { //po entru

            @Override
            public void handle(ActionEvent event) {
                String zadanyPrikaz = zadejPrikazTextField.getText();
                String odpoved = hra.zpracujPrikaz(zadanyPrikaz);
                
                centerText.appendText("\n"+zadanyPrikaz+"\n");
                centerText.appendText("\n"+odpoved+"\n");

                zadejPrikazTextField.setText("");
                
                if(hra.konecHry()){
                    zadejPrikazTextField.setEditable(false);
                }
                
            }
            
        });
        
        Button napoveda = new Button();
        napoveda.setText("Nápověda");
        napoveda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String odpoved = hra.zpracujPrikaz("nápověda");
                centerText.appendText("\n"+odpoved+"\n");
            }
        } );
        
    
        FlowPane flowPaneLeft = new FlowPane();
        FlowPane flowPaneLD =new FlowPane();
        FlowPane flowPaneLDD =new FlowPane();
        FlowPane flowPaneLC=new FlowPane();
        FlowPane flowPaneLT=new FlowPane();
        
        FlowPane flowPaneRight =new FlowPane();
        flowPaneRight.setMaxWidth(100);
        flowPaneRight.setAlignment(Pos.TOP_CENTER);
   
        FlowPane dolniPanel = new FlowPane();
        dolniPanel.setAlignment(Pos.CENTER);
        dolniPanel.getChildren().addAll(zadejPrikazLabel,zadejPrikazTextField);
        
        Label labelBatoh = new Label("Batoh (max. 3 itemy): ");
        Label labelPlanek = new Label("Plánek hry: ");
        Label labelVeciProstor = new Label("Věci v prostoru: ");
        Label labelVychody = new Label("Východy: ");
        
        labelBatoh.setFont(Font.font("Arial",FontWeight.BOLD, 16));
        labelPlanek.setFont(Font.font("Arial",FontWeight.BOLD, 16));
        labelVeciProstor.setFont(Font.font("Arial",FontWeight.BOLD, 16));
        labelVychody.setFont(Font.font("Arial",FontWeight.BOLD, 16));
        
        labelBatoh.setPrefHeight(50);
        labelVeciProstor.setPrefHeight(50);
        
        labelBatoh.setAlignment(Pos.BOTTOM_LEFT);

        flowPaneRight.getChildren().addAll(napoveda,labelVeciProstor,listVeciProstor);
        flowPaneLD.getChildren().addAll(labelBatoh);
        flowPaneLDD.getChildren().addAll(listBatoh);
        flowPaneLC.getChildren().addAll(labelVychody,listVychody);
        flowPaneLT.getChildren().addAll(labelPlanek, mapa);
        flowPaneLeft.getChildren().addAll(flowPaneLT,flowPaneLC,flowPaneLD,flowPaneLDD);
        
        borderPane.setLeft(flowPaneLeft);
        borderPane.setRight(flowPaneRight);
        borderPane.setTop(menu); 
        borderPane.setBottom(dolniPanel);
        Scene scene = new Scene(borderPane, 1200, 600); // vztvoreni nove sceny a vloyeni tlacitka (pane) do sceny , sirka vyska
        
        primaryStage.setTitle("Moje adventura"); // title sceny
        primaryStage.setScene(scene); // vlozeni sceny na stage 
        primaryStage.show(); // zobrazeni sceny
        
        zadejPrikazTextField.requestFocus(); // po spusteni adventury lze rovnou psat do pole pro text
        zadejPrikazTextField.autosize();
    }

    /**
     * Metoda realizuje spuštění hry.
     * V případě zadání parametru -text se hra spustí v textovém režimu.
     * @param args parametry příkazové řádky
     */
    public static void main(String[] args) {
        if(args.length == 0){ //spusti v gui
            launch(args); //launch(args); //zavola metodu start
        }else{
            if(args[0].equals("-text")){ // spusti v textovem rozhrani pri nastaveni parametru -text
                IHra hra = new Hra();
                TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
                textoveRozhrani.hraj();

            }else{
                System.out.println("Neplatny parametr");
                System.exit(1);
            }
        }
  
    }
    
    /**
     * Metoda realizuje spuštění nové hry.
     */
    public void novaHra() {
        hra = new Hra();
        centerText.setText(hra.vratUvitani());
        mapa.novaHra(hra);
        listVychody.novaHra(hra);
        listVeciProstor.novaHra(hra);
        listBatoh.novaHra(hra);
    }
    
    /**
     * Metoda vrací prmaryStage.
     * @return  primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
  
}
