package net.GingkoTreeX.totem.features;


import net.GingkoTreeX.totem.utils.IMinecraft;
import net.GingkoTreeX.totem.utils.MessageUtils;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public abstract class Module implements IMinecraft {

    private final String name;
    private final Category category;
    private final String description;
    private double config;
    private String configName;
    private boolean enabled;
    private int keyBind;

    public Module(String name,
                  Category category,
                  String description,
                  @Nullable Integer defaultKeyBind /*如果没有绑定按键 则此处填null即可*/,
                  @Nullable String configName /*如果没有配置 则此处填null即可*/,
                  double defConfig /*如果没有配置 则此处填0即可*/) {
                    this.name = name;
                    this.category = category;
                    this.description = description;
                    if (defaultKeyBind!=null) {
                        this.keyBind = defaultKeyBind;
                    }
                    if (configName!=null){
                        this.configName = configName;
                    }
                    this.config=defConfig;
    }

    public static void onUpdateAll() {
        for (Module module : ModuleHackFramework.getInstance().getModules()) {
            module.onUpdate();
            module.onRender();
        }
    }

    public abstract void onUpdate();

    public abstract void onRender();

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getConfigName(){
        return configName;
    }

    public void setConfig(double config){
        this.config=config;
    }

    public double getConfig(){
        return config;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (mc.player != null) {
            this.enabled = enabled;
            if (enabled) {
                onEnable();
                MessageUtils.addGreenChatMessage("[+]" + this.getName() + " has been Enabled!");
            } else {
                onDisable();
                MessageUtils.addRedChatMessage("[-]" + this.getName() + " has been Disabled!");
            }
        }
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public boolean isModuleKeyDown(Module model) {
        // 如果 keyBind 为 0，则表示未绑定按键，直接返回 false
            keyBind = model.getKeyBind();
            if (keyBind == 0) {
                return false;
            }
            long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
            return GLFW.glfwGetKey(windowHandle, keyBind) == GLFW.GLFW_PRESS;
    }

    public boolean isKeyDown(int key){
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return GLFW.glfwGetKey(windowHandle, keyBind) == GLFW.GLFW_PRESS;
    }

    public void onEnable() {}

    public void onDisable() {}

}
