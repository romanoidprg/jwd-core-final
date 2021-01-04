package com.epam.jwd.core_final.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public class ApplicationProperties {
    //todo
    private static String inputRootDir;
    private static String outputRootDir;
    private static String crewFileName;
    private static String missionsFileName;
    private static String spaceshipsFileName;
    private static Integer fileRefreshRate;
    private static String dateTimeFormat;
    final static Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    static {

        try {
            FileInputStream file = new FileInputStream("src/main/resources/application.properties");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            String s;
            for (int i = 0; i < 10; i++) {

                s = reader.readLine();
                if (s.contains("inputRootDir"))
                    inputRootDir = s.substring(13);
                else if (s.contains("outputRootDir"))
                    outputRootDir = s.substring(14);
                else if (s.contains("crewFileName"))
                    crewFileName = s.substring(13);
                else if (s.contains("missionsFileName"))
                    missionsFileName = s.substring(17);
                else if (s.contains("spaceshipsFileName"))
                    spaceshipsFileName = s.substring(19);
                else if (s.contains("fileRefreshRate"))
                    fileRefreshRate = Integer.valueOf(s.substring(16));
                else if (s.contains("dateTimeFormat"))
                    dateTimeFormat = s.substring(15);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    public static String getInputRootDir() {
        return inputRootDir;
    }

    public static String getOutputRootDir() {
        return outputRootDir;
    }

    public static String getCrewFileName() {
        return crewFileName;
    }

    public static String getMissionsFileName() {
        return missionsFileName;
    }

    public static String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public static Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public static String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
