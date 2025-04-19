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
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
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
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            
            try{
                XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(request));
                
                while(reader.hasNext()){
                    XMLEvent xmlEvent = reader.nextEvent();
                    
                    if(xmlEvent.isStartElement()){
                        StartElement startElement = xmlEvent.asStartElement();
                        
                        if(startElement.getName().getLocalPart().equals("monster")){
                            monster = new Monster();
                            monster.setType("xml");
                            
                            if(startElement.getName().getLocalPart().equals("name")){
                                xmlEvent = reader.nextEvent();
                                monster.setName(xmlEvent.asCharacters().getData());
                            } else if(startElement.getName().getLocalPart().equals("description")){
                                xmlEvent = reader.nextEvent();
                                monster.setDescribtion(xmlEvent.asCharacters().getData());
                            }
                        }
                    }
                    
                    if(xmlEvent.isEndElement()){
                        EndElement endElement = xmlEvent.asEndElement();
                        if(endElement.getName().getLocalPart().equals("monster")){
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
