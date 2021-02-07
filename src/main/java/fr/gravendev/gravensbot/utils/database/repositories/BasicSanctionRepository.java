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
import fr.gravendev.gravensbot.utils.sanctions.MongoEndingSanction;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicSanctionRepository {

    private final DatabaseConnection connection;
    private final MongoDatabase database;

    public BasicSanctionRepository(DatabaseConnection connection) {
        this.connection = connection;
        this.database = connection.getDatabase();
    }

    public List<MongoBasicSanction> findAll() {
        List<MongoBasicSanction> rt = new ArrayList<>();
        database.getCollection("basicSanctions")
            .find()
            .iterator()
            .forEachRemaining(a -> rt.add(MongoBasicSanction.fromBson(a)));
        return rt;
    }

    public Optional<MongoBasicSanction> findById(int sanctionId) {
        MongoCollection<Document> sanctions =
            database.getCollection("basicSanctions");
        Document filter = new Document()
            .append("sanctionId", sanctionId);
        MongoBasicSanction sanction = MongoBasicSanction.fromBson(sanctions
            .find(filter)
            .limit(1)
            .first());
        return Optional.ofNullable(sanction);
    }

    public List<MongoBasicSanction> findByTargetId(long userId) {
        MongoCollection<Document> sanctions =
            database.getCollection("basicSanctions");
        Document filter = new Document()
            .append("target", userId);
        List<MongoBasicSanction> rt = new ArrayList<>();
        sanctions
            .find(filter)
            .iterator()
            .forEachRemaining(a -> rt.add(MongoBasicSanction.fromBson(a)));
        return rt;
    }

    public int getLastSanctionId() {
        Document filter = new Document().append("sanctionId", 1);
        try {
            return database
                .getCollection("basicSanctions")
                .find()
                .sort(filter)
                .limit(1)
                .first()
                .get("sanctionId", 0);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public void save(MongoBasicSanction sanction) {
        if (findById(sanction.getSanctionId()).isPresent()) {
            MongoCollection<Document> sanctions = database
                .getCollection("basicSanctions");
            Document filter = new Document()
                .append("sanctionId", sanction.getSanctionId());
            sanctions.updateOne(filter, sanction.toBson());
        } else {
            database
                .getCollection("basicSanctions")
                .insertOne(sanction.toBson());
        }
    }
}
