/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handler;
import Monsters.Ingredients;
import Monsters.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Владислав
 */
public class JSONHandler extends BaseHandler{
    @Override
    public void handle(File request , ArrayList<Monster> monsterList) {
       if (request.getName().toLowerCase().endsWith(".json")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, List<Map>> data = mapper.readValue(request, Map.class);
                for (Map m : data.get("monsters")) {
                    Monster monster = new Monster();
                    monster.setType("json");
                    // Основные свойства
                    setProperties(monster, m);
                    // Иммунитеты
                    if (m.get("immunities") != null) {
                        ((List<String>)m.get("immunities")).forEach(monster::addImmunity);
                    }
                    // Рецепт
                    if (m.get("recipe") != null) {
                        monster.setRecipe(createRecipe((Map)m.get("recipe")));
                    }
                    monsterList.add(monster);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            super.handle(request, monsterList);
        }
    }
    
    
    private void setProperties(Monster m, Map data) {
        m.setName((String)data.get("name"));
        m.setDescribtion((String)data.get("description"));
        m.setDanger((Integer)data.get("danger"));
        m.setHabitat(toString(data.get("habitat")));
        m.setFirst_mentioned((String)data.get("first_mentioned"));
        m.setVulnerabilities(toString(data.get("vulnerabilities")));
        m.setResistances(toString(data.get("resistances")));
        m.setHeight(toString(data.get("height")));
        m.setWeight(toString(data.get("weight")));
        m.setActive_time((String)data.get("active_time"));
        m.setPreparation_time((Integer)data.get("preparation_time"));
        m.setEffectiveness((String)data.get("effectiveness"));
    }

    private Recipe createRecipe(Map r) {
        Recipe recipe = new Recipe();
        recipe.setType((String)r.get("type"));
        ((List<Map>)r.get("ingredients")).forEach(i -> {
            Ingredients ing = new Ingredients();
            ing.setIngredient_name((String)i.get("name"));
            ing.setAmount_ingredient(i.get("amount") != null ? (Integer)i.get("amount") : 0);
            recipe.addIngredient(ing);
        });
        return recipe;
    }

    private String toString(Object value) {
        if (value == null) return null;
        return value instanceof List ? String.join(", ", (List)value) : value.toString();
    }
      
}

    
    

