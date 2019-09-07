package com.kish.learning.fu.demofu;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.fu.jafu.JafuApplication;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.HandlerFunctionAdapter;

import java.util.Objects;

import static org.springframework.fu.jafu.Jafu.webApplication;
import static org.springframework.fu.jafu.web.WebFluxServerDsl.server;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class DemofuApplication {


    public static JafuApplication app = webApplication(a ->
            a.enable(server(s -> {
                s.router(r -> {
                            r.GET("/", request -> ok().syncBody("Hello world!"));
                            r.GET("/{name}", request -> {
                                String name = request.pathVariable("name");
                                if(Objects.isNull(name)) return badRequest().syncBody("mandatory data missing, Hello Guest");
                                return ok().syncBody(String.format("hello %s",request.pathVariable("name")));
                            } );
                        }
                );
                s.port(9999);

            }))
    );

    public static void main (String[] args) {
        app.run(args);
    }
}
