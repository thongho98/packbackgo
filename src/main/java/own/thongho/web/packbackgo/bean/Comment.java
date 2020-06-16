package own.thongho.web.packbackgo.bean;

import lombok.Data;

@Data
public class Comment {
    private long id;
    private String date_created;
    private String post;
    private long user;
    private String content;
}
