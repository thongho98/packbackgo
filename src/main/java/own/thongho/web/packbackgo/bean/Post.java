package own.thongho.web.packbackgo.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class Post {
    private long id;
    private List comments;
    private List post_medias;
    private String image_url;
    private String owner;
    private String date_created;
    private String title;
    private String slug;
    private String content;
    private String tags;
}
