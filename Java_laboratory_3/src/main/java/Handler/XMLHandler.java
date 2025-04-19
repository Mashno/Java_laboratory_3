/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handler;

import Monsters.*;
import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Владислав
 */
public class XMLHandler extends BaseHandler{
    @Override
    public void handle(File request, ArrayList<Monster> monsterList){
        if(request.getName().contains(".xml")){
            
            Monster monster = null;
            Recipe recipe = null;
            boolean inImmunitiesSection = false;
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            
            try{
                XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(request));
                
                while(reader.hasNext()){
                    XMLEvent xmlEvent = reader.nextEvent();
                    
                    if(xmlEvent.isStartElement()){
                        StartElement startElement = xmlEvent.asStartElement();
                        
                        if(startElement.getName().getLocalPart().equals("monster")){
                            monster = new Monster();
                            recipe = new Recipe();
                            ArrayList<Ingredients> ingredientList = new ArrayList<>();
                            monster.setType("xml");
                        }
                        else if(startElement.getName().getLocalPart().equals("name")){
                            xmlEvent = reader.nextEvent();
                            monster.setName(xmlEvent.asCharacters().getData());
                        } else if(startElement.getName().getLocalPart().equals("description")){
                            xmlEvent = reader.nextEvent();
                            monster.setDescribtion(xmlEvent.asCharacters().getData());
                        } else if(startElement.getName().getLocalPart().equals("danger")){
                            xmlEvent = reader.nextEvent();
                            monster.setDanger(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        } else if (startElement.getName().getLocalPart().equals("habitat")) {
                            xmlEvent = reader.nextEvent();
                            monster.setHabitat(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("first_mentioned")) {
                            xmlEvent = reader.nextEvent();
                            monster.setFirst_mentioned(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("vulnerabilities")) {
                            xmlEvent = reader.nextEvent();
                            monster.setVulnerabilities(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("resistances")) {
                            xmlEvent = reader.nextEvent();
                            monster.setResistances(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("height")) {
                            xmlEvent = reader.nextEvent();
                            monster.setHeight(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("weight")) {
                            xmlEvent = reader.nextEvent();
                            monster.setWeight(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("active_time")) {
                            xmlEvent = reader.nextEvent();
                            monster.setActive_time(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("preparation_time")) {
                            xmlEvent = reader.nextEvent();
                            monster.setPreparation_time(Integer.parseInt(xmlEvent.asCharacters().getData()));
                        } else if (startElement.getName().getLocalPart().equals("effectiveness")) {
                            xmlEvent = reader.nextEvent();
                            monster.setEffectiveness(xmlEvent.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals("immunities")) {
                            inImmunitiesSection = true;
                        }
                          else if (inImmunitiesSection && startElement.getName().getLocalPart().equals("immunity")) {
                            xmlEvent = reader.nextEvent();
                            if (xmlEvent.isCharacters()) {
                                String immunity = xmlEvent.asCharacters().getData().trim();
                                if (!immunity.isEmpty() && !immunity.equalsIgnoreCase("null")) {
                                    monster.addImmunity(immunity);
                                }
                            }
                        } else if (startElement.getName().getLocalPart().equals("recipe")){
                            Attribute typeRecipe = startElement.getAttributeByName(new QName("type"));
                            recipe.setType(typeRecipe.getValue());
                        } else if(startElement.getName().getLocalPart().equals("ingredient")){
                            Ingredients ingredient = new Ingredients();
                            Attribute NameIngredient = startElement.getAttributeByName(new QName("name"));
                            Attribute AmountIngredient = startElement.getAttributeByName(new QName("amount"));
                            ingredient.setIngredient_name(NameIngredient.getValue());
                            if(!AmountIngredient.getValue().equals("null")){
                                ingredient.setAmount_ingredient(Integer.parseInt(AmountIngredient.getValue()));
                            }
                            recipe.addIngredient(ingredient);
                            }
                        
                    }
                    
                    if(xmlEvent.isEndElement()){
                        EndElement endElement = xmlEvent.asEndElement();
                        if(endElement.getName().getLocalPart().equals("monster")){
                            monster.setRecipe(recipe);
                            monsterList.add(monster);
                        }
                    }
                }
            
            }catch(FileNotFoundException e){
                e.printStackTrace();
            } catch (XMLStreamException ex) {
                Logger.getLogger(XMLHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
            super.handle(request, monsterList);
        }
    }
}
