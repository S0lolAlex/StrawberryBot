package org.greenSnake.Loggers;

import org.apache.log4j.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Optional;
@Configuration
@PropertySource("application.properties")
public class BotLogger {

    @Bean
    public static void setup() {
        try {
            // creates pattern layout
            PatternLayout layout = new PatternLayout();
            layout.setConversionPattern("${log4j.conversion.pattern}");

            // creates console appender
            ConsoleAppender consoleAppender = new ConsoleAppender();
            consoleAppender.setLayout(layout);
            consoleAppender.setEncoding("${log4j.logger.encoding}");
            consoleAppender.activateOptions();

            // configures the root logger
            Logger rootLogger = Logger.getRootLogger();
            rootLogger.setLevel(Level.toLevel("${log4j.logger.level}"));
            rootLogger.removeAllAppenders();
            rootLogger.addAppender(consoleAppender);

            // creates file appender
            Optional<String> fileName = Optional.of("${log4j.appender.file}");

            fileName.ifPresent(logFile -> {
                DailyRollingFileAppender rollingFileAppender = new DailyRollingFileAppender();
                rollingFileAppender.setEncoding("${log4j.logger.encoding}");
                rollingFileAppender.setFile(logFile);
                rollingFileAppender.setLayout(layout);
                rollingFileAppender.setDatePattern("'.'yyyy-MM-dd");
                rollingFileAppender.activateOptions();
                rootLogger.addAppender(rollingFileAppender);
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
