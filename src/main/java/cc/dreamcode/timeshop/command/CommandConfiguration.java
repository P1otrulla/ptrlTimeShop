package cc.dreamcode.timeshop.command;

import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandConfiguration extends OkaeriConfig {

    @Comment("# Ustaw nazwy komend, argumenty oraz permisje:")
    public Map<String, Command> commands = ImmutableMap.of(
            "timeshop",
            new Command(
                    "timeshop",
                    Collections.singletonList("sklep"),
                    new ArrayList<>()
            ),

            "timeshopadmin",
            new Command(
                    "timeshopadmin",

                    Collections.singletonList("sklepadmin"),

                    Collections.singletonList("timeshop.admin"),

                    Arrays.asList(
                            new CommandArgument("add", "dodaj"),
                            new CommandArgument("remove", "usun"),
                            new CommandArgument("set", "ustaw"),
                            new CommandArgument("reload", "przeladuj")
                    )
            )
    );

    public static class Command extends OkaeriConfig {

        public String name;
        public List<String> aliases;
        public List<String> permissions;
        public List<CommandArgument> arguments = new ArrayList<>();

        public Command(String name, List<String> aliases, List<String> permissions, List<CommandArgument> arguments) {
            this.name = name;
            this.aliases = aliases;
            this.permissions = permissions;
            this.arguments = arguments;
        }

        public Command(String name, List<String> aliases, List<String> permissions) {
            this.name = name;
            this.aliases = aliases;
            this.permissions = permissions;
        }
    }

    public static class CommandArgument extends OkaeriConfig {

        public String name;
        public String alias;

        public CommandArgument(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }
    }
}
