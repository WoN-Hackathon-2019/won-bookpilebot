package won.bot.skeleton.impl;

/**
 * Created by Samantha on 10.12.2019.
 */
public class PileBook {
    private String title;
    private String author;
    private String series;
    private String publisher;
    private String mediaType;

    public PileBook(String title, String author, String series, String publisher, String mediaType) {
        this.title = title;
        this.author = author;
        this.series = series;
        this.publisher = publisher;
        this.mediaType = mediaType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
