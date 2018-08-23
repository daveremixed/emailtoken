package com.gonzobeans.emailtoken.controller;

import com.gonzobeans.emailtoken.model.*;
import com.gonzobeans.emailtoken.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/token")
public class TokenController {

    private TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> createToken(@RequestBody TokenRequest request) {
        if (request == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!tokenService.validateEmail(request.getEmailAddress())) {
            return new ResponseEntity<>(new ErrorResponse("Invalid Email Address",
                    String.format("Address provided [%s] did not pass validation.", request.getEmailAddress())),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Token> token =  tokenService.registerToken(request.getApplicationSecret(),
                request.getUsageId(), request.getEmailAddress());

        if (token.isPresent()) {
           return new ResponseEntity<>(token.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = GET, value = "/{token}")
    public ResponseEntity<?> readToken(@PathVariable String token, @RequestHeader("applicationSecret") String secret) {

        Optional<Token> tokenData = tokenService.checkForToken(secret, token);

        if (tokenData.isPresent()) {
            return new ResponseEntity<>(tokenData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
