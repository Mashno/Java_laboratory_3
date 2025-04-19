/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handler;

import Monsters.Monster;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Владислав
 */
public abstract class BaseHandler implements Handler {
    private Handler next;
    
    
    @Override
    public void setNext(Handler h) {
        this.next = h;
    }

    @Override
    public void handle(File request, ArrayList<Monster> monsterList) {
        if(next != null){
            next.handle(request, monsterList);
        }
    }
    
}
