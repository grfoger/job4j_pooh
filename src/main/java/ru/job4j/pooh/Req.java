package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] lines = content.split(System.lineSeparator());
        String[] head = lines[0].split(" ");
        String[] pooh = head[1].split("/");
        String param = "";
        if (pooh.length > 3) {
            param = pooh[3];
        }
        if ("".equals(lines[lines.length - 2])) {
            param = lines[lines.length - 1];
        }
        return new Req(head[0], pooh[1], pooh[2], param);

    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }

}