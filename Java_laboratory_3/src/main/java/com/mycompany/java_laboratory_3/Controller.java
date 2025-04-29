/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_laboratory_3;

import java.io.File;
import Handler.*;
import Monsters.GroupOfMonsters;
import Monsters.Monster;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Controller {
    private GroupOfMonsters monsters;
    private ExportingFiles exporterFiles;
    private ArrayList<Monster> monsterList;
    private final View view;

    public Controller() {
        this.monsters = new GroupOfMonsters();
        this.view = new View(this, monsters);
        this.view.setVisible(true);
        this.exporterFiles = new ExportingFiles();
        this.monsterList = new ArrayList<>();
    }

    public void ImportFile(File file) {
        System.out.println("Текущая кодировка системы: " + System.getProperty("file.encoding"));
        Handler xmlHandler = new XMLHandler();
        Handler yamlHandler = new YAMLHandler();
        Handler jsonHandler = new JSONHandler();

        xmlHandler.setNext(yamlHandler);
        yamlHandler.setNext(jsonHandler);

        monsterList.clear();
        xmlHandler.handle(file, monsterList);

        if (monsterList.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Не удалось загрузить данные из файла.\nПроверьте формат и содержимое файла.",
                    "Ошибка загрузки",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String type = monsterList.get(0).getType(); // Может быть "xml", "yaml" или "json"
        switch (type) {
            case "xml":
                monsters.setXml_monsters(groupByType(monsterList));
                break;
            case "yaml":
                monsters.setYaml_monsters(groupByType(monsterList));
                break;
            case "json":
                monsters.setJson_monsters(groupByType(monsterList));
                break;
            default:
                JOptionPane.showMessageDialog(view,
                        "Неизвестный тип файла: " + type,
                        "Ошибка импорта",
                        JOptionPane.ERROR_MESSAGE);
                return;
        }

        SwingUtilities.invokeLater(() -> {
            view.initTree(null); // Сбрасываем выбор
            view.clearInfoPanel();
        });
    }

    private HashMap<String, ArrayList<Monster>> groupByType(ArrayList<Monster> list) {
        HashMap<String, ArrayList<Monster>> grouped = new HashMap<>();
        String type = list.get(0).getType();
        grouped.put(type, new ArrayList<>(list));
        return grouped;
    }

    public void ExportFileXML(File file) {
        exporterFiles.ExportXML(file);
    }

    public void ExportFileYAML(File file) {
        exporterFiles.ExportYAML(file);
    }

    public void ExportFileJSON(File file) {
        exporterFiles.ExportJSON(file);
    }

    public void updateMonsterDanger(Monster monster, int newDanger) {
        if (monster == null) return;

        System.out.println("Updating danger for monster: " + monster.getName());
        monster.setDanger(newDanger);

        SwingUtilities.invokeLater(() -> {
            view.initTree(monster);  // Передаём объект монстра
        });

        JOptionPane.showMessageDialog(view,
                "Уровень опасности успешно обновлён!",
                "Успех",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
