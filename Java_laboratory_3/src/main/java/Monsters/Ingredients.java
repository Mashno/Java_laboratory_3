/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monsters;

/**
 *
 * @author Владислав
 */
public class Ingredients {
    private String ingredient_name;
    private int amount_ingredient;

    public Ingredients(String ingredient_name, int amount_ingredient) {
        this.ingredient_name = ingredient_name;
        this.amount_ingredient = amount_ingredient;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public int getAmount_ingredient() {
        return amount_ingredient;
    }

    public void setAmount_ingredient(int amount_ingredient) {
        this.amount_ingredient = amount_ingredient;
    }
    
}
