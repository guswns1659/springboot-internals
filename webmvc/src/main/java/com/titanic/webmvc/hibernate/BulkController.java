package com.titanic.webmvc.hibernate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
interface DocumentRepository extends JpaRepository<Document, Long> {

}

//@RestController
@RequiredArgsConstructor
public class BulkController {

    private final DocumentRepository documentRepository;

    @GetMapping("")
    public String save() {
        List<String> names = Arrays.asList("name", "name2");
        System.out.println(">>>>>" + this.getClass());

        List<Document> documents = names.stream().map(Document::new).collect(Collectors.toList());

        documentRepository.saveAll(documents);

        return "success";
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
class Document {

    @Id
    String name;
}
