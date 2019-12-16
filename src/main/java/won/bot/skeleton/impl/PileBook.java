package won.bot.skeleton.impl;

/**
 * Created by Samantha on 10.12.2019.
 */
public class PileBook {
    private String title;
    private String subtitle;
    private String author;
    private String series;
    private String publisher;
    private String mediaType;
    private String href;
    private String year;

    private String search;

    public PileBook(String search) {
        this.search = search;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author.trim();
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = (series != null ? series.trim() : series);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher.trim();
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType.trim();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search.trim();
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year.trim();
    }

    @Override
    public String toString() {
        return "PileBook{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", series='" + series + '\'' +
                ", publisher='" + publisher + '\'' +
                ", mediaType='" + mediaType + '\'' +
                '}';
    }
}
