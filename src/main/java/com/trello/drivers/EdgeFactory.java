package com.trello.drivers;

import com.trello.utils.dataReader.PropertyReader;
import com.trello.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class EdgeFactory extends AbstractDriver  {
    private EdgeOptions getOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<>();
        String userDir = System.getProperty("user.dir");
        String downloadPath = userDir + File.separator +
                "src" +
                File.separator +
                "test" +
                File.separator +
                "resources" +
                File.separator +
                "downloads";
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.default_directory",downloadPath);
        options.setExperimentalOption("prefs", prefs);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ENABLE_DOWNLOADS, true);
        options.setAcceptInsecureCerts(true);
        // options.addExtensions(haramBlurExtension);
        switch(PropertyReader.getProperty("executionType"))
        {
            case "local-headless" -> options.addArguments("--headless=new");
            case "remote" -> {
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.addArguments("--headless=new");
            }
        }
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }
    @Override
    public WebDriver createDriver() {
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("local") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("local-headless"))
        {
            return new EdgeDriver(getOptions());
        }
        else if(PropertyReader.getProperty("executionType").equalsIgnoreCase("remote"))
        {
            try {
                return new RemoteWebDriver(
                        new URI("http://"+ remoteHost + ":" + remotePort + "/wd/hub").toURL(), getOptions()
                );
            }
            catch(Exception e)
            {
                LogsManager.error("Unable to create remote driver: " + e.getMessage());
                throw new RuntimeException("Unable to create remote driver: " + e.getMessage());
            }

        }
        else {
            LogsManager.error("Invalid execution type: " + PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("Invalid execution type: " + PropertyReader.getProperty("executionType"));
        }
    }
}


