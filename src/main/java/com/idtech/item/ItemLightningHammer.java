package com.idtech.item;

import com.idtech.BaseMod;

import net.minecraft.util.math.BlockPos;

public class ItemLightningHammer extends QuickItem {
	
    {
        name = "Lightning Hammer";
        texture = "lightninghammer";
        tab = BaseMod.exampleTab;
    }
    
    public void onRightClick() {
    	 BlockPos block = findBlockAtCursor(20);
         createLightningBolt(block);
          
         float explosionSize = 10;
         boolean destroysBlocks = true;
         createExplosion(block, explosionSize, destroysBlocks);
    }
}