package com.springboot.springbootinternals.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Repository
interface DocumentRepository extends JpaRepository<Document, Long> {

}

@RestController
@RequiredArgsConstructor
public class BulkController {

    private final DocumentRepository documentRepository;

    @GetMapping("")
    public String save() {
        Document document = new Document("name");

        documentRepository.save(document);

        return "success";
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Entity
class Document {

    @Id
    String name;
}
