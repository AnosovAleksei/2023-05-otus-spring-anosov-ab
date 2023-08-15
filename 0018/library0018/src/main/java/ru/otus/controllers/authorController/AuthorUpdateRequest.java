package ru.otus.controllers.authorController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthorUpdateRequest {

    private Long id;

    private String name;
}
