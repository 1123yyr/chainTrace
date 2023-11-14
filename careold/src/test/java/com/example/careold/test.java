package com.example.careold;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CareoldApplication.class)
//@ContextConfiguration(locations = {"classpath:applicationContext*.xml"})
public class test {

 
    @Autowired
    SolrClient solrClient;
 
    @Test
    public void test1() throws IOException, SolrServerException {
        ContentStreamUpdateRequest up = new ContentStreamUpdateRequest(
                "/update/extract");
        File file = new File("C:/Users/linyw/Desktop/test.docx");
        up.addFile(file,"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        up.setParam("literal.file_name",file.getName());
        up.setParam("literal.file_path",file.getPath());
        up.setParam("literal.file_process",file.getName().substring(3));
        up.setParam("fmap.content", "file_text");
        up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
        solrClient.request(up);
        QueryResponse query = solrClient.query(new SolrQuery("*:*"));
        SolrDocumentList results = query.getResults();
        System.out.println(results);
    }
 
    @Test
    public void test2() throws IOException, SolrServerException {
        QueryResponse query = solrClient.query(new SolrQuery("file_keywords:*"));
        SolrDocumentList results = query.getResults();
        System.out.println(results);

    }

    @Test
    public void test3() throws IOException, SolrServerException {
        final SolrInputDocument doc = new SolrInputDocument();

        doc.addField("id", UUID.randomUUID().toString());
        doc.addField("name", "Amazon Kindle Paperwhite");
        final UpdateResponse updateResponse = solrClient.add("techproducts", doc);
// Indexed documents must be committed
        solrClient.commit("techproducts");
        System.out.println(updateResponse);
    }
}
