/**
 * Modified MIT License (MIT)
 * 
 * Copyright (c) 2020 shroomdog27
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, and sublicense the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 **/
package org.sweetiebelle.simplebackpacks.client;

import org.sweetiebelle.simplebackpacks.SimpleBackpacks;
import org.sweetiebelle.simplebackpacks.common.container.BackpackContainer;
import org.sweetiebelle.simplebackpacks.common.container.BackpackContainerTypes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(value = Dist.CLIENT, modid = SimpleBackpacks.MODID, bus = Bus.MOD)
public class BackpacksClient {

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        // My gradle complains if we don't explicitly cast the IScreenFactory<BackpackContainer, BackpackScreen>.
        ScreenManager.registerFactory(BackpackContainerTypes.LEATHER_BACKPACK.get(), (IScreenFactory<BackpackContainer, BackpackScreen>) (container, inventory, title) -> new BackpackScreen(container, inventory, title, 8.0F));
        ScreenManager.registerFactory(BackpackContainerTypes.IRON_BACKPACK.get(), (IScreenFactory<BackpackContainer, BackpackScreen>) (container, inventory, title) -> new BackpackScreen(container, inventory, title, 8.0F));
        ScreenManager.registerFactory(BackpackContainerTypes.GOLD_BACKPACK.get(), (IScreenFactory<BackpackContainer, BackpackScreen>) (container, inventory, title) -> new BackpackScreen(container, inventory, title, 8.0F));
        ScreenManager.registerFactory(BackpackContainerTypes.DIAMOND_BACKPACK.get(), (IScreenFactory<BackpackContainer, BackpackScreen>) (container, inventory, title) -> new BackpackScreen(container, inventory, title, 30.0F));
        ScreenManager.registerFactory(BackpackContainerTypes.NETHERITE_BACKPACK.get(), (IScreenFactory<BackpackContainer, BackpackScreen>) (container, inventory, title) -> new BackpackScreen(container, inventory, title, 47.0F));
    }
}
