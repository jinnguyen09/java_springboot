package webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webapp.model.User;
import webapp.repository.UserRepository;

import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Path uploadPath = Paths.get("uploads/avatars");

    @Autowired
    public ProfileController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public String showProfile(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        String username = principal.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return "redirect:/login";

        model.addAttribute("user", optionalUser.get());
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("user") User formUser,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        if (principal == null) return "redirect:/login";

        String username = principal.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại.");
            return "redirect:/profile";
        }

        User user = optionalUser.get();

        user.setFullname(formUser.getFullname());
        user.setEmail(formUser.getEmail());
        user.setPhone(formUser.getPhone());
        user.setAddress(formUser.getAddress());
        user.setGender(formUser.getGender());
        user.setAge(formUser.getAge());

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String original = StringUtils.cleanPath(Objects.requireNonNull(avatarFile.getOriginalFilename()));
            try {
                String ext = "";
                int dotIndex = original.lastIndexOf('.');
                if (dotIndex >= 0) {
                    ext = original.substring(dotIndex);
                }

                String filename = UUID.randomUUID().toString() + ext;
                Path target = uploadPath.resolve(filename);

                Files.copy(avatarFile.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

                user.setAvatar(filename);

            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Không thể lưu ảnh đại diện.");
                return "redirect:/profile";
            }
        }

        try {
            user.setUpdated_at(LocalDateTime.now());
        } catch (Throwable ignored) {
        }

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/profile";
    }

    @PostMapping("/settings")
    public String updateSettings(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        if (principal == null) return "redirect:/login";

        String username = principal.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại.");
            return "redirect:/profile";
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu hiện tại không đúng.");
            return "redirect:/profile";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Xác nhận mật khẩu không khớp.");
            return "redirect:/profile";
        }

        if (newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới phải >= 6 ký tự.");
            return "redirect:/profile";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        try {
            user.setUpdated_at(LocalDateTime.now());
        } catch (Throwable ignored) {
        }
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công!");
        return "redirect:/profile";
    }
}
