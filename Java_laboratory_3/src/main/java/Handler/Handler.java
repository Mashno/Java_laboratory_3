/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Handler;

import Monsters.Monster;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Владислав
 */
public interface Handler {
    public void setNext(Handler h);
    public void handle(File request, ArrayList<Monster> resultList);
}
