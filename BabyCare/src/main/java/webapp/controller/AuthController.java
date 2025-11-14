package webapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webapp.model.User;
import webapp.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String fullname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        try {
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("registerError", "Mật khẩu và tên tài khoản không khớp");
                return "redirect:/";
            }

            User u = new User();
            u.setFullname(fullname);
            u.setUsername(username);
            u.setEmail(email);
            u.setRole("USER");
            u.setPhone(phone);
            u.setPassword(password);

            User saved = authService.register(u);

            try {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
                Authentication auth = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                return "redirect:/";
            }

            try {
                if (saved != null && saved.getId() != null) {
                    session.setAttribute("USER_ID", saved.getId());
                }
            } catch (Exception ignore) {}

            return "redirect:/";
        } catch (IllegalArgumentException iae) {
            redirectAttributes.addFlashAttribute("registerError", iae.getMessage());
            return "redirect:/";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("registerError", "Đăng ký thất bại. Vui lòng thử lại.");
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/";
        }
        catch (Exception ex) {
            redirectAttributes.addFlashAttribute("loginError", "Sai tài khoản hoặc mật khẩu!");
            return "redirect:/";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        try {
            session.invalidate();
            SecurityContextHolder.clearContext();
        } catch (Exception ignore) {}
        return "redirect:/";
    }
}
