package com.Dice.Weather.Services;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class Pipeline {
    // these class used to add properties and configuration to the standfordcore nlp
    private static StanfordCoreNLP stanfordCoreNLP;
    private static Properties properties;
    private static String propertiesName="tokenize, ssplit, pos,  lemma, ner, parse, sentiment";

    private Pipeline(){

    }

    static {
        properties=new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    @Bean(value = "stanfordCoreNLP") // here creating single object to global point access
    public static  StanfordCoreNLP getPipeLine(){
        if(stanfordCoreNLP==null)
        {
            return stanfordCoreNLP=new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}
