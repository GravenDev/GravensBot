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

package fr.gravendev.gravensbot.utils.sanctions;

import org.bson.Document;

import java.time.Instant;

public class MongoBasicSanction {


    private final int sanctionId;
    private final Instant createdAt, updatedAt;
    private final String sanctionType;
    private final String reason, sanctionMessage;
    private final long server, applier, target;

    protected MongoBasicSanction(
        int sanctionId,
        String type,
        String reason,
        long server,
        long applier,
        long target,
        String sanctionMessage,
        Instant createdAt,
        Instant updatedAt
    ) {
        this.sanctionId = sanctionId;
        this.sanctionType = type;
        this.server = server;
        this.reason = reason;
        this.applier = applier;
        this.sanctionMessage = sanctionMessage;
        this.target = target;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MongoBasicSanction fromBasicSanction(BasicSanction sanction) {
        return new MongoBasicSanction(
            sanction.getId(),
            sanction.getSanctionType().name(),
            sanction.getReason(),
            sanction.getServer().getId(),
            sanction.getApplier().getId(),
            sanction.getTarget().getId(),
            sanction.getSanctionMessage().getLink().toString(),
            sanction.getCreatedAt(),
            sanction.getUpdatedAt()
        );
    }

    public static MongoBasicSanction fromBson(Document a) {
        if (a == null) return null;
        return new MongoBasicSanction(
            a.get("sanctionId", Integer.class),
            a.get("type", String.class),
            a.get("reason", String.class),
            a.get("server", Long.class),
            a.get("applier", Long.class),
            a.get("target", Long.class),
            a.get("message", String.class),
            a.get("createdAt", Instant.class),
            a.get("updatedAt", Instant.class)
        );
    }

    public final int getSanctionId() {
        return sanctionId;
    }

    public final String getSanctionType() {
        return sanctionType;
    }

    public final String getReason() {
        return reason;
    }

    public long getServer() {
        return server;
    }

    public final long getApplier() {
        return applier;
    }

    public final long getTarget() {
        return target;
    }

    public final String getSanctionMessage() {
        return sanctionMessage;
    }

    public final Instant getCreatedAt() {
        return createdAt;
    }

    public final Instant getUpdatedAt() {
        return updatedAt;
    }

    public Document toBson() {
        return new Document()
            .append("sanctionId", getSanctionId())
            .append("type", getSanctionType())
            .append("reason", getReason())
            .append("server", getServer())
            .append("applier", getApplier())
            .append("target", getTarget())
            .append("message", getSanctionMessage())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt());
    }
}
