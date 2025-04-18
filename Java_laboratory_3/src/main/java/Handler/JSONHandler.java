/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handler;

import java.io.File;

/**
 *
 * @author Владислав
 */
public class JSONHandler extends BaseHandler{
    @Override
    public void handle(File request){
        if(request.getName().contains(".json")){
            System.out.println("Читаем JSON");
        } else{
            System.out.println("Error");
        }
    }
}
