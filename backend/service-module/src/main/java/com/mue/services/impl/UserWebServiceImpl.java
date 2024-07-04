package com.mue.services.impl;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.core.exception.UnauthorizedException;
import com.mue.core.exception.UserNotFoundException;
import com.mue.entities.*;
import com.mue.payload.request.ActionRequest;
import com.mue.payload.response.*;
import com.mue.repositories.*;
import com.mue.security.domain.UserPrincipal;
import com.mue.services.*;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserWebServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final PlaylistRepository playlistRepository;


    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal principal) {
            return userRepository.findById(principal.getId()).orElseThrow(() -> new UserNotFoundException("Not found user with id " + principal.getId()));
        }
        return null;
    }

    @Override
    public void saveTracks(List<UUID> ids) {
        User user = getAuthenticatedUser();
        List<Track> tracks = trackRepository.findAllById(ids);
        user.getLikedTracks().addAll(tracks);
        userRepository.save(user);
    }

    @Override
    public void removeTracks(List<UUID> ids) {
        User user = getAuthenticatedUser();
        List<Track> tracks = trackRepository.findAllById(ids);
        user.getLikedTracks().removeAll(tracks);
        userRepository.save(user);
    }

    @Override
    public void followAlbums(List<UUID> ids) {
        User user = getAuthenticatedUser();
        List<Album> albums = albumRepository.findAllById(ids);
        user.getLikedAlbums().addAll(albums);
        userRepository.save(user);
    }

    @Override
    public void unfollowAlbums(List<UUID> ids) {
        User user = getAuthenticatedUser();
        List<Album> albums = albumRepository.findAllById(ids);
        user.getLikedAlbums().removeAll(albums);
        userRepository.save(user);
    }

    @Override
    public void followArtists(List<UUID> ids) {
        User user = getAuthenticatedUser();
        List<Artist> artists = artistRepository.findAllById(ids);
        user.getLikedArtists().addAll(artists);
        userRepository.save(user);
    }

    @Override
    public void unfollowArtists(List<UUID> ids) {
        User user = getAuthenticatedUser();
        List<Artist> artists = artistRepository.findAllById(ids);
        user.getLikedArtists().removeAll(artists);
        userRepository.save(user);
    }

    @Override
    public void followPlaylists(List<UUID> ids) {
        User user = getAuthenticatedUser();
        List<PlayList> playlists = playlistRepository.findAllById(ids);
        user.getLikedPlaylists().addAll(playlists);
        userRepository.save(user);
    }

    @Override
    public void unfollowPlaylists(List<UUID> ids) {
        User user = getAuthenticatedUser();
        List<PlayList> playlists = playlistRepository.findAllById(ids);
        user.getLikedPlaylists().removeAll(playlists);
        userRepository.save(user);
    }








}


