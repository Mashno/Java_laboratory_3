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
public class YAMLHandler extends BaseHandler{
    @Override
    public void handle(File request){
        if(request.getName().contains(".yaml")){
            System.out.println("Читаем YAML");
        } else{
            super.handle(request);
        }
    }
}
