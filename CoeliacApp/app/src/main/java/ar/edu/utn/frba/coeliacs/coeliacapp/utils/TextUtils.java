package ar.edu.utn.frba.coeliacs.coeliacapp.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by max on 6/11/2016.
 */

public class TextUtils {
    public static String getRideOfAccents(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
