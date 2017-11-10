package com.alphaprogrammer.InformationRetrieval;

import com.google.common.base.Stopwatch;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.util.*;

/**
 * Created by younes on 6/11/17.
 */
class main {

    public static void main(String args[]) throws Exception {

        File fXmlFile = new File("filesIn/Text_Only_Ascii_Coll_MWI_NoSem");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("doc");
        System.out.println(nList.getLength());
        List<Document> documentList = new ArrayList<>();
        Map<String, String> idToBody = new HashMap<>();

        for (int i = 0; i < nList.getLength(); i++) {

            Node n = nList.item(i);
            NodeList nodeList = n.getChildNodes();
            // ok c moi
            String id = nodeList.item(0).getTextContent().toString();
            String body = nodeList.item(1).getTextContent().toString();

            Document document = new Document(body, id);
            documentList.add(document);

            idToBody.put(id, body);
        }

        Stopwatch sw = Stopwatch.createUnstarted();
        sw.start();
        TextSearchIndex index = SearchIndexFactory.buildIndex(documentList);
        sw.stop();

        System.out.println("finished building index took " + sw.toString());
        System.out.println("num documents: " + index.numDocuments());
        System.out.println("num terms: " + index.termCount());

        File fQueries = new File("filesIn/request.txt");
        // int j=0;
        // a enlever
        // for(j=0;j<1;j++){
        String string = "filesOut/textFiles/IbrahimAlexisKevinYouness_" + "03_" + 001
                        + "_bm25_" + "articles.text.k1.2b0.7" + ".txt";
        File sortiee = new File(string);

        LineIterator it = FileUtils.lineIterator(fQueries, "UTF-8");
        try {

            while (it.hasNext()) {
                String line = it.nextLine();
                sw.reset();
                sw.start();
                SearchResultBatch batch = index.search(line, 1500);
                sw.stop();
                System.out.println("printing results for query: " + line);
                int rank = 1;
                for (SearchResult result : batch.getSearchResults()) {
                    String[] arr = line.split(" ", 2);
                    String linee = arr[0] + "" + " Q0 " + result.getUniqueIdentifier() + " " + rank + " "
                                   + result.getBm25() + " " + "IbrahimAlexisKevinYouness" + " " + "/article[1]\n";
                    System.out.println("----------\n\n");
                    FileUtils.write(sortiee, linee, "UTF-8", true);
                    System.out.println("score = " + result.getRelevanceScoreCosine());
                    System.out.println("ltn = " + result.getLtn());
                    System.out.println("ltc = " + result.getLtc());
                    System.out.println("bm25 = " + result.getBm25());
                    rank++;
                }
                System.out.println("finished searching took: " + sw.toString());
                System.out.println("num documents searched: " + batch.getStats().getDocumentsSearched());

            }

            System.out.println("\n\nEnd the run was created (See the filesOut/textFiles)");
        }
        finally {
            LineIterator.closeQuietly(it);
        }

        // }

        /*
         * Scanner scanner = new Scanner(System.in);
         * String searchTerm = "";
         * while (!searchTerm.equalsIgnoreCase("EXIT")) {
         * System.out.print("Enter your search terms or type EXIT: ");
         * searchTerm = scanner.nextLine();
         * sw.reset();
         * sw.start();
         * SearchResultBatch batch = index.search(searchTerm, 3);
         * sw.stop();
         * System.out.println("printing results for term: " + searchTerm);
         * for (SearchResult result : batch.getSearchResults()) {
         * System.out.println("----------\n\n");
         * System.out.println("score = " + result.getRelevanceScore());
         * System.out.println(idToBody.get(result.getUniqueIdentifier().toString()));
         * }
         * System.out.println("finished searching took: " + sw.toString());
         * System.out.println("num documents searched: " + batch.getStats().getDocumentsSearched());
         * }
         */
    }
}
