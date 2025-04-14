package com.kianchart.kianchart.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;


public class EnvConfig {
    private static final Dotenv dotenv = Dotenv.load();

    @Getter
    private static String APP_NAME = dotenv.get("APP_NAME");

    @Getter
    private static String BASE_URL = dotenv.get("BASE_URL");

    @Getter
    private static int MAX_IMAGE_SIZE = Integer.parseInt(dotenv.get("MAX_IMAGE_SIZE"));
    @Getter
    private static int MAX_VIDEO_SIZE = Integer.parseInt(dotenv.get("MAX_VIDEO_SIZE"));
    @Getter
    private static int MAX_DOCUMENT_SIZE = Integer.parseInt(dotenv.get("MAX_DOCUMENT_SIZE"));

//  jwt config ------------------
    @Getter
    private static String SECRET_KEY = dotenv.get("SECRET_KEY");
//    @Getter
//    private static int EXPIRE_TIME = Integer.parseInt(dotenv.get("EXPIRE_TIME"));
}
