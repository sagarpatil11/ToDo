package com.bridgeit.todo.dao.daointerface;

import java.util.List;

import com.bridgeit.todo.model.WebScraper;

public interface WebScraperDao 
{
	public void saveWebScraper(WebScraper webScraper);
	
	public List<WebScraper> getWebScraper(int tid);
}
