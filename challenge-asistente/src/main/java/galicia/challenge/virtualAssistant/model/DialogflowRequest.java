package galicia.challenge.virtualAssistant.model;

public class DialogflowRequest {

    private QueryResult queryResult;

    public QueryResult getQueryResult() {
        return queryResult;
    }
    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

    public static class QueryResult {
        private String queryText;
     
        public QueryResult() {}
     
        public void setQueryText(String queryText) {
            this.queryText = queryText;
        }
        public String getQueryText() {
            return queryText;
        }
    }
}
