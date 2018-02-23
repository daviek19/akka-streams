package com.streamers.akka.streams;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import com.streamers.akka.actors.MasterActor;
import akka.util.Timeout;
import com.streamers.akka.messages.Result;
import java.util.concurrent.CompletionStage;
import scala.concurrent.Await;

import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class Streams {

    public static void main(String[] argv) throws InterruptedException, Exception {
        Timeout timeout = new Timeout(Duration.fromNanos(5000 * 10000));
        ActorSystem _system = ActorSystem.create("MapReduceApp");
        ActorRef master = _system.actorOf(MasterActor.props(), "master");
        master.tell("The quick brown fox tried to jump over the lazy dog and fell on the dog", master);
        master.tell("Dog is man's best friend", master);
        master.tell("Dog and Fox belong to the same family", master);
        Thread.sleep(5000);

        Future<Object> future = Patterns.ask(master, new Result(), timeout);
        String result = (String) Await.result(future,timeout.duration());
        System.out.println(result);
        _system.stop(master);

    }
}
