package com.lambdaschool.songsuggest.controllers;

import com.lambdaschool.songsuggest.models.Useremail;
import com.lambdaschool.songsuggest.services.UseremailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/useremails")
public class UseremailController {

    @Autowired
    UseremailService useremailService;


    @GetMapping(value = "/useremails",
            produces = "application/json")
    public ResponseEntity<?> listAllUseremails() {
        List<Useremail> allUserEmails = useremailService.findAll();
        return new ResponseEntity<>(allUserEmails,
                HttpStatus.OK);
    }


    @GetMapping(value = "/useremail/{useremailId}",
            produces = "application/json")
    public ResponseEntity<?> getUserEmailById(
            @PathVariable
                    Long useremailId) {
        Useremail ue = useremailService.findUseremailById(useremailId);
        return new ResponseEntity<>(ue,
                HttpStatus.OK);
    }


    @DeleteMapping(value = "/useremail/{useremailid}")
    public ResponseEntity<?> deleteUserEmailById(
            @PathVariable
                    long useremailid) {
        useremailService.delete(useremailid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/useremail/{useremailid}/email/{emailaddress}")
    public ResponseEntity<?> updateUserEmail(
            @PathVariable
                    long useremailid,
            @PathVariable
                    String emailaddress) {
        useremailService.update(useremailid,
                emailaddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/user/{userid}/email/{emailaddress}")
    public ResponseEntity<?> addNewUserEmail(
            @PathVariable
                    long userid,
            @PathVariable
                    String emailaddress) throws
            URISyntaxException {
        Useremail newUserEmail = useremailService.save(userid,
                emailaddress);


        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserEmailURI = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/useremails/useremail/{useremailid}")
                .buildAndExpand(newUserEmail.getUseremailid())
                .toUri();
        responseHeaders.setLocation(newUserEmailURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }
}
