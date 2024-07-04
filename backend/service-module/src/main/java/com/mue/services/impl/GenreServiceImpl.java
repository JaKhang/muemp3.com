package com.mue.services.impl;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.Genre;
import com.mue.converters.Converter;
import com.mue.payload.request.GenreRequest;
import com.mue.payload.response.GenreResponse;
import com.mue.repositories.GenreRepository;
import com.mue.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final Converter<GenreResponse, GenreResponse, Genre, GenreRequest> genreMapper;

    @Override
    public List<GenreResponse> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::convertToLine).toList();
    }

    @Override
    public GenreResponse findOne(UUID id) {
        Genre genre = findByIdElseThrow(id);
        return genreMapper.convertToDetails(genre);
    }

    @Override
    public UUID create(GenreRequest genreRequest) {
        Genre genre = genreMapper.convertToEntity(genreRequest);
        genreRepository.save(genre);
        return genre.getId();
    }

    @Override
    public void softDelete(UUID id) {
        Genre genre = findByIdElseThrow(id);
        genreRepository.save(genre);
    }

    @Override
    public void update(UUID id, GenreRequest genreRequest) {
        Genre newGenre = genreMapper.convertToEntity(genreRequest);
        Genre genre = findByIdElseThrow(id);
        genre.setAlias(newGenre.getAlias());
        genre.setCoverImage(newGenre.getCoverImage());
        genre.setName(newGenre.getName());
        genreRepository.save(genre);
    }

    private Genre findByIdElseThrow(UUID id) {
        return genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Genre", "ID", id)
        );
    }
}
