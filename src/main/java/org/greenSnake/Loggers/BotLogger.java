package org.greenSnake.Loggers;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("application.properties")
public class BotLogger {
    @Value("${log4j.logger.encoding}")
    private String encoding;
    @Value("${log4j.appender.file.File}")
    private String file;
    @Value("${log4j.conversion.pattern}")
    private String layouts;
    @Value("${log4j.logger.level}")
    private String level;
    @PostConstruct
    public void setup() {
        try {
            // creates pattern layout
            PatternLayout layout = new PatternLayout();
            layout.setConversionPattern(layouts);

            // creates console appender
            RollingFileAppender fileAppender = new RollingFileAppender();
            fileAppender.setFile(file);
            fileAppender.setLayout(layout);
            fileAppender.setEncoding(encoding);
            fileAppender.setMaxFileSize("10MB");
            fileAppender.setMaxBackupIndex(2);
            fileAppender.activateOptions();

            // configures the root logger
            Logger rootLogger = Logger.getRootLogger();
            rootLogger.setLevel(Level.toLevel(level));
            rootLogger.removeAllAppenders();
            rootLogger.addAppender(fileAppender);

            // creates file appender
//            Optional<String> fileName = Optional.of(file);
//
//            fileName.ifPresent(logFile -> {
//                DailyRollingFileAppender rollingFileAppender = new DailyRollingFileAppender();
//                rollingFileAppender.setEncoding(encoding);
//                rollingFileAppender.setFile(file);
//                rollingFileAppender.setLayout(layout);
//                rollingFileAppender.setDatePattern("'.'yyyy-MM-dd");
//                rollingFileAppender.activateOptions();
//                rootLogger.addAppender(rollingFileAppender);
//            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
