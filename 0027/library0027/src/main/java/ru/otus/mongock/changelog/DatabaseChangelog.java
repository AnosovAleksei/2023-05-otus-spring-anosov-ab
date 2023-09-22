package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


@ChangeLog
public class DatabaseChangelog {

    private Document authorPushkin;

    private Document authorTolstoy;

    private Document genryPoetry;

    private Document genryProse;

    private Document bookEugeneOnegin;


    @ChangeSet(order = "001", id = "dropDb", author = "anosov_ab", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertPushkin", author = "anosov_ab")
    public void insertPushkin(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("author");
        var doc = new Document().append("name", "Pushkin");
        myCollection.insertOne(doc);
        authorPushkin = doc;
    }

    @ChangeSet(order = "003", id = "insertTolstoy", author = "anosov_ab")
    public void insertTolstoy(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("author");
        var doc = new Document().append("name", "Tolstoy");
        myCollection.insertOne(doc);
        authorTolstoy = doc;
    }

    @ChangeSet(order = "003", id = "insertPoetry", author = "anosov_ab")
    public void insertPoetry(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("genre");
        var doc = new Document().append("name", "poetry");
        myCollection.insertOne(doc);
        genryPoetry = doc;
    }

    @ChangeSet(order = "004", id = "insertProse", author = "anosov_ab")
    public void insertProse(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("genre");
        var doc = new Document().append("name", "prose");
        myCollection.insertOne(doc);
        genryProse = doc;
    }

    @ChangeSet(order = "005", id = "insertEugeneOnegin", author = "anosov_ab")
    public void insertEugeneOnegin(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("book");
        var doc = new Document().append("name", "Eugene Onegin")
                .append("author", authorPushkin)
                .append("genre", genryPoetry);
        myCollection.insertOne(doc);
        bookEugeneOnegin = doc;
    }

    @ChangeSet(order = "006", id = "insertWarAndPeace", author = "anosov_ab")
    public void insertWarAndPeace(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("book");
        var doc = new Document().append("name", "War and Peace")
                .append("author", authorTolstoy)
                .append("genre", genryProse);

        myCollection.insertOne(doc);
    }

    @ChangeSet(order = "007", id = "insertCommentary1", author = "anosov_ab")
    public void insertCommentary1(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("commentary");
        var doc = new Document().append("message", "commentary 1").append("book", bookEugeneOnegin);

        myCollection.insertOne(doc);
    }

    @ChangeSet(order = "008", id = "insertCommentary2", author = "anosov_ab")
    public void insertCommentary2(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("commentary");
        var doc = new Document().append("message", "commentary 2").append("book", bookEugeneOnegin);

        myCollection.insertOne(doc);
    }

}

