/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monsters;

/**
 *
 * @author Владислав
 */
public class Recipe {
    private String type;
    private Ingredients[] ingredients;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Ingredients[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients[] ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe(String type, Ingredients[] ingredients) {
        this.type = type;
        this.ingredients = ingredients;
    }
}
