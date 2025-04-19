/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handler;
import Monsters.Monster;
import java.io.File;
import java.util.ArrayList;
import java.io.File;

/**
 *
 * @author Владислав
 */
public class JSONHandler extends BaseHandler{
    @Override
    public void handle(File request , ArrayList<Monster> monsterList){
        if(request.getName().contains(".json")){
            System.out.println("Читаем JSON");
        } else{
            super.handle(request, monsterList);
        }
    }
}
