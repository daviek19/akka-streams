package com.streamers.akka.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.streamers.akka.messages.ReduceData;
import com.streamers.akka.messages.Result;
import java.util.HashMap;
import java.util.Map;

public class AggregateActor extends AbstractLoggingActor {

    static public Props props() {
        return Props.create(AggregateActor.class, () -> new AggregateActor());
    }

    private Map<String, Integer> finalReducedMap = new HashMap<String, Integer>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ReduceData.class, (i) -> {
                    ReduceData reduceData = i;
                    aggregateInMemoryReduce(reduceData.getReduceDataList());
                })
                .match(Result.class, (i) -> {
                    getSender().tell(finalReducedMap.toString(), getSelf());
                })
                .matchAny(o -> log().info("received unknown message"))
                .build();
    }

    private void aggregateInMemoryReduce(Map<String, Integer> reducedList) {
        Integer count = null;
        for (String key : reducedList.keySet()) {
            if (finalReducedMap.containsKey(key)) {
                count = reducedList.get(key)
                        + finalReducedMap.get(key);
                finalReducedMap.put(key, count);
            } else {
                finalReducedMap.put(key, reducedList.get(key));
            }
        }
    }

}
