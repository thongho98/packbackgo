package own.thongho.web.packbackgo.dto;

import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String content;
    private String tags;
    private String image_url;
}
