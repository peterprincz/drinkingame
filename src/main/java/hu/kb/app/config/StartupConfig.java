package hu.kb.app.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hu.kb.app.game.model.Question;
import hu.kb.app.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class StartupConfig {

    private Logger logger = LoggerFactory.getLogger(StartupConfig.class);


    @Autowired
    private QuestionRepository questionRepository;

    @PostConstruct
    public void loadQuestions() throws IOException {
        logger.info("Loading default question from resources/quiestions.json..");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ClassLoader classLoader = getClass().getClassLoader();
        File historyFile = new File(classLoader.getResource("history_questions.json").getFile());
        String jsonHistory = new String(Files.readAllBytes(Paths.get(historyFile.getPath())));
        File geographyFile = new File(classLoader.getResource("foldrajz_questions.json").getFile());
        String jsonGeography = new String(Files.readAllBytes(Paths.get(geographyFile.getPath())));
        File szolasFile = new File(classLoader.getResource("szolasok_questions.json").getFile());
        String jsonSzolas = new String(Files.readAllBytes(Paths.get(szolasFile.getPath())));
        questionRepository.saveAll(mapper.readValue(jsonHistory, new TypeReference<List<Question>>(){}));
        questionRepository.saveAll(mapper.readValue(jsonGeography, new TypeReference<List<Question>>(){}));
        questionRepository.saveAll(mapper.readValue(jsonSzolas, new TypeReference<List<Question>>(){}));
        logger.info("Saving quesitons to database was succesfull");
    }
}
