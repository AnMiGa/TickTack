package com.fhdw.webproject.postgresql.controller;

import com.fhdw.webproject.postgresql.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/db")
public class SettingsController {

    @Autowired
    private SettingsRepository timeEntryRepository;


}
