/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_laboratory_3;

import java.io.File;
import Handler.*;
/**
 *
 * @author Владислав
 */
public class Controller {
    //private GroupOfMonster monsters;
    public void ImportFile(File file){
        Handler xmlHandler =  new XMLHandler();
        Handler yamlHandler =  new YAMLHandler();
        Handler jsonHandler =  new JSONHandler();
        
        xmlHandler.setNext(yamlHandler);
        yamlHandler.setNext(jsonHandler);
        //в строке ниже будет возврашаться хешмап где ключ - название расширения, значение - список монстров
        xmlHandler.handle(file);
    }
    
    public Controller(){
        new View(this).setVisible(true);
    }
}
