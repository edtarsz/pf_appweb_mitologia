/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.controller.post;

import org.itson.mythify.controller.ControllerException;

/**
 *
 * @author user
 */
public interface IFacadePostBO {

    public void crearPostDTO(PostDTO postDTO) throws ControllerException;

    public void eliminarPost() throws ControllerException;

    public void actualizarPost() throws ControllerException;

}
