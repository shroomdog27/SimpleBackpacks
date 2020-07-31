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

import static org.sweetiebelle.simplebackpacks.common.BackpackType.DIAMOND;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public class SlotHandler {

    private ArrayList<BackpackSlot> slots;
    private Inventory chestInventory;

    public SlotHandler(Inventory chestInventory, PlayerInventory playerInventory, BackpackType type) {
        this.chestInventory = chestInventory;
        slots = new ArrayList<BackpackSlot>(type.size + 36);
        if (type.equals(DIAMOND))
            addDiamondChestSlot();
        else
            addOtherChestSlot(type);

        int leftCol = (type.xSize - 162) / 2 + 2;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
                slots.add(new BackpackSlot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, type.ySize - (4 - playerInvRow) * 18 - 9));

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            slots.add(new BackpackSlot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, type.ySize - 23));
        }
    }

    private void addDiamondChestSlot() {
        for (int chestRow = 0; chestRow < DIAMOND.getRowCount(); chestRow++)
            for (int chestCol = 0; chestCol < DIAMOND.rowLength; chestCol++)
                slots.add(new BackpackSlot(chestInventory, chestCol + chestRow * DIAMOND.rowLength, 12 + chestCol * 18, 18 + chestRow * 18));
    }

    private void addOtherChestSlot(BackpackType type) {
        for (int chestRow = 0; chestRow < type.getRowCount(); chestRow++)
            for (int chestCol = 0; chestCol < type.rowLength; chestCol++)
                // The difference between this and the diamond is that xPosition is 8 + chestCol
                // * 18 here. Diamond is 12 + chestCol * 18. That 4 makes a big difference!
                slots.add(new BackpackSlot(chestInventory, chestCol + chestRow * type.rowLength, 8 + chestCol * 18, 18 + chestRow * 18));
    }

    public ArrayList<BackpackSlot> getSlots() {
        return slots;
    }
}
