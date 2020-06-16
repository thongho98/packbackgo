package own.thongho.web.packbackgo.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private String post_id;
    private String content;
}
