package com.bridgeit.todo.dao.daointerface;

import java.util.List;

import com.bridgeit.todo.model.WebScraper;

public interface WebScraperDao 
{
	public void saveWebScraper(WebScraper webScraper);
	
	public List<WebScraper> getWebScraper(int tid);
	
	public WebScraper getWebScraperByHostUrl(String hosturl);
	
	public void deleteScraper(int id);
}
