package com.rebane2001.aimobs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AIMobsMod implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("aimobs");

    @Override
    public void onInitializeClient() {
        AIMobsConfig.loadConfig();
        ClientCommandRegistrationCallback.EVENT.register(AIMobsCommand::setupAIMobsCommand);
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!AIMobsConfig.config.enabled) return ActionResult.PASS;
            if (!player.isSneaking()) {
                if (entity.getId() == ActionHandler.entityId) ActionHandler.handlePunch(entity, player);
                return ActionResult.PASS;
            }
            ActionHandler.startConversation(entity, player);
            return ActionResult.FAIL;
        });
    }
}
