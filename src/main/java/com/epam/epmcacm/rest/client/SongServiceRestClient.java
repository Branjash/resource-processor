package com.epam.epmcacm.rest.client;

import com.epam.epmcacm.model.Song;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "songs-api",value = "songs-api", url = "${rest.client.songs.api.url}")
public interface SongServiceRestClient {

    @GetMapping("{id}")
    ResponseEntity<?> getSongById(@PathVariable("id") Long id);
    @PostMapping
    ResponseEntity<?> saveSong(@RequestBody Song song);

    @PutMapping("{id}")
    ResponseEntity<?> updateSong(@PathVariable("id") Long id,@RequestBody Song song);

    @DeleteMapping
    ResponseEntity<?> deleteSongsWithIds(@RequestParam("id") List<Long> ids);

}
