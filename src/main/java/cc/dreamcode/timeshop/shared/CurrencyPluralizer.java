package cc.dreamcode.timeshop.shared;

import cc.dreamcode.timeshop.config.PluginConfiguration;
import eu.okaeri.pluralize.Pluralize;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public final class CurrencyPluralizer {

    private final PluginConfiguration configuration;
    private final Logger logger;

    public CurrencyPluralizer(PluginConfiguration configuration, Logger logger) {
        this.configuration = configuration;
        this.logger = logger;
    }

    public String prularlize(int amount) {
        List<String> prulars = this.configuration.currencyPlurals;

        if (prulars.size() != 3) {
            this.logger.severe("Invalid currency pluarlize list size, using default");

            prulars = Arrays.asList("monete", "monety", "monet");
        }

        return Pluralize.pluralize(Locale.forLanguageTag("pl"), amount, prulars.get(0), prulars.get(1), prulars.get(2));
    }
}
