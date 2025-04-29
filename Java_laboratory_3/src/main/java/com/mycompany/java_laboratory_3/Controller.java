/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_laboratory_3;

import java.io.File;
import Handler.*;
import Monsters.GroupOfMonsters;
import Monsters.Ingredients;
import Monsters.Monster;
import Monsters.Recipe;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author Владислав
 */
public class Controller {
    private GroupOfMonsters monsters;
    private ExportingFiles exporterFiles;
    private ArrayList<Monster> monsterList;
    private final View view;
    
    public Controller(){
        this.monsters = new GroupOfMonsters();
        this.view = new View(this, monsters);
        this.view.setVisible(true);
        this.exporterFiles = new ExportingFiles();
        this.monsterList = new ArrayList<>();
        
    }
    
    public void ImportFile(File file){
        System.out.println("Текущая кодировка системы: " + System.getProperty("file.encoding"));
        Handler xmlHandler =  new XMLHandler();
        Handler yamlHandler =  new YAMLHandler();
        Handler jsonHandler =  new JSONHandler();
        
        xmlHandler.setNext(yamlHandler);
        yamlHandler.setNext(jsonHandler);

        monsterList.clear();
        xmlHandler.handle(file, monsterList);
        //получили список - добавили его в группу списков и отчистили
        if (monsterList.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Не удалось загрузить данные из файла.\nПроверьте формат и содержимое файла.",
                "Ошибка загрузки",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        switch(monsterList.get(0).getType()){
            case "xml":
                HashMap<String,ArrayList<Monster>> monsterHashMap = new HashMap<>();
                monsterHashMap.put("xml",monsterList );
                monsters.setXml_monsters(monsterHashMap);
                break;
            case "yaml":
                break;
            case "json":
                break;
        }
        SwingUtilities.invokeLater(() -> {
            view.initTree();
            view.clearInfoPanel();// Перестраиваем дерево с новыми данными
        });
    }
    
    public void ExportFileXML(File file){
        exporterFiles.ExportXML(file);
    }
    
    public void ExportFileYAML(File file){
        exporterFiles.ExportYAML(file);
    }
    
    public void ExportFileJSON(File file){
        exporterFiles.ExportJSON(file);
    }
    
    public void updateMonsterDanger(Monster monster, int newDanger) {
        monster.setDanger(newDanger);
        
        // Обновляем дерево
        SwingUtilities.invokeLater(() -> {
            view.initTree();
        });
        
        JOptionPane.showMessageDialog(view, 
            "Danger level updated successfully!", 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
}
