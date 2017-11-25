/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import logika.IHra;

/**
 *  Rozhraní určující metody, které musí obsahovat všechny třídy implementující dané rozhraní.
 *
 *@author Monika Dokoupilová
 *@version 1.0.0
 */
public interface Observer {
    /**
     * Metoda refreshující stav observera vzhledem ke změnám, které se udály s pozorovaným subjektem.
     */
    void update();
     
    /**
     * Metoda regujicí na zavolání nové hry (přeregistrování observerů)
     * @param hra - instance hry
     */
    void novaHra(IHra hra);
    
}
