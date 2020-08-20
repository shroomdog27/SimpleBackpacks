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
package org.sweetiebelle.simplebackpacks.common.container;

import org.sweetiebelle.simplebackpacks.common.BackpackType;
import org.sweetiebelle.simplebackpacks.common.inventory.InventoryProvider;
import org.sweetiebelle.simplebackpacks.common.item.ItemBackpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class BackpackContainerProvider implements INamedContainerProvider {

    private BackpackType type;
    private InventoryProvider provider;
    private ItemStack item;

    public BackpackContainerProvider(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemBackpack))
            throw new IllegalArgumentException(stack.getItem().getClass() + " is not a ItemBackpack.");
        item = stack;
        this.type = ((ItemBackpack) stack.getItem()).getBackpackType();
        this.provider = InventoryProvider.getProviderFromStack(stack);
    }

    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        switch (type) {
            case NETHERITE:
                return BackpackContainer.createNetheriteContainer(windowId, playerInventory, provider);
            case DIAMOND:
                return BackpackContainer.createDiamondContainer(windowId, playerInventory, provider);
            case GOLD:
                return BackpackContainer.createGoldContainer(windowId, playerInventory, provider);
            case IRON:
                return BackpackContainer.createIronContainer(windowId, playerInventory, provider);
            case LEATHER:
                return BackpackContainer.createLeatherContainer(windowId, playerInventory, provider);
            default:
                throw new IllegalStateException("Unknown backpack type: " + type);

        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return item.getDisplayName();
    }

}
