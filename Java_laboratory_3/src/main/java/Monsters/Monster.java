/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monsters;

import java.util.ArrayList;

/**
 *
 * @author Владислав
 */
public class Monster {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    private String name;
    private String describtion;
    private int danger;
    private String habitat;
    private String first_mentioned;
    private String vulnerabilities;
    private String resistances;
    private String height;
    private String weight;
    private ArrayList<String> immunities = new ArrayList<>();;
    private String active_time;
    private Recipe recipe;
    private int preparation_time;
    private String effectiveness;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getFirst_mentioned() {
        return first_mentioned;
    }

    public void setFirst_mentioned(String first_mentioned) {
        this.first_mentioned = first_mentioned;
    }

    public String getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(String vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public String getResistances() {
        return resistances;
    }

    public void setResistances(String resistances) {
        this.resistances = resistances;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public ArrayList<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(ArrayList<String> immunities) {
        this.immunities = immunities;
    }

    public String getActive_time() {
        return active_time;
    }

    public void setActive_time(String active_time) {
        this.active_time = active_time;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(int preparation_time) {
        this.preparation_time = preparation_time;
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
    }

    public Monster() {
        
//        this.name = name;
//        this.describtion = describtion;
//        this.danger = danger;
//        this.habitat = habitat;
//        this.first_mentioned = first_mentioned;
//        this.vulnerabilities = vulnerabilities;
//        this.resistances = resistances;
//        this.height = height;
//        this.weight = weight;
//        this.immunities = immunities;
//        this.active_time = active_time;
//        this.recipe = recipe;
//        this.preparation_time = preparation_time;
//        this.effectiveness = effectiveness;
    }
    
    public void addImmunity(String immunity){
        if (immunity != null && !immunity.trim().isEmpty() && !immunity.equalsIgnoreCase("null")) {
            this.immunities.add(immunity);
        }
    }
    @Override
    public String toString() {
        return this.name; // Будет отображать имя монстра вместо стандартного представления
    }
}
