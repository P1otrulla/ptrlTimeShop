package cc.dreamcode.timeshop.command.argument;

import cc.dreamcode.timeshop.user.User;
import cc.dreamcode.timeshop.user.UserRepository;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Result;

import java.util.List;
import java.util.stream.Collectors;

public class UserArgument implements OneArgument<User> {

    private final UserRepository userRepository;

    public UserArgument(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<User, ?> parse(LiteInvocation invocation, String argument) {
        return this.userRepository
                .findAll()
                .stream()
                .filter(user -> user.name().equals(argument))
                .findFirst()
                .<Result<User, ?>>map(Result::ok)
                .orElse(Result.error());
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> Suggestion.of(user.name()))
                .collect(Collectors.toList());
    }
}
