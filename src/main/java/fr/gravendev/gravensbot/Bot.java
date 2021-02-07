/*
 *
 * MIT License
 *
 * Copyright (c) 2021 Graven - Développement
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.gravendev.gravensbot;

import com.github.shyiko.dotenv.DotEnv;
import fr.gravendev.gravensbot.events.MessageEventListener;
import fr.gravendev.gravensbot.utils.database.DatabaseConnection;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.sql.SQLException;

public class Bot {

    private DiscordApi api;
    private DatabaseConnection connection;
    private volatile boolean running;

    public Bot() {
        this.running = false;
        this.connection = new DatabaseConnection();
    }

    public void run() {
        if (running) throw new RuntimeException("The bot is already running");

        api = new DiscordApiBuilder()
            .setToken(DotEnv.load().get("DISCORD_BOT_TOKEN"))
            .login().join();
        running = true;
        setup();
    }

    private void setup() {

        api.addMessageCreateListener(new MessageEventListener(api, connection));

    }

    public void stop() throws SQLException {
        if (!running) throw new RuntimeException("The bot is not running");

        api.disconnect();
        connection.close();
        running = false;
    }

}
