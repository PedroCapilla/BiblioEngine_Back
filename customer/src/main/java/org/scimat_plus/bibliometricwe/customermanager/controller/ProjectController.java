package org.scimat_plus.bibliometricwe.customermanager.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.scimat_plus.bibliometricwe.customermanager.model.CustomerDto;
import org.scimat_plus.bibliometricwe.customermanager.model.ProjectDto;
import org.scimat_plus.bibliometricwe.customermanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> findAll(){
        List<ProjectDto> projectList = projectService.findAll();
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto project){
        ProjectDto projectCreated = projectService.save(project);
        return new ResponseEntity<>(projectCreated, HttpStatus.OK);
    }
    @PostMapping(value ="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // Guardar el archivo en la carpeta 'uploads'
                String directory = "./src/main/java/org/scimat_plus/bibliometricwe/customermanager/tmp/";
                String filename = directory + file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(filename));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + file.getOriginalFilename() + "!";
            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<ProjectDto>> findProjectsByCustomerId(@PathVariable Long customerId ){
        List<ProjectDto> projectLists = projectService.findProjectsByCustomerId(customerId);

        return new ResponseEntity<>(projectLists, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProject(@PathVariable Long id){
        Optional<ProjectDto> projectToDelete = projectService.findById(id);
        projectToDelete.ifPresent(project -> projectService.deleteProject(project));

    }


}
