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
public class Recipe {
    private String type;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredients ingredient){
        ingredients.add(ingredient);
    }
    public Recipe() {
        
    }
}
