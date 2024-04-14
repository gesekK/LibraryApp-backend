package com.example.technologiesieciowe1.google_books_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDetail {

    @JsonProperty("genre")
    private String genre;
    @JsonProperty("description")
    private String description;
    @JsonProperty("coverImageUrl")
    private String coverImageUrl;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}
