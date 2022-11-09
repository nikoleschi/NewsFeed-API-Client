package bg.sofia.uni.fmi.mjt.newsfeed.news;
 
import java.util.Arrays;
import java.util.Objects;
 
public class NewsFeed {
 
    private final QueryMetadata metadata;
    private final Article[] articles; 
 
    public NewsFeed(QueryMetadata metadata, Article[] articles) {
        this.metadata = metadata;
        this.articles = articles;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewsFeed)) {
            return false;
        }
        NewsFeed newsFeed = (NewsFeed) o;
        return metadata.equals(newsFeed.metadata) && Arrays.equals(articles, newsFeed.articles);
    }
 
    @Override
    public int hashCode() {
        int result = Objects.hash(metadata);
        result = 31 * result + Arrays.hashCode(articles);
        return result;
    }
}
