/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.java_laboratory_3;

import Monsters.GroupOfMonsters;
import Monsters.Monster;
import Monsters.Recipe;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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

        rootNode = new DefaultMutableTreeNode("Monsters");
        treeModel = new DefaultTreeModel(rootNode);
        DefaultMutableTreeNode xmlNode = new DefaultMutableTreeNode("XML Monsters");
        DefaultMutableTreeNode yamlNode = new DefaultMutableTreeNode("YAML Monsters");
        DefaultMutableTreeNode jsonNode = new DefaultMutableTreeNode("JSON Monsters");

        rootNode.add(xmlNode);
        rootNode.add(yamlNode);
        rootNode.add(jsonNode);

        monsterTree = new JTree(treeModel);
        monsterTree.setShowsRootHandles(true);

        monsterInfoArea = new JTextArea();
        monsterInfoArea.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(monsterInfoArea);

        JPanel buttonPanel = new JPanel();

        JButton importButton = new JButton("Import File");
        importButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new File("files"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                controller.ImportFile(fileChooser.getSelectedFile());
            }
        });

        JButton exportXmlButton = new JButton("Export XML");
        exportXmlButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
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
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".yaml")) {
                    file = new File(file.getAbsolutePath() + ".yaml");
                }
                try {
                    controller.ExportFileYAML(file);
                } catch (IOException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JButton exportJsonButton = new JButton("Export JSON");
        exportJsonButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
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
                        JOptionPane.QUESTION_MESSAGE);
                try {
                    int newDanger = Integer.parseInt(newDangerStr);
                    if (newDanger < 1 || newDanger > 10) {
                        JOptionPane.showMessageDialog(this,
                                "Danger level must be between 1 and 10",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.updateMonsterDanger(selectedMonster, newDanger);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid number",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(importButton);
        buttonPanel.add(exportXmlButton);
        buttonPanel.add(exportYamlButton);
        buttonPanel.add(exportJsonButton);
        buttonPanel.add(editDangerButton);

        monsterTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) monsterTree.getLastSelectedPathComponent();
                selectedMonster = null;
                editDangerButton.setEnabled(false);
                monsterInfoArea.setText("");

                if (selectedNode == null || selectedNode.isRoot() || selectedNode.getParent().equals(rootNode)) {
                    return;
                }

                Object userObject = selectedNode.getUserObject();
                if (userObject instanceof Monster) {
                    selectedMonster = (Monster) userObject;
                    displayMonsterInfo(selectedMonster);
                    editDangerButton.setEnabled(true);
                }
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(monsterTree), infoScrollPane);
        splitPane.setDividerLocation(200);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    public void initTree(Monster selectedMonsterToSelect) {
        DefaultMutableTreeNode xmlNode = (DefaultMutableTreeNode) rootNode.getChildAt(0);
        DefaultMutableTreeNode yamlNode = (DefaultMutableTreeNode) rootNode.getChildAt(1);
        DefaultMutableTreeNode jsonNode = (DefaultMutableTreeNode) rootNode.getChildAt(2);

        xmlNode.removeAllChildren();
        yamlNode.removeAllChildren();
        jsonNode.removeAllChildren();

        addMonstersToTreeNode(monsters.getXml_monsters(), xmlNode, selectedMonsterToSelect, "xml");
        addMonstersToTreeNode(monsters.getYaml_monsters(), yamlNode, selectedMonsterToSelect, "yaml");
        addMonstersToTreeNode(monsters.getJson_monsters(), jsonNode, selectedMonsterToSelect, "json");

        treeModel.reload();

        if (selectedMonsterToSelect != null && this.selectedMonster == selectedMonsterToSelect) {
            displayMonsterInfo(selectedMonsterToSelect);
            editDangerButton.setEnabled(true);
        } else {
            clearInfoPanel();
            editDangerButton.setEnabled(false);
        }
    }

    private void addMonstersToTreeNode(HashMap<String, ArrayList<Monster>> monstersMap, DefaultMutableTreeNode parentNode,Monster selectedMonsterToSelect,String type) {
        if (monstersMap == null) return;
        for (ArrayList<Monster> list : monstersMap.values()) {
            for (Monster monster : list) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(monster.getName());
                node.setUserObject(monster);
                parentNode.add(node);
                if (selectedMonsterToSelect != null &&
                    selectedMonsterToSelect.getName().equals(monster.getName()) &&
                    selectedMonsterToSelect.getType().equals(type)) {
                    this.selectedMonster = monster;
                    SwingUtilities.invokeLater(() -> {
                        try {
                            monsterTree.setSelectionPath(new TreePath(node.getPath()));
                        } catch (Exception ignored) {}
                    });
                }
            }
        }
    }

    private void displayMonsterInfo(Monster monster) {
        if (monster == null) {
            clearInfoPanel();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(monster.getName()).append("\n");
        sb.append("Description: ").append(monster.getDescribtion()).append("\n");
        sb.append("Danger Level: ").append(monster.getDanger()).append("\n");
        sb.append("Habitat: ").append(monster.getHabitat()).append("\n");
        sb.append("First Mentioned: ").append(monster.getFirst_mentioned()).append("\n");
        sb.append("Vulnerabilities: ").append(monster.getVulnerabilities()).append("\n");
        sb.append("Resistances: ").append(monster.getResistances()).append("\n");
        sb.append("Height: ").append(monster.getHeight()).append("\n");
        sb.append("Weight: ").append(monster.getWeight()).append("\n");
        sb.append("Active Time: ").append(monster.getActive_time()).append("\n");
        sb.append("Preparation Time: ").append(monster.getPreparation_time()).append("\n");
        sb.append("Effectiveness: ").append(monster.getEffectiveness()).append("\n");
        sb.append("Immunities:\n");
        for (String immunity : monster.getImmunities()) {
            sb.append("- ").append(immunity).append("\n");
        }
        sb.append("\n");
        Recipe recipe = monster.getRecipe();
        if (recipe != null) {
            sb.append("Recipe Type: ").append(recipe.getType()).append("\n");
            sb.append("Ingredients:\n");
            for (Monsters.Ingredients ingredient : recipe.getIngredients()) {
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