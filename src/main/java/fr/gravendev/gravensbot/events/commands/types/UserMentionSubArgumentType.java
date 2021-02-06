package fr.gravendev.gravensbot.events.commands.types;

import net.feedthemadness.glib.command.sub.argument.ISubArgumentType;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMentionSubArgumentType implements ISubArgumentType {

    private final DiscordApi api;

    public UserMentionSubArgumentType(DiscordApi api) {
        this.api = api;
    }

    private final Pattern mentionPattern = Pattern.compile("<@![0-9]{18}>");
    private final Pattern idPattern = Pattern.compile("[0-9]{18}");

    @Override
    public boolean validate(String input) {
        Matcher id = idPattern.matcher(input);
        Matcher mention = mentionPattern.matcher(input);
        if (id.find() && input.length() == 18) {
            input = input.substring(id.start(), id.end());
        } else if (mention.find() && input.length() == 18 + 4) {
            input = input.substring(mention.start() + 3, mention.end() - 1);
        } else {
            return false;
        }
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicBoolean valid = new AtomicBoolean(false);
        api.getUserById(input).thenAccept(user -> {
            valid.set(user != null);
            found.set(true);
        });
        while (!found.get()) ;
        return valid.get();
    }

    @Override
    public Object parse(String input) {
        Matcher id = idPattern.matcher(input);
        Matcher mention = mentionPattern.matcher(input);
        if (id.find()) {
            input = input.substring(id.start(), id.end());
        } else if (mention.find()) {
            input = input.substring(mention.start() + 2, mention.end() - 1);
        }

        AtomicBoolean found = new AtomicBoolean(false);
        AtomicReference<User> user = new AtomicReference<>();
        api.getUserById(input).thenAccept(fUser -> {
            user.set(fUser);
            found.set(true);
        });
        while (!found.get()) ;
        return user.get();
    }
}
