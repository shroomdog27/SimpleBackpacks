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

package org.sweetiebelle.simplebackpacks.common;

import java.util.Locale;

import javax.annotation.Nullable;

import org.sweetiebelle.simplebackpacks.SimpleBackpacks;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public enum BackpackType implements IStringSerializable {

    LEATHER("leather_backpack", 18, 9, 175, 149, new ResourceLocation(SimpleBackpacks.MODID, "textures/gui/leather_container.png"), 256, 256),
    IRON("iron_backpack", 36, 9, 175, 185, new ResourceLocation(SimpleBackpacks.MODID, "textures/gui/iron_container.png"), 256, 256),
    GOLD("gold_backpack", 54, 9, 175, 221, new ResourceLocation(SimpleBackpacks.MODID, "textures/gui/gold_container.png"), 256, 256),
    DIAMOND("diamond_backpack", 77, 11, 219, 239, new ResourceLocation(SimpleBackpacks.MODID, "textures/gui/diamond_container.png"), 256, 256),
    NETHERITE("netherite_backpack", 104, 13, 255, 258, new ResourceLocation(SimpleBackpacks.MODID, "textures/gui/netherite_container.png"), 512, 512);

    public String name;
    public int size;
    public int rowLength;
    public int xSize;
    public int ySize;
    public ResourceLocation guiTexture;
    public int textureXSize;
    public int textureYSize;

    private BackpackType(int size, int rowLength, int xSize, int ySize, ResourceLocation guiTexture, int textureXSize, int textureYSize) {
        this(null, size, rowLength, xSize, ySize, guiTexture, textureXSize, textureYSize);
    }

    private BackpackType(@Nullable String name, int size, int rowLength, int xSize, int ySize, ResourceLocation guiTexture, int textureXSize, int textureYSize) {
        this.name = name == null ? name() : name;
        this.size = size;
        this.rowLength = rowLength;
        this.xSize = xSize;
        this.ySize = ySize;
        this.guiTexture = guiTexture;
        this.textureXSize = textureXSize;
        this.textureYSize = textureYSize;
    }

    public String getId() {
        return name().toLowerCase(Locale.ROOT);
    }
    
    @Override
    public String getString() {
        return I18n.format("item.simplebackpacks.%s", name());
    }

    public int getRowCount() {
        return size / rowLength;
    }
}
