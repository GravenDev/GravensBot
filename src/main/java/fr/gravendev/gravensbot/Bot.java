package fr.gravendev.gravensbot;

import com.github.shyiko.dotenv.DotEnv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Bot {

    private DiscordApi api;
    private volatile boolean running;

    public Bot() {
        running = false;
    }

    public void run() {
        api = new DiscordApiBuilder()
            .setToken(DotEnv.load().get("DISCORD_BOT_TOKEN"))
            .login().join();
        running = true;
    }

}
