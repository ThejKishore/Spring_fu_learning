package com.kish.learning.fu.demofu.repo;

import com.kish.learning.fu.demofu.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.r2dbc.function.DatabaseClient;

public class UserRepository {

    private final DatabaseClient client;

    public UserRepository(DatabaseClient client) {
        this.client = client;
    }

    public Mono<Integer> count() {
        return client.execute().sql("SELECT COUNT(*) FROM users").as(Integer.class).fetch().one();
    }

    public Flux<User> findAll() {
        return client.select().from("users").as(User.class).fetch().all();
    }

    public Mono<User> findOne(String id) {
        return client.execute().sql("SELECT * FROM users WHERE login = $1").bind(1, id).as(User.class).fetch().one();
    }

    public Mono<Void> deleteAll() {
        return client.execute().sql("DELETE FROM users").fetch().one().then();
    }

    public Mono<String> save(User user) {
        return client.insert().into(User.class).table("users").using(user)
                .map((r, m) -> r.get("login", String.class)).one();
    }

    public void init() {
        client.execute().sql("CREATE TABLE IF NOT EXISTS users (login varchar PRIMARY KEY, firstname varchar, lastname varchar);").then()
                .then(deleteAll())
                .then(save(new User("kishores", "Thej Kishore","Karuneegar")))
                .then(save(new User("shanayas", "Shanaya","Karuneegar")))
                .then(save(new User("bharathi", "Bharathi","Kaeuneegar")))
                .then(save(new User("ganesh", "Ganesh","Kaeuneegar")))
                .block();
    }
}