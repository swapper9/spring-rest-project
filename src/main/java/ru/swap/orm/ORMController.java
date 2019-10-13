package ru.swap.orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.swap.bean.User;

import java.util.List;

@Controller
public class ORMController {

    @Autowired
    private ORMService ormService;

    @RequestMapping(value = "/ormFindAllUsers", method= RequestMethod.GET)
    public ModelAndView ormFindAllUsers() {
        System.out.println("ORMController ormFindAllUsers is called");
        List<User> users = ormService.queryFindAllUsersJPA();
        return new ModelAndView("/orm/orm", "resultObject", users);
    }

    @RequestMapping(value = "/queryFindByIdUser/{id}", method = RequestMethod.GET)
    public ModelAndView queryFindByIdUser(@PathVariable("id") int id) {
        System.out.println("ORMController queryFindByIdUser is called");
        User user = ormService.queryFindUserById(id);
        return new ModelAndView("/orm/orm", "resultObject", user);
    }

    @RequestMapping(value = "/ormUpdateUser/iduser/{id}/enabled/{enabled}", method= RequestMethod.GET)
    public ModelAndView ormUpdateUser(
            @PathVariable(value="id") int id,
            @PathVariable(value="enabled") boolean enabled) {
        System.out.println("ORMController ormUpdateUser is called");
        boolean result = ormService.updateUser(id, enabled);
        return new ModelAndView("/orm/orm", "resultObject", result);
    }
    @RequestMapping(value = "/ormDeleteUser/iduser/{id}", method= RequestMethod.GET)
    public ModelAndView ormDeleteUser(@PathVariable(value= "id") int id) {
        System.out.println("ORMController jdbcDelete is called");
        boolean result = ormService.deleteUser(id);
        return new ModelAndView("/orm/orm", "resultObject", result);
    }

    @RequestMapping(value = "/ormInsertUser/username/{username}/password/{password}/enabled/{enabled}"
            , method= RequestMethod.GET)
    public ModelAndView ormInsertUser(
            @PathVariable(value="username") String username,
            @PathVariable(value="password") String password,
            @PathVariable(value="enabled") boolean enabled) {
        System.out.println("ORMController ormInsertUser is called");
        boolean result = ormService.insertUser(username, password, enabled);
        return new ModelAndView("/orm/orm", "resultObject", result);

    }
}
