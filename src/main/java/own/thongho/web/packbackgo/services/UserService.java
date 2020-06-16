package own.thongho.web.packbackgo.services;

import own.thongho.web.packbackgo.bean.User;

public interface UserService {
    public User createUser(User user);
    public String login(String username, String password);
}
