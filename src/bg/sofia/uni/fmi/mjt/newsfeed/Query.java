package bg.sofia.uni.fmi.mjt.newsfeed;
 
public class Query {
 
    private static final String API_ENDPOINT_QUERY_KEYWORDS = "q=";
    private static final String API_ENDPOINT_QUERY_COUNTRY = "country=%s&";
    private static final String API_ENDPOINT_QUERY_CATEGORY = "category=%s&";
    private static final String DELIMITER_KEYWORDS = "+";
 
    private final String[] keywords;
 
    private final String category;
    private final String country;
    private final int pageSize;
 
    private final String queryEndpoint;
 
    public static QueryBuilder builder(String... keywords) {
        return new QueryBuilder(keywords);
    }
 
    private Query(QueryBuilder builder) {
        this.keywords = builder.keywords;
        this.category = builder.category;
        this.country = builder.country;
        this.pageSize = builder.pageSize;
 
        this.queryEndpoint = this.createQueryEndpoint();
    }
 
    public int getPageSize() {
        return pageSize;
    }
 
    public String getQueryEndpoint() {
        return queryEndpoint;
    }
 
    private String createQueryEndpoint() {
        StringBuilder query = new StringBuilder();
 
        if (category != null) {
            query.append(API_ENDPOINT_QUERY_CATEGORY.formatted(category));
        }
 
        if (country != null) {
            query.append(API_ENDPOINT_QUERY_COUNTRY.formatted(country));
        }
 
        query.append(API_ENDPOINT_QUERY_KEYWORDS)
                .append(String.join(DELIMITER_KEYWORDS, keywords));
 
        return query.toString();
    }
 
    public static class QueryBuilder {
 
        private String[] keywords;
 
        private String category;
        private String country;
        private int pageSize;
 
        private QueryBuilder(String... keywords) {
            this.keywords = keywords;
        }
 
        public QueryBuilder setCategory(String category) {
            this.category = category;
            return this;
        }
 
        public QueryBuilder setCountry(String country) {
            this.country = country;
            return this;
        }
 
        public QueryBuilder setPageSize(int size) {
            this.pageSize = size;
            return this;
        }
 
        public Query build() {
            return new Query(this);
        }
 
    }
}
