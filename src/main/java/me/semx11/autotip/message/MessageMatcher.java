package me.semx11.autotip.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageMatcher {
    private final Matcher matcher;

    public MessageMatcher(Pattern pattern, String input) {
        this.matcher = pattern.matcher(input);
    }

    public boolean matches() {
        return matcher.matches();
    }
}
