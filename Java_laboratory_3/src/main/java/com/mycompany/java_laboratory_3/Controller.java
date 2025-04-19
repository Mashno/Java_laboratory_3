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
        for (int i = 0; i < monsterList.size(); i++) {
            Monster monster = monsterList.get(i);
            System.out.println("=== Монстр #" + (i + 1) + " ===");
            System.out.println("Тип: " + monster.getType());
        System.out.println("Имя: " + monster.getName());
        System.out.println("Описание: " + monster.getDescribtion());
        System.out.println("Уровень опасности: " + monster.getDanger());
        System.out.println("Среда обитания: " + monster.getHabitat());
        System.out.println("Первое упоминание: " + monster.getFirst_mentioned());
        System.out.println("Уязвимости: " + monster.getVulnerabilities());
        System.out.println("Сопротивления: " + monster.getResistances());
        System.out.println("Рост: " + monster.getHeight() + " м");
        System.out.println("Вес: " + monster.getWeight() + " кг");
        System.out.println("Активен: " + monster.getActive_time());
        System.out.println("Время подготовки: " + monster.getPreparation_time() + " мин");
        System.out.println("Эффективность: " + monster.getEffectiveness());
            System.out.println("=======================");
            System.out.println();
        }
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
