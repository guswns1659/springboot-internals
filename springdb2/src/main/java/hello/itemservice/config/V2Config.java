package hello.itemservice.config;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jpa.JpaItemRepositoryV3;
import hello.itemservice.repository.v2.ItemQueryRepository;
import hello.itemservice.repository.v2.ItemRepositoryV2;
import hello.itemservice.repository.v2.ItemServiceV2;
import hello.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class V2Config {
    private final ItemRepositoryV2 itemRepositoryV2;
    private final EntityManager em;

    @Bean
    public ItemQueryRepository itemQueryRepository() {
        return new ItemQueryRepository(em);
    }

    @Bean
    public ItemService itemService() {
        return new ItemServiceV2(itemRepositoryV2, itemQueryRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV3(em);
    }
}
