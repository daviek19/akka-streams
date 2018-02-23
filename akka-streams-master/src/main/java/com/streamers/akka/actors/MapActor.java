package com.streamers.akka.actors;

/**
 * The work of this actor is to receive a string break it down to word count
 * then add the word counts into mapdata the return the map data to the calling
 * actor that is the master actor via the getSender()
 */
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import com.streamers.akka.messages.MapData;
import com.streamers.akka.messages.WordCount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MapActor extends AbstractLoggingActor {

    static public Props props() {
        return Props
                .create(MapActor.class, () -> new MapActor());
    }

    private final String[] stopWords
            = {"a", "am", "an", "and", "are", "as", "at",
                "be", "do", "go", "if", "in", "is", "it",
                "of", "on", "the", "to"};

    private final List<String> stopWordsList = Arrays.asList(stopWords);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, (s) -> {
                    String work = s;
                    // Map the words in the sentence.
                    //Send the result to MasterActor
                    log().info("received the message. " + s);
                    getSender().tell(evaluateExpression(work), getSelf());
                })
                .matchAny(o -> log().info("received unknown message"))
                .build();

    }

    private MapData evaluateExpression(String work) {
        List<WordCount> wordCounts = new ArrayList<WordCount>();
        StringTokenizer st = new StringTokenizer(work);
        while (st.hasMoreElements()) {
            String word = st.nextToken().toLowerCase();
            if (!stopWordsList.contains(word)) {
                //this is not a stop word
                wordCounts.add(new WordCount(word, Integer.valueOf(1)));
            }
        }
        return new MapData(wordCounts);
    }
}
