package com.khanhtypo.tothemoon.data.c;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.TabInstance;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.utls.AppendableComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModLanguageGenerator extends LanguageProvider {
    public static final Map<String, String> DEFAULT_TRANSLATION_MAP = new TreeMap<>();
    public static final AppendableComponent MOD_NEEDS_INSTALLATION = AppendableComponent.create("tooltip", "mod_needs_installation", "Install %s.");
    public static final AppendableComponent TOGGLE = AppendableComponent.create("button", "Click To Toggle %s");
    public static final AppendableComponent FUEL_LEFT = AppendableComponent.create("tooltip", "burning_fuel_left", "Fuel Left : %s%");
    public static final AppendableComponent ENERGY_TOOLTIP = AppendableComponent.create("tooltip", "energy", "Energy : %s FE / %s FE");
    public static final AppendableComponent ACTION_TIME = AppendableComponent.create("tooltip", "%s in : %s seconds");
    public static final AppendableComponent REDSTONE_MODE = AppendableComponent.create("tooltip", "redstone_mode", "Redstone Mode : %s");
    public static final AppendableComponent STORING_FLUID = AppendableComponent.create("tooltip", "fluid_storing", "Storing Fluid : %s");
    public static final AppendableComponent STORING_AMOUNT_FLUID = AppendableComponent.create("tooltip", "fluid_storing_amount", "Fluid Storage : %smB / %smb");
    public static final AppendableComponent UPGRADE_GENERATOR_ITEM = AppendableComponent.create("tooltip", "generator_upgradable_item", "- Can be applied in %s").withStyle(ChatFormatting.DARK_GRAY);
    public static final Component TOGGLE_UPGRADE_BOX = createTranslatable("gui", "toggle_upgrade_box", "Toggle Upgrade Box");
    public static final Component POWER_GENERATOR_GENERAL = createTranslatable("tooltip", "general_power_generator", "Power Generator").withStyle(Style.EMPTY.withColor(Color.ORANGE.getRGB()));
    public static final Component TITLE_BLACK_STONE_FURNACE = createTranslatable("gui", "blackstone_furnace", "Blackstone Furnace");
    public static final Component REDSTONE_IGNORED = createTranslatable("tooltip", "redstone_mode_ignored", "Ignore Redstone");
    public static final Component REDSTONE_IGNORED_DESC = createTranslatable("tooltip", "redstone_mode_ignored.description", " - Redstone has no effect on machine").withStyle(ChatFormatting.DARK_GRAY);
    public static final Component REDSTONE_REQUIRED = createTranslatable("tooltip", "redstone_mode_required", "Requires Redstone");
    public static final Component REDSTONE_REQUIRED_DESC = createTranslatable("tooltip", "redstone_mode_required.description", " - Machine can only function when activated by redstone").withStyle(ChatFormatting.DARK_GRAY);
    public static final Component REDSTONE_STOP_MACHINE = createTranslatable("tooltip", "redstone_stops_machine", "Redstone Stop Machine");
    public static final Component REDSTONE_STOP_MACHINE_DESC = createTranslatable("tooltip", "redstone_stops_machine.description", " - Machine will be turned off when affected by redstone signal").withStyle(ChatFormatting.DARK_GRAY);
    public static final Component ON = createTranslatable("tooltip", "on", "On").withStyle(ChatFormatting.GREEN);
    public static final Component OFF = createTranslatable("tooltip", "off", "Off").withStyle(ChatFormatting.RED);
    public static final Component BACK = createTranslatable("tooltip", "exit", "Exit Screen");
    public static final String NO_CONTROLLER = createTranslatableKey("multiblock", "no_controller", "A controller is required.");
    public static final String FURNACE_TOO_MUCH_CONTROLLERS = createTranslatableKey("multiblock", "controller_overload", "Nether Brick Furnace can only has 1 controller");
    public static final String CONTROLLER_FRAME_IS_ERROR = createTranslatableKey("multiblock", "controller_pos_is_not_good.frame", "Controller must not be placed as frame.");
    public static final String CONTROLLER_FACE_ERROR = createTranslatableKey("multiblock", "controller_pos_is_not_good.face", "Controller must not be placed on top or at the bottom.");
    public static final String CONTROLLER_FACING_ERROR = createTranslatableKey("multiblock", "controller_face_is_not_good", "Controller must face outside");
    public static final String BLACKSTONE_ACCEPTOR_NOT_FRAME = createTranslatableKey("multiblock", "blackstone_acceptor_not_frame", "Blackstone Acceptor must be frame");
    public static final String NETHER_ACCEPTOR_NOT_FRAME = createTranslatableKey("multiblock", "nether_acceptor_not_face", "Nether Acceptor should not be the frame");
    public static final String FRAME_MUST_BE_BLACKSTONE_ACCEPTABLE = createTranslatableKey("multiblock", "frame_only_blackstone", "Frame must only has %s or %s");
    public static final String NETHER_BRICK_MISPLACED = createTranslatableKey("multiblock", "nether_brick_misplace", "Nether brick block or acceptor should be at the middle of a side");
    public static final String BLOCK_MISMATCHED = createTranslatableKey("multiblock", "block_mismatched", "Block mismatched.");

    public ModLanguageGenerator(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    public static String createTranslatableKey(String prefix, String suffix, String translated) {
        return ((TranslatableContents) createTranslatable(prefix, suffix, translated).getContents()).getKey();
    }

    public static MutableComponent createTranslatable(String prefix, String suffix, String translated) {
        String key = createKey(prefix, suffix);
        MutableComponent component = Component.translatable(key);
        DEFAULT_TRANSLATION_MAP.put(key, translated);
        return component;
    }

    public static String createKey(String prefix, String suffix) {
        return String.format("%s.%s.%s", prefix, ToTheMoon.MODID, suffix);
    }

    public static @NotNull String transform(String itemPath) {
        StringBuilder translationBuilder = new StringBuilder(itemPath.length());
        translationBuilder.append(Character.toUpperCase(itemPath.charAt(0)));
        for (int i = 1; i < itemPath.length(); i++) {
            if (itemPath.charAt(i) == '_') {
                translationBuilder.append(" ");
            } else if (i > 1 && itemPath.charAt(i - 1) == '_') {
                translationBuilder.append(Character.toUpperCase(itemPath.charAt(i)));
            } else translationBuilder.append(itemPath.charAt(i));
        }

        return translationBuilder.toString();
    }

    public static void addTranslationMapping(String key, String translation) {
        DEFAULT_TRANSLATION_MAP.put(key, translation);
    }

    @Override
    protected void addTranslations() {
        this.tabInstances();
        this.blockAndItem();
        this.extraTranslation();
    }

    private void extraTranslation() {
        DEFAULT_TRANSLATION_MAP.forEach(super::add);
    }

    private void blockAndItem() {
        this.registriesToLang(ModRegistries.ITEMS, super::addItem);
        this.registriesToLang(ModRegistries.BLOCKS, super::addBlock);
    }

    private <T> void registriesToLang(DeferredRegister<T> registry, BiConsumer<Supplier<T>, String> consumer) {
        registry.getEntries().forEach(
                t -> consumer.accept(t, transform(t.getId().getPath()))
        );
    }

    private void tabInstances() {
        TabInstance.ALL_TABS.forEach(tab -> super.add(tab.getTranslationKey(), tab.getTranslation()));
    }
}
