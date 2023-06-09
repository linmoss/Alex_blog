package org.Alex.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "website")
public class WebSite {

    public static final String LOGIN_SIGN = "login_sign";
    private String title;
    private String cdn = "";
    private String url;
    private List<String> keywords;
    private String description;
    private String avatar;
    private String nickname;
    private String username;
    private String password;
    private String address;
    private List<String> tags;
    private String navdesc;
    private String qq;
    private String github;
    private String sina;
    private String mail;
    private String footer;
    private String logo;
    private String favicon;
    private boolean debug;


    public void setCdn(String cdn) {
        if (StringUtils.isEmptyOrWhitespace(cdn)) {
            cdn = "";
        }
        this.cdn = cdn;
    }

}
