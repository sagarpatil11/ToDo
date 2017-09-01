package com.bridgeit.todo.service;

import java.net.URI;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.daointerface.WebScraperDao;
import com.bridgeit.todo.model.WebScraper;
import com.bridgeit.todo.validation.UrlValidator;

@Service
public class WebScraperService 
{
	@Autowired
	WebScraperDao webScraperDao;
	
	public WebScraper createWebScraper(String description)
	{
		WebScraper webScraper=null;
		
		try
		{
			if(description != null)
			{
				String url=UrlValidator.isValidUrl(description);
				
				System.out.println("url: "+url);
				
				URI uri=new URI(url);
				
				String hostname=uri.getHost();
				
				String title=null;
				String imgurl=null;
				
				Document document=Jsoup.connect(url).get();
				
				Elements ogTitle=document.select("meta[property=og:title]");
				Elements ogImage=document.select("meta[property=og:image]");
				
				if (ogTitle != null) {
					title = ogTitle.attr("content");
				} else {
					title = document.text();
				}

//				metaOgImage = document.select("meta[property=og:image]");
				if (ogImage != null) {
					imgurl = ogImage.attr("content");
				}

				webScraper = new WebScraper();
				webScraper.setTitle(title);
				webScraper.setImgurl(imgurl);
				webScraper.setHostname(hostname);
				webScraper.setHosturl(url);
			}
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return webScraper;
	}
	
	@Transactional
	public void saveWebScraper(WebScraper webScraper)
	{
		webScraperDao.saveWebScraper(webScraper);
	}
	
	@Transactional(readOnly=true)
	public List<WebScraper> getWebScraper(int tid)
	{
		return webScraperDao.getWebScraper(tid);
	}

}
