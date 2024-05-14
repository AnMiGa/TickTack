package com.fhdw.webproject.api;

import com.openapi.api.DefaultApiDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DefaultApiDelegateImpl implements DefaultApiDelegate {

    public DefaultApiDelegateImpl(){

    }

    @Override
    public ResponseEntity<String> helloGet() {
        return new ResponseEntity<>("Toll", HttpStatus.OK);
    }
}
