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
package org.sweetiebelle.simplebackpacks.common.sounds;

import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import org.sweetiebelle.simplebackpacks.SimpleBackpacks;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class BackpackSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SimpleBackpacks.MODID);

    public static final RegistryObject<SoundEvent> OPEN_SOUND = register("open_backpack_sound", () -> new SoundEvent(new ResourceLocation(SimpleBackpacks.MODID, "open_backpack")));

    public static final RegistryObject<SoundEvent> CLOSE_SOUND = register("close_backpack_sound", () -> new SoundEvent(new ResourceLocation(SimpleBackpacks.MODID, "close_backpack")));

    private static final <T extends SoundEvent> RegistryObject<T> register(String name, Supplier<T> sup) {
        return SOUNDS.register(name, sup);
    }

}
