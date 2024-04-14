package com.example.technologiesieciowe1.services;

import com.example.technologiesieciowe1.google_books_api.BookDetail;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookDetailsService {

    @Value("${GOOGLE_BOOKS_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookDetailsService.class);


    public BookDetailsService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public BookDetail getBookDetails(String title) {

        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title + "&key=" + apiKey;
        String response = restTemplate.getForObject(apiUrl, String.class);
        logger.info("Response from Google Books API: {}", response);

        try {
            JsonNode rootNode = objectMapper.readTree(response);

            int totalItems = rootNode.get("totalItems").asInt();
            if (totalItems > 0) {
                JsonNode itemsNode = rootNode.get("items");
                JsonNode firstItemNode = itemsNode.get(0);

                BookDetail bookDetail = new BookDetail();
                JsonNode volumeInfoNode = firstItemNode.get("volumeInfo");
                if (volumeInfoNode.has("categories")) {
                    String genre = volumeInfoNode.get("categories").get(0).asText();
                    bookDetail.setGenre(genre);
                } else {
                    bookDetail.setGenre("Unknown");
                }
                if (volumeInfoNode.has("description")) {
                    String description = volumeInfoNode.get("description").asText();
                    bookDetail.setDescription(description);
                } else {
                    bookDetail.setDescription("No information");
                }
                if (volumeInfoNode.has("imageLinks")) {
                    JsonNode imageLinksNode = volumeInfoNode.get("imageLinks");
                    if (imageLinksNode.has("thumbnail")) {
                        String coverImageUrl = imageLinksNode.get("thumbnail").asText();
                        bookDetail.setCoverImageUrl(coverImageUrl);
                    }
                } else {
                    bookDetail.setCoverImageUrl("No cover image found");
                }

                return bookDetail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
