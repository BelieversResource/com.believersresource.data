package com.believersresource.data;

import java.io.File;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class TextSearch {
	
	public static String filePath = "d:\\temp\\lucene\\";

	public static int findMatch(String body)
    {
		try{
			File file=new File(filePath);
	        FSDirectory directory = FSDirectory.open(file);
	        IndexReader reader = IndexReader.open(directory, true);
	        Searcher searcher = new IndexSearcher(reader);
	        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_29);
	        QueryParser parser = new QueryParser(Version.LUCENE_29, "Body", analyzer);
	        Query query = parser.parse(body);
	        TopScoreDocCollector collector = TopScoreDocCollector.create(100, true);
	        searcher.search(query, collector);
	        ScoreDoc[] hits = collector.topDocs().scoreDocs;
	        ArrayList<Integer> productIds = new ArrayList<Integer>();
	        return Integer.parseInt(searcher.doc(hits[0].doc).get("Id"));
		} catch (Exception ex) { return 0;}
    }
	
}
