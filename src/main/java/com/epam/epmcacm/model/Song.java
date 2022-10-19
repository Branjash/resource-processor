package com.epam.epmcacm.model;

import com.epam.epmcacm.util.FileUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class Song {

    private String name;
    private String artist;
    private String album;
    private String length;
    private Long resourceId;
    private String year;

    public Song() {
    }

    public Song(String name, String artist, String album, String length, Long resourceId, String year) {

        this.name = name;
        this.artist = artist;
        this.album = album;
        this.length = length;
        this.resourceId = resourceId;
        this.year = year;
    }

    public Song(String resourceId, Map<FileUtil.FileAttribute, String> additionalAttributes) {
        this.name = additionalAttributes.get(FileUtil.FileAttribute.TRACK);
        this.artist = additionalAttributes.get(FileUtil.FileAttribute.ARTIST);
        this.album = additionalAttributes.get(FileUtil.FileAttribute.ALBUM);
        this.length = additionalAttributes.get(FileUtil.FileAttribute.LENGTH);
        this.year = additionalAttributes.get(FileUtil.FileAttribute.YEAR);
        this.resourceId = Long.valueOf(resourceId);
    }

    public static Song createSongDTOForClientRequest(Resource resource, byte[] bytes) throws InvalidDataException, UnsupportedTagException, IOException {
        Mp3File mp3 = FileUtil.createMp3File(resource.getName(),bytes);
        Map<FileUtil.FileAttribute, String> fileAttributeStringMap = FileUtil.generateMp3FileAttributeMap(mp3);
        Song songDTO = new Song(String.valueOf(resource.getId()), fileAttributeStringMap);
        FileUtil.deleteTempFile(resource.getName());
        return songDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return name.equals(song.name) && resourceId.equals(song.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, resourceId);
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", length='" + length + '\'' +
                ", resourceId=" + resourceId +
                ", year=" + year +
                '}';
    }
}