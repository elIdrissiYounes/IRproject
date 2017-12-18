package com.alphaprogrammer.InformationRetrieval;

import com.google.common.base.Stopwatch;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.util.*;

class XmlMain {
	
	private static int getOrder(Node node) {
		String name = node.getNodeName();
		int cpt = 1;
		while (node.getPreviousSibling() != null) {
			node = node.getPreviousSibling();
			if (node.getNodeName().equals(name)) {
				cpt++;
			}
		}
		return cpt;
	}

	private static String getXpath(Node node) {
		String path = "";
		while (node != null) {
			path = node.getNodeName() + "[" + getOrder(node) + "]" + "/" + path;
			node = node.getParentNode();
		}
		return path;
	}
    public static void main(String args[]) throws Exception {

        File dir = new File("filesIn/coll");
        File[] directoryListing = dir.listFiles();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        List<Document> documentList = new ArrayList<>();
        for (File fXmlFile : directoryListing) {

            
            org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
            XPathFactory xpf = XPathFactory.newInstance();
			XPath path = xpf.newXPath();

            doc.getDocumentElement().normalize();
            Element root=doc.getDocumentElement();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("article");

            Map<String, String> idToBody = new HashMap<>();

            Node n = nList.item(0);
            Element e = (Element) n;

            String id = e.getElementsByTagName("id").item(0).getTextContent().toString();
            System.out.println(id);
            //String body = e.getElementsByTagName("bdy").item(0).getTextContent().toString();
            boolean sectionsLeft = true;
			int sCpt = 1;
			loop: while (sectionsLeft) {
				String expression = "//sec[" + sCpt + "]";
				String content = (String) path.evaluate(expression, root);
				Node node = (Node) path.evaluate(expression, root, XPathConstants.NODE);
				content = content.trim().replaceAll(" +", " ");
				if (content.isEmpty() || content.equals(" "))
					break loop;
				else {
					String xpath = getXpath(node);
					StringBuilder xpathb = new StringBuilder(xpath);
					xpathb.setCharAt(21, '1');
					xpath = xpathb.toString();
					xpath = xpath.substring(13, xpath.length() - 1);
					Document document = new Document(content, id,xpath);
		            documentList.add(document);
				
		            idToBody.put(xpath, content);
		            sCpt++;
				}
				
				
			}
            

            

        }
        Stopwatch sw = Stopwatch.createUnstarted();
        sw.start();
        TextSearchIndex index = SearchIndexFactory.buildIndex(documentList);
        sw.stop();
        System.out.println("finished building index took: " + sw.toString());
        System.out.println("num documents: " + index.numDocuments());
        System.out.println("num terms: " + index.termCount());

        File fQueries = new File("filesIn/request.txt");
        // int j=0;
        // for(j=0;j<6;j++){
        String string = "filesOut/xmlFiles/IbrahimAlexisKevinYouness_" + "05_" + 111
                        + "_bm25_k1.2b0.5" + "articles" + ".txt";
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
                                   + result.getBm25() + " " + "IbrahimAlexisKevinYouness" + " " + result.getXpath()+"\n";
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
            System.out.println("\n\nEnd the run was created (See the filesOut/xmlFiles)");
        }
        finally {
            LineIterator.closeQuietly(it);
        }

        // }

    }
}
