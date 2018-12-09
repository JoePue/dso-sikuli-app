package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.puettner.sikuli.dso.AppProperties;
import de.puettner.sikuli.dso.exception.AppException;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
public class FileService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public FileService() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttackCamp.class, new AttackCampDeserializer());
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String removeFilePath(String filename) {
        int idx = filename.lastIndexOf(File.separator);
        if (idx > 1) {
            return filename.substring(idx + 1);
        }
        return filename;
    }

    public void saveAdventureState(AdventureState state, File filename) {
        log.info("saveState()");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filename, state);
            JsonFileFormatter.format(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AdventureState restoreAdventureState(File filename) {
        log.info("restoreState()");
        AdventureState state;
        try {
            state = objectMapper.readValue(filename, AdventureState.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        renumberSteps(state);
        return state;
    }

    private void renumberSteps(AdventureState state) {List<AdventureStep> list = state.getAdventureSteps();
        if (list == null) {
            list = new ArrayList<>();
        }
        int i = 0;
        for (AdventureStep item : list) {
            item.setNo(++i);
        }
    }

    public AppProperties loadFile(File file) {
        if (!(file.exists() && file.isFile())) {
            throw new AppException("Missing file: " + file.getAbsolutePath());
        }
        try {
            return objectMapper.readValue(file, AppProperties.class);
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
