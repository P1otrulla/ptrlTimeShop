package cc.dreamcode.timeshop.command.configurer;

import cc.dreamcode.timeshop.command.CommandConfiguration;
import dev.rollczi.litecommands.factory.CommandEditor;

public class CommandConfigurer implements CommandEditor {

    private final CommandConfiguration commandConfiguration;

    public CommandConfigurer(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public State edit(State state) {
        CommandConfiguration.Command command = this.commandConfiguration.commands.get(state.getName());

        if (command == null) {
            return state;
        }

        if (command.arguments.size() >= 1) {
            for (CommandConfiguration.CommandArgument argument : command.arguments) {
                state = state.editChild(argument.name, editor -> editor.name(argument.alias));
            }
        }

        return state.name(command.name)
                .aliases(command.aliases, true)
                .permission(command.permissions, true);

    }
}
