package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

import ru.otus.domain.Child;
import ru.otus.services.LessonService;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> itemsChannel() {
        return MessageChannels.queue(2);
    }

    @Bean
    public MessageChannelSpec<?, ?> schoolChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow cafeFlow(LessonService lessonService) {
        return IntegrationFlow.from(itemsChannel())
                .split()
                .handle(lessonService, "work")
                .<List<Child>, List<Child>>transform(f -> {
                            return new ArrayList<>() {{
                                for (Child c : f) {
                                    add(new Child(c.getName().toUpperCase(),
                                            c.getTraining().toUpperCase(),
                                            c.getGrate() + 1));
                                }
                            }};
                        }

                )
                .aggregate()
                .channel(schoolChannel())
                .get();
    }
}
