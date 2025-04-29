/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monsters;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Владислав
 */
public class GroupOfMonsters {
    private HashMap<String, ArrayList<Monster>> xml_monsters;
    private HashMap<String, ArrayList<Monster>> yaml_monsters;
    private HashMap<String, ArrayList<Monster>> json_monsters;

    public HashMap<String, ArrayList<Monster>> getXml_monsters() {
        return xml_monsters;
    }

    public void setXml_monsters(HashMap<String, ArrayList<Monster>> xml_monsters) {
        this.xml_monsters = xml_monsters;
    }

    public HashMap<String, ArrayList<Monster>> getYaml_monsters() {
        return yaml_monsters;
    }

    public void setYaml_monsters(HashMap<String, ArrayList<Monster>> yaml_monsters) {
        this.yaml_monsters = yaml_monsters;
    }

    public HashMap<String, ArrayList<Monster>> getJson_monsters() {
        return json_monsters;
    }

    public void setJson_monsters(HashMap<String, ArrayList<Monster>> json_monsters) {
        this.json_monsters = json_monsters;
    }
    
}
