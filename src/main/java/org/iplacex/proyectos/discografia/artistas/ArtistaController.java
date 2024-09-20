package org.iplacex.proyectos.discografia.artistas;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;
    
    //------------------------POST 
    @PostMapping(
        value ="/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity <artistas> HandleInsertArtistaRequest(@RequestBody artistas artistas){
        artistas temp = artistaRepo.insert(artistas);
        return new ResponseEntity<>(temp, null, HttpStatus.CREATED);
    }
    //------------------------FIN POST
        @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<artistas>> HandleInsertArtistasRequest(){
        List<artistas> Artistas = artistaRepo.findAll();

        return new ResponseEntity<>(Artistas,null,HttpStatus.OK);
    }
    //-----------------------MetodoGET
    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<artistas> HandleGetArtistaRequest(@PathVariable("id") String id){
        Optional<artistas> temp = artistaRepo.findById(id);
        
        if(!temp.isPresent()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(temp.get(),null,HttpStatus.OK);
    }
    //--------------------fin get
    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<artistas> HandleUpdateArtistaRequest(
        @PathVariable ("id") String id,
        @RequestBody artistas Artistas 
        ){
        if(!artistaRepo.existsById(id)){
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        Artistas._id = id;
        artistas temp = artistaRepo.save(Artistas);

        return new ResponseEntity<>(temp, null, HttpStatus.OK);
    }
    //------------------------put por id
    //delete por id
    @DeleteMapping(
        value = "/artista/{id}"
    )
    public ResponseEntity<artistas> HandleDeleteArtistaRequest (@PathVariable("id") String id){
        if(!artistaRepo.existsById(id)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        artistas temp = artistaRepo.findById(id).get();
        artistaRepo.deleteById(id);

        return new ResponseEntity<>(temp ,null, HttpStatus.OK); //httpSatus.gone
    }
}

