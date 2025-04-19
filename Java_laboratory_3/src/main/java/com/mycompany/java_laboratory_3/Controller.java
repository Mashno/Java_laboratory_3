/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_laboratory_3;

import java.io.File;
import Handler.*;
import Monsters.Monster;
import java.util.ArrayList;
/**
 *
 * @author Владислав
 */
public class Controller {
    //private GroupOfMonster monsters;
    private ExportingFiles exporterFiles;
    private ArrayList<Monster> monsterList;
    
    public Controller(){
        new View(this).setVisible(true);
        this.exporterFiles = new ExportingFiles();
        this.monsterList = new ArrayList<>();
    }
    
    public void ImportFile(File file){
        Handler xmlHandler =  new XMLHandler();
        Handler yamlHandler =  new YAMLHandler();
        Handler jsonHandler =  new JSONHandler();
        
        xmlHandler.setNext(yamlHandler);
        yamlHandler.setNext(jsonHandler);

        xmlHandler.handle(file, monsterList);
        //получили список - добавили его в группу списков и отчистили
    }
    
//    public void ExportFileXML(File file){
//        exporterFiles.ExportXML();
//    }
//    
//    public void ExportFileYAML(File file){
//        exporterFiles.ExportYAML();
//    }
//    
//    public void ExportFileJSON(File file){
//        exporterFiles.ExportJSON();
//    }
    
}
