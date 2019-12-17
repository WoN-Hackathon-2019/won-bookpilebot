package won.bot.bookpile.utils;

import org.apache.commons.lang3.StringEscapeUtils;

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
        this.title = preProcess(title);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = preProcess(subtitle);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = preProcess(author);
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = (series != null ? preProcess(series) : series);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = preProcess(publisher);
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = preProcess(mediaType);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = preProcess(search);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        // .replace("&amp;", "&")
        this.url = preProcess(url);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = preProcess(year);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = preProcess(isbn);
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

    public String preProcess(String input){
        String withCharacters = StringEscapeUtils.unescapeHtml3(input);
        
        return withCharacters.trim().replaceAll("<.?mark>", "");
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
