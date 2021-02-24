/*
 * MIT License
 *
 * Copyright (c) 2020-2021 shroomdog27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.sweetiebelle.simplebackpacks.common.container;

import java.util.ArrayList;

import org.sweetiebelle.simplebackpacks.common.BackpackType;
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

public class BackpackContainer extends Container {

    private final IInventory inventory;
    private final BackpackType backpackType;
    private InventoryProvider provider;

    public static BackpackContainer createLeatherContainer(int windowId, PlayerInventory playerInventory) {
        return createLeatherContainer( windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.LEATHER));
    }

    public static BackpackContainer createLeatherContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.LEATHER_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.LEATHER);
    }

    public static BackpackContainer createIronContainer(int windowId, PlayerInventory playerInventory) {
        return createIronContainer(windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.IRON));
    }

    public static BackpackContainer createIronContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.IRON_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.IRON);
    }

    public static BackpackContainer createGoldContainer(int windowId, PlayerInventory playerInventory) {
        return createGoldContainer(windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.GOLD));
    }

    public static BackpackContainer createGoldContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.GOLD_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.GOLD);
    }

    public static BackpackContainer createDiamondContainer(int windowId, PlayerInventory playerInventory) {
        return createDiamondContainer(windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.DIAMOND));
    }

    public static BackpackContainer createDiamondContainer(int windowId, PlayerInventory playerInventory, InventoryProvider provider) {
        return new BackpackContainer(BackpackContainerTypes.DIAMOND_BACKPACK.get(), windowId, playerInventory, provider, BackpackType.DIAMOND);
    }

    public static BackpackContainer createNetheriteContainer(int windowId, PlayerInventory playerInventory) {
        return createNetheriteContainer(windowId, playerInventory, InventoryProvider.createEmptyInventoryProvider(BackpackType.NETHERITE));
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

        getSlots(provider.getInventory(), playerInventory, backpackType, getOffset(backpackType)).forEach((slot) -> addSlot(slot));
    }

    private int getOffset(BackpackType type) {
        switch (type) {
            case LEATHER:
                return 8;
            case IRON:
                return 8;
            case GOLD:
                return 8;
            case DIAMOND:
                return 12;
            case NETHERITE:
                return 12;
            default:
                return 0;
        }
    }

    private ArrayList<BackpackSlot> getSlots(Inventory chestInventory, PlayerInventory playerInventory, BackpackType type, int slotOffset) {
        ArrayList<BackpackSlot> slots = new ArrayList<BackpackSlot>(type.size + 36);
        for (int chestRow = 0; chestRow < type.getRowCount(); chestRow++)
            for (int chestCol = 0; chestCol < type.rowLength; chestCol++)
                slots.add(new BackpackSlot(chestInventory, chestCol + chestRow * type.rowLength, slotOffset + chestCol * 18, 18 + chestRow * 18));

        int leftCol = (type.xSize - 162) / 2 + 2;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
                slots.add(new BackpackSlot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, type.ySize - (4 - playerInvRow) * 18 - 9));

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            slots.add(new BackpackSlot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, type.ySize - 23));
        }
        return slots;
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

    public BackpackType getChestType() {
        return backpackType;
    }

}
