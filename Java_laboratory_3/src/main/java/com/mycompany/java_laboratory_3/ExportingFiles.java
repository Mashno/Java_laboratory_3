/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_laboratory_3;

import Handler.XMLHandler;
import Monsters.Ingredients;
import Monsters.Monster;
import Monsters.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Владислав
 */
public class ExportingFiles {
    public static void ExportXML(ArrayList<Monster> monsters, File filePath) 
            throws IOException, XMLStreamException {
        
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(fos, "UTF-8");
            
            try {
                writer.writeStartDocument("UTF-8", "1.0");
                writer.writeStartElement("monsters"); // Корневой элемент
                
                for (Monster monster : monsters) {
                    writeMonsterElement(writer, monster);
                }
                
                writer.writeEndElement(); // Закрываем <monsters>
                writer.writeEndDocument();
                
            } finally {
                writer.close();
            }
        }
    }

    private static void writeMonsterElement(XMLStreamWriter writer, Monster monster) 
            throws XMLStreamException {
        
        writer.writeStartElement("monster");
        
        // Основные атрибуты
        writeElement(writer, "name", monster.getName());
        writeElement(writer, "description", monster.getDescribtion());
        writeElement(writer, "danger", String.valueOf(monster.getDanger()));
        
        // Дополнительные поля
        writeElement(writer, "habitat", monster.getHabitat());
        writeElement(writer, "first_mentioned", monster.getFirst_mentioned());
        writeElement(writer, "vulnerabilities", monster.getVulnerabilities());
        writeElement(writer, "resistances", monster.getResistances());
        writeElement(writer, "height", monster.getHeight());
        writeElement(writer, "weight", monster.getWeight());
        writeElement(writer, "active_time", monster.getActive_time());
        writeElement(writer, "preparation_time", String.valueOf(monster.getPreparation_time()));
        writeElement(writer, "effectiveness", monster.getEffectiveness());
        
        // Иммунитеты
        if (!monster.getImmunities().isEmpty()) {
            writer.writeStartElement("immunities");
            for (String immunity : monster.getImmunities()) {
                writeElement(writer, "immunity", immunity);
            }
            writer.writeEndElement();
        }
        
        // Рецепт
        if (monster.getRecipe() != null) {
            writeRecipeElement(writer, monster.getRecipe());
        }
        
        writer.writeEndElement(); // Закрываем <monster>
    }

    private static void writeRecipeElement(XMLStreamWriter writer, Recipe recipe) 
            throws XMLStreamException {
        
        writer.writeStartElement("recipe");
        writer.writeAttribute("type", recipe.getType());
        
        for (Ingredients ingredient : recipe.getIngredients()) {
            writer.writeStartElement("ingredient");
            writer.writeAttribute("name", ingredient.getIngredient_name());
            
            // Для amount используем "null" если значение 0 или отрицательное
            String amount = (ingredient.getAmount_ingredient() <= 0) ? 
                          "null" : String.valueOf(ingredient.getAmount_ingredient());
            writer.writeAttribute("amount", amount);
            
            writer.writeEndElement();
        }
        
        writer.writeEndElement();
    }

    private static void writeElement(XMLStreamWriter writer, String elementName, String value) 
            throws XMLStreamException {
        
        if (value != null && !value.isEmpty()) {
            writer.writeStartElement(elementName);
            writer.writeCharacters(value);
            writer.writeEndElement();
        }
    }
    
    public void ExportYAML(ArrayList<Monster> monsters, File file) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(2);
        options.setPrettyFlow(true);
        
        Yaml yaml = new Yaml(options);
        
        try (FileWriter writer = new FileWriter(file)) {
            // Создаем структуру данных для YAML
            Map<String, Object> yamlData = new LinkedHashMap<>();
            ArrayList<Map<String, Object>> monstersList = new ArrayList<>();
            
            for (Monster monster : monsters) {
                Map<String, Object> monsterMap = new LinkedHashMap<>();
                monsterMap.put("name", monster.getName());
                monsterMap.put("description", monster.getDescribtion());
                monsterMap.put("danger", monster.getDanger());
                monsterMap.put("habitat", monster.getHabitat());
                monsterMap.put("first_mentioned", monster.getFirst_mentioned());
                monsterMap.put("vulnerabilities", monster.getVulnerabilities());
                monsterMap.put("resistances", monster.getResistances());
                monsterMap.put("height", monster.getHeight());
                monsterMap.put("weight", monster.getWeight());
                
                if (!monster.getImmunities().isEmpty()) {
                    monsterMap.put("immunities", monster.getImmunities());
                } else {
                    monsterMap.put("immunities", null);
                }
                
                monsterMap.put("active_time", monster.getActive_time());
                
                // Формируем рецепт
                if (monster.getRecipe() != null) {
                    Map<String, Object> recipeMap = new LinkedHashMap<>();
                    recipeMap.put("type", monster.getRecipe().getType());
                    
                    ArrayList<Map<String, Object>> ingredientsList = new ArrayList<>();
                    for (Ingredients ingredient : monster.getRecipe().getIngredients()) {
                        Map<String, Object> ingredientMap = new LinkedHashMap<>();
                        ingredientMap.put("name", ingredient.getIngredient_name());
                        ingredientMap.put("amount", 
                            ingredient.getAmount_ingredient() == 0 ? null : ingredient.getAmount_ingredient());
                        ingredientsList.add(ingredientMap);
                    }
                    recipeMap.put("ingredients", ingredientsList);
                    monsterMap.put("recipe", recipeMap);
                }
                
                monsterMap.put("preparation_time", monster.getPreparation_time());
                monsterMap.put("effectiveness", monster.getEffectiveness());
                
                monstersList.add(monsterMap);
            }
            
            yamlData.put("monsters", monstersList);
            yaml.dump(yamlData, writer);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void ExportJSON(ArrayList<Monster> monsters, File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // Красивый формат с отступами

            // Создаем структуру данных для экспорта
            Map<String, List<Map<String, Object>>> exportData = new HashMap<>();
            List<Map<String, Object>> monstersList = new ArrayList<>();

            for (Monster monster : monsters) {
                Map<String, Object> monsterMap = new LinkedHashMap<>();

                // Основные свойства
                monsterMap.put("name", monster.getName());
                monsterMap.put("description", monster.getDescribtion());
                monsterMap.put("danger", monster.getDanger());
                monsterMap.put("habitat", monster.getHabitat());
                monsterMap.put("first_mentioned", monster.getFirst_mentioned());
                monsterMap.put("vulnerabilities", monster.getVulnerabilities());
                monsterMap.put("resistances", monster.getResistances());
                monsterMap.put("height", monster.getHeight());
                monsterMap.put("weight", monster.getWeight());

                // Иммунитеты
                if (monster.getImmunities() != null && !monster.getImmunities().isEmpty()) {
                    monsterMap.put("immunities", new ArrayList<>(monster.getImmunities()));
                } else {
                    monsterMap.put("immunities", null);
                }

                monsterMap.put("active_time", monster.getActive_time());
                monsterMap.put("preparation_time", monster.getPreparation_time());
                monsterMap.put("effectiveness", monster.getEffectiveness());

                // Рецепт
                if (monster.getRecipe() != null) {
                    Map<String, Object> recipeMap = new LinkedHashMap<>();
                    recipeMap.put("type", monster.getRecipe().getType());

                    List<Map<String, Object>> ingredientsList = new ArrayList<>();
                    for (Ingredients ingredient : monster.getRecipe().getIngredients()) {
                        Map<String, Object> ingredientMap = new LinkedHashMap<>();
                        ingredientMap.put("name", ingredient.getIngredient_name());
                        ingredientMap.put("amount", ingredient.getAmount_ingredient() == 0 ? null : ingredient.getAmount_ingredient());
                        ingredientsList.add(ingredientMap);
                    }
                    recipeMap.put("ingredients", ingredientsList);
                    monsterMap.put("recipe", recipeMap);
                } else {
                    monsterMap.put("recipe", null);
                }

                monstersList.add(monsterMap);
            }

            exportData.put("monsters", monstersList);

            // Записываем в файл
            mapper.writeValue(file, exportData);

        } catch (IOException e) {
            System.err.println("Ошибка при экспорте JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
