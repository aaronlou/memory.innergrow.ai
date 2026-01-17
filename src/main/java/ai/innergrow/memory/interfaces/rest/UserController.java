package ai.innergrow.memory.interfaces.rest;

import ai.innergrow.memory.application.user.UserAppService;
import ai.innergrow.memory.domain.user.model.User;
import ai.innergrow.memory.interfaces.rest.dto.Result;
import ai.innergrow.memory.interfaces.rest.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Management", description = "用户管理相关接口")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserAppService userAppService;

    @Operation(summary = "用户注册", description = "创建新用户账号")
    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = userAppService.register(request.getUsername(), request.getEmail());
        return Result.success(user);
    }

    @Operation(summary = "用户登录", description = "验证用户身份并返回 JWT Token")
    @PostMapping("/login")
    public Result<String> login(@RequestParam String username) {
        return Result.success(userAppService.login(username));
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        return Result.success(userAppService.getUser(id));
    }
}
