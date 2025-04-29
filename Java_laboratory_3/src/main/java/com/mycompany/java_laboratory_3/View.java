/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_laboratory_3;

import Monsters.GroupOfMonsters;
import Monsters.Monster;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import Monsters.Ingredients;

import Monsters.Recipe;

import Monsters.Ingredients;

import Monsters.Ingredients;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class View extends JFrame {
    private Controller controller;
    private GroupOfMonsters monsters;
    private JTree monsterTree;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private JButton editDangerButton;
    private Monster selectedMonster;
    private JTextArea monsterInfoArea;
    
    public View(Controller controller, GroupOfMonsters monsters) {
        this.controller = controller;
        this.monsters = monsters;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Monster Catalog");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create root node for the tree
        rootNode = new DefaultMutableTreeNode("Monsters");
        treeModel = new DefaultTreeModel(rootNode);
        
        // Add the three categories
        DefaultMutableTreeNode xmlNode = new DefaultMutableTreeNode("XML Monsters");
        DefaultMutableTreeNode yamlNode = new DefaultMutableTreeNode("YAML Monsters");
        DefaultMutableTreeNode jsonNode = new DefaultMutableTreeNode("JSON Monsters");
        
        rootNode.add(xmlNode);
        rootNode.add(yamlNode);
        rootNode.add(jsonNode);
        
        // Create the tree
        monsterTree = new JTree(treeModel);
        monsterTree.setShowsRootHandles(true);
        
        // Create the info panel
        monsterInfoArea = new JTextArea();
        monsterInfoArea.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(monsterInfoArea);
        
        // Create buttons panel
        JPanel buttonPanel = new JPanel();
        
        // Import button
        JButton importButton = new JButton("Import File");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Владислав\\Desktop\\код на гитхаб\\Java_laboratory_3"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    controller.ImportFile(selectedFile);
                }
            }
        });
        
        // Export buttons
        JButton exportXmlButton = new JButton("Export XML");
        exportXmlButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
                
                int result = fileChooser.showSaveDialog(null);
                
                if( result == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if (!file.getName().endsWith(".xml")) {
                        file = new File(file.getAbsolutePath() + ".xml");
                    }
                    
                    controller.ExportFileXML(file);
                }
        });
        
        JButton exportYamlButton = new JButton("Export YAML");
        exportYamlButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
                
                int result = fileChooser.showSaveDialog(null);
                
                if( result == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if (!file.getName().endsWith(".yaml")) {
                        file = new File(file.getAbsolutePath() + ".yaml");
                    }
                    
                    controller.ExportFileYAML(file);
                }
        });
        
        JButton exportJsonButton = new JButton("Export JSON");
        exportJsonButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
                
                int result = fileChooser.showSaveDialog(null);
                
                if( result == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if (!file.getName().endsWith(".json")) {
                        file = new File(file.getAbsolutePath() + ".json");
                    }
                    
                    controller.ExportFileJSON(file);
                }
        });
        
        editDangerButton = new JButton("Edit Danger");
        editDangerButton.setEnabled(false);
        editDangerButton.addActionListener(e -> {
            if (selectedMonster != null) {
                String newDangerStr = JOptionPane.showInputDialog(
                    this, 
                    "Enter new danger level (1-10):", 
                    "Edit Danger Level", 
                    JOptionPane.QUESTION_MESSAGE
                );
                
                try {
                    int newDanger = Integer.parseInt(newDangerStr);
                    if (newDanger < 1 || newDanger > 10) {
                        JOptionPane.showMessageDialog(
                            this, 
                            "Danger level must be between 1 and 10", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    
                    // Обновляем опасность через контроллер
                    controller.updateMonsterDanger(selectedMonster, newDanger);
                    
                    // Обновляем отображение
                    displayMonsterInfo(selectedMonster);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        this, 
                        "Please enter a valid number", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
        
        buttonPanel.add(importButton);
        buttonPanel.add(exportXmlButton);
        buttonPanel.add(exportYamlButton);
        buttonPanel.add(exportJsonButton);
        buttonPanel.add(editDangerButton); 
        
        // Create tree selection listener
        monsterTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) monsterTree.getLastSelectedPathComponent();

                // Сбрасываем выбранного монстра и отключаем кнопку
                selectedMonster = null;
                editDangerButton.setEnabled(false);
                monsterInfoArea.setText("");

                if (selectedNode == null || selectedNode.isRoot()) {
                    return;
                }

                // Если выбран узел категории (XML, YAML, JSON)
                if (selectedNode.getParent().equals(rootNode)) {
                    return;
                }

                // Получаем объект монстра из userObject
                Object userObject = selectedNode.getUserObject();
                if (userObject instanceof Monster) {
                    selectedMonster = (Monster) userObject;
                    if (selectedMonster != null) {
                        displayMonsterInfo(selectedMonster);
                        editDangerButton.setEnabled(true);
                    }
                }
            }
        });
        
        // Create split pane for tree and info
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(monsterTree), infoScrollPane);
        splitPane.setDividerLocation(200);
        
        // Main panel layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    public void initTree() {
    // Clear existing monster nodes
        DefaultMutableTreeNode xmlNode = (DefaultMutableTreeNode) rootNode.getChildAt(0);
        DefaultMutableTreeNode yamlNode = (DefaultMutableTreeNode) rootNode.getChildAt(1);
        DefaultMutableTreeNode jsonNode = (DefaultMutableTreeNode) rootNode.getChildAt(2);

        xmlNode.removeAllChildren();
        yamlNode.removeAllChildren();
        jsonNode.removeAllChildren();

        // Add XML monsters
        if (monsters.getXml_monsters() != null) {
            for (ArrayList<Monster> monsterList : monsters.getXml_monsters().values()) {
                for (Monster monster : monsterList) {
                    // Создаем узел с текстом = имени монстра
                    DefaultMutableTreeNode monsterNode = new DefaultMutableTreeNode(monster.getName());
                    // Сохраняем сам объект монстра как userObject
                    monsterNode.setUserObject(monster);
                    xmlNode.add(monsterNode);
                }
            }
        }

        // Аналогично для YAML и JSON
        if (monsters.getYaml_monsters() != null) {
            for (ArrayList<Monster> monsterList : monsters.getYaml_monsters().values()) {
                for (Monster monster : monsterList) {
                    DefaultMutableTreeNode monsterNode = new DefaultMutableTreeNode(monster.getName());
                    monsterNode.setUserObject(monster);
                    yamlNode.add(monsterNode);
                }
            }
        }

        if (monsters.getJson_monsters() != null) {
            for (ArrayList<Monster> monsterList : monsters.getJson_monsters().values()) {
                for (Monster monster : monsterList) {
                    DefaultMutableTreeNode monsterNode = new DefaultMutableTreeNode(monster.getName());
                    monsterNode.setUserObject(monster);
                    jsonNode.add(monsterNode);
                }
            }
        }

        treeModel.reload();
}
    
    private void displayMonsterInfo(Monster monster) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(monster.getName()).append("\n\n");
        sb.append("Description: ").append(monster.getDescribtion()).append("\n\n");
        sb.append("Danger Level: ").append(monster.getDanger()).append("\n\n");
        sb.append("Habitat: ").append(monster.getHabitat()).append("\n\n");
        sb.append("First Mentioned: ").append(monster.getFirst_mentioned()).append("\n\n");
        sb.append("Vulnerabilities: ").append(monster.getVulnerabilities()).append("\n\n");
        sb.append("Resistances: ").append(monster.getResistances()).append("\n\n");
        sb.append("Height: ").append(monster.getHeight()).append("\n\n");
        sb.append("Weight: ").append(monster.getWeight()).append("\n\n");
        sb.append("Active Time: ").append(monster.getActive_time()).append("\n\n");
        sb.append("Preparation Time: ").append(monster.getPreparation_time()).append("\n\n");
        sb.append("Effectiveness: ").append(monster.getEffectiveness()).append("\n\n");
        
        sb.append("Immunities:\n");
        for (String immunity : monster.getImmunities()) {
            sb.append("- ").append(immunity).append("\n");
        }
        sb.append("\n");
        
        Recipe recipe = monster.getRecipe();
        if (recipe != null) {
            sb.append("Recipe Type: ").append(recipe.getType()).append("\n");
            sb.append("Ingredients:\n");
            for (Ingredients ingredient : recipe.getIngredients()) {
                sb.append("- ").append(ingredient.getIngredient_name())
                  .append(": ").append(ingredient.getAmount_ingredient()).append("\n");
            }
        }
        
        monsterInfoArea.setText(sb.toString());
    }
    
    public void clearInfoPanel() {
        monsterInfoArea.setText("");
    }
}