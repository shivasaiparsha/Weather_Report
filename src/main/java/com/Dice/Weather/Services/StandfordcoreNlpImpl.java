package com.Dice.Weather.Services;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Service;

import java.util.List;


public class StandfordcoreNlpImpl {

    // This method will check weather the user provided name is place name or not
      public static boolean checkWeatherStringLocationOrNot(String placeName)
      {
          StanfordCoreNLP stanfordCoreNLP=Pipeline.getPipeLine();
          char ch[]=placeName.toCharArray();
          if((ch[0]>='a'&&ch[0]<='z'))
          {
              ch[0]=Character.toUpperCase(ch[0]);
          }
          String convetedPlacename =new String(ch); // to recognize the place names the first character should be capital
                                                     // Nouns starts with capital
          CoreDocument coreDocument=new CoreDocument(convetedPlacename);
          stanfordCoreNLP.annotate(coreDocument);
          List<CoreLabel> coreLabelList=coreDocument.tokens();
          CoreLabel coreLabel = coreLabelList.get(0);
          String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class); // ner stands named entity recognizer
          System.out.println("before execution "+ner);
          if(ner.equalsIgnoreCase("LOCATION") //  check weather the user  entered proper location name or city name or country
                  ||ner.equalsIgnoreCase("CITY")
                  || ner.equalsIgnoreCase("STATE_OR_PROVIENCE")
                  || ner.equalsIgnoreCase("COUNTRY")
                 ) return true;
          System.out.println(ner);
          return false;
      }
}
