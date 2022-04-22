package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = null;
        if ("GET".equals(req.httpRequestType())) {
            resp = get(req);
        }
        if ("POST".equals(req.httpRequestType())) {
            post(req);
        }
        return resp;
    }

    private Resp get(Req req) {
        String answer = queue.get(req.getSourceName()).poll();
        if (answer == null) {
            answer = "";
        }
        return new Resp(answer, !"".equals(answer) ? "200" : "204");
    }

    private void post(Req req) {
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        queue.get(req.getSourceName()).add(req.getParam());
    }
}
