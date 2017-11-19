/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.edhouse.javaee.rest;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

/**
 *
 * @author spacek
 */
@Stateless
@WebService
public class SoapService {
    
    @Inject
    private NoteManager noteManager;
    
    public List<Note> getAll(){
        return noteManager.getAll();
    }
}
