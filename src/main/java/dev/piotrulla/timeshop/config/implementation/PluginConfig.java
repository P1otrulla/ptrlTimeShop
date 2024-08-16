package dev.piotrulla.timeshop.config.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.Arrays;
import java.util.List;

public class PluginConfig extends OkaeriConfig {

    @Comment({ "", "# Time in secounds for coin:" })
    public int currencyProgress = 300;

    @Comment({ "", "# How much coins for one progress:" })
    public int currencyValue = 5;

    @Comment({ "", "# Plural settings:" })
    public String currencyLang = "pl";
    public List<String> currencyPlurals = Arrays.asList("monete", "monety", "monet");
}
