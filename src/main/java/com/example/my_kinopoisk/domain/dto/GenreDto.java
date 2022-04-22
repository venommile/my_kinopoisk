package com.example.my_kinopoisk.domain.dto;

import com.example.my_kinopoisk.validation.OnCreate;
import com.example.my_kinopoisk.validation.OnSearch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;
import java.io.Serializable;


@Getter
@Setter
public class GenreDto implements Serializable {
    @Null(groups = OnCreate.class)
    private Long id;

    @Null(groups = OnSearch.class)
    private String title;

    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof GenreDto)) return false;
        GenreDto genreDto = (GenreDto) object;
        return this.id.equals(genreDto.getId());
    }
}
