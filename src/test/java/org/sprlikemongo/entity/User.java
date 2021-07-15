package org.sprlikemongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fangjie
 * @date Created in 3:39 下午 2021/7/5.
 */
@Document(collection = "user")
public class User {
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

