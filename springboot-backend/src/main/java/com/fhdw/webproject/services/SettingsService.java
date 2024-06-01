package com.fhdw.webproject.services;

import com.fhdw.webproject.postgresql.model.SettingsDTO;
import com.fhdw.webproject.postgresql.repository.SettingsRepository;
import com.openapi.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    public Settings loadSettings() {
        Settings result = new Settings();
        Optional<SettingsDTO> savedSettings = this.settingsRepository.findById(1L);
        if (savedSettings.isPresent()) {
            result.setId(savedSettings.get().getId());
            result.setUserName(savedSettings.get().getUser_name());
            result.setWeeklyHours(savedSettings.get().getWeekly_hours());
            result.setBreakDuration(savedSettings.get().getBreak_duration());
        }
        return result;
    }

    public void saveSettings(Settings settings) {
        SettingsDTO result = convertToDTO(settings);
        this.settingsRepository.save(result);
    }

    private SettingsDTO convertToDTO(Settings settings) {
        SettingsDTO result = new SettingsDTO();
        result.setId(settings.getId());
        result.setUser_name(settings.getUserName());
        result.setWeekly_hours(settings.getWeeklyHours());
        result.setBreak_duration(settings.getBreakDuration());
        return result;
    }
}
