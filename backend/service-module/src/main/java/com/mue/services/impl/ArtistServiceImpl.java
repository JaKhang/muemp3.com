package com.mue.services.impl;

import com.mue.converters.ObjectConverter;
import com.mue.core.domain.ApiQuery;
import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.Album;
import com.mue.entities.Artist;
import com.mue.converters.Converter;
import com.mue.entities.User;
import com.mue.payload.request.ArtistRequest;
import com.mue.payload.response.ArtistLineResponse;
import com.mue.payload.response.ArtistResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.payload.response.ObjectResponse;
import com.mue.repositories.ArtistRepository;
import com.mue.repositories.ArtistTypeRepository;
import com.mue.services.ArtistService;
import com.mue.specifications.SpecificationBuilder;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final Converter<ArtistLineResponse, ArtistResponse, Artist, ArtistRequest> mapper;
    private final ObjectConverter objectConverter;
    private final ArtistTypeRepository artistTypeRepository;

    @Override
    public InfiniteListResponse<ArtistLineResponse> findAll(Pageable pageable, List<ApiQuery> queryRequests) {
        System.out.println(queryRequests);
        Specification<Artist> artistSpecification = SpecificationBuilder.build(queryRequests);
        Page<Artist> artists = artistRepository.findAll(artistSpecification, pageable);
        return new InfiniteListResponse<>(
                artists.getNumberOfElements(),
                artists.stream().map(mapper::convertToLine).toList(),
                artists.hasNext()
        );
    }

    @Override
    public ArtistResponse findOne(UUID id) {
        Artist artist = findByIdElseThrow(id);
        return mapper.convertToDetails(artist);
    }

    @Override
    public UUID create(ArtistRequest artistRequest) {
        System.out.println(artistRequest);
        Artist artist = mapper.convertToEntity(artistRequest);
        return artistRepository.save(artist).getId();
    }

    @Override
    public void softDelete(UUID id) {
        Artist artist = findByIdElseThrow(id);
        artist.setDeleted(true);
        artistRepository.save(artist);
    }

    @Override
    public void update(UUID id, ArtistRequest artistRequest) {
        Artist artist = findByIdElseThrow(id);
        Artist newArtist = mapper.convertToEntity(artistRequest);
        artist.setAlias(newArtist.getAlias());
        artist.setAvatar(newArtist.getAvatar());
        artist.setCountry(newArtist.getCountry());
        artist.setDescription(newArtist.getDescription());
        artist.setName(newArtist.getName());
        artist.setIsBand(newArtist.getIsBand());
        artist.setIsIndie(newArtist.getIsIndie());
        artist.setBirthday(newArtist.getBirthday());
        artist.setCoverImage(newArtist.getCoverImage());
        artist.setTypes(newArtist.getTypes());
        artist.setIsOfficial(artist.getIsOfficial());
        artistRepository.save(artist);

    }

    @Override
    public List<ObjectResponse> findAllType() {
        return artistTypeRepository.findAll().stream().map(objectConverter::convert).toList();
    }

    @Override
    public InfiniteListResponse<ArtistLineResponse> findAllByUserId(UUID id, Pageable pageable) {
        Specification<Artist> s = (root, var, criteriaBuilder) -> {
            Join<Artist, User> users = root.join("likedUsers");
            return criteriaBuilder.equal(users.get("id"), id);
        };

        Page<Artist> artists = artistRepository.findAll(s, pageable);
        return new InfiniteListResponse<>(
                artists.getNumberOfElements(),
                artists.stream().map(mapper::convertToLine).toList(),
                artists.hasNext()
        );
    }

    @Override
    public List<ArtistLineResponse> findTop(int top, Sort sort) {
//        Pageable pageable = PageRequest.of(0, top, );
        List<Artist> artists = artistRepository.findAll(JpaSort.by(sort.stream().toList()));
        return artists.stream().map(mapper::convertToLine).toList();
    }

    private Artist findByIdElseThrow(UUID id) {
        return artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist", "ID", id));
    }
}
