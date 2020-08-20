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

import org.sweetiebelle.simplebackpacks.common.BackpackType;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public class DiamondSlotHandler extends SlotHandler {

    public DiamondSlotHandler(Inventory chestInventory, PlayerInventory playerInventory, BackpackType type) {
        super(chestInventory, playerInventory, type);

    }

    @Override
    protected void addChestSlots() {
        for (int chestRow = 0; chestRow < type.getRowCount(); chestRow++)
            for (int chestCol = 0; chestCol < type.rowLength; chestCol++)
                slots.add(new BackpackSlot(chestInventory, chestCol + chestRow * type.rowLength, 12 + chestCol * 18, 18 + chestRow * 18));
    }

}
