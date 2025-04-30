/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_laboratory_3;

import Handler.XMLHandler;
import Monsters.Ingredients;
import Monsters.Monster;
import Monsters.Recipe;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

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
    
    public void ExportYAML(File file){
        
    }
    
    public void ExportJSON(File file){
        
    }
}
