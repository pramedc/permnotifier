package permnotifier.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import permnotifier.domain.AbstractModel;

import com.google.common.base.Function;

@Service
public class SolrService {

	@Autowired
    SolrServer solrServer;

	public QueryResponse search(String queryString) {
        return search(new SolrQuery(queryString.toLowerCase()));
    }

	public QueryResponse search(SolrQuery query) {
        try {
            return solrServer.query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }

	public <T extends AbstractModel> void indexItem(T item, Function<T, SolrInputDocument> function) {
        List<T> lists = new ArrayList<>();
        lists.add(item);
        indexItems(lists, function);
    }

	@Async
    public <T extends AbstractModel> void indexItems(Iterable<T> items, Function<T, SolrInputDocument> function) {
        if (items == null || !items.iterator().hasNext()) {
        	// no item to process
        	return;
        }
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (T item : items) {
        	SolrInputDocument document = function.apply(item);
        	document.addField("id", generateIndexId(item));
            documents.add(document);
        }
        try {
            solrServer.add(documents);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Async
    public <T extends AbstractModel> void deleteIndex(T item) {
        try {
            solrServer.deleteById(generateIndexId(item));
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private <T extends AbstractModel> String generateIndexId(T item) {
		return item.getClass().getSimpleName().toLowerCase() + "_" + item.getId();
	}
	
}
