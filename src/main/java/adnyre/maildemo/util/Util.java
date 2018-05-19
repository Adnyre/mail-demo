package adnyre.maildemo.util;

import adnyre.maildemo.model.Addressee;
import org.springframework.util.StringUtils;

public class Util {

    public static final String NAME_PLACEHOLDER = "<==name==>";

    public static String getTextFromTemplate(String template, Addressee addressee) {
        String name = "Customer";
        if (!StringUtils.isEmpty(addressee.getFirstName())) {
            name = addressee.getFirstName();
        }
        return template.replaceAll(NAME_PLACEHOLDER, name);
    }
}