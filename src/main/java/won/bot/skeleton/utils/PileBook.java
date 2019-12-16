package won.bot.skeleton.utils;

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
    private String url;
    private String year;
    private String isbn;

    private String search;

    public PileBook(String search) {
        this.search = search;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim().replaceAll("<.?mark>", "");
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url.trim().replace("&amp;", "&");
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year.trim();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        if (subtitle != null && !subtitle.equals("")) {
            String desc = subtitle;

            if (year != null) {
                return desc + ", " + year;
            }

            return desc;
        }
        if (year != null) {
            return ""+year;
        }
        return "";
    }

    @Override
    public String toString() {
        return "PileBook{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", author='" + author + '\'' +
                ", series='" + series + '\'' +
                ", publisher='" + publisher + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", url='" + url + '\'' +
                ", year='" + year + '\'' +
                ", isbn='" + isbn + '\'' +
                ", search='" + search + '\'' +
                '}';
    }
}
