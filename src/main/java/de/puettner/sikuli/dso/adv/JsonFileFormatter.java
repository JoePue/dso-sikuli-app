package de.puettner.sikuli.dso.adv;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsonFileFormatter {

    public static void format(File filename) throws IOException {
        String fileContent = IOUtils.toString(new FileInputStream(filename), UTF_8);
        fileContent = fileContent.replace("\" : ", "\": ");

        fileContent = fileContent.replace("\r\n       \"height\"", " \"height\"");
        fileContent = fileContent.replace("\r\n      \"height\"", " \"height\"");
        // units
        // fileContent = fileContent.replace("\r\n          \"quantity\"", " \"quantity\"");
        fileContent = fileContent.replace("\r\n      \"quantity\"", " \"quantity\"");
        fileContent = fileContent.replace("\r\n      \"stepType\"", " \"stepType\"");
        fileContent = fileContent.replace("\r\n      \"state\"", " \"state\"");
        fileContent = fileContent.replace("\r\n      \"delay\"", " \"delay\"");
        fileContent = fileContent.replace("\r\n      \"generalName\"", " \"generalName\"");
        fileContent = fileContent.replace("\r\n    \"stepType\"", " \"stepType\"");
        fileContent = fileContent.replace("\r\n    \"state\"", " \"state\"");
        fileContent = fileContent.replace("\r\n    \"delay\"", " \"delay\"");
        fileContent = fileContent.replace("\r\n    \"generalName\"", " \"generalName\"");
        // nav
        fileContent = fileContent.replace("\r\n      \"targetNavPoint\"", " \"targetNavPoint\"");
        fileContent = fileContent.replace("\r\n    \"targetNavPoint\"", " \"targetNavPoint\"");
        IOUtils.write(fileContent, new FileOutputStream(filename), UTF_8);
    }
}
