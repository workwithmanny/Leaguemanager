package ie.dbs.leaguemanager.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userAccountService.findAll());
        return "user/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("userForm", new CreateUserForm());
        model.addAttribute("roles", UserRole.values());
        return "user/create";
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("userForm") CreateUserForm userForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", UserRole.values());
            return "user/create";
        }

        try {
            userAccountService.createUser(userForm);
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue("username", "username.exists", exception.getMessage());
            model.addAttribute("roles", UserRole.values());
            return "user/create";
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/disable")
    public String disableUser(@PathVariable Long id) {
        userAccountService.setEnabled(id, false);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/enable")
    public String enableUser(@PathVariable Long id) {
        userAccountService.setEnabled(id, true);
        return "redirect:/admin/users";
    }
}