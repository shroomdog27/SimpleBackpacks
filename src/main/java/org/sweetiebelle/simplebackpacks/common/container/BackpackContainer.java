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
import org.sweetiebelle.simplebackpacks.common.container.slot.BackpackSlot;
import org.sweetiebelle.simplebackpacks.common.container.slot.DiamondSlotHandler;
import org.sweetiebelle.simplebackpacks.common.container.slot.IronSlotHandler;
import org.sweetiebelle.simplebackpacks.common.container.slot.NetheriteSlotHandler;
import org.sweetiebelle.simplebackpacks.common.container.slot.SlotHandler;
import org.sweetiebelle.simplebackpacks.common.inventory.InventoryProvider;
import org.sweetiebelle.simplebackpacks.common.sounds.BackpackSounds;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BackpackContainer extends Container {

    private final IInventory inventory;
    private final BackpackType backpackType;
    private InventoryProvider provider;

    public static BackpackContainer createLeatherContainer(int windowId, PlayerInventory playerInventory) {
        return new BackpackContainer(BackpackContainerTypes.LEATHER_BACKPACK.get(), windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.LEATHER), BackpackType.LEATHER);
    }

    public static BackpackContainer createLeatherContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.LEATHER_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.LEATHER);
    }

    public static BackpackContainer createIronContainer(int windowId, PlayerInventory playerInventory) {
        return new BackpackContainer(BackpackContainerTypes.IRON_BACKPACK.get(), windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.IRON), BackpackType.IRON);
    }

    public static BackpackContainer createIronContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.IRON_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.IRON);
    }

    public static BackpackContainer createGoldContainer(int windowId, PlayerInventory playerInventory) {
        return new BackpackContainer(BackpackContainerTypes.GOLD_BACKPACK.get(), windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.GOLD), BackpackType.GOLD);
    }

    public static BackpackContainer createGoldContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.GOLD_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.GOLD);
    }

    public static BackpackContainer createDiamondContainer(int windowId, PlayerInventory playerInventory) {
        return new BackpackContainer(BackpackContainerTypes.DIAMOND_BACKPACK.get(), windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.DIAMOND), BackpackType.DIAMOND);
    }

    public static BackpackContainer createDiamondContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.DIAMOND_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.DIAMOND);
    }

    public static BackpackContainer createNetheriteContainer(int windowId, PlayerInventory playerInventory) {
        return new BackpackContainer(BackpackContainerTypes.NETHERITE_BACKPACK.get(), windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.NETHERITE), BackpackType.NETHERITE);
    }

    public static BackpackContainer createNetheriteContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.NETHERITE_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.NETHERITE);
    }

    public BackpackContainer(ContainerType<? extends BackpackContainer> containerType, int windowId, PlayerInventory playerInventory, InventoryProvider provider, BackpackType backpackType) {
        super(containerType, windowId);
        this.provider = provider;
        this.inventory = provider.getInventory();
        assertInventorySize(inventory, backpackType.size);

        this.backpackType = backpackType;

        inventory.openInventory(playerInventory.player);

        SlotHandler slotHandler;
        switch (backpackType) {
            case NETHERITE: {
                slotHandler = new NetheriteSlotHandler(provider.getInventory(), playerInventory, backpackType);
                break;
            }
            case DIAMOND: {
                slotHandler = new DiamondSlotHandler(provider.getInventory(), playerInventory, backpackType);
                break;
            }
            default: {
                slotHandler = new IronSlotHandler(provider.getInventory(), playerInventory, backpackType);
                break;
            }
        }
        for (BackpackSlot slot : slotHandler.getSlots())
            addSlot(slot);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < backpackType.size) {
                if (!mergeItemStack(itemstack1, backpackType.size, inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!mergeItemStack(itemstack1, 0, backpackType.size, false))
                return ItemStack.EMPTY;

            if (itemstack1.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        inventory.closeInventory(playerIn);
        provider.setInventory((Inventory) inventory);
        provider.save();
        playerIn.playSound(BackpackSounds.CLOSE_SOUND.get(), 1.0F, 1.0F);

    }

    @OnlyIn(Dist.CLIENT)
    public BackpackType getChestType() {
        return backpackType;
    }

}
