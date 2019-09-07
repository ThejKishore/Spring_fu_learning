package com.kish.learning.fu.demofu;

import com.kish.learning.fu.demofu.config.Configuration;
import com.kish.learning.fu.demofu.repo.UserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.fu.jafu.JafuApplication;

import static org.springframework.fu.jafu.Jafu.webApplication;

@SpringBootApplication
public class DemofuApplication {
    public static JafuApplication app = webApplication(app ->
            app.enable(Configuration.dataConfig)
                    .enable(Configuration.webConfig)
                    .listener(ApplicationReadyEvent.class, e -> app.ref(UserRepository.class).init())
    );

    public static void main (String[] args) {
        app.run(args);
    }
}
