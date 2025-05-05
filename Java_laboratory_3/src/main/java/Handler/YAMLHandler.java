/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handler;
import Monsters.Ingredients;
import Monsters.Monster;
import Monsters.Recipe;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Владислав
 */
public class YAMLHandler extends BaseHandler{
    @Override
    public void handle(File request, ArrayList<Monster> monsterList){
        if(request.getName().contains(".yaml")){
            Yaml yaml = new Yaml();

        try (FileInputStream inputStream = new FileInputStream(request)) {
            
            Map<String, Object> root = yaml.load(inputStream);

            if (root != null && root.containsKey("monsters")) {
                List<Map<String, Object>> monstersData = (List<Map<String, Object>>) root.get("monsters");

                for (Map<String, Object> monsterData : monstersData) {
                    Monster monster = createMonsterFromMap(monsterData);
                    if (monster != null) {
                        monsterList.add(monster);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + request.getPath());
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке YAML: " + e.getMessage());
        }
            
        } else{
            super.handle(request, monsterList);
        }
    }
    
    private Monster createMonsterFromMap(Map<String, Object> data) {
        try {
            Monster monster = new Monster();

           
            monster.setType("yaml");  
            monster.setName(getStringValue(data, "name"));
            monster.setDescribtion(getStringValue(data, "description"));  
            monster.setDanger(getIntValue(data, "danger"));

            
            monster.setHabitat(getStringValue(data, "habitat"));
            monster.setVulnerabilities(getStringValue(data, "vulnerabilities"));
            monster.setResistances(getStringValue(data, "resistances"));
            if (data.containsKey("immunities") && data.get("immunities") instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<String> immunities = (List<String>) data.get("immunities");

                for (String immunity : immunities) {
                    monster.addImmunity(immunity);
                }
            }
            

           
            monster.setHeight(getStringValue(data, "height"));
            monster.setWeight(getStringValue(data, "weight"));
            monster.setFirst_mentioned(getStringValue(data, "first_mentioned"));
            monster.setActive_time(getStringValue(data, "active_time"));
            monster.setPreparation_time(getIntValue(data, "preparation_time"));
            monster.setEffectiveness(getStringValue(data, "effectiveness"));

            // Рецепт
            Map<String, Object> recipeData = (Map<String, Object>) data.get("recipe");
            if (recipeData != null) {
                Recipe recipe = new Recipe();
                recipe.setType(getStringValue(recipeData, "type"));

                List<Map<String, Object>> ingredientsData = (List<Map<String, Object>>) recipeData.get("ingredients");
                if (ingredientsData != null) {
                    for (Map<String, Object> ingredientData : ingredientsData) {
                        Ingredients ingredient = new Ingredients();
                        ingredient.setIngredient_name(getStringValue(ingredientData, "name"));
                        ingredient.setAmount_ingredient(getIntValue(ingredientData, "amount"));
                        recipe.addIngredient(ingredient);
                    }
                }
                monster.setRecipe(recipe);
            }

            return monster;

        } catch (Exception e) {
            System.err.println("Ошибка создания монстра: " + e.getMessage());
            return null;
        }
    }



    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private int getIntValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return 0; 
    }

    private List<String> getStringList(Map<String, Object> map, String key) {
        Object list = map.get(key);
        if (list instanceof List<?>) {
            List<?> rawList = (List<?>) list;
            List<String> stringList = new ArrayList<>();
            for (Object item : rawList) {
                if (item != null) {
                    stringList.add(item.toString());
                }
            }
            return stringList;
        }
        return new ArrayList<>();
    }
}
