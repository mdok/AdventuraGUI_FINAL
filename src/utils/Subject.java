/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *  Rozhraní určující metody, které musí obsahovat všechny třídy implementující dané rozhraní.
 *
 *@author Monika Dokoupilová
 *@version 1.0.0
 */
public interface Subject {
    /**
     * Metoda registruje observera k pozorování událostí týkajícíh se daného subjektu.
     * @param observer - parametrem je observer (instance třídy, která pozoruje).
     */ 
    void registerObserver(Observer observer); // nemusi pred nimi byt public v interface jsou vsechny metody public
    
    /**
     * Metoda ruší registraci observera k pozorování událostí týkajícíh daného subjektu
     * @param observer - parametrem je observer (instance třídy, která pozoruje).
     */ 
    void deleteObserver(Observer observer);
    
    /**
     * Metoda notifikuje všechny observery = volá na ně metodu update().
     */ 
    void notifyAlllObservers();
}
