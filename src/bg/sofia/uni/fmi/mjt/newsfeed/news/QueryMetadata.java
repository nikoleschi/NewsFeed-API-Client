package bg.sofia.uni.fmi.mjt.newsfeed.news;
 
import java.util.Objects;
import java.util.Optional;
 
public class QueryMetadata {
 
    private final String status;
    private final String message;
    private final int totalResults;
 
    public QueryMetadata(String status, String message, int totalResults) {
        this.status = status;
        this.message = message;
        this.totalResults = totalResults;
    }
 
    public String getMessage() {
        return message;
    }
 
    public String getStatus() {
        return status;
    }
 
    public int getTotalResults() {
        return totalResults;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QueryMetadata source)) {
            return false;
        }
        return getTotalResults() == source.getTotalResults() && getStatus().equals(source.getStatus());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getTotalResults());
    }
}
