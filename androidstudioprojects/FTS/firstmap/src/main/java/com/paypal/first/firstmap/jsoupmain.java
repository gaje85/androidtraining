package com.paypal.first.firstmap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by gajg on 2/4/2015.
 */
public class jsoupmain {

    public static void main(String s[]){
        try {
            Document doc = Jsoup.connect("https://www.eventbrite.sg/d/singapore--singapore/events/").get();
            //System.out.println(doc.toString());
            Elements elements = doc.getElementsByAttributeValue("itemprop","startDate");
            Elements elementsLocation = doc.getElementsByAttributeValue("itemprop","location");
            for (int i=0;i<elements.size();i++){
                //System.out.println(elements.get(i));
                Element element = elements.get(i);
                String dateValue = element.text();
                Element elementLocation = elementsLocation.get(i);
                String locationValue = elementLocation.text();
                System.out.println(dateValue + " == "+locationValue);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
