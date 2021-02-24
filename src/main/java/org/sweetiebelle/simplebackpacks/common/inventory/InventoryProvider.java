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

package org.sweetiebelle.simplebackpacks.common.inventory;

import javax.annotation.Nonnull;

import org.sweetiebelle.simplebackpacks.common.BackpackType;
import org.sweetiebelle.simplebackpacks.common.item.BackpackItems;
import org.sweetiebelle.simplebackpacks.common.item.ItemBackpack;
import com.google.common.base.Preconditions;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class InventoryProvider implements INBTSerializable<ListNBT> {

    private Inventory inventory;
    private ItemStack backpack;

    private InventoryProvider(ItemStack backpack, ItemBackpack backpackItem) {
        this.backpack = backpack;
        inventory = new Inventory(backpackItem.getBackpackType().size);

    }

    @Override
    public ListNBT serializeNBT() {
        ListNBT list = new ListNBT();
        for (int i = 0; i < inventory.getSizeInventory(); i++)
            list.add(inventory.getStackInSlot(i).serializeNBT());
        return list;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(@Nonnull Inventory inventory) {
        this.inventory = Preconditions.checkNotNull(inventory);
    }

    public void setItemStack(@Nonnull ItemStack backpack) {
        Preconditions.checkNotNull(backpack);
        Preconditions.checkArgument(backpack.getItem() instanceof ItemBackpack, "Item " + backpack.getItem().toString() + " is not an ItemBackpack");
        this.backpack = backpack;
        save();
    }

    public void save() {
        if (!backpack.hasTag())
            backpack.setTag(new CompoundNBT());
        CompoundNBT current = backpack.getTag();
        current.put("inventory", serializeNBT());
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        if (nbt == null)
            return;
        for (int i = 0; i < nbt.size(); i++) {
            inventory.setInventorySlotContents(i, ItemStack.read(nbt.getCompound(i)));
        }
    }

    public static InventoryProvider getProviderFromStack(ItemStack stack) {
        Preconditions.checkNotNull(stack);
        Item item = stack.getItem();
        if (!(item instanceof ItemBackpack))
            return null;
        ItemBackpack backpack = (ItemBackpack) item;
        InventoryProvider provider = new InventoryProvider(stack, backpack);
        if (!stack.hasTag())
            stack.setTag(new CompoundNBT());
        provider.deserializeNBT(stack.getTag().getList("inventory", 10));
        return provider;
    }

    public static InventoryProvider createEmptyInventoryProvider(BackpackType type) {
        // I feel like there's a better way to do this...
        switch (type) {
            case NETHERITE: {
                ItemBackpack netheriteBackpack = BackpackItems.NETHERITE_BACKPACK.get();
                return new InventoryProvider(new ItemStack(netheriteBackpack, 1), netheriteBackpack);
            }
            case DIAMOND: {
                ItemBackpack diamondBackpack = BackpackItems.DIAMOND_BACKPACK.get();
                return new InventoryProvider(new ItemStack(diamondBackpack, 1), diamondBackpack);
            }
            case GOLD: {
                ItemBackpack goldBackpack = BackpackItems.GOLD_BACKPACK.get();
                return new InventoryProvider(new ItemStack(goldBackpack, 1), goldBackpack);
            }
            case IRON: {
                ItemBackpack ironBackpack = BackpackItems.IRON_BACKPACK.get();
                return new InventoryProvider(new ItemStack(ironBackpack, 1), ironBackpack);
            }
            case LEATHER: {
                ItemBackpack leatherBackpack = BackpackItems.LEATHER_BACKPACK.get();
                return new InventoryProvider(new ItemStack(leatherBackpack, 1), leatherBackpack);
            }
            default:
                throw new IllegalArgumentException("Unknown type: " + type.getString());

        }
    }
}
