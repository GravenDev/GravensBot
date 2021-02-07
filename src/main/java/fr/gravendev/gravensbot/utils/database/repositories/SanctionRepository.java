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

package fr.gravendev.gravensbot.utils.database.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.gravendev.gravensbot.utils.database.DatabaseConnection;
import fr.gravendev.gravensbot.utils.sanctions.MongoBasicSanction;
import org.bson.Document;
import org.javacord.api.entity.user.User;

import java.util.List;
import java.util.Optional;

public class SanctionRepository {

    private final DatabaseConnection connection;
    private final MongoDatabase database;

    public SanctionRepository(DatabaseConnection connection) {
        this.connection = connection;
        this.database = connection.getDatabase();
    }

    public MongoCollection<MongoBasicSanction> findAll() {
        return database.getCollection("sanctions", MongoBasicSanction.class);
    }

    public Optional<MongoBasicSanction> findById(int sanctionId) {
        MongoCollection<MongoBasicSanction> sanctions =
            database.getCollection("sanctions", MongoBasicSanction.class);
        Document filter = new Document()
            .append("sanctionId", sanctionId);
        MongoBasicSanction sanction = sanctions
            .find(filter)
            .limit(1)
            .first();
        return Optional.ofNullable(sanction);
    }

    public List<MongoBasicSanction> findByUserId(long userId) {

    }
}
