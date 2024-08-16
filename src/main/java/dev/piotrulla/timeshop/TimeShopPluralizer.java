package dev.piotrulla.timeshop;

import dev.piotrulla.timeshop.config.implementation.PluginConfig;
import eu.okaeri.pluralize.Pluralize;

import java.util.List;

public class TimeShopPluralizer {

    private final PluginConfig pluginConfig;

    public TimeShopPluralizer(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    public String pluralize(int amount) {
        List<String> currencyPlurals = this.pluginConfig.currencyPlurals;

        String one = currencyPlurals.get(0);

        if (one == null) {
            return "";
        }

        String two = currencyPlurals.get(1);
        if (two == null) {
            return Pluralize.pluralize(this.pluginConfig.currencyLang, amount, one);
        }

        String few = currencyPlurals.get(2);
        if (few == null) {
            return Pluralize.pluralize(this.pluginConfig.currencyLang, amount, one, two);
        }

        return Pluralize.pluralize(this.pluginConfig.currencyLang, amount, one, two, few);
    }
}
