package com.mue.services.impl;

import com.mue.converters.Converter;
import com.mue.core.domain.ApiQuery;
import com.mue.core.exception.MethodNotImplementException;
import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.Album;
import com.mue.entities.PlayerCounter;
import com.mue.entities.Track;
import com.mue.entities.User;
import com.mue.enums.Bitrate;
import com.mue.factories.URLFactory;
import com.mue.payload.request.TrackRequest;
import com.mue.payload.response.TrackLineResponse;
import com.mue.payload.response.TrackResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.repositories.PlayerCounterRepository;
import com.mue.repositories.TrackRepository;
import com.mue.services.AudioProcessService;
import com.mue.services.TrackService;
import com.mue.specifications.SpecificationBuilder;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;
    private final Converter<TrackLineResponse, TrackResponse, Track, TrackRequest> trackConverter;
    private final PlayerCounterRepository playerCounterRepository;
    private final URLFactory urlFactory;
    private final AudioProcessService audioProcessService;

    @Override
    public InfiniteListResponse<TrackLineResponse> findAll(Pageable pageable, List<ApiQuery> queryRequests) {
        Specification<Track> trackSpecification = SpecificationBuilder.build(queryRequests);
        Page<Track> tracks = trackRepository.findAll(trackSpecification, pageable);
        return new InfiniteListResponse<>(
                tracks.getNumberOfElements(),
                tracks.stream().map(trackConverter::convertToLine).toList(),
                tracks.hasNext()
        );
    }

    @Override
    public TrackResponse findOne(UUID id) {
        Track track = findByIdElseThrow(id);
        return trackConverter.convertToDetails(track);
    }

    @Override
    public UUID create(TrackRequest trackRequest) {

        Track track = trackConverter.convertToEntity(trackRequest);
        track = trackRepository.save(track);
        PlayerCounter playerCounter = new PlayerCounter();
        track.setPlayerCounter(playerCounter);
        playerCounter.setTrack(track);
        trackRepository.save(track);
        processAudio(trackRequest);
        return track.getId();
    }

    @Override
    public void softDelete(UUID id) {
        Track track = findByIdElseThrow(id);
        track.setDeleted(false);
        trackRepository.save(track);
    }

    @Override
    public void update(UUID id, TrackRequest trackRequest) {
        throw new MethodNotImplementException("Method Not Implement");
    }

    @Override
    public Map<Bitrate, String> getStreamUrl(UUID id) {
        Track track = findByIdElseThrow(id);
        return urlFactory.generateHlsUrl(track.getAudio());
    }

    @Override
    public InfiniteListResponse<TrackLineResponse> findAllByUserId(UUID id, Pageable pageable) {
        Specification<Track> s = (root, var, criteriaBuilder) -> {
            Join<Track, User> users = root.join("users");
            return criteriaBuilder.equal(users.get("id"), id);
        };

        Page<Track> tracks = trackRepository.findAll(s, pageable);
        return new InfiniteListResponse<>(
                tracks.getNumberOfElements(),
                tracks.stream().map(trackConverter::convertToLine).toList(),
                tracks.hasNext()
        );
    }

    @Override
    public List<TrackLineResponse> findByAlbumId(UUID id) {
        List<Track> tracks = trackRepository.findAllByAlbumId(id, Sort.by(Sort.Direction.ASC, "trackIndex"));
        return tracks.stream().map(trackConverter::convertToLine).toList();
    }


    private Track findByIdElseThrow(UUID id) {
        return trackRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Track", "ID", id)
        );
    }

    private void processAudio(TrackRequest trackRequest) {
        if (trackRequest.isProcess() && trackRequest.getAudioId() != null) {
            Thread thread = new Thread(() -> {
                audioProcessService.processToM3u8(trackRequest.getAudioId());
            });

            thread.start();
        }
    }
}
