package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final String inputRootDir;
    private static final String outputRootDir;
    private static final String crewFileName;
    private static final String missionsFileName;
    private static final String spaceshipsFileName;
    private static Integer fileRefreshRate;
    private static final String dateTimeFormat;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    static {
        PropertyReaderUtil.loadProperties();
        inputRootDir = PropertyReaderUtil.readProperties().getProperty("inputRootDir");
        outputRootDir = PropertyReaderUtil.readProperties().getProperty("outputRootDir");
        crewFileName = PropertyReaderUtil.readProperties().getProperty("crewFileName");
        missionsFileName = PropertyReaderUtil.readProperties().getProperty("missionsFileName");
        spaceshipsFileName = PropertyReaderUtil.readProperties().getProperty("spaceshipsFileName");
        try {
            fileRefreshRate = Integer.parseInt(PropertyReaderUtil.readProperties().getProperty("fileRefreshRate"));
        } catch (NumberFormatException e){
            logger.error(e.getMessage());
            fileRefreshRate = 60;
        }
        dateTimeFormat = PropertyReaderUtil.readProperties().getProperty("dateTimeFormat");
    }

    private ApplicationProperties() {
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
