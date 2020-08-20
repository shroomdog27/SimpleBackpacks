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
package org.sweetiebelle.simplebackpacks.common.container.slot;

import java.util.ArrayList;

import org.sweetiebelle.simplebackpacks.common.BackpackType;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public abstract class SlotHandler {

    protected final ArrayList<BackpackSlot> slots;
    protected final Inventory chestInventory;
    protected final BackpackType type;

    public SlotHandler(Inventory chestInventory, PlayerInventory playerInventory, BackpackType type) {
        this.chestInventory = chestInventory;
        this.type = type;
        slots = new ArrayList<BackpackSlot>(type.size + 36);
        addChestSlots();

        int leftCol = (type.xSize - 162) / 2 + 2;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
                slots.add(new BackpackSlot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, type.ySize - (4 - playerInvRow) * 18 - 9));

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            slots.add(new BackpackSlot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, type.ySize - 23));
        }
    }

    protected abstract void addChestSlots();


    public final ArrayList<BackpackSlot> getSlots() {
        return slots;
    }
}
