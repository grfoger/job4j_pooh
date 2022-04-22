package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topic = new ConcurrentHashMap<>();

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
        topic.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
        topic.computeIfPresent(req.getSourceName(), (top, users) -> {
            users.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            return users;
        });
        String answer = topic.get(req.getSourceName()).get(req.getParam()).poll();
        if (answer == null) {
            answer = "";
        }
        return new Resp(answer, !"".equals(answer) ? "200" : "204");
    }

    private void post(Req req) {
        topic.computeIfPresent(req.getSourceName(), (top, users) -> {
            users.forEach((k, v) -> v.add(req.getParam()));
            return users;
        });
    }
}
