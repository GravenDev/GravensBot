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

package fr.gravendev.gravensbot.commands.misc;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class PingCommand {

    public void run(MessageCreateEvent event, String[] args) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
            .setTitle("Pinging...")
            .setColor(Color.ORANGE);

        event.getChannel().sendMessage(embedBuilder).thenAccept((message) -> {
            event.getApi().measureRestLatency().thenAccept(latency -> {
                embedBuilder
                    .setTitle("Pong !")
                    .setColor(Color.GREEN)
                    .addField(
                        "Bot Latency",
                        "" + (
                            message.getCreationTimestamp().toEpochMilli() -
                                event.getMessage().getCreationTimestamp().toEpochMilli()
                        ) + " ms"
                    )
                    .addField("API Latency", latency.toMillis() + " ms");
                message.edit(embedBuilder);
            });
        });
    }
}
