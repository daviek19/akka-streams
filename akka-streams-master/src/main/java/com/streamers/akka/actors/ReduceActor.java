package com.streamers.akka.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.streamers.akka.messages.MapData;
import com.streamers.akka.messages.ReduceData;
import com.streamers.akka.messages.WordCount;
import java.util.HashMap;
import java.util.List;

public class ReduceActor extends AbstractLoggingActor {

    static public Props props() {
        return Props.create(ReduceActor.class, () -> new ReduceActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MapData.class, (i) -> {
                    MapData mapData = i;
                    log().info("Received message " + i.toString());
                    // reduce the incoming data and forward the result toMaster actor
                    getSender().tell(reduce(mapData.getDataList()), getSelf());
                })
                .matchAny(o -> log().info("received unknown message"))
                .build();
    }

    private ReduceData reduce(List<WordCount> wordCounts) {
        HashMap<String, Integer> reducedMap = new HashMap<>();

        wordCounts.forEach((wordCount) -> {
            if (reducedMap.containsKey(wordCount.getWord())) {
                Integer value = reducedMap.get(wordCount.getWord());
                value++;
                //Replace the value of the key.
                reducedMap.put(wordCount.getWord(), value);
            } else {
                reducedMap.put(wordCount.getWord(), 1);
            }
        });

        return new ReduceData(reducedMap);
    }

}
